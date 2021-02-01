package View.LaborBereich.SwingKomponenten;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 * Diese Klasse ist ein Eigenbau einer JCheckBox, der Schriftart, Text und ein
 * ActionListener übergeben wird
 * @author stefanscherle
 */
public class TextField extends JTextField
{
    /**
     *
     * @param f Schriftart
     * @param text Text
     * @param act Action
     */
    public TextField(Font f, String text, ActionListener act)
    {
        setFont(f);
        setText(text);
        addActionListener(act);
    }
}
