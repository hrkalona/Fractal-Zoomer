package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class CircleDistanceBailoutCondition extends ConvergentBailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    public CircleDistanceBailoutCondition(double convergent_bailout, Fractal f) {
        super(convergent_bailout);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (NumericLibrary.allocateMPFR(f)) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (NumericLibrary.allocateMPIR(f)) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return z.distance_squared(zold) <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(zold);
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(zold);
        return distance.compare(binddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(zold);
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(zold, temp1, temp2);
        return distance.compare(convergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(zold, temp1p, temp2p);
        return distance.compare(convergent_bailout) <= 0;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return z.distance_squared(root) <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(root);
        return distance.compare(binddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        return distance.compare(convergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(root, temp1p, temp2p);
        return distance.compare(convergent_bailout) <= 0;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return z.distance_squared(root) <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(root);
        return distance.compare(binddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        return distance.compare(convergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(root, temp1p, temp2p);
        return distance.compare(convergent_bailout) <= 0;
    }
}
