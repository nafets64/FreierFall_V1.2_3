package Controller;

import Modell.Berechnung;
import Modell.Daten;
import Modell.MenueEintrag;
import View.Auswertungsbereich.Diagramm;
import View.ExperimentierBereich.ExpFeld;
import View.GUI;
import View.LaborBereich.Labor;
import View.LaborBereich.LaborWerte;
import View.LaborBereich.Labortisch;
import View.LaborBereich.Monitor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFileChooser;



/**
 * Diese Klasse enthält alle steuernden Aufgaben nach dem MVC-Modell Zu Beginn
 * wird die Berechnungsklasse und die GUI/View angelegt
 *
 * Version 05.01.20
 *
 * @author stefanscherle
 */
public final class Controller implements IExpService
{
    private static final Controller controller = new Controller();

    private boolean experimentAn;
    private int status;     // Status in dem sich das Program befindet beim Aufbau befindet
    private int statusSimulation;  // Status in der Simulation
    private int statusSimMax;      // Maximaler Statuswer, bedingt aus Berechnung

    private Timer timer;

    private final Berechnung berechnung;
    private MenueEintrag information;

    /**
     * Startet den Controller, der eine Benutzeroberfläche, eine Berechnung und
     * ein blinkendes Startfeld, in dem die Startwerte eingestellt werden
     * können, anlegt.
     */
    private Controller()
    {
        GUI gui = GUI.getGuiInstanz();
        berechnung = new Berechnung();

        //begruessung();    // Informationsfenster zur Begrüßung
        Monitor.getMonitorInstanz().startwerteSetzen();     //Blinkvarainte

    }

    /**
     * Ermöglicht die eingestellten Werte als Experimentieraufbau im
     * Experimentierfeld zu sehen.
     */
    @Override
    public void aufbau()
    {
        // Zuerst MÜSSEN die eingestellten Laborwerte  weitergereicht werden
        Labor.gibLaborInstanz().laborWerte();
        LaborWerte laborwerte = Labor.gibLaborInstanz().getLaborWerte();
        ExpFeld.getExpFeldInstanz().setLaborWerte(laborwerte);
        // DANN kann das Experiment aufgebaut werden
        ExpFeld.getExpFeldInstanz().aufbau();

        // Anlegen der Stardaten
        berechnung.setLaborWerte(Labor.gibLaborInstanz().getLaborWerte());
        // Berechnung mit Luftwiderstand auf Basis der Startdaten
        berechnung.berechneWerte();
        Diagramm.getDiagrammInstanz().setDatenTarnsfer(berechnung.getDatenTransfer());
        ExpFeld.getExpFeldInstanz().setDatenTarnsfer(berechnung.getDatenTransfer());
    }

