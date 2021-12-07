package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class SquareDistanceBailoutCondition extends ConvergentBailoutCondition {
    public SquareDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(zold);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(zold);
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

        if(result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(result) {
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

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result = Math.max(diff.getAbsRe(), diff.getAbsIm()) <= convergent_bailout;

        if(result) {
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

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

}
