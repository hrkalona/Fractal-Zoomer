package fractalzoomer.fractal_options.initial_value;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;

public class DefaultInitialValueWithFactor extends PlanePointOption {
    private double factor;

    public DefaultInitialValueWithFactor(double factor) {

        super();
        this.factor = factor;

    }


    @Override
    public Complex getValue(Complex pixel) {

        return pixel.times(factor);

    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return pixel.times(factor);
    }

    @Override
    public String toString() {

        return "c * " + factor;

    }
}
