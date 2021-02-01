package View.LaborBereich;

import Controller.Koerper;
import Controller.Material;
import java.io.Serializable;

/**
 * Diese Klasse nimmt alle eingestellten Werte des Labors entgegen und übergibt
 * diese in einem Wertepaket
 *
 * @author stefanscherle
 */
public class LaborWerte implements Serializable
{
    // Im labor eingestellte Werte
    private final int fallHoehe;
    private final String planet;
    private final int radius;
    private final String material;
    private final String koerper;
    private final boolean luftwiderstandAn;
    private final boolean fallschirm;
    // Daraus berechnete Stardaten
    private final double angriffsflaeche;
    private double cWert;
    private final double masse;

    /**
     *
     * @param fallHoehe Fallhöhe des Körpers
     * @param planet Planet / Otsfaktor
     * @param durchmesser Durchmesser des Körpers
     * @param material Materiel aus dem der Körper besteht
     * @param koerper Körperform
     * @param luftwiderstand mit Luftwiderstand
     * @param fallschirm mit Fallschirm
     */
    public LaborWerte(int fallHoehe, String planet, int durchmesser, String material, String koerper,
            boolean luftwiderstand, boolean fallschirm)
    {
        this.fallHoehe = fallHoehe;
        this.planet = planet;
        this.radius = (int) durchmesser / 2;
        this.material = material;
        this.koerper = koerper;
        this.luftwiderstandAn = luftwiderstand;
        this.fallschirm = fallschirm;

        double volumen = 0;
        double dichteKoerper = 0;
        for (Material materie : Material.values())
        {
            if (materie.getName() == material)
            {
                dichteKoerper = materie.getDichteKoerper();
                break;
            }
        }

        for (Koerper formen : Koerper.values())
        {
            if (formen.getName() == material)
            {
                cWert = formen.getcWert();
                volumen = formen.getVolumenProp() * Math.PI * radius * radius * radius * 0.000000001;
                break;
            }
        }

        masse = volumen * dichteKoerper;
        angriffsflaeche = Math.PI * radius * radius * 0.0001;

    }

    /**
     * Körperradius
     *
     * @return Radius des Körpers
     */
    public int getRadius()
    {
        return radius;
    }

    /**
     * KörperMaterial
     *
     * @return Materal aus dem der Körper besteht
     */
    public String getMaterial()
    {
        return material;
    }

    /**
     * KörperForm
     *
     * @return Name der Körperform
     */
    public String getKoerper()
    {
        return koerper;
    }

    /**
     * Die Querschnitttsfläche in m^2
     *
     * @return Größe der Querschnitsfläche
     */
    public double getAngriffsflaeche()
    {
        return angriffsflaeche;
    }

    /**
     * Widerstandsbeiwert der Körperform
     *
     * @return Wiederstandsbeiwert der gewählten Körperform
     */
    public double getcWert()
    {
        return cWert;
    }

    /**
     * Masse des Körpers
     *
     * @return Masse des ausgewählten Körpers
     */
    public double getMasse()
    {
        return masse;
    }

    /**
     * Höhe aus der der Körper fallen gelassen wird, WICHTIG für Luftdichte.
     *
     * @return Fallhöhe
     */
    public int getFallHoehe()
    {
        return fallHoehe;
    }

    /**
     * Ortsfaktor des gewählten Planeten
     *
     * @return Ortsfaktor g
     */
    public String getPlanet()
    {
        return planet;
    }

    /**
     * Luftwiderstand an oder aus
     *
     * @return true, falls luftwiderstand gewählt
     */
    public boolean isLuftwiderstandAn()
    {
        return luftwiderstandAn;
    }

    /**
     * mit odre ohne Fallschirm
     *
     * @return true, falls mit Fallschirm
     */
    public boolean isFallschirm()
    {
        return fallschirm;
    }

}
