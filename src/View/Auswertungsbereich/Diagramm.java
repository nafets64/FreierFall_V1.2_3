package View.Auswertungsbereich;

import Modell.Daten;
import Controller.Controller;
import Controller.IDatenTransfer;
import Modell.DTO;
import View.LaborBereich.Labor;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.*;

/**
 * Diese Klasse enthält den reinen Zeichenbereich der Auswertung also das
 * t-v-Diagramm und t-a-Diagramm
 *
 * Version 2.11.20
 *
 * @author stefanscherle
 */
public class Diagramm extends JPanel implements IDatenTransfer
{
    private static final Diagramm diagrammInstanz = new Diagramm();

    private boolean auswertungGeschwAn;
    private boolean auswertungBeschlAn;

    private int statusZaehler;
    private boolean start;

    private double maxGeschwindigkeit;
    private double maxZeitspanne;
    private double zoomFaktorX;
    private double zoomFaktorY;

    private List<Daten> daten;    // Messpunkte
    private DTO datenTransfer;

    private final int dx;          // Messungenauikeit

    /**
     * Konstruktor des Diagrammbereichs, legt auch Messfehler fest.
     *
     */
    private Diagramm()
    {
        auswertungGeschwAn = true;
        auswertungBeschlAn = true;
        statusZaehler = 0;
        dx = 4;    //Messgenauigkeit
    }

    /**
     * Zeichnemethode für die Koordinatensysteme samt Achsenbeschriftung und den
     * Messpunkten der Geschwindigkeit und Beschleunigung
     *
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Anlegen der Grundstruktur des Zeichnblatts
        koordinatenSystem(g);
        koordinatenAchsen(g);

        // Synchrones Zeichnen der Geschwindigkeitsmeßpunkte zur Simulation
        if (daten != null && statusZaehler < daten.size())
        {
            for (int i = 0; i < statusZaehler; i++)
            {
                double time;
                if (auswertungGeschwAn)
                {
                    g.setColor(Color.BLUE);
                    time = daten.get(i).getT();
                    double geschwindigkeit = daten.get(i).getGeschwindigkeit();
                    g.drawOval((int) (time * 50 * (1 / zoomFaktorX)) + 48,
                            (int) (geschwindigkeit * 50 * (1 / zoomFaktorY)) + 48, dx, dx);
                }
                if (auswertungBeschlAn)
                {
                    g.setColor(Color.RED);
                    time = daten.get(i).getT();
                    double beschleunigung = daten.get(i).getBeschleunigung();
                    g.drawOval((int) (time * 50 * (1 / zoomFaktorX)) + 48,
                            (int) (beschleunigung * 50) + 48, dx, dx);
                }
            }
            //statusZaehler++;
            if (statusZaehler == daten.size() - 1)
            {
                Controller.gibInstanz().setExperimentAn(false);      //Timerunterbrechung im Controller
                Labor.gibLaborInstanz().getStartButton().setText("Again");
            }
        }

    }

    /**
     * Anlegen des Koordinatensystems
     *
     * @param g
     */
    private void koordinatenSystem(Graphics g)
    {
        int breite = getWidth();
        int hoehe = getHeight();

        // Hintergrundgitter anlegen
        g.setColor(Color.lightGray);
        for (int x = 0; x <= breite; x = x + 10)
        {
            g.drawLine(x, 0, x, hoehe);
        }
        for (int y = 0; y <= hoehe; y = y + 10)
        {
            g.drawLine(0, y, breite, y);
        }

        // Anlegen der Koordinatenachsen
        g.setColor(Color.BLACK);
        g.drawLine(30, 50, getWidth() - 30, 50);  //x-Achse
        g.drawLine(getWidth() - 40, 45, getWidth() - 30, 50);  //x-Achsenpfeil
        g.drawLine(getWidth() - 40, 55, getWidth() - 30, 50);  //x-Achsenpfeil

        g.drawLine(50, 30, 50, getHeight() - 30); //y-Achse
        g.drawLine(45, getHeight() - 40, 50, getHeight() - 30); //y-Achsenpfeil
        g.drawLine(55, getHeight() - 40, 50, getHeight() - 30); //y-Achsenpfeil

    }

