package Controller;

import View.LaborBereich.LaborWerte;

/**
 * Dieses Interface stellt den Werteaustausch zwischen den beteilgten Klassen
 * sicher soll heißem die durch den Benutzer eingestellten Werte werden an die
 * Berechnung wietegegegben Version 5.1.21
 *
 * @author stefanscherle
 */
public interface IWerteTransfer
{

    /**
     * Hier werden die eingestellten Werte aus dem Labor weitergereicht.
     *
     * @return Laborwerte werden weitergereicht
     */
    public LaborWerte getLaborWerte();

    /**
     * Hier werden die eingestellten Werte an der entsprecehnden Stelle gesetz.
     *
     * @param laborwerte Laborwerte werden übergeben
     */
    public void setLaborWerte(LaborWerte laborwerte);
}
