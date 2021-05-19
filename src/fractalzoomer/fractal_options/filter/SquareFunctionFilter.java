package fractalzoomer.fractal_options.filter;
import fractalzoomer.core.Complex;

public class SquareFunctionFilter extends FunctionFilter {

    public SquareFunctionFilter() {
        super();
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start) {
        return z.square_mutable();
    }
}
