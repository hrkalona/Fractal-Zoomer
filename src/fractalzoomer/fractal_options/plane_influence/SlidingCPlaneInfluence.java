package fractalzoomer.fractal_options.plane_influence;

import fractalzoomer.core.Complex;

public class SlidingCPlaneInfluence extends PlaneInfluence {
    private int max_iterations;
    public SlidingCPlaneInfluence(int max_iterations) {
        this.max_iterations = max_iterations;
    }

    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex zold, Complex zold2, Complex c0, Complex pixel) {

        return c.plus_mutable(z.times(((double)iterations) / (max_iterations + iterations)));

    }
}
