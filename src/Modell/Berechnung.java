package Modell;

import Controller.IDatenTransfer;
import Controller.IWerteTransfer;
import View.LaborBereich.LaborWerte;
import java.util.LinkedList;
import java.util.List;

/**
 * In dieser Klasse werden alle benötigten Werte berechnet und gespeichert.
 *
 * @author Gerald
 */
public class Berechnung implements IWerteTransfer, IDatenTransfer
{

    // Beziehungsklassen 
    private LaborWerte laborwerte;

    // Variablen aus der Klasse Benutzerwerte
    private double schrittWeite; // Länge des Zeitintervalls in s
    private double dichteKoerper; // Dichte des Körpers in kg/dm^3
    private double dichteLuft; // Dichte der Luft in kg/m^3
    private double ortsFaktor; // Fallbeschleunigung am Planet/Mond in m/s^2

    // Berechnete Daten des Körpers
    private double volumenKoerper; // Volumen des Körpers in dm^3

    // Berechnete Werte des Experiments
    private double zeitPunkt; // ein Zeitpunkt des Experiments
    private double beschleunigung; // Beschleunigung des Körpers zum jeweiligen Zeitpunkt
    private double geschwindigkeit; // Geschwindigkeit des Körpers zum jeweiligen Zeitpunkt
    private double weg; // zurückgelegter Weg des Körpers zum jeweiligen Zeitpunkt

    // Hilfsvariablen für die Berechnung der Werte.
    private double cw;
    private double rohLuft;
    private double m;
    private double FG;
    private double A;
    private double v;
    private double s;
    private double a;
    private double dt;
    private boolean erde; // Wahr, wenn der gewählte Planet die Erde ist.
    int i;
    int j;

    // Werte für die Diagramm-Grenzen
    private double vMax; // die größte angenommene Geschwindigkeit - die kleinste ist 0
    private double aMax = 0; // Beim Fallschirmsprung der größte Wert der Fallbeschleunigung
    private double fallDauer; // die Zeit, die im Diagramm dargestellt werden muss
    private int iSchirm = 10000; // Der Schritt, bei dem der Fallschirm gezogen wird
                                 // Sonst ein Wert der deutlich gößer als die Anzahl der Schritte ist

    private final List<Daten> datenPunkte;

    /**
     *
     */
    public Berechnung()
    {
        // Initialisierung der Liste in der die berechneten Werte gespeichert werden
        datenPunkte = new LinkedList<>();
    }

    /**
     * Berechnet alle benötigten Werte
     */
    public void berechneWerte()
    {
        // Die Anfangswerte werden gesetzt, alte Daten werden gelöscht.        
        datenReset();
        j = 1;
        setDt(0.1);
        rohLuft = getDichteLuft();
        m = getMasseKoerper();
        FG = getOrtsFaktor() * m;
        int n = 1000;

        // Verhindert im Fall Mond oder kein Luftwiderstand auf der Erde
        // dass mehr Werte als benötigt berechnet werden.
        if(!laborwerte.isLuftwiderstandAn() || !erde)
        {
            n = 501;
        }
        
        // Falls mit der vorgegebenen Schrittweite zu wenige Werte berechnet 
        // werden wird die Schrittweite verkleinert, bis die Anzahl der 
        // berechneten Werte zweischen 500 und 1000 liegt. Dafür werden zu 
        // Beginn die Anfangswerte wieder zurückgesetzt und alte Daten gelöscht.
        while (i < 499)
        {
            datenReset();
            cw = getC_WertKoerper();
            A = getAngriffsflaeche();
            dt = getDt() / j;
            j++;
            
            // Die Rechnung wird solange durchgeführt bis entweder die Beschleunigung
            // einen kleinst-Wert unterschreitet, eine gewisse Anzahl an Werten 
            // berechnet wurde (wenn sich im freien Fall ohne Luftwiderstand 
            // die Beschleunigung nicht ändert) oder wenn die Fallgeschwindigkeit 
            // einen Höchstwert erreicht hat.
            while (a >= 0.01 && i < n && v < 300)
            {
                rechneAus();
                a = beschleunigung;
            }
        }
        setDt(dt);
        vMax = geschwindigkeit;
        
        // Falls der Benutzer den Fallschirmsprung gewählt hat, wird im Fall der 
        // Erde die Berechnung mit den Werten des geöffneten Fallschirms 
        // weitergeführt, sofern auch er Luftwiderstand ausgewählt wurde.
        if (laborwerte.isFallschirm() && erde && laborwerte.isLuftwiderstandAn())
        {
            cw = 0.9; // Für unseren Fallschirm gewählter Wert.
            A = getAngriffsflaeche() * 50; // Die 50-fache Angriffsfläche des Körpers
            v = geschwindigkeit;
            s = weg;
            dt = getDt();
            int z = i + i / 3; // Die Anzahl der weiteren Schritte wird mit der 
                               // Anzahl der bisherigen Schritte angepasst.
            
            // Die Berechnung wird einen Schritt weitergeführt, damit der Wert
            // der maximalen Beschleunigung (die nun erstmal in die Gegenrichtung
            // zeigt) festgehalten werden kann.
            rechneAus();
            aMax = beschleunigung; // Könnte im Diagramm berücksichtigt werden
            iSchirm = i;
            // Die weiteren Schritte werden berechnet.
            while (i < z)
            {
                rechneAus();
            }
        }
        fallDauer = zeitPunkt;
    }

