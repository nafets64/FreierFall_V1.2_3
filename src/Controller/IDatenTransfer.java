package Controller;

import Modell.DTO;

/**
 * Dieses Interface stellt den Datenaustausch zwischen den beteilgten Klassen
 * sicher.
 *
 * @author stefanscherle
 */
public interface IDatenTransfer
{

    /**
     * Der Inahlt der Daten wird gelöscht.
     */
    public void datenReset();

    /**
     * Daten werden weitergereicht
     *
     * @return Datenpaket aus der Berechnung wird weitergereicht
     */
    public DTO getDatenTransfer();

    /**
     * Daten werden gesetzt
     *
     * @param datenTransfer Datenpaket wird übergeben
     */
    public void setDatenTarnsfer(DTO datenTransfer);

}
