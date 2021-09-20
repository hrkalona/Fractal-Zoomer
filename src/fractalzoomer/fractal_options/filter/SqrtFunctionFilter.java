package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class SqrtFunctionFilter extends FunctionFilter {

    public SqrtFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0) {
        return z.sqrt_mutable();
    }
}
