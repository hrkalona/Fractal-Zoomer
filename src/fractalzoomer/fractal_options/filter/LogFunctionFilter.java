package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;

public class LogFunctionFilter extends FunctionFilter {

    public LogFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return z.log_mutable();
    }
}
