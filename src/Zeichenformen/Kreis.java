package Zeichenformen;



import java.awt.Graphics;

/**
 * Unterklasse der Form Ermöglicht Zeitpunkt sowie x bzw y und Radius
 * festzulegen
 *
 * Version 10.11.20
 *
 * @author stefanscherle
 */
public class Kreis extends Form
{
    private final int x;          // x-Koordinate
    private final int y;          // y-Koordinate
    private final double t;       // Zeitpunkt der Erstellung
    private final int radius;     // Radius

    /**
     *
     * @param t
     * @param x
     * @param y
     * @param radius
     */
    public Kreis(double t, int x, int y, int radius)
    {
        super(t, x, y, radius);
        this.t = t;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     *
     * @return Zeitpunkt
     */
    @Override
    public double getT()
    {
        return t;
    }

    /**
     *
     * @return
     */
    @Override
    public int getX()
    {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public int getY()
    {
        return y;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRadius()
    {
        return radius;
    }

    /**
     *
     * @param graphics
     */
    @Override
    public void zeichne(Graphics graphics)
    {
        graphics.fillOval(x, y, radius, radius);
    }
}
