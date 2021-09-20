package fractalzoomer.fractal_options.plane_influence;

import fractalzoomer.core.Complex;

public class NoPlaneInfluence extends PlaneInfluence {

    public NoPlaneInfluence() {
        super();
    }

    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex zold, Complex zold2, Complex c0) {
        return c;
    }
}
