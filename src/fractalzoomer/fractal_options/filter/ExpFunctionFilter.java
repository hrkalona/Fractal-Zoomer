package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class ExpFunctionFilter extends FunctionFilter {

    public ExpFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return z.exp();
    }
}
