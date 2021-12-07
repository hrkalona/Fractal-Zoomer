package fractalzoomer.bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

public class CustomBailoutCondition extends BailoutCondition {

    public CustomBailoutCondition(double bound) {

        super(Math.pow(bound, 1 / Math.sqrt(bound)));

    }

    @Override
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {

        return z.norm() >= bound;

    }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        return z.norm().compareTo(ddbound) >= 0;

    }
}
