package Modell;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Bereich für die Bereitstellung der Informationen bzw Benutzrhandbuch
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 */
public class MenueEintrag extends JFrame
{
    private String text;

    private final Info informationen;

    /**
     *
     * @param x
     */
    public MenueEintrag(int x)
    {
        informationen = new Info(x);

        int hoehe = informationen.getHoehe();
        int breite = informationen.getBreite();

        setTitle("Information");
        setLocation(200, 100);
        setVisible(true);

        add(informationen);

        pack();
        setSize(breite, hoehe);
    }

    /**
     *
     * @param fall
     */
    public void dialog(int fall)
    {
        setTitle("Information");
        setLayout(null);
        setVisible(true);
        setSize(1200, 100);

                switch (fall)
        {
            case 1: // Planet fehlt
        setLocation(300, 400);
        text = "Treffen Sie ihre Planetenauswahl"; 
                break;
            case 2: // Höhe nicht im richtigen Bereich
        setLocation(300, 500);
        text = "Bitte geben Sie eine sinnvolle Höhenmeterangabe ein."; 
                break;
            case 3: // Höhe nicht als Zahl
        setLocation(300, 600);
        text = "Bitte geben Sie eine gültige Höhenmeterangabe als Zahl ein."; 
                break;
        }

        
        JLabel textbereich;

        textbereich = new JLabel(text);
        textbereich.setBackground(new java.awt.Color(255, 255, 0));
        textbereich.setFont(new java.awt.Font("Lucida Grande", 3, 36)); // 
        textbereich.setBounds(20, 5, 1200, 50);
        add(textbereich);
    }

    /**
     * Beschreibung bzw Handreichung zum Gebrauch der Software
     */
    public void beschreibung()
    {
        informationen.beschreibung();
    }

    /**
     * Versioninformation
     */
    public void version()
    {
        informationen.version();
    }


}
