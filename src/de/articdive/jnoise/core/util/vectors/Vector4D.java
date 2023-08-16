package de.articdive.jnoise.core.util.vectors;



/**
 * Record that denotes a mathematical 4D vector with an X, Y, Z and W component.
 *
 * @author Articdive
 */
public class Vector4D implements Vector {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Calculates the dot product of this vector with another 4D vector.
     *
     * @param vector4D {@link Vector4D} other vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dot( Vector4D vector4D) {
        return (x * vector4D.x) + (y * vector4D.y) + (z * vector4D.z) + (w * vector4D.w);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double w() {
        return w;
    }
}