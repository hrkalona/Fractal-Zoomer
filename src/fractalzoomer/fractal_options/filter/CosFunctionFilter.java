package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class CosFunctionFilter extends FunctionFilter {

    public CosFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0) {
        return z.cos();
    }
}
