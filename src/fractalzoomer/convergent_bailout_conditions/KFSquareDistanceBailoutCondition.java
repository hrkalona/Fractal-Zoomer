package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.norms.NormInfinity;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class KFSquareDistanceBailoutCondition extends ConvergentBailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpfrBigNum temp3;
    private MpfrBigNum temp4;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    private MpirBigNum temp3p;
    private MpirBigNum temp4p;
    public KFSquareDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);

        normImpl = new NormInfinity();

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
        Complex diff = z.sub(zold);
        Complex diff1 = zold.sub(zold2);
        double distance = Math.max(diff.getAbsRe(), diff.getAbsIm());
        double distancem1 = Math.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        BigComplex diff = z.sub(zold);
        BigComplex diff1 = zold.sub(zold2);
        Apfloat distance = ApfloatMath.max(diff.getAbsRe(), diff.getAbsIm());
        Apfloat distancem1 = ApfloatMath.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(zold);
        BigIntNumComplex diff1 = zold.sub(zold2);
        BigIntNum distance = BigIntNum.max(diff.getAbsRe(), diff.getAbsIm());
        BigIntNum distancem1 = BigIntNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DDComplex diff = z.sub(zold);
        DDComplex diff1 = zold.sub(zold2);
        DoubleDouble distance = DoubleDouble.max(diff.getAbsRe(), diff.getAbsIm());
        DoubleDouble distancem1 = DoubleDouble.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(zold, temp1, temp2);
        MpfrBigNumComplex diff1 = zold.sub(zold2, temp3, temp4);
        MpfrBigNum distance = MpfrBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpfrBigNum distancem1 = MpfrBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(zold, temp1p, temp2p);
        MpirBigNumComplex diff1 = zold.sub(zold2, temp3p, temp4p);
        MpirBigNum distance = MpirBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpirBigNum distancem1 = MpirBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        Complex diff = z.sub(root);
        Complex diff1 = zold.sub(root);
        double distance = Math.max(diff.getAbsRe(), diff.getAbsIm());
        double distancem1 = Math.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        BigComplex diff = z.sub(root);
        BigComplex diff1 = zold.sub(root);
        Apfloat distance = ApfloatMath.max(diff.getAbsRe(), diff.getAbsIm());
        Apfloat distancem1 = ApfloatMath.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(root);
        BigIntNumComplex diff1 = zold.sub(root);
        BigIntNum distance = BigIntNum.max(diff.getAbsRe(), diff.getAbsIm());
        BigIntNum distancem1 = BigIntNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DDComplex diff = z.sub(root);
        DDComplex diff1 = zold.sub(root);
        DoubleDouble distance = DoubleDouble.max(diff.getAbsRe(), diff.getAbsIm());
        DoubleDouble distancem1 = DoubleDouble.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);
        MpfrBigNumComplex diff1 = zold.sub(root, temp3, temp4);
        MpfrBigNum distance = MpfrBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpfrBigNum distancem1 = MpfrBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(root, temp1p, temp2p);
        MpirBigNumComplex diff1 = zold.sub(root, temp3p, temp4p);
        MpirBigNum distance = MpirBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpirBigNum distancem1 = MpirBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        Complex diff = z.sub(root);
        Complex diff1 = zold.sub(root);
        double distance = Math.max(diff.getAbsRe(), diff.getAbsIm());
        double distancem1 = Math.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1 < convergent_bailout && distance < distancem1;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        BigComplex diff = z.sub(root);
        BigComplex diff1 = zold.sub(root);
        Apfloat distance = ApfloatMath.max(diff.getAbsRe(), diff.getAbsIm());
        Apfloat distancem1 = ApfloatMath.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(root);
        BigIntNumComplex diff1 = zold.sub(root);
        BigIntNum distance = BigIntNum.max(diff.getAbsRe(), diff.getAbsIm());
        BigIntNum distancem1 = BigIntNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(binddconvergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DDComplex diff = z.sub(root);
        DDComplex diff1 = zold.sub(root);
        DoubleDouble distance = DoubleDouble.max(diff.getAbsRe(), diff.getAbsIm());
        DoubleDouble distancem1 = DoubleDouble.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compareTo(ddcconvergent_bailout) < 0 && distance.compareTo(distancem1) < 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);
        MpfrBigNumComplex diff1 = zold.sub(root, temp3, temp4);
        MpfrBigNum distance = MpfrBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpfrBigNum distancem1 = MpfrBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(root, temp1p, temp2p);
        MpirBigNumComplex diff1 = zold.sub(root, temp3p, temp4p);
        MpirBigNum distance = MpirBigNum.max(diff.getAbsRe(), diff.getAbsIm());
        MpirBigNum distancem1 = MpirBigNum.max(diff1.getAbsRe(), diff1.getAbsIm());
        return distancem1.compare(convergent_bailout) < 0 && distance.compare(distancem1) < 0;
    }
}
