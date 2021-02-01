/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 * Dieses Enum enthält die auswählbaren Planten samt ihren Otsfaktoren
 *
 * @author stefanscherle
 */
public enum Planet
{
    /**
     * Planeteneigenschaften Erde: Name, Ortsfaktor in der Eionheit m/s^2.
     */
    Erde("Erde", 9.81),
    /**
     * Planeteneigenschaften Mond: Name, Ortsfaktor in der Eionheit m/s^2.
     */
    Mond("Mond", 1.62);
    private final String name;
    private final Double ortsFaktor;

    Planet(String name, Double otsFaktor)
    {
        this.name = name;
        this.ortsFaktor = otsFaktor;
    }

    /**
     * Rückgabe des Namen des Planeten
     *
     * @return Namen des Planeten
     */
    public String getName()
    {
        return name;
    }

    /**
     * Rückgabe des Otsfaktors des Planeten
     *
     * @return Array der Plantennamen
     */
    public Double getOrtsFaktor()
    {
        return ortsFaktor;
    }

    /**
     * Gibt die Sammlung der Planetennamen als String mit dem Auswahlbegriff
     * "Planet" zurück
     *
     * @return Array der Planetennamen
     */
    public static String[] getPlanetenAuswahl()
    {
        String[] planetnamen = new String[Planet.values().length + 1];
        Planet[] planeten = Planet.values();
        planetnamen[0] = "Planet";
        for (int i = 1; i <= planeten.length; i++)
        {
            planetnamen[i] = planeten[i - 1].name;
        }
        return planetnamen;
    }
}
