package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class ReciprocalFunctionFilter extends FunctionFilter {

    public ReciprocalFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start) {
        return z.reciprocal_mutable();
    }
}
