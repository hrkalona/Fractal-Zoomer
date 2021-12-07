package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import org.apfloat.Apfloat;

public class RhombusDistanceBailoutCondition extends ConvergentBailoutCondition {
    public RhombusDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(zold);
        boolean result =  diff.getAbsRe() + diff.getAbsIm() <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(zold);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(zold);

        boolean result =  MyApfloat.fp.add(diff.getAbsRe(), diff.getAbsIm()).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(zold).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result =  diff.getAbsRe() + diff.getAbsIm() <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(root);

        boolean result =  MyApfloat.fp.add(diff.getAbsRe(), diff.getAbsIm()).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        Complex diff = z.sub(root);
        boolean result =  diff.getAbsRe() + diff.getAbsIm() <= convergent_bailout;

        if(result) {
            distance = z.distance_squared(root);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {

        BigComplex diff = z.sub(root);

        boolean result =  MyApfloat.fp.add(diff.getAbsRe(), diff.getAbsIm()).compareTo(ddconvergent_bailout) <= 0;

        if(result) {
            distance = z.distance_squared(root).doubleValue();
        }

        return result;
    }
}
