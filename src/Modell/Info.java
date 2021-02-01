package Modell;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Die im Menüpunkt Info aufrufbaren Informationen. 
 * 
 * @author stefanscherle
 */
public class Info extends JPanel
{
    private int hoehe;
    private int breite;

    private BufferedImage beschreibung;
    private BufferedImage version;
    private BufferedImage bild;

    /**
     *
     * @param x
     */
    public Info(int x)
    {
        try
        {
            beschreibung = ImageIO.read(getClass().getResource("/Bilder/Beschreibung.jpg"));
            version = ImageIO.read(getClass().getResource("/Bilder/Version.jpg"));

        }
        catch (IOException ex)
        {
            Logger.getLogger(Info.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (x)
        {
            case 1: //
                hoehe = beschreibung.getHeight()+28;
                breite = beschreibung.getWidth()+18;
                break;
            case 2: // 
                hoehe = version.getHeight()+38;
                breite = version.getWidth()+16;
                break;
        }

    }


    /**
     * Beschreibung bzw Handreichung zum Gebrauch der Software
     */
    public void beschreibung()
    {
        bild = beschreibung;
        repaint();
    }

    /**
     * Versioninformation
     */
    public void version()
    {
        bild = version;
        repaint();
    }

    /**
     *
     */
    @Override
    public void paint(Graphics g)
    {
        g.drawImage(bild, 0, 0, null);
    }

    public int getHoehe()
    {
        return hoehe;
    }

    public int getBreite()
    {
        return breite;
    }

}
