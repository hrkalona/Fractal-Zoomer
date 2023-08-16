package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class SquareDistanceBailoutCondition extends ConvergentBailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    public SquareDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);

        if(TaskDraw.PERTURBATION_THEORY || TaskDraw.HIGH_PRECISION_CALCULATION) {
            if (TaskDraw.allocateMPFR()) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (TaskDraw.allocateMPIR()) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(zold);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold);
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(zold, temp1, temp2);
        MpfrBigNum absRe = diff.getAbsRe(temp1);
        MpfrBigNum absIm = diff.getAbsIm(temp2);

        MpfrBigNum max = MpfrBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(zold, temp1p, temp2p);
        MpirBigNum absRe = diff.getAbsRe(temp1p);
        MpirBigNum absIm = diff.getAbsIm(temp2p);

        MpirBigNum max = MpirBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold, temp1p, temp2p).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(zold);
        Apfloat absRe = diff.getAbsRe();
        Apfloat absIm = diff.getAbsIm();

        Apfloat max = ApfloatMath.max(absRe, absIm);

        boolean result =  max.compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(zold);
        BigIntNum absRe = diff.getAbsRe();
        BigIntNum absIm = diff.getAbsIm();

        BigIntNum max = BigIntNum.max(absRe, absIm);

        boolean result = max.compare(binddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        DDComplex diff = z.sub(zold);
        DoubleDouble absRe = diff.getAbsRe();
        DoubleDouble absIm = diff.getAbsIm();

        DoubleDouble max = absRe.max(absIm);

        boolean result =  max.compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(root);
        Apfloat absRe = diff.getAbsRe();
        Apfloat absIm = diff.getAbsIm();

        Apfloat max = ApfloatMath.max(absRe, absIm);

        boolean result =  max.compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(root);
        BigIntNum absRe = diff.getAbsRe();
        BigIntNum absIm = diff.getAbsIm();

        BigIntNum max = BigIntNum.max(absRe, absIm);

        boolean result =  max.compare(binddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        DDComplex diff = z.sub(root);
        DoubleDouble absRe = diff.getAbsRe();
        DoubleDouble absIm = diff.getAbsIm();

        DoubleDouble max = absRe.max(absIm);

        boolean result =  max.compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);
        MpfrBigNum absRe = diff.getAbsRe(temp1);
        MpfrBigNum absIm = diff.getAbsIm(temp2);

        MpfrBigNum max = MpfrBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(root, temp1p, temp2p);
        MpirBigNum absRe = diff.getAbsRe(temp1p);
        MpirBigNum absIm = diff.getAbsIm(temp2p);

        MpirBigNum max = MpirBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1p, temp2p).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(root);
        Apfloat absRe = diff.getAbsRe();
        Apfloat absIm = diff.getAbsIm();

        Apfloat max = ApfloatMath.max(absRe, absIm);

        boolean result =  max.compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        BigIntNumComplex diff = z.sub(root);
        BigIntNum absRe = diff.getAbsRe();
        BigIntNum absIm = diff.getAbsIm();

        BigIntNum max = BigIntNum.max(absRe, absIm);

        boolean result =  max.compare(binddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        DDComplex diff = z.sub(root);
        DoubleDouble absRe = diff.getAbsRe();
        DoubleDouble absIm = diff.getAbsIm();

        DoubleDouble max = absRe.max(absIm);

        boolean result =  max.compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);
        MpfrBigNum absRe = diff.getAbsRe(temp1);
        MpfrBigNum absIm = diff.getAbsIm(temp2);

        MpfrBigNum max = MpfrBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        MpirBigNumComplex diff = z.sub(root, temp1p, temp2p);
        MpirBigNum absRe = diff.getAbsRe(temp1p);
        MpirBigNum absIm = diff.getAbsIm(temp2p);

        MpirBigNum max = MpirBigNum.max(absRe, absIm);

        boolean result =  max.compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1p, temp2p).doubleValue();
        }

        return result;
    }

}
