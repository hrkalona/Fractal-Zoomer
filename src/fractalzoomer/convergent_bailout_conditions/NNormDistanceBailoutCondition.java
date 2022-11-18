package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

public class NNormDistanceBailoutCondition extends ConvergentBailoutCondition {
    protected double n_norm;
    protected Apfloat ddn_norm;
    protected MpfrBigNum mpfrbn_norm;

    protected DoubleDouble ddcn_norm;

    public NNormDistanceBailoutCondition(double convergent_bailout, double n_norm) {
        super(convergent_bailout);
        this.n_norm = n_norm;


        if(ThreadDraw.PERTURBATION_THEORY) {
            ddn_norm = new MyApfloat(n_norm);
            ddcn_norm = new DoubleDouble(n_norm);

            if(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE) {

                if(LibMpfr.LOAD_ERROR == null) {
                    mpfrbn_norm = new MpfrBigNum(n_norm);
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
        boolean result = diff.nnorm(n_norm) <= convergent_bailout;

        if(result) {
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

        boolean result = diff.nnorm(ddn_norm).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
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

        boolean result = diff.nnorm(ddcn_norm).compareTo(ddcconvergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {

        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(zold);

        boolean result = diff.nnorm(mpfrbn_norm).compare(convergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        if(n_norm == 0) {
            return false;
        }

        Complex diff = z.sub(root);
        boolean result = diff.nnorm(n_norm) <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(root);

        boolean result = diff.nnorm(mpfrbn_norm).compare(convergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        BigComplex diff = z.sub(root);

        boolean result = diff.nnorm(ddn_norm).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
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

        boolean result = diff.nnorm(ddcn_norm).compareTo(ddcconvergent_bailout) <= 0;

        if(result) {
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
        boolean result = diff.nnorm(n_norm) <= convergent_bailout;

        if(result) {
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

        boolean result = diff.nnorm(ddn_norm).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
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

        boolean result = diff.nnorm(ddcn_norm).compareTo(ddcconvergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        if(n_norm == 0) {
            return false;
        }

        MpfrBigNumComplex diff = z.sub(root);

        boolean result = diff.nnorm(mpfrbn_norm).compare(convergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }
}
