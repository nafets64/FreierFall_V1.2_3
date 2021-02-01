package Zeichenformen;

import java.awt.Graphics;

/**
 * Diese Klasse ermöglicht die Darstellung eines Dreicks mit Hilfe eiens
 * Polygons
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 */
public class Dreieck extends Form
{
    private final double t;
    private final int[] xPoints = new int[3];
    private final int[] yPoints = new int[3];
    private final int radius;

    /**
     *
     * @param t
     * @param x
     * @param y
     * @param radius
     */
    public Dreieck(double t, int x, int y, int radius)
    {
        super(t, x, y, radius);

        this.t = t;
        this.radius = radius;

        xPoints[0] = x;
        xPoints[1] = x + radius / 2;
        xPoints[2] = x + radius;
        yPoints[0] = y;
        yPoints[1] = y + radius;
        yPoints[2] = y;
    }

    /**
     *
     * @return ZeitPunkt
     */
    @Override
    public double getT()
    {
        return t;
    }

    /**
     * Liefert die x-Kordinate des Punktes
     *
     * @return Array der x-Koordinaten
     */
    public int[] getxPoints()
    {
        return xPoints;
    }

    /**
     * Liefert die y-Kordinate des Punktes
     *
     * @return Array der Y-Koordinaten
     */
    public int[] getyPoints()
    {
        return yPoints;
    }

    /**
     * Radius der Grundfäche des Kegels
     *
     * @return Radius
     */
    @Override
    public int getRadius()
    {
        return radius;
    }

    /**
     * Erzeugt ein Dreick mit Polygon
     *
     * @param graphics Dreick
     */
    @Override
    public void zeichne(Graphics graphics)
    {
        graphics.fillPolygon(xPoints, yPoints, 3);
    }
}
