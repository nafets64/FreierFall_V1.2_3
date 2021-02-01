package View;

import Controller.Controller;
import View.Auswertungsbereich.Auswertung;
import View.ExperimentierBereich.Experiment;
import View.LaborBereich.Labor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Benutzeroberfläche mit Buttons, Slider Checkboxen zum einstellen der
 * Stardaten Darstellung des Labor, Experiment und Auswertungsbereich
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 *
 */
public class GUI extends JFrame
{
    private static final GUI guiInstanz = new GUI();
    private JMenuBar jMenuBar1;

    private final Labor laborPanel;
    private final Auswertung auswertungPanel;
    private final Experiment experimentPanel;
    //protected final Experiment jPanel_Exp2;

    /**
     * Legt das Fenster an, indem die Panls Labor, Experiment und Auswertung
     * liegen.
     */
    private GUI()
    {
        setTitle("Freier Fall");
        // Anlegen der drei Panels Labor, Experiment undAuswertung
        laborPanel = Labor.gibLaborInstanz();
        experimentPanel = new Experiment();
        auswertungPanel = new Auswertung();
        // Ausgelegerte Bereiche
        menueComboneneten();
        guiLayout();
        setJMenuBar(jMenuBar1);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Rückgabe des JPanels Auswertung
     *
     * @return Auswertungspanel
     */
    public Auswertung getAuswertung()
    {
        return auswertungPanel;
    }

    /**
     * Rückgabe des JPanels Experiment
     *
     * @return Experimentenpanel
     */
    public Experiment getExperiment()
    {
        return experimentPanel;
    }

    private void menueComboneneten()
    {
        // Menübereich
        jMenuBar1 = new JMenuBar();
        //Menüattributte
        JMenu experimentMenue = new JMenu();
        JMenuItem neuItem = new JMenuItem();
        JMenuItem oeffnenItem = new JMenuItem();
        JMenuItem speichernItem = new JMenuItem();
        JMenu optionenMenue = new JMenu();
        JMenuItem zweiterKoreperItem = new JMenuItem();
        JMenu infoMenue = new JMenu();
        JMenuItem beschreibungItem = new JMenuItem();
        JMenuItem infoItem = new JMenuItem();

        experimentMenue.setText("Experiment");

        neuItem.setText("Neuer Körper");
        neuItem.addActionListener(e -> Controller.gibInstanz().neu());
        experimentMenue.add(neuItem);

        oeffnenItem.setText("Öffnen");
        oeffnenItem.addActionListener(e ->
        {
            try
            {
                Controller.gibInstanz().oeffnen();
            }
            catch (IOException ex)
            {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            catch (ClassNotFoundException ex)
            {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        experimentMenue.add(oeffnenItem);

        speichernItem.setText("Speichern");
        speichernItem.addActionListener(e ->
        {
            try
            {
                Controller.gibInstanz().speichern();
            }
            catch (IOException ex)
            {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        experimentMenue.add(speichernItem);

        jMenuBar1.add(experimentMenue);

        optionenMenue.setText("Optionen");

        zweiterKoreperItem.setText("Fall zweier Körper");
        zweiterKoreperItem.addActionListener(e -> Controller.gibInstanz().zweiKoerper());
        optionenMenue.add(zweiterKoreperItem);

        jMenuBar1.add(optionenMenue);

        infoMenue.setText("Info");

        beschreibungItem.setText("Beschreibung");
        beschreibungItem.addActionListener(e -> Controller.gibInstanz().begruessung());
        infoMenue.add(beschreibungItem);

        infoItem.setText("Version");
        infoItem.addActionListener(e -> Controller.gibInstanz().version());
        infoMenue.add(infoItem);

        jMenuBar1.add(infoMenue);
    }

    private void guiLayout()
    {
        setBackground(new java.awt.Color(255, 0, 255));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(laborPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(experimentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(auswertungPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(laborPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(experimentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(auswertungPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        setVisible(rootPaneCheckingEnabled);

        setSize(1400, 1000);
    }

    /**
     * Gibt die Singelton-Instanz der GUI zurück
     *
     * @return Gui-Singelton
     */
    public static GUI getGuiInstanz()
    {
        return guiInstanz;
    }

}