    /**
     * Führt die bei der Berechnung notwendigen Schritte aus nach der in der 
     * Physik der Jahrgangsstufe 10 verwendeten Methode der kleinen Schritte aus.
     */
    private void rechneAus()
    {
        zeitPunkt = i * dt;
        // Luftwiderstandskraft des Körpers zum jeweiligen Zeitpunkt
        double luftKraft = 0.5 * cw * rohLuft * v * v * A;
        // Resultierende Kraft zum jeweiligen Zeitpunkt
        double resKraft = FG - luftKraft;
        beschleunigung = resKraft / m;
        geschwindigkeit = v + beschleunigung * dt;
        weg = s + geschwindigkeit * dt;
        // die jeweilige Wegänderung zum entsprechnenden Zeitpunkt
        double wegAenderung = weg - s;

        //Abspeichern im Datensatz
        Daten datenPunkt = new Daten(zeitPunkt, beschleunigung, geschwindigkeit, weg,
                wegAenderung, luftKraft, FG, resKraft);

        //Anlegen der Listen
        datenPunkte.add(datenPunkt);

        i++;
        v = geschwindigkeit;
        s = weg;
    }

 
    /**
     * Gibt die aus dem Volumen und der Dichte des gewählten Körpers berechnete
     * Masse in kg aus.
     */
    private double getMasseKoerper()
    {
        //Masse des Körpers in kg
        double masseKoerper = (getVolumenKoerper() * getDichteKoerper());
        return masseKoerper;
    }

    /**
     * Gibt das aus dem eingestellten Radius berechnete Volumen des Körpers 
     * in dm^3 aus.
     */
    private double getVolumenKoerper()
    {
        String koerperindex = laborwerte.getKoerper();
        double x = laborwerte.getRadius(); // Radius in mm
        double r = x / 100; // Radius in dm
        switch (koerperindex)
        {
            case "Kugel": // Kugel
                volumenKoerper = (4 * Math.PI * r * r * r / 3);
                break;
            case "Kegel": // Kegel mit der Hoehe Radius;
                volumenKoerper = (Math.PI * r * r * r / 3);
                break;
        }
        return volumenKoerper;
    }

    /**
     * Gibt den C-Wert des Körpers aus. Im Fall ohne Luftwiderstand ist er 0.
     */
    private double getC_WertKoerper()
    {
        // Luftwiderstandsbeiwert
        double c_WertKoerper = 0;

        if (laborwerte.isLuftwiderstandAn() == true)
        {
            // der Körperindex gibt an, welche Form der gewählte Körper besitzt
            String koerperindex = laborwerte.getKoerper();
            switch (koerperindex)
            {
                case "Kugel": // Kugel
                    c_WertKoerper = 0.45;
                    break;
                case "Kegel": // Kegel mit der Höehe Radius;
                    c_WertKoerper = 0.52;
                    break;
            }
        }
        return c_WertKoerper;
    }

