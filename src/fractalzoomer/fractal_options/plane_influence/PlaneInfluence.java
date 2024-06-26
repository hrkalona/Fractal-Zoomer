package fractalzoomer.fractal_options.plane_influence;

import fractalzoomer.core.Complex;

public abstract class PlaneInfluence {

    public abstract Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex zold, Complex zold2, Complex c0, Complex pixel);

}
