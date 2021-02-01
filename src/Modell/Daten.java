package Modell;
import java.io.Serializable;


/**
 * In dieser Klasse werden die Daten aus der Berechnungsklasse gespeichert.
 * Sie werden den anderen Klassen zur Verfügung gestellt
 *
 * @author Gerald
 */
public class Daten implements Serializable
{
    
    // Berechnete Werte aus der Rechenklasse
    private final double zeitPunkt; // ein Zeitpunkt des Experiments
    private final double beschleunigung; // Beschleunigung des Körpers zum jeweiligen Zeitpunkt
    private final double geschwindigkeit; // Geschwindigkeit des Körpers zum jeweiligen Zeitpunkt
    private final double weg; // zurückgelegter Gesamtweg des Körpers zum jeweiligen Zeitpunkt
    private final double wegAenderung; // die jeweilige Wegänderung zum entsprechnenden Zeitpunkt
    private final double resKraft; // Resultierende Kraft zum jeweiligen Zeitpunkt
    private final double FG; // Gewichtskraft zum jeweiligen Zeitpunkt
    private final double luftKraft; // Luftwiderstandskraft des Körpers zum jeweiligen Zeitpunkt

/*
 * Konstruktor
 */    
    public Daten (double zeitPunkt, double beschleunigung, double geschwindigkeit, 
    double weg, double wegAenderung, double luftKraft, double FG, double resKraft)
    {
        this.zeitPunkt = zeitPunkt;
        this.beschleunigung = beschleunigung; 
        this.geschwindigkeit = geschwindigkeit;
        this.weg = weg;
        this.wegAenderung = wegAenderung;
        this.luftKraft = luftKraft;
        this.FG = FG;
        this.resKraft = resKraft;
    }
        
    public double getT()
    {
        return zeitPunkt;
    }

    public double getBeschleunigung()
    {
        return beschleunigung;
    }

    public double getGeschwindigkeit()
    {
        return geschwindigkeit;
    }

    public double getWeg()
    {
        return weg;
    }

    public double getLuftKraft()
    {
        return luftKraft;
    }

    public double getWegAenderung()
    {
        return wegAenderung;
    }

    public double getResKraft()
    {
        return resKraft;
    }

    public double getFG()
    {
        return FG;
    }
    
}
