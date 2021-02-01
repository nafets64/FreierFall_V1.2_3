package Controller;

/**
 * Diese ENUM enthält alle spezifische Eigenschaften der aufgelisteten
 * Materialien
 *
 * @author stefanscherle
 */
public enum Material
{
    /**
     * Materialeigenschaften Stahl : Name, Dichte in der Einheit kg/m^3.
     */
    STAHL("Stahl", 7900),
    /**
     * Materialeigenschaften Styropor : Name, Dichte in der Einheit kg/m^3.
     */
    STYROPR("Styropor", 40),
    /**
     * Materialeigenschaften Holz : Name, Dichte in der Einheit kg/m^3.
     */
    HOLZ("Holz", 600),
    /**
     * Materialeigenschaften Wasser : Name, Dichte in der Einheit kg/m^3.
     */
    WASSER("Wasser", 1000);
    private final String name;

    private final int dichteKoerper;

    Material(String name, int dichtekoerper)
    {
        this.dichteKoerper = dichtekoerper;
        this.name = name;
    }

    /**
     * Rückgabe der Dichte des Materials
     *
     * @return Dichte des Materials
     */
    public int getDichteKoerper()
    {
        return dichteKoerper;
    }

    /**
     * Rückgabe des Namen des Materials
     *
     * @return Namen des Materials
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt die Sammlung der Materialiennamen als String mit dem Auswahlbegriff
     * "Material" zurück
     *
     * @return Array der Materialnamen
     */
    public static String[] getMaterialAuswahl()
    {
        String[] materialnamen = new String[Material.values().length + 1];
        Material[] materials = Material.values();
        //Stream.of(materials).map((material) -> material.name);
        materialnamen[0] = "Material";
        for (int i = 1; i <= materials.length; i++)
        {
            materialnamen[i] = materials[i - 1].name;
        }
        return materialnamen;
    }
}
