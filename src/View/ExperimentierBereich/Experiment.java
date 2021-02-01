package View.ExperimentierBereich;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Legt das Layout des Experimentierbereiches fest
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 */
public class Experiment extends JPanel implements ActionListener
{
    private final JCheckBox jCheckBox_Luftkraft;
    private final JCheckBox jCheckBox_Geschwindigkeit;
    private final JCheckBox jCheck_Kraft;
    private final JCheckBox jCheckBox_Fg;

    /**
     * Bereich für die Simulation.
     */
    private final ExpFeld expFeld;

    public Experiment()
    {
        Color blau = new Color(94, 99, 255); //Farbe der Luftkraft
        Color gelb = new Color(238, 228, 32); //Farbe der Gewichtskraft
        Color rot = new Color(255, 55, 55); //Farbe der Gewichtskraft
        Color gruen = new Color(46, 225, 77); //Farbe der Gewichtskraft


        Font fontG = new java.awt.Font("Lucida Grande", 1, 24);
        Font fontK = new java.awt.Font("Lucida Grande", 0, 16);
        JLabel jLabel_Experiment = new JLabel();
        jCheckBox_Geschwindigkeit = new JCheckBox();
        jCheckBox_Luftkraft = new JCheckBox();
        jCheck_Kraft = new JCheckBox();
        jCheckBox_Fg = new JCheckBox();

        setBackground(new java.awt.Color(255, 153, 153));

        jLabel_Experiment.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel_Experiment.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_Experiment.setText("Experiment");

        jCheckBox_Geschwindigkeit.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jCheckBox_Geschwindigkeit.setForeground(gruen);
        jCheckBox_Geschwindigkeit.setText("Geschwindigkeit");
        jCheckBox_Geschwindigkeit.addActionListener(this);
        jCheckBox_Geschwindigkeit.setSelected(true);

        jCheckBox_Luftkraft.setFont(new java.awt.Font("Lucida Grande", 0, 16));
        jCheckBox_Luftkraft.setForeground(blau);
        jCheckBox_Luftkraft.setText("Luftwiderstand");
        jCheckBox_Luftkraft.addActionListener(this);
        jCheckBox_Luftkraft.setSelected(true);

        jCheckBox_Fg.setFont(new java.awt.Font("Lucida Grande", 0, 16));
        jCheckBox_Fg.setForeground(gelb);
        jCheckBox_Fg.setText("Gewichtskraft");
        jCheckBox_Fg.addActionListener(this);
        jCheckBox_Fg.setSelected(true);
        
        jCheck_Kraft.setFont(new java.awt.Font("Lucida Grande", 0, 16));
        jCheck_Kraft.setForeground(rot);
        jCheck_Kraft.setText("Gesamtkraft");
        jCheck_Kraft.addActionListener(this);
        jCheck_Kraft.setSelected(true);
        
        expFeld = ExpFeld.getExpFeldInstanz();


        GroupLayout jPanel4Layout = new GroupLayout(this);
        setLayout(jPanel4Layout);

        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jLabel_Experiment, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox_Luftkraft)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_Fg)
                        .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(expFeld, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jCheckBox_Geschwindigkeit, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheck_Kraft, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                        .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel_Experiment, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox_Luftkraft, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox_Fg))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expFeld, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox_Geschwindigkeit)
                                .addComponent(jCheck_Kraft))
                        .addContainerGap())
        );
        setSize(300, 1000);
    }

    /**
     * ActionPerformed: Kraftpfeile
     *
     * @param evt 
     */
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == jCheckBox_Geschwindigkeit)
        {
            expFeld.setGeschwindigkeitAn();
        }
        if (evt.getSource() == jCheck_Kraft)
        {
            expFeld.setKraftpfeilAn();
        }
        if (evt.getSource() == jCheckBox_Luftkraft)
        {
            expFeld.setLuftKraftAn();
        }
        if (evt.getSource() == jCheckBox_Fg)
        {
            expFeld.setFgAn();
        }
        

    }

    private void ausgewaelt(double time)
    {
        //experimentierFeld_1.setTimeSynchron(time); 
    }

}
