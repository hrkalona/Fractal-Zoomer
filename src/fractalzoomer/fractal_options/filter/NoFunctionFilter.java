package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class NoFunctionFilter extends FunctionFilter {

    public NoFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0) {
        return z;
    }
}
