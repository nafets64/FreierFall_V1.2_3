/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ExperimentierBereich;

import Controller.IDatenTransfer;
import Controller.IExpService;
import Controller.IWerteTransfer;
import Modell.DTO;
import Modell.Daten;
import View.LaborBereich.Labor;
import View.LaborBereich.LaborWerte;
import View.LaborBereich.Labortisch;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *Diese Klasse visualisiert den freien Fall des Objekts entsprechend der im 
 * Labor gewählten Einstellungen.
 * @author Uwe Lachmann
 */
public final class ExpFeld extends JPanel implements IDatenTransfer, IWerteTransfer, IExpService
{
    private static final ExpFeld expFeldInstanz = new ExpFeld();

    private double posY1;             // y-Koordinate der Wolken
    private double posY2;
    private double posY3;
    private double posY4;
    private double posX4;
    private double posY5;
    private double posYUfo;
    private double posXUfo;
    private double posYTodesstern;
    private double posYDrohne;
    private double posYK;          //Y Position der Kugel/des Kegels
    private double posYRakete;
    private double posYHG;          //Position des Hintergrundes
    private double posXK;           //Position der Kugel / des Kegels
    private double posYVek;
    private double posXVek;

    private double groesseWolke;

    private int r;            // Achtung: Radius entspricht beim Zeichnen dem Durchmesser
    private int groesseDrohne;

    //Vektoren
    private int breiteVek;
    private double laengeVekGesch;
    private double laengeVekKraft;
    private double laengeVekLuftKra;
    private double laengeVekFg;

    private int bewFaktor;          // Faktor für Vergrößerung der Bewegungsgeschwindigkeit
    private double bewFaktorHG;         // Faktor der die Bewegung von Hintergrundobjekten verlangsamt

    private double zoomFaktorKraft;
    private double zoomFaktorGesch;

    private int datenPunkt;     //Nummer des Datenpunktes
    private int geschwTimer;    //Geschwindigkeit des Timers
    private int iFallschirm;    //Datenpunkt, an dem der Schirm ausgelöst wird

    private boolean resKraftAn;         // Resultierened Kraft?
    private boolean f_gAn;              // Gewichtskraft?
    private boolean geschwAn;           // Gewichstkarft?
    private boolean luftKraftAn;        // Vektor Luftwiderstand?
    private boolean luftwiderstand;     // Luftwiderstand?
    private boolean fallschirmAn;       // Fallschirm?
    private boolean aufbau;             // Aufbau fertig?
    private boolean simulation;         //Simulation fertig?

    private String planet;
    private String koerperauswahl;
    private String material;

    private BufferedImage StartHintergrund;
    private BufferedImage HintergrundErde;
    private BufferedImage HintergrundMond;
    private BufferedImage Wolke1;
    private BufferedImage Wolke2;
    private BufferedImage Wolke3;
    private BufferedImage Wolke4;
    private BufferedImage Wolkenband;
    private BufferedImage Ufo;
    private BufferedImage Erde;
    private BufferedImage Astronaut;
    private BufferedImage Todesstern;

    private BufferedImage Stahlkugel;
    private BufferedImage Holzkugel;
    private BufferedImage Styroporkugel;
    private BufferedImage Wasserkugel;
    private BufferedImage Stahlkegel;
    private BufferedImage Wasserkegel;
    private BufferedImage Styroporkegel;
    private BufferedImage Holzkegel;
    private BufferedImage Fallschirm;

    private BufferedImage Rakete;
    private BufferedImage Drohne;
    private BufferedImage vKoerper;             //Körper der Pfeils
    private BufferedImage vSpitze;              //Pfeilspitze
    private BufferedImage KraftKoerper;
    private BufferedImage KraftSpitze;
    private BufferedImage luftKraftKoerper;
    private BufferedImage luftKraftSpitze;
    private BufferedImage gewichtsKraftKoerper;
    private BufferedImage gewichtsKraftSpitze;

    private Timer timer;

    // Einlesen der daten
    private LaborWerte laborwerte;
    private List<Daten> daten;
    private DTO datenTransfer;

    /**
     * Konstruktor.
     */
    public ExpFeld()
    {
        grundZustand();
    }

