package de.articdive.jnoise.core.util.vectors;



/**
 * Record that denotes a mathematical 2D vector with an X and Y component.
 *
 * @author Articdive
 */
public class Vector2D implements Vector {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Calculates the dot product of this vector with another 2D vector.
     *
     * @param vector2D {@link Vector2D} other vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dot( Vector2D vector2D) {
        return (x * vector2D.x) + (y * vector2D.y);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }
}