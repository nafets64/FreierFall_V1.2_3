package View.LaborBereich;

import Controller.Controller;
import Controller.IWerteTransfer;
import Controller.Koerper;
import Controller.Material;
import View.LaborBereich.SwingKomponenten.Button;
import View.LaborBereich.SwingKomponenten.CheckBox;
import View.LaborBereich.SwingKomponenten.ComboBox;
import View.LaborBereich.SwingKomponenten.Label;
import View.LaborBereich.SwingKomponenten.TextField;
import java.awt.Color;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JTextArea;

/**
 * Diese Klasse übernimmt die Anzeige und Aktivitäten, die durch den Benutzer im
 * Labor vorgenommen werden
 *
 * @author stefanscherle
 */
public class Monitor implements IWerteTransfer
{
    private static final Monitor monitorInstanz = new Monitor();

    private final Button aufbauButton;
    private final JTextArea startdatenTextArea;
    private final Label radiusLabel;
    private final JSlider radiusSlider;
    private final TextField radiusTextfield;
    private final ComboBox koerperFormenBox;
    private final ComboBox materialBox;
    private final ComboBox planetenBox;
    private final CheckBox luftwiderstandCheck;
    private final CheckBox fallschirmCheck;
    private final TextField hoeheTextField;

    private Timer timer1;
    private final List<JComponent> componenten;
    private LaborWerte laborwerte;

    /**
     * Konstruktor der die Swingcomponenetn die im Labor angelegt sind
     * initialisierrt, soll heissen die Swingcoboneneten vom Labor wurden
     * weitergereicht.
     */
    private Monitor()
    {
        timer1 = new Timer();

        this.aufbauButton = Labor.gibLaborInstanz().getAufbauButton();
        this.startdatenTextArea = Labor.gibLaborInstanz().getStartdatenTextArea();
        this.radiusSlider = Labor.gibLaborInstanz().getRadiusSlider();
        this.koerperFormenBox = Labor.gibLaborInstanz().getKoerperFormenBox();
        this.materialBox = Labor.gibLaborInstanz().getMaterialBox();
        this.planetenBox = Labor.gibLaborInstanz().getPlanetenBox();
        this.hoeheTextField = Labor.gibLaborInstanz().getHoeheTextField();
        this.radiusTextfield = Labor.gibLaborInstanz().getRadiusTextfield();
        this.luftwiderstandCheck = Labor.gibLaborInstanz().getLuftwiderstandCheck();
        this.fallschirmCheck = Labor.gibLaborInstanz().getFallschirmCheck();
        this.radiusLabel = Labor.gibLaborInstanz().getRadiusLabel();

        componenten = Labor.gibLaborInstanz().getComponenten();
    }

    /**
     * Zeigt den Radius bei Veränderung durch Slider in einem Textfeld an.
     */
    public void setRadius()
    {
        startwerteAnzeigen();
        radiusTextfield.setText("" + radiusSlider.getValue());
        ausgewaelt(radiusLabel);
    }

