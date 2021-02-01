/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 * Dieses Enum enthält alle spezifischen Körpereigenschaften
 *
 * Version 5.1.21
 *
 * @author stefanscherle
 */
public enum Koerper
{
    /**
     * Kugeleigenschaften: Name, Widerstandsbeiwert cw,
     * Volumenproportinalitätsfaktor.
     */
    Kugel("Kugel", 0.45, 1.33),
    /**
     * Kegeleigenschaften: Name, Widerstandsbeiwert cw,
     * Volumenproportinalitätsfaktor.
     */
    Kegel("Kegel", 0.52, 0.33);
    
    // 
    private final String name;
    private final double cWert;
    private final double volumenProp;

    Koerper(String name, double cWert, double prop)
    {
        this.name = name;
        this.cWert = cWert;
        this.volumenProp = prop;
    }

    /**
     * Gibt den Name des Körpers als String zurück.
     *
     * @return Name des Köreprs
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt den Widerstandsbeiwert cw des Körpers als Zahl zurück
     *
     * @return Widerstandsbeiwert cw des Körpers
     */
    public double getcWert()
    {
        return cWert;
    }

    /**
     * Gibt den Proprtionalitätsfaktor zur Volumenberechnung zurück, soll heißen
     * der Rückgabewert muss nur noch mit dem Radius^3 in m multipliziert
     * werden.
     *
     * @return Proprtionalitätsfaktor
     */
    public double getVolumenProp()
    {
        return volumenProp;
    }

    /**
     * Gibt die Sammlung der Körpernamen als String mit dem Auswahlbegriff
     * "Körperform" zurück
     *
     * @return Array der Körpernamen
     */
    public static String[] getKoerperAuswahl()
    {
        String[] koerpernamen = new String[Koerper.values().length + 1];
        Koerper[] koerper = Koerper.values();
        koerpernamen[0] = "Körperform";
        for (int i = 1; i <= koerper.length; i++)
        {
            koerpernamen[i] = koerper[i - 1].name;
        }
        return koerpernamen;
    }
}
