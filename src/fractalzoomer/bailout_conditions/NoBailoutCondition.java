package fractalzoomer.bailout_conditions;

import fractalzoomer.core.Complex;

public class NoBailoutCondition extends BailoutCondition {

    public NoBailoutCondition() {

        super(0.0);

    }

    @Override
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start) {

        return false;

    }
}
