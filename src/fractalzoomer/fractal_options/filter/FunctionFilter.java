package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public abstract class FunctionFilter {

    public abstract Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0, Complex pixel);

}