    /**
     * Blinkende Anzeigetafel mit der Aufforderung Körperdaten zu wählen.
     */
    public void startwerteSetzen()
    {
        aufbauButton.setEnabled(false);
        // Timer steurt das Aufblinken der Textaera
        timer1.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                startdatenTextArea.setFont(new java.awt.Font("Lucida Grande", 1, 20));
                startdatenTextArea.setText("     Wählen Sie " + "\n");
                startdatenTextArea.append("    Ihren Körper" + "\n");
                try
                {
                    combonentenBlinken(Color.black);
                    startdatenTextArea.setBackground(Color.red);
                    Thread.sleep(500);
                    combonentenBlinken(Color.red);
                    startdatenTextArea.setBackground(Color.white);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Labor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }, 0, 2000);
    }

    /**
     * Anzeigetafel mit den ausgewählten Körperdaten.
     */
    public void startwerteAnzeigen()
    {
        timer1.cancel();
        double radius = radiusSlider.getValue();
        String form = koerperFormenBox.getSelectedItem().toString();
        String material = materialBox.getSelectedItem().toString();
        String planet = planetenBox.getSelectedItem().toString();
        startdatenTextArea.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        startdatenTextArea.setText("Form: " + "\t" + form + "\n");
        startdatenTextArea.append("Material: " + "\t" + material + "\n");
        startdatenTextArea.append("Radius: " + "\t" + radius / 20 + " cm");
    }

    /**
     * Entfernt die ausgewählten Swingkomponenten der Körperdaten aus dem
     * Blinkmodus und schatltet den AufbauButton frei
     *
     * @param component zu entfernende Componente
     */
    public void ausgewaelt(JComponent component)
    {
        // Entfernt die ausgewählten Swingkomponenten der Körperdaten aus dem Blinkmodus
        component.setForeground(Color.BLACK);
        componenten.remove(component);
        if (koerperFormenBox.getSelectedItem() != Koerper.getKoerperAuswahl()[0]
                && materialBox.getSelectedItem() != Material.getMaterialAuswahl()[0])
        {
            aufbauButton.setEnabled(true);
        }
        else
        {
            aufbauButton.setEnabled(false);
        }
    }

    /**
     * Überprüfung, ob die gewählten Körperdaten sinnvoll sind.
     *
     */
    public void pruefeEingabe()
    {
        int status = Controller.gibInstanz().getStatus();
        if (status >= 1 && planetenBox.getSelectedItem() == "Planet")
        {
            System.out.println("Treffen Sie ihre Planetenauswahl");
            Controller.gibInstanz().dialog(1);
            Controller.gibInstanz().setStatus(0);
        }
        try
        {
            int hoehe = Integer.parseInt(hoeheTextField.getText());
            if (hoehe < 0 || hoehe > 8890)
            {
                System.out.println("Bitte geben Sie eine sinnvolle Höhenmeterangabe ein.");
                Controller.gibInstanz().dialog(2);
                Controller.gibInstanz().setStatus(0);
            }
            else if (status == 1)
            {
                System.out.println("Der Körper wird auf die Höhe " + hoehe + " hm transportiert.");
            }
        }
        catch (NumberFormatException hoehe)
        {
            System.out.println("Bitte geben Sie eine gültige Höhenmeterangabe als Zahl ein.");
            Controller.gibInstanz().dialog(3);
            Controller.gibInstanz().setStatus(0);
        }
    }

    /**
     * Blinkende Körperdaten die noch nicht ausgewählt wurden.
     *
     * @param color Blinkfarbe
     */
    public void combonentenBlinken(Color color)
    {

        for (JComponent component : componenten)
        {
            if (component.isEnabled())
            {
                component.setForeground(color);
            }
        }
    }

    /**
     * Singelton der MonitorKlasse
     *
     * @return SingeltonInastanz Monitor
     */
    public static Monitor getMonitorInstanz()
    {
        return monitorInstanz;
    }

    /**
     * Bei Neustart wird Grundzustand des Monitors hergestellt.
     */
    public void grundZustand()
    {
        timer1.cancel();
        timer1 = new Timer();
    }

    /**
     * Setzt die Werte im Laborbereich so, dass sie passend zu den eingelesen
     * Werten sind
     */
    public void oeffnenZustand()
    {
        radiusSlider.setValue(laborwerte.getRadius());
        koerperFormenBox.setSelectedItem(laborwerte.getKoerper());
        materialBox.setSelectedItem(laborwerte.getMaterial());
        planetenBox.setSelectedItem(laborwerte.getPlanet());
        luftwiderstandCheck.setSelected(laborwerte.isLuftwiderstandAn());
        fallschirmCheck.setSelected(laborwerte.isFallschirm());
    }

    @Override
    public LaborWerte getLaborWerte()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLaborWerte(LaborWerte laborwerte)
    {
        this.laborwerte = laborwerte;
    }
}
