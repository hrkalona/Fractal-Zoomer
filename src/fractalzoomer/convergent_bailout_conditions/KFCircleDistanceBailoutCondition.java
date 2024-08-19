package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;

public class KFCircleDistanceBailoutCondition extends ConvergentBailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpfrBigNum temp3;
    private MpfrBigNum temp4;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    private MpirBigNum temp3p;
    private MpirBigNum temp4p;
    public KFCircleDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (TaskRender.allocateMPFR()) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
                temp3 = new MpfrBigNum();
                temp4 = new MpfrBigNum();
            } else if (TaskRender.allocateMPIR()) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
                temp3p = new MpirBigNum();
                temp4p = new MpirBigNum();
            }
        }
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        double distance = z.distance_squared(zold);
        double distancem1 = zold.distance_squared(zold2);
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(zold);
        Apfloat distancem1 = zold.distance_squared(zold2);
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(zold);
        BigIntNum distancem1 = zold.distance_squared(zold2);
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(zold);
        DoubleDouble distancem1 = zold.distance_squared(zold2);
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(zold, temp1, temp2);
        MpfrBigNum distancem1 = zold.distance_squared(zold2, temp3, temp4);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(zold, temp1p, temp2p);
        MpirBigNum distancem1 = zold.distance_squared(zold2, temp3p, temp4p);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        double distance = z.distance_squared(root);
        double distancem1 = zold.distance_squared(root);
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        Apfloat distancem1 = zold.distance_squared(root);
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(root);
        BigIntNum distancem1 = zold.distance_squared(root);
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        DoubleDouble distancem1 = zold.distance_squared(root);
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        MpfrBigNum distancem1 = zold.distance_squared(root, temp3, temp4);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(root, temp1p, temp2p);
        MpirBigNum distancem1 = zold.distance_squared(root, temp3p, temp4p);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        double distance = z.distance_squared(root);
        double distancem1 = zold.distance_squared(root);
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        Apfloat distancem1 = zold.distance_squared(root);
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNum distance = z.distance_squared(root);
        BigIntNum distancem1 = zold.distance_squared(root);
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        DoubleDouble distancem1 = zold.distance_squared(root);
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        MpfrBigNum distancem1 = zold.distance_squared(root, temp3, temp4);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNum distance = z.distance_squared(root, temp1p, temp2p);
        MpirBigNum distancem1 = zold.distance_squared(root, temp3p, temp4p);
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }
}
