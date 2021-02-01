package View.LaborBereich.SwingKomponenten;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 * Diese Klasse ist ein Eigenbau einer JCheckBox, der Schriftart, Text und ein
 * ActionListener übergeben wird
 *
 * @author stefanscherle
 */
public class CheckBox extends JCheckBox
{
    /**
     *
     * @param f Schriftart
     * @param text Text
     * @param act Action
     */
    public CheckBox(Font f, String text, ActionListener act)
    {
        setFont(f);
        setText(text);
        addActionListener(act);
    }
}
