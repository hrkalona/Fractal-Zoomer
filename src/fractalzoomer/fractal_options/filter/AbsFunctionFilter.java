package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class AbsFunctionFilter extends FunctionFilter {

    public AbsFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0) {
        return z.abs_mutable();
    }
}
