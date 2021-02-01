package View.LaborBereich;

import Controller.IWerteTransfer;
import View.LaborBereich.SwingKomponenten.Label;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Diese Klasse ermöglicht dem Benutzer den eingestellten Körper zu
 * visualisieren
 *
 * Version 8.12.20
 *
 * @author Uwe Lachmann
 */
public class Labortisch extends JPanel implements IWerteTransfer
{
    private static final Labortisch labortischInstanz=new Labortisch();
    
    private boolean gewaehlt;
    
    private int radius;           //Radius der gewählten Kugel
    private int iAufbau;               //Zählt wie oft der AufbauButton gedrückt wurde
    private BufferedImage Kugel;
    private BufferedImage Stahlkugel;
    private BufferedImage Holzkugel;
    private BufferedImage Styroporkugel;
    private BufferedImage Hintergrund;
    private BufferedImage Mond;
    private BufferedImage Erde;
    private BufferedImage Stahlkegel;
    private BufferedImage Wasserkegel;
    private BufferedImage Styroporkegel;
    private BufferedImage Holzkegel;  

    private String koerper;
    private String material;
    private String planet;
    private int groesseKu;                     //Größe der Kugel
    private int groesseKe;                     //Größe des Kegels
    private int groesse;                       //Größe des ausgwählten Körpers
    
    private Timer timer;

    /**
     *Konstruktor: Stellt Grundzustand her und lädt die Bilder.
     */
    private Labortisch()
    {
        grundZustand();  
        ladeBilder();             
    }

    /**
     * Zeichnet die gewählten Objekte 
     * @param graphics 
     */
    @Override
    public void paint(Graphics graphics)
    {  
        //Ausgangssitutation für den Labortisch. Bedingund: Es wurde noch nichts gewählt.
        if (gewaehlt == false & iAufbau == 0) 
        {
            graphics.drawImage(Hintergrund, 0, 0, getWidth(), getHeight(), null);
            graphics.drawImage(Kugel, getWidth()/2-groesseKu/2, 100-groesseKu/2, groesseKu, groesseKu, null);
            graphics.drawImage(Stahlkegel, getWidth()/2-groesseKe/2, 35 - groesseKe/2, groesseKe, groesseKe, null);
        }
        
        if (planet == "Mond")
        {
            graphics.drawImage(Mond, 0, 0, getWidth(), getHeight(), null);
        }
        
        if (planet == "Erde")
        {
            graphics.drawImage(Erde, 0, 0, getWidth(), getHeight(), null);
        }
        
        //Wenn kein Planet gewählt wurde, verbleibt das Startbild im Labortisch.
        if (planet == "Planet") 
        {
            graphics.drawImage(Hintergrund, 0, 0, getWidth(), getHeight(), null);
        }
         
        if (koerper == "Kugel")
        {
            int posX = getWidth()/2 - radius;
            int posY = getHeight()/2 - radius;
            switch (material)
                {
                    case "Stahl":
                        graphics.drawImage(Stahlkugel, posX, posY, groesse, groesse, null);
                        break;

                     case "Holz":
                        graphics.drawImage(Holzkugel, posX, posY, groesse, groesse, null);
                        break;

                    case "Styropor":
                        graphics.drawImage(Styroporkugel, posX, posY, groesse, groesse, null);
                        break;
                        
                    case "Wasser":
                        graphics.drawImage(Kugel, posX, posY, groesse, groesse, null);
                        break;
                }
        }
        
        if (koerper == "Kegel")
        {
            int posX = getWidth()/2 - radius;
            int posY = getHeight()/2 -radius/2;
            switch (material)
                {
                    case "Stahl":
                        graphics.drawImage(Stahlkegel, posX , posY, groesse, radius, null);
                        break;

                     case "Holz":
                        graphics.drawImage(Holzkegel, posX , posY, groesse, radius, null);
                        break;

                    case "Styropor":
                        graphics.drawImage(Styroporkegel, posX , posY, groesse, radius, null);
                        break;
                        
                    case "Wasser":
                        graphics.drawImage(Wasserkegel, posX , posY, groesse, radius, null);
                        break;
                }
        }   
    }
    
