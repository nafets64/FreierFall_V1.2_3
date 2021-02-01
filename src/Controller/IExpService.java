/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 * Diese Interface gewährleiestet den Austausch der benötigten Methoden für die
 * Simulation.
 *
 * @author stefanscherle
 */
public interface IExpService
{

    /**
     * Der Aufbau im Experimetierfeld startet.
     */
    public void aufbau();

    /**
     * Die Simulation im Experiementerfeld startet.
     */
    public void simulation();

}
