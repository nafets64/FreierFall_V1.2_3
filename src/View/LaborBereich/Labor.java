package View.LaborBereich;

import View.LaborBereich.SwingKomponenten.Label;
import View.LaborBereich.SwingKomponenten.ComboBox;
import View.LaborBereich.SwingKomponenten.CheckBox;
import View.LaborBereich.SwingKomponenten.Button;
import Controller.Controller;
import Controller.IWerteTransfer;
import Controller.Koerper;
import Controller.Material;
import Controller.Planet;
import View.LaborBereich.SwingKomponenten.TextField;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

/**
 * Diese Klasse legt das Layout des Labors fest Hier werden sämtliche
 * Einsellungsmöglichkeiten für die Startdaten festgelegt
 *
 * Version 3.01.21
 *
 * @author stefanscherle
 */
public class Labor extends JPanel implements IWerteTransfer
{
    private static Labor laborInstanz = new Labor();
    private Label labor;

    private Button startButton;
    private Button stopButton;

    private Label startdatenLabel;
    private Button aufbauButton;
    private Label radiusLabel;
    private JSlider radiusSlider;
    private TextField radiusTextfield;
    private ComboBox koerperFormenBox;
    private ComboBox materialBox;
    private ComboBox planetenBox;
    private CheckBox luftwiderstandCheck;
    private CheckBox fallschirmCheck;
    private Label hoeheLabel;
    private TextField hoeheTextField;

    private final Labortisch labortisch;

    private JTextArea startdatenTextArea;
    private JScrollPane jScrollPane1;

    private LaborWerte laborwerte;

    private final List<JComponent> componenten;

    /**
     * Laborbereich Konstruktor: Initialisierung von Labortisch,
     * KomponenetnListe für Anzeige, Swingcomponenten und das Layout
     * (Group-Layout).
     *
     */
    private Labor()
    {
        laborInstanz = this;
        labortisch = Labortisch.getLabortischInstanz();
        componenten = new LinkedList<>();

        swingComponenten();
        laborLayout();

        Monitor monitor = Monitor.getMonitorInstanz();
    }

    /**
     * Initialisierung der SwingKomponenten
     */
    private void swingComponenten()
    {
        Font fontG = new java.awt.Font("Lucida Grande", 1, 24);
        Font fontK = new java.awt.Font("Lucida Grande", 0, 16);
        setBackground(new java.awt.Color(204, 255, 204));

        // Initialisierung der Buttons im Labor
        startButton = new Button(fontG, "Start", e -> Controller.gibInstanz().start(), false);
        stopButton = new Button(fontG, "Stop", e -> Controller.gibInstanz().stop(), false);
        aufbauButton = new Button(fontG, "Ready?", e -> Controller.gibInstanz().laborAufbau(), true);

        // Initialisierung der Labels im Labor
        labor = new Label(fontG, "Labor", GroupLayout.Alignment.CENTER);
        startdatenLabel = new Label(fontG, "Startdaten", GroupLayout.Alignment.LEADING);
        radiusLabel = new Label(fontK, "Durchmesser in mm", GroupLayout.Alignment.TRAILING);
        hoeheLabel = new Label(fontK, "Fallhöhe in m ü.M.:", GroupLayout.Alignment.TRAILING);
        hoeheTextField = new TextField(fontG, "0", e -> ausgewaelt(hoeheLabel));

        // Initialisierung der ComboBoxen
        koerperFormenBox = new ComboBox(fontK, (Koerper.getKoerperAuswahl()), e -> ausgewaelt(koerperFormenBox));
        materialBox = new ComboBox(fontK, (Material.getMaterialAuswahl()), e -> ausgewaelt(materialBox));
        planetenBox = new ComboBox(fontK, (Planet.getPlanetenAuswahl()), e -> ausgewaelt(planetenBox));

        // Initialisierung der CheckBoxen
        luftwiderstandCheck = new CheckBox(fontK, "Luftwiderstand", e -> ausgewaelt(luftwiderstandCheck));
        fallschirmCheck = new CheckBox(fontK, "mit Fallschirm", e -> ausgewaelt(fallschirmCheck));

        // Anzeigefenster anlegen
        startdatenTextArea = new JTextArea();
        startdatenTextArea.setColumns(17);
        startdatenTextArea.setRows(6);
        jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(startdatenTextArea);

        //Radiuseinstellungsbereich
        radiusSlider = new JSlider(2, 200);
        radiusSlider.addChangeListener(e -> Monitor.getMonitorInstanz().setRadius());
        radiusTextfield = new TextField(fontK, "" + radiusSlider.getValue(), e -> ausgewaelt(radiusLabel));
        radiusTextfield.setEnabled(false);

        // List der Componenten die eingestellt werden
        addComponenten();
    }

