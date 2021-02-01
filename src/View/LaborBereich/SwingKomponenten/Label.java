package View.LaborBereich.SwingKomponenten;

import java.awt.Font;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JLabel;

/**
 * Diese Klasse ist ein Eigenbau eines JLabel, dem Schriftart, Text und ein
 * Layout übergeben wird
 *
 * @author stefanscherle
 */
public class Label extends JLabel
{
    /**
     *
     * @param f Schriftart
     * @param text Text
     * @param alig Anordnung
     */
    public Label(Font f, String text, Alignment alig)
    {
        setFont(f);
        setText(text);
        setHorizontalAlignment(CENTER);
    }
}
