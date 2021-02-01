package View.LaborBereich.SwingKomponenten;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Diese Klasse ist ein Eigenbau eines JButton, dem Schriftart, Text,
 * ActionListener und Sichtbarkeit übergeben wird
 *
 * @author stefanscherle
 */
public class Button extends JButton
{
    /**
     *
     * @param f Schriftgrösse
     * @param text Text
     * @param act Action
     * @param sichtbar aktiv
     */
    public Button(Font f, String text, ActionListener act, boolean sichtbar)
    {
        setFont(f);
        setText(text);
        addActionListener(act);
        setEnabled(sichtbar);
    }

}
