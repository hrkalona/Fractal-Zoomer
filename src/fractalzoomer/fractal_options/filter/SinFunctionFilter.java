package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class SinFunctionFilter extends FunctionFilter {

    public SinFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start) {
        return z.sin();
    }
}
