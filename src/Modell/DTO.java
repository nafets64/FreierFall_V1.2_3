package Modell;

import java.util.List;

/**
 * Diese Klasse übernimmt die Erzeugung des DatenTransferObjekts in dem alle
 * berechneten Daten gesammelt an die anderen Klassen weitergegeben werden 
 * @author stefanscherle Und Gerald
 */
public class DTO
{
    private final List<Daten> datenPunkte;
    private final double vMax; // die größte angenommene Geschwindigkeit - die kleinste ist 0
    private final double aMax; // der Ortsfaktor des jeweiligen Planeten
    private final double fallDauer; // die Zeit, die im Diagramm dargestellt werden muss
    private final double FG; //
    private final int iSchirm; //

    public DTO(List<Daten> datenPunkte, double vMax, double aMax, double fallDauer, double FG, int iSchirm)
    {
        this.datenPunkte = datenPunkte;
        this.vMax = vMax;
        this.aMax = aMax;
        this.fallDauer = fallDauer;
        this.FG = FG;
        this.iSchirm = iSchirm;
    }

    /**
     *
     * @return
     */
    public List<Daten> getDatenPunkte()
    {
        return datenPunkte;
    }

    public double getvMax()
    {
        return vMax;
    }

    public double getaMax()
    {
        return aMax;
    }

    public double getaMin()
    {
        // die kleinste erreichte Fallbeschleunigung - außer beim Fallschirmsprung 0
        double aMin = 0;
        return aMin;
    }

    public double getFallDauer()
    {
        return fallDauer;
    }

    public double getFG()
    {
        return FG;
    }

    public int getiSchirm()
    {
        return iSchirm;
    }

}
