package fractalzoomer.bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

public class NoBailoutCondition extends BailoutCondition {

    public NoBailoutCondition() {

        super(0.0);

    }

    @Override
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared) {

        return false;

    }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared) {

        return false;

    }
}