    /**
     * Anlegen der Koordinatenachsen.
     *
     * @param g
     */
    private void koordinatenAchsen(Graphics g)
    {
        // GrößenBeschriftung der x-Acshe
        g.drawString("t in s", getWidth() - 40, 70);

        // GrößenBeschriftung der y-Achse
        if (auswertungGeschwAn)
        {
            g.setColor(Color.BLUE);
            g.drawString("v", 20, getHeight() - 35);
            g.drawString("in m/s", 10, getHeight() - 20);
        }
        if (auswertungBeschlAn)
        {
            g.setColor(Color.RED);
            g.drawString("a", 70, getHeight() - 35);
            g.drawString("in m/s^2", 60, getHeight() - 20);
        }

        // Anlegen der Skalierung mit Beschriftung auf x-Achse
        for (int i = 2; i < getWidth() / 50; i++)
        {
            g.setColor(Color.BLACK);
            g.drawString("" + Math.round((i - 1) * zoomFaktorX * 100.0) / 100.0, i * 50 - 10, 40);
            g.drawLine(i * 50, 45, i * 50, 55);
        }

        // Anlegen der Skalierung mit Beschriftung auf y-Achse
        for (int i = 2; i < getHeight() / 50; i++)
        {
            if (auswertungGeschwAn)
            {
                g.setColor(Color.BLUE);
                g.drawString("" + (i - 1) * zoomFaktorY, 20, i * 50 + 5);
            }
            if (auswertungBeschlAn)
            {
                g.setColor(Color.RED);
                g.drawString("" + (i - 1), 65, i * 50 + 5);
            }
            g.drawLine(45, i * 50, 55, i * 50);   // Skalierungestriche
        }
    }

    /**
     * Zeichnet die berechneten Messpunkte in Kreisform mit einer
     * Messfehlerdarstellung von 4 Pixel.
     *
     */
    public void zeichneMesspunkte()
    {
        if (statusZaehler == 0)
        {
            setZoomFaktorX();
            setZoomFaktorY();
        }
        if (statusZaehler < daten.size() - 1)
        {
            repaint();
        }
    }

    /**
     * Zoomfaktor Zeitachse wird berechnet.
     */
    private void setZoomFaktorX()
    {
        maxZeitspanne = datenTransfer.getFallDauer();
        double masstabZeit = maxZeitspanne / 10;
        zoomFaktorX = Math.ceil((masstabZeit) * 30) / 40;
    }

    /**
     * Zoomfaktor für y-Achse wird berechnet.
     */
    private void setZoomFaktorY()
    {
        maxGeschwindigkeit = datenTransfer.getvMax();
        double masstabGroesse = maxGeschwindigkeit / 12;
        zoomFaktorY = Math.ceil((masstabGroesse) * 4) / 4;
    }

    /**
     * Blendet die t-v-Kennlinie ein oder aus.
     *
     * @param auswertungAn Boolean für An und Aus
     */
    public void setAuswertungGeschwAn(boolean auswertungAn)
    {
        this.auswertungGeschwAn = auswertungAn;
        statusZaehler = statusZaehler - 1;
        repaint();
    }

    /**
     * Blendet die t-a-Kennlinie ein oder aus.
     *
     * @param auswertungAn Boolean für An und Aus
     */
    public void setAuswertungBeschlAn(boolean auswertungAn)
    {
        this.auswertungBeschlAn = auswertungAn;
        statusZaehler = statusZaehler - 1;
        repaint();
    }

    /**
     * Singelton Instanz des Diagramms
     *
     * @return Singelton der Diagramm-Klasse
     */
    public static Diagramm getDiagrammInstanz()
    {
        return diagrammInstanz;
    }

    /**
     * Grundzustand der Daigramm-Klasse -> für Neustart.
     */
    public void grundZustand()
    {
        if (daten != null)
        {
            daten.clear();
            maxGeschwindigkeit = 0;
            double maxBeschleunigung = 0;
            maxZeitspanne = 0;
        }
        auswertungGeschwAn = true;
        auswertungBeschlAn = true;
        zoomFaktorX = 0;
        zoomFaktorY = 0;
        statusZaehler = 0;
    }

    /**
     * Hier wird der Status beim Zeichnen der Diagramme gesetz für Pause etc.
     *
     * @param statusZaehler Setzt den Status-Zustand im Diagramm
     */
    public void setStatusZaehler(int statusZaehler)
    {
        this.statusZaehler = statusZaehler;
    }

    /**
     * Nicht unterstütz, da keine Daten in der Klasse berechnet werden
     *
     * @return
     */
    @Override
    public DTO getDatenTransfer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Hier werden die Daten entgegengenommen
     *
     * @param datenTransfer DTO wird empfangen
     */
    @Override
    public void setDatenTarnsfer(DTO datenTransfer)
    {
        this.datenTransfer = datenTransfer;
        this.daten = datenTransfer.getDatenPunkte();
    }

    /**
     * Hier werden die Daten zurückgesetzt.
     *
     */
    @Override
    public void datenReset()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
