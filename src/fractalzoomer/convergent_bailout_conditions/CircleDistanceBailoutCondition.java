package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

public class CircleDistanceBailoutCondition extends ConvergentBailoutCondition {
    public CircleDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        distance = z.distance_squared(zold);
        return distance <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(zold);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        distance = z.distance_squared(root);
        return distance <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        distance = z.distance_squared(root);
        return distance <= convergent_bailout;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        Apfloat distance = z.distance_squared(root);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddconvergent_bailout) <= 0;
    }
}
