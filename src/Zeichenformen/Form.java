package Zeichenformen;

import java.awt.Graphics;

/**
 * Gemeinsame Oberklasse für Kreis und Dreieck
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 */
public abstract class Form
{
    private final double t;
    private final int x;
    private final int y;
    private final int radius;

    /**
     * Konstruktor.
     *
     * @param t
     * @param x
     * @param y
     * @param radius
     */
    public Form(double t, int x, int y, int radius)
    {
        this.t = t;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public double getT()
    {
        return t;
    }

    /**
     *
     * @return
     */
    public int getX()
    {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY()
    {
        return y;
    }

    /**
     *
     * @return
     */
    public int getRadius()
    {
        return radius;
    }

    /**
     * Zeichnet die Form.
     *
     * @param graphics
     */
    public abstract void zeichne(Graphics graphics);
}
