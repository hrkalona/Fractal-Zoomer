package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

public class NNormDistanceBailoutCondition extends ConvergentBailoutCondition {
    protected double n_norm;
    protected double n_norm_reciprocal;
    protected Apfloat ddn_norm;
    protected Apfloat ddn_norm_reciprocal;
    protected MpfrBigNum mpfrbn_norm;

    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpfrBigNum mpfrbn_norm_reciprocal;

    protected DoubleDouble ddcn_norm;
    protected DoubleDouble ddcn_norm_reciprocal;

    public NNormDistanceBailoutCondition(double convergent_bailout, double n_norm) {
        super(convergent_bailout);
        this.n_norm = n_norm;
        n_norm_reciprocal = 1 / n_norm;


        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            ddn_norm = new MyApfloat(n_norm);
            ddcn_norm = new DoubleDouble(n_norm);

            if(n_norm != 0) {
                ddn_norm_reciprocal = MyApfloat.reciprocal(ddn_norm);
                ddcn_norm_reciprocal = ddcn_norm.reciprocal();
            }

            if(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE || ThreadDraw.HIGH_PRECISION_CALCULATION) {

                if(!LibMpfr.hasError()) {
                    mpfrbn_norm = new MpfrBigNum(n_norm);
                    temp1 = new MpfrBigNum();
                    temp2 = new MpfrBigNum();

                    if(n_norm != 0) {
                        mpfrbn_norm_reciprocal = mpfrbn_norm.reciprocal();
                    }
                }
            }
        }
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        if(n_norm == 0) {
            return false;
        }

        Complex diff = z.sub(zold);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        BigComplex diff = z.sub(zold);

        boolean result = diff.nnorm(ddn_norm, ddn_norm_reciprocal).compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        if(ddcn_norm.isZero()) {
            return false;
        }

        DDComplex diff = z.sub(zold);

        boolean result = diff.nnorm(ddcn_norm, ddcn_norm_reciprocal).compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {

        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(zold, temp1, temp2);

        boolean result = diff.nnorm(mpfrbn_norm, temp1, temp2, mpfrbn_norm_reciprocal).compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(zold, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        Complex cz = z.toComplex();
        Complex czold = zold.toComplex();
        Complex diff = cz.sub(czold);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = cz.distance_squared(czold);
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        if(n_norm == 0) {
            return false;
        }

        Complex diff = z.sub(root);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);

        boolean result = diff.nnorm(mpfrbn_norm, temp1, temp2, mpfrbn_norm_reciprocal).compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        Complex cz = z.toComplex();
        double droot = root.doubleValue();
        Complex diff = cz.sub(droot);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = cz.distance_squared(droot);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        BigComplex diff = z.sub(root);

        boolean result = diff.nnorm(ddn_norm, ddn_norm_reciprocal).compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        if(ddcn_norm.isZero()) {
            return false;
        }

        DDComplex diff = z.sub(root);

        boolean result = diff.nnorm(ddcn_norm, ddcn_norm_reciprocal).compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        if(n_norm == 0) {
            return false;
        }

        Complex diff = z.sub(root);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        BigComplex diff = z.sub(root);

        boolean result = diff.nnorm(ddn_norm, ddn_norm_reciprocal).compareTo(ddconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {

        if(ddcn_norm.isZero()) {
            return false;
        }

        DDComplex diff = z.sub(root);

        boolean result = diff.nnorm(ddcn_norm, ddcn_norm_reciprocal).compareTo(ddcconvergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(root, temp1, temp2);

        boolean result = diff.nnorm(mpfrbn_norm, temp1, temp2, mpfrbn_norm_reciprocal).compare(convergent_bailout) <= 0;

        if(calculateDistance && result) {
            distance = z.distance_squared(root, temp1, temp2).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        Complex cz = z.toComplex();
        Complex croot = root.toComplex();
        Complex diff = cz.sub(croot);
        boolean result = diff.nnorm(n_norm, n_norm_reciprocal) <= convergent_bailout;

        if(calculateDistance && result) {
            distance = cz.distance_squared(croot);
        }

        return result;
    }
}
