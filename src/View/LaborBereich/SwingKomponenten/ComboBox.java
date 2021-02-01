package View.LaborBereich.SwingKomponenten;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * Diese Klasse ist ein Eigenbau einer JComboBox, der Schriftart, Text und ein
 * ActionListener übergeben wird
 *
 * @author stefanscherle
 */
public class ComboBox extends JComboBox<String>
{
    /**
     *
     * @param f Schriftart
     * @param text Text
     * @param act Action
     */
    public ComboBox(Font f, String[] text, ActionListener act)
    {
        setFont(f);
        setModel(new DefaultComboBoxModel<>(text));
        addActionListener(act);
    }
}