    /**
     * Methode lädt die Bilder. Wird im Konstruktor aufgerufen.
     */
    public void ladeBilder()
    {
        try
        {
            Kugel = ImageIO.read(getClass().getResource("/Bilder/Wasserkugel.png"));
            Stahlkugel = ImageIO.read(getClass().getResource("/Bilder/Stahlkugel.png"));
            Holzkugel = ImageIO.read(getClass().getResource("/Bilder/Holzkugel.png"));
            Styroporkugel = ImageIO.read(getClass().getResource("/Bilder/Styroporkugel.png"));
            Stahlkegel = ImageIO.read(getClass().getResource("/Bilder/Stahlkegel.png"));
            Wasserkegel = ImageIO.read(getClass().getResource("/Bilder/Wasserkegel.png"));
            Holzkegel = ImageIO.read(getClass().getResource("/Bilder/Holzkegel.png"));
            Styroporkegel = ImageIO.read(getClass().getResource("/Bilder/Styroporkegel.png"));
            Hintergrund = ImageIO.read(getClass().getResource("/Bilder/LT-Hintergrund.png"));
            Mond = ImageIO.read(getClass().getResource("/Bilder/Mondoberfläche(klein).png"));
            Erde = ImageIO.read(getClass().getResource("/Bilder/ErdeHintergrund.png"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(Labortisch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ein Bild wurde nicht gefunden");
        }
    }

    /**
     * Methode wird vom Controller aus gestartet und weißt den entsprechenden 
     * wählbaren Variablen einen Wert zu. Des Weiteren werden die Methoden 
     * gewaehlt() und setRadius() aufgerufen.
     */
    public void aufbau()
    {   
        iAufbau++;
        radius = groesse/2;
        koerper = (String)Labor.gibLaborInstanz().getKoerperFormenBox().getSelectedItem();
        material = (String)Labor.gibLaborInstanz().getMaterialBox().getSelectedItem();
        groesse = Labor.gibLaborInstanz().getRadiusSlider().getValue();
        planet = (String)Labor.gibLaborInstanz().getPlanetenBox().getSelectedItem();
        gewaehlt();
        timerStop();
        setLabortisch();
    }
    
    /**
     * Metode verändert den boolean gewählt und stoppt gleichzeitig den Timer.
     * Wird in der Methode aufbau() aufgerufen.
     */
    public void gewaehlt()
    {
        gewaehlt = !(koerper == "Körperform" & material == "Material" & planet == "Planet");
        timer.cancel();
    }
    
    /**
    *Bewirkt, dass die Köper im Labortisch zu Beginn pulsieren. 
    *Wird in der Methode Grundzustand aufgerufen.
    */
    public void zoomen()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            double i=0;
            double faktorKu;
            double faktorKe;
            @Override
            public void run()
            {
                double x = Math.sin(i);
                i=i+0.1;
                faktorKu = x*10+60;
                faktorKe = -x*10+60;
                groesseKu = (int)faktorKu;
                groesseKe = (int) faktorKe;
                repaint();
            }
        }, 2000, 20);
    }
    
    /**
     * Methode zeichnet das Bild des Körpers im Labortisch bei Veränderung 
     * des Radius und des gewählten Planeten durch Nutzung des Slider 
     * direkt neu. "iAufbau größer als 2" soll den Timer nach der 2ten 
     * Betätigung des AufbauButtons stoppen,
     */
    public void setLabortisch()
    {  
        if (gewaehlt == true & iAufbau < 2)
        {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask()
            {  
                double i=0;
                double faktorKu;
                @Override
                public void run()
                {
                    double x = Math.sin(i);
                    i=i+0.1;
                    faktorKu = x*7;
                    groesse = Labor.gibLaborInstanz().getRadiusSlider().getValue()+(int)faktorKu;
                    radius = groesse/2;
                    planet = (String)Labor.gibLaborInstanz().getPlanetenBox().getSelectedItem();
                    repaint();
                }
            }, 0, 20);
        }
        else
        {
        }
    }
    
    //Stoppt den Timer
    public void timerStop()
    {
        timer.cancel();
    }
    
    /**
    Setzt den Labortisch (wieder) in den Ausgangszustand zurück. 
    * Wird vom Konstruktor aufgerufen.
    */
    public void grundZustand()
    {
        iAufbau = 0;
        groesseKu = 50;
        gewaehlt = false;
        
        zoomen();
        
          //Font fontG = new java.awt.Font("Lucida Grande", 1, 24);
        Font fontK = new java.awt.Font("Lucida Grande", 0, 16);
        Label hoeheLabel = new Label(fontK, "Zeitintervall:", GroupLayout.Alignment.TRAILING);
        //hoeheTextField = new TextField(fontG, "2", e -> ausgewaelt(hoeheLabel));
        hoeheLabel.setBounds(10,500,200,30);
        hoeheLabel.setVisible(true);
        GroupLayout labortisch1Layout = new GroupLayout(this);
        setLayout(labortisch1Layout);
        labortisch1Layout.setHorizontalGroup(
                labortisch1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(hoeheLabel)
                        //.addComponent(hoeheTextField)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        
          labortisch1Layout.setVerticalGroup(
                labortisch1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(hoeheLabel)
                       // .addComponent(hoeheTextField)
                        .addGap(0, 200, Short.MAX_VALUE)
        );
    }

    public static Labortisch getLabortischInstanz()
    {
        return labortischInstanz;
    }

    @Override
    public LaborWerte getLaborWerte()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLaborWerte(LaborWerte laborwerte)
    {
        //Einlesen der Daten
    }
}