    /**
     * Gibt den aus dem eingestellten Radius des Körpers berechnete
     * Angriffsfläche des Körpers in m^2 aus.
     */
    private double getAngriffsflaeche()
    {
        double x = laborwerte.getRadius(); // Radius in mm
        double r = x / 1000;  // Radius in m
        // nach unten zeigende Angriffsfläche des Körpers in dm^2
        double flaecheKoerper = (Math.PI * r * r);
        return flaecheKoerper;
    }

    /**
     * Gibt die Dichte des Körpers je nach gewähltem Materialindex in kg/dm^3 
     * aus.
     */
    private double getDichteKoerper()
    {
        // der Materialindex gibt an, aus welchem Material der gewählte Körper 
        // besteht
        String koerper = laborwerte.getMaterial();

        switch (koerper)
        {
            case "Stahl":
                dichteKoerper = 7.900;        //Stahl      in kg/dm^3
                break;
            case "Styropor":
                dichteKoerper = 0.040;        //Styropr    in kg/dm^3
                break;
            case "Holz":
                dichteKoerper = 0.690;        //Buchenholz in kg/dm^3
                break;
            case "Wasser":
                dichteKoerper = 1.000;        //Wasser     in kg/dm^3
                break;
        }
        return dichteKoerper;
    }

    /**
     * Gibt die bei der Berechnung verwendete Schrittweite weiter.
     * @return schrittWeite
     */
    public double getDt()
    {
        return schrittWeite;
    }
   
    /**
     * Setzt die in der Berechnung verwendete Schrittweite.
     */
    private void setDt(double schrittWeite)
    {
        this.schrittWeite = schrittWeite;
    }

    /**
     * Gibt den zum jeweiligen Körper gehörigen Ortsfaktor weiter.
     * Außerdem wird die Variable erde auf wahr oder falsch gesetzt.
     */
    private double getOrtsFaktor()
    {
        String planet = laborwerte.getPlanet();

        switch (planet)
        {
            case "Erde": // Erde
                ortsFaktor = 9.81;
                erde = true;
                break;
            case "Mond": // Mond
                ortsFaktor = 1.62;
                erde = false;
                break;
        }
        return ortsFaktor;
    }

    /**
     * Gibt die berechnete Dichte der Luft in Abhängigkeit von der ausgewählten
     * Höhe weiter. Dazu wird die Formel von der folgenden Website verwendet:
     * https://wind-data.ch/tools/luftdichte.php
     * Diese Näherungsformel gilt für trokene Luft über der Schweiz.
     * Im Falle des Mondes ist die Dichte 0.
     */
    private double getDichteLuft()
    {
        String planet = laborwerte.getPlanet();
        switch (planet)
        {
            case "Erde": // Erde
                double h = laborwerte.getFallHoehe();
                double y = 0.000104 * h;
                double x = Math.exp(y);
                dichteLuft = 1.24 / x;
                break;
            case "Mond": // Mond
                dichteLuft = 0;
                break;
        }
        return dichteLuft;
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

    /**
     * Stellt die verwendeten Variablen wieder auf den Anfangszustand.
     */
    @Override
    public void datenReset()
    {
        v = 0;
        s = 0;
        a = 1;
        vMax = 0;
        aMax = 0;
        fallDauer = 0;
        i = 1;
        datenPunkte.clear();
    }

    /**
     * Gibt die berechneten Daten weiter.
     */
    @Override
    public DTO getDatenTransfer()
    {
        DTO datenTransfer = new DTO(datenPunkte, vMax, aMax, fallDauer, FG, iSchirm);
        return datenTransfer;
    }

    @Override
    public void setDatenTarnsfer(DTO datenTransfer)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