    /**
     * Methode aubau() wird vom Controller aufgerufen nachdem der "Go???"-Button
     * gedrückt wurde. An dieser Stelle wird dann der Körper in die gewünschte 
     * Startposition transportiert. Dabei hat  der gewählte Ort Auswirkungen 
     * auf die Art wie der gewählte Körper zur Startpsoition gebracht wird.
     */
    @Override
    public void aufbau()
    {
        aufbau = true;
        planet = (String) Labor.gibLaborInstanz().getPlanetenBox().getSelectedItem();
        koerperauswahl = Labor.gibLaborInstanz().getLaborWerte().getKoerper();
        material = laborwerte.getMaterial();
        int radius = laborwerte.getRadius();

        repaint();

        if ("Mond".equals(planet))
        {
            geschwTimer = 5;
        }
        //Erzeugt einen Timer, der solange läuft, bis die Objekte in Startposition sind.
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if ("Erde".equals(planet))
                {
                    r++;
                    groesseDrohne = groesseDrohne + 2;
                    posYK = getHeight() / 2 - r;
                    posXK = getWidth() / 2 - r;

                    posYDrohne = posYK - groesseDrohne;
                    repaint();
                    if (r >= radius)
                    {
                        // Controller braucht Info über startklar
                        Labor.gibLaborInstanz().getStartButton().setEnabled(true);
                        // Ende Controller
                        timer.cancel();
                    }
                }
                
                if ("Mond".equals(planet))
                {
                    posYK--;
                    posYRakete = posYK - 235;
                    r = laborwerte.getRadius();
                    posXK = getWidth() / 2 - radius;
                    repaint();

                    if (posYK == getHeight() / 2 - radius)
                    {
                        // Controller braucht Info über startklar
                        Labor.gibLaborInstanz().getStartButton().setEnabled(true);
                        timer.cancel();
                    }
                }
            }
        }, 500, geschwTimer);
    }

    /**
     * Nachdem der Aufbau beendet ist und der Nutzer nun den "Start"-Button 
     * betätigen kann, wird mit drücken dieses Buttons vom Controller aus die
     * Methode simulation() aus gestartet. Hier werden die zu zeichenden Größen
     * wie z.B. die verschiedenen Position der Wolken, ermittelt. Dabei werden 
     * mehrere Fallunterscheidungen (Mond oder Erde, gewählte Körperform etc.) 
     * getroffen. Die Daten werden von der Klasse Berechung ermittelt
     * und über das Interface iDatenTransfer importiert?
     */
    @Override
    public void simulation()
    {
        simulation = true;
        int i = datenPunkt;
        int iMax = daten.size();

        if (i == 0)   //Folgendes wird nur einmal zu Beginn berechnet/ermittelt
        {
            setZoomfaktor();
            setBewegungsfaktor();
            setLuftwiederstandAn();
            fallschirmAn = false;       // Hier notwendig damit bei "Again" Fallschirm wieder verschwindet.
            //Legt die Länge des Vektors Fg fest.
            laengeVekFg = datenTransfer.getFG() * zoomFaktorKraft; //Notwendig weil Werte 0,00X sind und int somit 0 wäre
            iFallschirm = datenTransfer.getiSchirm(); //Holt sich den DatenPunkt aus der Liste
            posXVek = getWidth() / 2 - breiteVek / 2;
            posYVek = posYK + r;
        }

        if (i == iFallschirm)
        {
            setFallschirmAn();
        }
        
        //i<iMax -> verhindert Exeption
        if (i < iMax & "Erde".equals(planet))
        {
            double deltaX = daten.get(i).getWegAenderung() * bewFaktor;
            //Y-Position der Wolken aus LinkList
            setWolken();
            posY1 = posY1 - deltaX * bewFaktorHG;   //Wolke 1
            posY2 = posY2 - deltaX * bewFaktorHG;   //Wolke 2
            posY3 = posY3 - deltaX * bewFaktorHG;   //Wolke 3
            posY4 = posY4 - deltaX;                 //große Wolke
            posY5 = posY5 - deltaX * bewFaktorHG * 0.2; //langsame Wolke
            posYDrohne = posYDrohne - deltaX;

            if (luftwiderstand == false)
            {
                //Drohne kam von Hinten und soll nach Vorne verschwinden. 
                //Bei mit Luftwiderstand würde die Drohne zu langsam verschwinden
                groesseDrohne++;  
            }
            
            // Setzt Größe der Vektoren v, F_Luft unf F_Res fest 
            laengeVekGesch = daten.get(i).getGeschwindigkeit() * zoomFaktorGesch;
            laengeVekLuftKra = -daten.get(i).getLuftKraft() * zoomFaktorKraft; //"-" -> Zeigt nach oben!
            laengeVekKraft = daten.get(i).getResKraft() * zoomFaktorKraft;
            i++;
            datenPunkt = i;
        }

        
        if (i < iMax & "Mond".equals(planet))
        {
            double deltaX = daten.get(i).getWegAenderung() * bewFaktor;
            setMondObjekte();
            posYHG = posYHG - deltaX * bewFaktorHG * 0.1; //MondHintergrund
            posY5 = posY5 - deltaX * bewFaktorHG;  //Erde 
            posY3 = posY3 - deltaX;             //Astronaut 
            posXUfo = -200 + i;                 //Ufo X
            posYUfo = posYUfo - deltaX;
            posYRakete = posYRakete - i / 2;
            posYTodesstern = posYTodesstern - deltaX; //Todesstern

            // Setzt Größe der Vektoren fest
            laengeVekGesch = (int) daten.get(i).getGeschwindigkeit();
            i++;
            datenPunkt = i;
        }
        repaint();
    }

    /**
     * Erzeugt das Hintergrundbild sowie den Körper mit den wirkenden Kräften.
     * @param graphics
     */
    @Override //wird gleich zu beginn ausgeführt
    public void paint(Graphics graphics)
    {
        if (aufbau == false)
        {
            graphics.drawImage(StartHintergrund, 0, 0, getWidth(), getHeight(), null);
        }

        //Zeichnet Folgendes, wenn die Erde ausgewählt wurde!
        if (planet == "Erde") //& laborwerte != null
        {
            //Fügt den Hintergrund und die Wolken ein
            graphics.drawImage(HintergrundErde, 0, 0, getWidth(), getHeight(), null);
            graphics.drawImage(Wolkenband, -50, (int) posY5, 600, 150, null);
            graphics.drawImage(Wolke1, 20, (int) posY1, 120, 90, null);
            graphics.drawImage(Wolke2, 50, (int) posY2, 120, 100, null);
            graphics.drawImage(Wolke3, 150, (int) posY3, 120, 100, null);
            graphics.drawImage(Wolke4, (int) posX4, (int) posY4, (int) (groesseWolke * 1.5), (int) groesseWolke, null); //Wolke im Vordergrund
            graphics.drawImage(Drohne, getWidth() / 2 - groesseDrohne / 2, (int) posYDrohne, groesseDrohne, groesseDrohne, this);
        }

        //Zeichnet Folgendes, wenn Mond ausgewählt wurde!
        if (planet == "Mond") //& laborwerte != null
        {
            graphics.drawImage(HintergrundMond, 0, (int) posYHG, getWidth(), getHeight() + 100, null);
            graphics.drawImage(Erde, 20, (int) posY5, 80, 80, null);
            graphics.drawImage(Todesstern, 30, (int) posYTodesstern, 100, 100, null);
            graphics.drawImage(Ufo, (int) posXUfo, (int) posYUfo, 255, 200, null);
            graphics.drawImage(Astronaut, 150, (int) posY3, 150, 150, null);
            graphics.drawImage(Rakete, getWidth() / 2 - 50, (int) posYRakete, 100, 235, null);
        }

        //Bedingungen für das Zeichnen des Fallschirms
        if (simulation == true & fallschirmAn == true)
        {
            int breite = r * 4; //Fallschrim Doppelt so breit wie der Körper
            int posX = getWidth() / 2 - breite / 2;
            double posY = posYK-breite; //lässt die Seile des Fallschirms etwas verschwinden.
            graphics.drawImage(Fallschirm, posX, (int) posY, breite, breite, null);
        }

        //Bedingungen für das Zeichnen verschiedenen Kugeln
        if (laborwerte != null & koerperauswahl == "Kugel")
        {
            switch (material)
            {
                case "Stahl":
                    graphics.drawImage(Stahlkugel, (int) posXK, (int) posYK, r * 2, r * 2, null);
                    break;

                case "Holz":
                    graphics.drawImage(Holzkugel, (int) posXK, (int) posYK, r * 2, r * 2, null);
                    break;

                case "Styropor":
                    graphics.drawImage(Styroporkugel, (int) posXK, (int) posYK, r * 2, r * 2, null);
                    break;

                case "Wasser":
                    graphics.drawImage(Wasserkugel, (int) posXK, (int) posYK, r * 2, r * 2, null);
                    break;
            }
        }

        //Bedingungen für das Zeichnen verschiedenen Kegeln
        if (laborwerte != null & koerperauswahl == "Kegel")
        {
            double posX = posXK - r;
            switch (material)
            {
                case "Stahl":
                    graphics.drawImage(Stahlkegel, (int) posX, (int) posYK - 10, r * 4, r * 2, null);
                    break;

                case "Holz":
                    graphics.drawImage(Holzkegel, (int) posX, (int) posYK, r * 4, r * 2, null);
                    break;

                case "Styropor":
                    graphics.drawImage(Styroporkegel, (int) posX, (int) posYK, r * 4, r * 2, null);
                    break;

                case "Wasser":
                    graphics.drawImage(Wasserkegel, (int) posX, (int) posYK, r * 4, r * 2, null);
                    break;
            }
        }

        //Zeichnet die Vektoren (Objekt, x, y, Breite, Länge, null)
        if (simulation == true & f_gAn == true) //Sorgt dafür, dass die Pfeilspitze nicht zu Beginn schon gezeichnet wird.
        {
            //Zeichnet Vektor F_g
            graphics.drawImage(gewichtsKraftKoerper, (int) posXVek, (int) posYVek, breiteVek, (int) laengeVekFg, null);
            graphics.drawImage(gewichtsKraftSpitze, (int) posXVek, (int) posYVek + (int) laengeVekFg, breiteVek, breiteVek, null);
        }

        //laengeVekKraft > 1 -> Nicht "0", weil aufgrund der Verwendung von double wird der Wert nie "0" und Pfeilspitze würde stehen 
        if (laengeVekKraft > 1 & simulation == true & laengeVekLuftKra != 0 & resKraftAn == true)
        {
            //Zeichnet Vektor F_Res
            graphics.drawImage(KraftKoerper, (int) posXVek + 50, (int) posYVek, breiteVek, (int) laengeVekKraft, null);
            graphics.drawImage(KraftSpitze, (int) posXVek + 50, (int) (posYVek + laengeVekKraft), breiteVek, breiteVek, null);
        }

        if (laengeVekGesch != 0 & geschwAn == true) //Sorgt dafür, dass die Pfeilspitze nicht zu Beginn schon gezeichnet wird.
        {
            //Zeichnet Vektor v
            graphics.drawImage(vSpitze, (int) posXVek - 50, (int) (posYVek + laengeVekGesch), breiteVek, breiteVek, null);
            graphics.drawImage(vKoerper, (int) posXVek - 50, (int) posYVek, breiteVek, (int) laengeVekGesch, null);
        }

        if (laengeVekLuftKra != 0 & luftKraftAn == true) //Abfrage über die Checkbox?!
        {
            //Zeichnet Vektor F_Luft
            graphics.drawImage(luftKraftKoerper, (int) posXVek, (int) posYVek, breiteVek, (int) laengeVekLuftKra, null);
            graphics.drawImage(luftKraftSpitze, (int) posXVek, (int) (posYVek + laengeVekLuftKra) - breiteVek, breiteVek, breiteVek, null);
        }
    }

    /**
    * Methode grundzustand() wird im Konstruktor aufgerufen und setzt die 
    * Anfangswerte für die globabeln Variablen fest.
    * Des Weiteren werden die Bilder geladen. 
    * Zeck: Macht den Konstruktor übersichtlicher.
    */
    public void grundZustand()
    {
        // Startbedingungen
        resKraftAn = true;
        f_gAn = true;
        luftKraftAn = true;
        geschwAn = true;
        aufbau = false;
        simulation = false;
        fallschirmAn = false;
        luftwiderstand = false;
        datenPunkt = 0;
        geschwTimer = 20;

        planet = "Planet";
        koerperauswahl = "Körperform";
        material = "Material";

        posY1 = 0;
        posY2 = 300;
        posY3 = 600;
        posY4 = 800;
        posY5 = 100;
        posXUfo = -50;
        posYUfo = 1000;
        posYTodesstern = 1500;
        groesseWolke = 200;
        posYHG = 0;
        posYDrohne = 0;
        posYK = 900;
        posYRakete = 900;

        zoomFaktorKraft = 0;

        bewFaktor = 2;
        bewFaktorHG = 0.05;

        groesseDrohne = 0;
        r = 1;

        laengeVekGesch = 0;
        laengeVekKraft = 0;
        laengeVekLuftKra = 0;
        breiteVek = 35;

        //  Hintergrund und Wolken werden geladen       
        try
        {
            StartHintergrund = ImageIO.read(getClass().getResource("/Bilder/StartHintergrund.png"));

            HintergrundErde = ImageIO.read(getClass().getResource("/Bilder/Hintergrund.png"));
            HintergrundMond = ImageIO.read(getClass().getResource("/Bilder/Mondoberfläche.png"));
            Wolke1 = ImageIO.read(getClass().getResource("/Bilder/Wolke1.png"));
            Wolke2 = ImageIO.read(getClass().getResource("/Bilder/Wolke2.png"));
            Wolke3 = ImageIO.read(getClass().getResource("/Bilder/Wolke3.png"));
            Wolke4 = ImageIO.read(getClass().getResource("/Bilder/Wolke4.png"));
            Wolkenband = ImageIO.read(getClass().getResource("/Bilder/Wolkenband.png"));
            Drohne = ImageIO.read(getClass().getResource("/Bilder/DrohneMitSeil.png"));
            Ufo = ImageIO.read(getClass().getResource("/Bilder/Alien.png"));
            Rakete = ImageIO.read(getClass().getResource("/Bilder/RaketeMitSeil.png"));
            Erde = ImageIO.read(getClass().getResource("/Bilder/Erde.png"));
            Astronaut = ImageIO.read(getClass().getResource("/Bilder/Astronaut.png"));
            Todesstern = ImageIO.read(getClass().getResource("/Bilder/Todesstern.png"));

            Stahlkugel = ImageIO.read(getClass().getResource("/Bilder/Stahlkugel.png"));
            Holzkugel = ImageIO.read(getClass().getResource("/Bilder/Holzkugel.png"));
            Styroporkugel = ImageIO.read(getClass().getResource("/Bilder/Styroporkugel.png"));
            Wasserkugel = ImageIO.read(getClass().getResource("/Bilder/Wasserkugel.png"));
            Stahlkegel = ImageIO.read(getClass().getResource("/Bilder/Stahlkegel.png"));
            Wasserkegel = ImageIO.read(getClass().getResource("/Bilder/Wasserkegel.png"));
            Holzkegel = ImageIO.read(getClass().getResource("/Bilder/Holzkegel.png"));
            Styroporkegel = ImageIO.read(getClass().getResource("/Bilder/Styroporkegel.png"));
            Fallschirm = ImageIO.read(getClass().getResource("/Bilder/Fallschirm.png"));

            vKoerper = ImageIO.read(getClass().getResource("/Bilder/VektorKoerperGruen.png"));
            vSpitze = ImageIO.read(getClass().getResource("/Bilder/VektorSpitzeGruen.png"));
            KraftKoerper = ImageIO.read(getClass().getResource("/Bilder/VektorKoerperRot.png"));
            KraftSpitze = ImageIO.read(getClass().getResource("/Bilder/VektorSpitzeRot.png"));
            gewichtsKraftKoerper = ImageIO.read(getClass().getResource("/Bilder/VektorKoerperGelb.png"));
            gewichtsKraftSpitze = ImageIO.read(getClass().getResource("/Bilder/VektorSpitzeGelb.png"));
            luftKraftKoerper = ImageIO.read(getClass().getResource("/Bilder/VektorKoerperBlau.png"));
            luftKraftSpitze = ImageIO.read(getClass().getResource("/Bilder/VektorSpitzeBlau.png"));
        }

        catch (IOException ex)
        {
            Logger.getLogger(Labortisch.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ein Bild wurde nicht gefunden");
        }
    }

    /**
     * Setzt den boolean fallschirmAn auf true wenn Checkbox "mit Fallschirm"
     * aktiviert ist.
     */
    public void setFallschirmAn()
    {
        if (Labor.gibLaborInstanz().getFallschirmCheck().isSelected() == true)
        {
            fallschirmAn = true;
        }
    }

    /**
     * Wenn der Luftkraft-Button gedrückt ist, wird der boolean "LuftkraftAn" 
     * auf true gesetzt.
     */
    public void setLuftwiederstandAn()
    {
        if (Labor.gibLaborInstanz().getLuftwiderstandCheck().isSelected() == true)
        {
            luftwiderstand = true;
        }
    }

    /**
     * Methode verändert den boolean f_gAn.
     */
    public void setFgAn()
    {
        f_gAn = !f_gAn;
        repaint();
    }

    /**
     * Methode verändert den resKraftAn.
     */
    public void setKraftpfeilAn()
    {
        resKraftAn = !resKraftAn;
        repaint();
    }

    /**
     * Methode verändert den geschwAn.
     */
    public void setGeschwindigkeitAn()
    {
        geschwAn = !geschwAn;
        repaint();
    }

    /**
     * Methode verändert den boolean luftKraftAn.
     */
    public void setLuftKraftAn()
    {
        luftKraftAn = !luftKraftAn;
        repaint();
    }

    /**
     * Legt den Zoomfaktor fest und sorgt dafür, dass die Kraftvektoren die
     * Größe des Panels nutzen. Dem Vektor steht dabei nur die halbe Höhe des
     * Panels zur Verfügung. Der Vektor für Fg ist somit für alle Körper gleich
     * lang.
     */
    public void setZoomfaktor()
    {
        zoomFaktorKraft = (getHeight() / 2 - 50) / datenTransfer.getFG();
        zoomFaktorGesch = (getHeight() / 3) / datenTransfer.getvMax();
    }
  
    /**
     * Methode verändert den Bewegungsfaktor wenn Styroporkugel und mit
     * Luftwiderstnad gewählt wurde.
     * Grund: Bei "normalem Bewegungsfaktor würden sich die Wolken aufgrund der 
     * geringen Geschwindigkeit kaum bewegen.
     */
    public void setBewegungsfaktor()
    {
        if (planet == "Mond")
        {
            bewFaktor = 1;
        }
        
        if (planet == "Erde" & luftwiderstand == true)          
            {
            setLuftwiederstandAn();
            
            if (material == "Styropor" )
                {
                    bewFaktor = 20;
                }
        
            if (material == "Holz")
            {
                bewFaktor = 10;
            }
        }
        
    }

    /**
     * Sorgt dafür, dass die Wolken erneut durchs Bild laufen. 
     * Die Zahlen entsprechen der Höhe der Wolken
     */
    public void setWolken()
    {
        if (posY1 < -90)
        {
            posY1 = getHeight();
        }
        if (posY2 < -100)
        {
            posY2 = getHeight();
        }
        if (posY3 < -100)
        {
            posY3 = getHeight();
        }
        if (posY4 < -groesseWolke)
        {
            posY4 = getHeight();
            posX4 = -100 + Math.random() * 300;
            groesseWolke = 100 + Math.random() * 200;
        }
    }

    /**
     * Sorgt dafür, dass die Hintergrundobjekte beim Mondhintergrund
     * erneut durchs Bild laufen. 
     */
    public void setMondObjekte()
    {
        if (posYTodesstern < -getHeight())
        {
            posYTodesstern = getHeight();
        }

        if (posY3 < -getHeight())
        {
            posY3 = getHeight();
        }
    }

    @Override
    public LaborWerte getLaborWerte()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLaborWerte(LaborWerte laborwerte)
    {
        this.laborwerte = laborwerte;
    }

    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }

    @Override
    public void datenReset()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static ExpFeld getExpFeldInstanz()
    {
        return expFeldInstanz;
    }

    public void setStatusZaehler(int i)
    {
        datenPunkt = i; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DTO getDatenTransfer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDatenTarnsfer(DTO datenTransfer)
    {
        this.datenTransfer = datenTransfer;
        this.daten = datenTransfer.getDatenPunkte();
    }
}