    /**
     * Startet mit der Simulation.
     */
    @Override
    public void simulation()
    {
        statusSimMax = berechnung.getDatenTransfer().getDatenPunkte().size();
        // Startet die Simulation und die Auswertung im Diagramm (Synchroner Ablauf)
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if (experimentAn && statusSimulation < statusSimMax)
                {
                    Diagramm.getDiagrammInstanz().setStatusZaehler(statusSimulation);
                    Diagramm.getDiagrammInstanz().zeichneMesspunkte();
                    ExpFeld.getExpFeldInstanz().setStatusZaehler(statusSimulation);
                    ExpFeld.getExpFeldInstanz().simulation();
                    statusSimulation++;
                }
                if (statusSimulation == statusSimMax)
                {
                    Controller.gibInstanz().setExperimentAn(false);      //Timerunterbrechung im Controller
                    Labor.gibLaborInstanz().getStartButton().setText("Again");
                }
            }
        }, 0, 16);

    }

    /**
     * Schaltet die Simulation auf Pause.
     */
    private void experimentPause()
    {
        experimentAn = false;
    }

    /**
     * Beendet die Pause der Simulation.
     */
    private void experimentWeiter()
    {
        experimentAn = true;
    }

    /**
     * Beendet die Siumaltion.
     */
    private void experimentEnde()
    {
        timer.cancel();
    }

    /**
     * Aufbau des Experiments: Zuerst im Labor, dann auf dem Experimentierfeld.
     */
    public void laborAufbau()
    {
        // Bevor der Körper auf das Experimentierfeld kommt, werden die Einstellungen geprüft
        Monitor.getMonitorInstanz().pruefeEingabe();
        // Der Körper wird auf den Labortisch gestellt
        Labor.gibLaborInstanz().startRampe();
        if (status == 2)
        {
            // Das Experiment wird aufgebaut
            aufbau();
            // Veränderung der Zustände der dazugehörigen Komponenten          
            Labor.gibLaborInstanz().getHoeheTextField().setEnabled(false);
            Labor.gibLaborInstanz().getLuftwiderstandCheck().setEnabled(false);
            Labor.gibLaborInstanz().getFallschirmCheck().setEnabled(false);
            Labor.gibLaborInstanz().getAufbauButton().setEnabled(false);
        }
        if (status == 1)
        {
            // Veränderung der Zustände der dazugehörigen Combonenten
            Labor.gibLaborInstanz().getRadiusSlider().setEnabled(false);
            Labor.gibLaborInstanz().getPlanetenBox().setEnabled(false);
            Labor.gibLaborInstanz().getAufbauButton().setText(" GO ??");
            status++;
        }
        if (status == 0)
        {
            // Veränderung der Zustände der dazugehörigen Komponenten
            Monitor.getMonitorInstanz().startwerteAnzeigen();
            Labor.gibLaborInstanz().getAufbauButton().setText("Steady");
            Labor.gibLaborInstanz().getKoerperFormenBox().setEnabled(false);
            Labor.gibLaborInstanz().getMaterialBox().setEnabled(false);
            status++;
        }
    }

    /**
     * Controller-Steurung des Start-Buttons und den damit verbundenen
     * Veränderungen sowohl der Methoden und damit verbunden Zustandsänderungen
     * sowohl im Labor als auch im Experimentierfeld.
     */
    public void start()
    {
        String text = Labor.gibLaborInstanz().getStartButton().getText();
        switch (text)
        {
            case "Start":
                experimentAn = true;
                simulation();
                Labor.gibLaborInstanz().getStartButton().setText("Pause");
                Labor.gibLaborInstanz().getStopButton().setEnabled(true);
                break;

            case "Pause":
                experimentPause();
                Labor.gibLaborInstanz().getStartButton().setText("Weiter");
                break;

            case "Weiter":
                experimentWeiter();
                Labor.gibLaborInstanz().getStartButton().setText("Pause");
                Labor.gibLaborInstanz().getStopButton().setText("Stop");
                break;

            case "Again":
                timer.cancel();
                statusSimulation = 0;
                Diagramm.getDiagrammInstanz().setStatusZaehler(0);
                ExpFeld.getExpFeldInstanz().setStatusZaehler(0);
                experimentAn = true;
                simulation();
                Labor.gibLaborInstanz().getStartButton().setText("Pause");
                break;

            default:
                System.out.println("Start: Kein gültiger Status");
                break;
        }
    }

    /**
     * Steurung des Stop-Buttons und den damit verbundenen Veränderungen.
     */
    public void stop()
    {
        if ("ENDE".equals(Labor.gibLaborInstanz().getStopButton().getText()))
        {
            experimentEnde();
            Labor.gibLaborInstanz().getStartButton().setEnabled(false);
            Labor.gibLaborInstanz().getStopButton().setEnabled(false);
            Labor.gibLaborInstanz().getAufbauButton().setEnabled(false);
        }
        else
        {
            experimentPause();
            Labor.gibLaborInstanz().getStartButton().setText("Weiter");
            Labor.gibLaborInstanz().getStopButton().setText("ENDE");
        }

    }

    /**
     * Diese Methode öffnet und erstelllt bei Bedarf einen Frame der Menüklasse,
     * der ein Inforamtionsfenster für die Beschreibung der Software öffnet.
     */
    public void begruessung()
    {
        if (information != null)
        {
            information.dispose();
        }
        information = new MenueEintrag(1);
        information.beschreibung();
    }

    /**
     * Diese Methode öffnet und erstelllt bei Bedarf einen Frame der Menüklasse,
     * der ein Inforamtionsfenster für die Version der Software öffnet.
     */
    public void version()
    {
        if (information != null)
        {
            information.dispose();
        }
        information = new MenueEintrag(2);
        information.version();
    }

    /**
     * Diese Methode öffnet und erstelllt bei Bedarf einen Frame der Menüklasse,
     * der ein Inforamtionsfenster öffnet, dass den Benutzer informiert.
     *
     * @param fall Informationsfall
     */
    public void dialog(int fall)
    {
        if (information != null)
        {
            information.dispose();
        }
        information = new MenueEintrag(3);
        information.dialog(fall);
    }

    /**
     * Start eines neuen Experimentes mit neu eingestellbarem Körper.
     */
    public void neu()
    {
        System.out.println("Reset");
        // Controller Grundzustand:
        status = 0;
        statusSimulation = 0;
        experimentAn = false;
        // Berechnung leeeren und Grundzustand herstellen
        berechnung.datenReset();
        // GUI - Bereiche werden in den Grundzustand zurück versetzt
        Diagramm.getDiagrammInstanz().grundZustand();
        Labor.gibLaborInstanz().grundZustand();
        Labortisch.getLabortischInstanz().grundZustand();
        Monitor.getMonitorInstanz().grundZustand();
        ExpFeld.getExpFeldInstanz().grundZustand();
        // Bei Neustart muss die Blinkvariante der Einstellungen im Labor initialisiert werden
        Monitor.getMonitorInstanz().startwerteSetzen();

    }

    /**
     * Speichrt die in der Berechnungskasse berechneten Simulationsdaten
     *
     * @throws FileNotFoundException -> Fehlermeldung wenn Pfad nicht gefunden
     * @throws IOException -> Fehlermeldung
     */
    public void speichern() throws FileNotFoundException, IOException
    {
        JFileChooser fenster = new JFileChooser();
        fenster.showSaveDialog(null);

        ObjectOutputStream os;
        os = new ObjectOutputStream(new FileOutputStream(fenster.getSelectedFile()));
        os.writeObject(Labor.gibLaborInstanz().getLaborWerte());
        os.close();
    }

    /**
     * Öffnet ein bereits durchgeführtes Experiment, soll heißen es werden alle
     * damit zusammenhängende Laborwerte der Körepereisnstellungen und den
     * berechneten Simulationsdaten als Input-Stream geladen
     *
     * @throws FileNotFoundException -> Fehlermeldung wenn Pfad nicht gefunden
     * @throws IOException -> Fehlermeldung wenn Pfad nicht gefunden
     * @throws ClassNotFoundException -> Fehlermeldung wenn Pfad nicht gefunden
     */
    public void oeffnen() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        // GUI - Bereiche werden in den Grundzustand zurück versetzt
        Diagramm.getDiagrammInstanz().grundZustand();
        Labor.gibLaborInstanz().grundZustand();
        Monitor.getMonitorInstanz().grundZustand();
        ExpFeld.getExpFeldInstanz().grundZustand();

        // Die alten gespeicherten Laborwerte werden geladen
        JFileChooser fenster = new JFileChooser();
        fenster.showOpenDialog(null);
        List<Daten> datenPunkte;
        ObjectInputStream is;
        is = new ObjectInputStream(new FileInputStream(fenster.getSelectedFile()));
        LaborWerte laborwerte = (LaborWerte) is.readObject();
        Labor.gibLaborInstanz().setLaborWerte(laborwerte);

        Monitor.getMonitorInstanz().setLaborWerte(laborwerte);
        Monitor.getMonitorInstanz().oeffnenZustand();
        Labor.gibLaborInstanz().getAufbauButton().setEnabled(false);
        ExpFeld.getExpFeldInstanz().setLaborWerte(laborwerte);
        Labor.gibLaborInstanz().startRampe();
        aufbau();
        System.out.println("erfolgreich geöffnet");
        is.close();
    }

    /**
     * Gibt die Singelton-Instanz des Controller zurück
     *
     * @return Singelton des Controllers
     */
    public static Controller gibInstanz()
    {
        return controller;
    }

    /**
     * Boolean der angibt, ob das Experiment/Simulation läuft.
     *
     * @param experimentAn Inforamtion ob Experiment läuft
     */
    public void setExperimentAn(boolean experimentAn)
    {
        this.experimentAn = experimentAn;
    }

    /**
     * In welchem Aufbau-Zustand befindet sich der Controller
     *
     * @return Aufbau-Zustand in dem der Controller sich befindet
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Setzt den Aufbau-Zustand des Controller
     *
     * @param status setzt den Aufbau-Zustand des Controllers
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Erstellt eine GUI fur den FAll zweier Körper.
     */
    public void zweiKoerper()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
