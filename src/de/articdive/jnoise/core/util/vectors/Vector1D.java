package de.articdive.jnoise.core.util.vectors;


/**
 * Record that denotes a mathematical 1D vector with an X component.
 *
 * @author Articdive
 */
public class Vector1D implements Vector {

    private double x;

    public Vector1D(double x) {
        this.x = x;
    }

    /**
     * Calculates the dot product of this vector with another 1D vector.
     *
     * @param vector1D {@link Vector1D} other vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dot(Vector1D vector1D) {
        return (x * vector1D.x);
    }

    @Override
    public double x() {
        return x;
    }
}
