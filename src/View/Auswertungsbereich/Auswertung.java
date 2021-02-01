package View.Auswertungsbereich;

import View.LaborBereich.SwingKomponenten.CheckBox;
import View.LaborBereich.SwingKomponenten.Label;
import java.awt.Font;
import javax.swing.*;

/**
 * Diese Klasse enthält den Auswertungsbereich, also das Layout und den
 * Diagrammzeichenbereich
 *
 * Versionn 10.11.20
 *
 * @author stefanscherle
 */
public class Auswertung extends JPanel
{
    private final CheckBox t_v_DiagrammCheck;
    private final CheckBox t_a_DiagrammCheck;
    private final Label auswertungLabel;

    /**
     * Legt das Koordinatensystem an, in das die Messpunkte eingetragen werden.
     */
    private final Diagramm diagramm;

    /**
     * Konstruktor, der das Layout und die Checkboxen sowie das Diagrammfenster
     * zum zeichnen anlegt.
     *
     */
    public Auswertung()
    {
        Font fontG = new java.awt.Font("Lucida Grande", 1, 24);
        Font fontK = new java.awt.Font("Lucida Grande", 0, 16);
        setBackground(new java.awt.Color(204, 204, 255));
        // Initialisierung der Combonenenten
        auswertungLabel = new Label(fontG, "Auswertung", GroupLayout.Alignment.CENTER);
        t_v_DiagrammCheck = new CheckBox(fontK, "t-v-Diagramm", e -> t_v_DiagrammAn());
        t_v_DiagrammCheck.setSelected(true);
        t_a_DiagrammCheck = new CheckBox(fontK, "t-a-Diagramm", e -> t_a_DiagrammAn());
        t_a_DiagrammCheck.setSelected(true);
        // Zeichenbereich anlegen
        diagramm = Diagramm.getDiagrammInstanz();

        auswertungLayout();
    }

    /**
     * Aktiviert das Zeichnen des t-v-Diagramm.
     */
    private void t_v_DiagrammAn()
    {
        diagramm.setAuswertungGeschwAn(t_v_DiagrammCheck.isSelected());
    }

    /**
     * Aktiviert das Zeichnen des t-a-Diagramm.
     */
    private void t_a_DiagrammAn()
    {
        diagramm.setAuswertungBeschlAn(t_a_DiagrammCheck.isSelected());
    }

    /**
     * Layoutbereich der Auswertung.
     */
    private void auswertungLayout()
    {
        GroupLayout auswertung = new GroupLayout(this);
        setLayout(auswertung);
        auswertung.setHorizontalGroup(auswertung.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(auswertungLabel, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addGroup(auswertung.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(diagramm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(auswertung.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(t_v_DiagrammCheck)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(t_a_DiagrammCheck)
                        .addContainerGap())
        );
        auswertung.setVerticalGroup(auswertung.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(auswertung.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(auswertungLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(diagramm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(auswertung.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(t_v_DiagrammCheck, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addComponent(t_a_DiagrammCheck, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
        );
    }
}
