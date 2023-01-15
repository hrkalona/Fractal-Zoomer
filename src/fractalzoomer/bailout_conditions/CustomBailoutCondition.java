package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;

public class CustomBailoutCondition extends BailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;

    private MpirBigNum temp1p;
    private MpirBigNum temp2p;

    public CustomBailoutCondition(double bound) {

        super(Math.pow(bound, 1 / Math.sqrt(bound)));

        if((ThreadDraw.PERTURBATION_THEORY && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE) || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            if (ThreadDraw.allocateMPFR()) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (ThreadDraw.allocateMPIR()) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }

    }

    @Override
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {

        return z.norm() >= bound;

    }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        return z.norm().compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        return z.norm().compareBothPositive(bnbound) >= 0;
    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        return z.norm(temp1, temp2).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        return z.norm(temp1p, temp2p).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        return z.norm().compareTo(ddcbound) >= 0;

    }
}