    /**
     * anlegen des layout
     */
    private void laborLayout()
    {

        GroupLayout laborLayout = new GroupLayout(this);
        setLayout(laborLayout);
        laborLayout.setHorizontalGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(labor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(laborLayout.createSequentialGroup()
                        .addComponent(startButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopButton))
                .addGroup(laborLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(hoeheLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hoeheTextField))
                .addComponent(planetenBox)
                .addComponent(luftwiderstandCheck)
                .addComponent(fallschirmCheck)
                .addGroup(laborLayout.createSequentialGroup()
                        .addComponent(startdatenLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aufbauButton))
                .addGroup(laborLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, 230, 230, 230)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                .addComponent(labortisch)
                .addGroup(laborLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(radiusLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radiusTextfield)
                        .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(radiusSlider)
                .addComponent(koerperFormenBox)
                .addComponent(materialBox)
        );
        laborLayout.setVerticalGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(laborLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labor, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(startButton)
                                .addComponent(stopButton))
                        .addGap(40, 40, 40)
                        .addGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(startdatenLabel)
                                .addComponent(aufbauButton))
                        .addGap(10, 10, 10)
                        .addGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(radiusLabel)
                                .addComponent(radiusTextfield))
                        .addComponent(radiusSlider)
                        .addComponent(koerperFormenBox)
                        .addComponent(materialBox)
                        .addComponent(planetenBox)
                        .addComponent(luftwiderstandCheck)
                        .addComponent(fallschirmCheck)
                        .addGroup(laborLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(hoeheLabel)
                                .addComponent(hoeheTextField))
                        .addGap(40, 40, 40)
                        .addComponent(labortisch)
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, 120, 120, 120)
                        .addGap(20, 20, 20))
        );
        setSize(350, 1000);

    }

    // Entfernt die ausgewählten Swingkomponenten der Körperdaten aus dem Blinkmodus
    private void ausgewaelt(JComponent component)
    {
        Monitor.getMonitorInstanz().ausgewaelt(component);
    }

    /**
     * Methode die die Körperauswahl auf dem Labortisch anzeigt.
     */
    public void startRampe()
    {
        labortisch.aufbau();
    }

    /**
     * Sammeln der eingestellten Körperdaten in LaboborwerteKLasse zur
     * Weitergabe.
     */
    public void laborWerte()
    {
        int fallHoehe = Integer.parseInt(hoeheTextField.getText());
        laborwerte = new LaborWerte(fallHoehe, (String) planetenBox.getSelectedItem(),
                radiusSlider.getValue(), (String) materialBox.getSelectedItem(), (String) koerperFormenBox.getSelectedItem(),
                luftwiderstandCheck.isSelected(), fallschirmCheck.isSelected());
    }

    /**
     * Weiterreichen der Laborwerte
     *
     * @return Laborwerte
     */
    @Override
    public LaborWerte getLaborWerte()
    {
        return laborwerte;
    }

    /**
     * Status des StartButtons
     *
     * @return 
     */
    public Button getStartButton()
    {
        return startButton;
    }

    /**
     * Status des StopButtons
     *
     * @return
     */
    public Button getStopButton()
    {
        return stopButton;
    }

    /**
     * Status des AufbauButton
     *
     * @return
     */
    public Button getAufbauButton()
    {
        return aufbauButton;
    }

    /**
     * Ausgewählter Körper
     *
     * @return
     */
    public ComboBox getKoerperFormenBox()
    {
        return koerperFormenBox;
    }

    /**
     * Ausgewähltes Material
     *
     * @return
     */
    public ComboBox getMaterialBox()
    {
        return materialBox;
    }

    /**
     * Ausgewählter Planet
     *
     * @return
     */
    public ComboBox getPlanetenBox()
    {
        return planetenBox;
    }

    /**
     * mit oder ohne Luftwiderstand
     *
     * @return
     */
    public CheckBox getLuftwiderstandCheck()
    {
        return luftwiderstandCheck;
    }

    /**
     * mit oder ohne Fallschirm
     *
     * @return
     */
    public CheckBox getFallschirmCheck()
    {
        return fallschirmCheck;
    }

    /**
     * eingestellter Wert des Radius
     *
     * @return
     */
    public Label getRadiusLabel()
    {
        return radiusLabel;
    }

    /**
     * RadiusAnzeige
     *
     * @return
     */
    public TextField getRadiusTextfield()
    {
        return radiusTextfield;
    }

    /**
     * Sliderwert des Radius
     *
     * @return
     */
    public JSlider getRadiusSlider()
    {
        return radiusSlider;
    }

    /**
     * eingestellte FallHöhe des Körpers
     *
     * @return
     */
    public Label getHoeheLabel()
    {
        return hoeheLabel;
    }

    /**
     * Textfeld der Höhenmeterauswahl
     *
     * @return
     */
    public TextField getHoeheTextField()
    {
        return hoeheTextField;
    }

    /**
     * Textfeld für die ausgewählten Körperwerte
     *
     * @return
     */
    public JTextArea getStartdatenTextArea()
    {
        return startdatenTextArea;
    }

    /**
     * Liste der auswählbaren Komponeten
     *
     * @return
     */
    public List<JComponent> getComponenten()
    {
        return componenten;
    }

    /**
     * SingeltonInstanz des Labors wird zurückgegeben.
     *
     * @return SingeltonInstanz des Labors
     */
    public static Labor gibLaborInstanz()
    {
        if (laborInstanz == null)
        {
            laborInstanz = new Labor();
        }

        return laborInstanz;
    }

    private void addComponenten()
    {
        componenten.add(aufbauButton);
        componenten.add(radiusLabel);
        componenten.add(koerperFormenBox);
        componenten.add(materialBox);
        componenten.add(planetenBox);
        componenten.add(luftwiderstandCheck);
        componenten.add(fallschirmCheck);
        componenten.add(hoeheLabel);
    }

    /**
     * Bei Neustart wird Grundzustand des Labors hergestellt.
     */
    public void grundZustand()
    {
        // Zurücksetzten
        int statusZaehler = 0;
        laborwerte = null;
        componenten.clear();
        // Anfangszustand herstellen
        startButton.setEnabled(false);
        startButton.setText("Start");
        stopButton.setEnabled(false);
        stopButton.setText("Stop");
        aufbauButton.setEnabled(true);
        aufbauButton.setText("Ready?");
        radiusSlider.setEnabled(true);
        radiusSlider.setValue(100);
        koerperFormenBox.setEnabled(true);
        koerperFormenBox.setSelectedItem(Koerper.getKoerperAuswahl()[0]);
        materialBox.setEnabled(true);
        materialBox.setSelectedItem(Material.getMaterialAuswahl()[0]);
        planetenBox.setEnabled(true);
        planetenBox.setSelectedItem(Planet.getPlanetenAuswahl()[0]);
        luftwiderstandCheck.setEnabled(true);
        luftwiderstandCheck.setSelected(false);
        fallschirmCheck.setEnabled(true);
        fallschirmCheck.setSelected(false);
        hoeheTextField.setEnabled(true);
        hoeheTextField.setText("0");
        addComponenten();
    }

    @Override
    public void setLaborWerte(LaborWerte laborwerte)
    {
        this.laborwerte = laborwerte;
    }

}
