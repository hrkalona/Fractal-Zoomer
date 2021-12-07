package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

public class NNormDistanceBailoutCondition extends ConvergentBailoutCondition {
    protected double n_norm;
    protected Apfloat ddn_norm;

    public NNormDistanceBailoutCondition(double convergent_bailout, double n_norm) {
        super(convergent_bailout);
        this.n_norm = n_norm;
        ddn_norm = new MyApfloat(n_norm);
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

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
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result = diff.nnorm(n_norm) <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(root);
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
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

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
}
