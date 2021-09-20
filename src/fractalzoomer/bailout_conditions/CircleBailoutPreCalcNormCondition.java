package fractalzoomer.bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

public class CircleBailoutPreCalcNormCondition extends BailoutCondition {

    public CircleBailoutPreCalcNormCondition(double bound) {

        super(bound);

    }

    @Override //euclidean precalculated norm
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared) {

        return norm_squared >= bound;

    }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared) {

        return norm_squared.compareTo(ddbound) >= 0;

    }
}
