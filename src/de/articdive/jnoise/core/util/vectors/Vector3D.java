package de.articdive.jnoise.core.util.vectors;



/**
 * Record that denotes a mathematical 3D vector with an X, Y and Z component.
 *
 * @author Articdive
 */
public class Vector3D implements Vector {

    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the dot product of this vector with another 3D vector.
     *
     * @param vector3D {@link Vector3D} other vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dot( Vector3D vector3D) {
        return (x * vector3D.x) + (y * vector3D.y) + (z * vector3D.z);
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
}