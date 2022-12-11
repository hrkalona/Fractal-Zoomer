package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apfloat;

public class CircleDistanceBailoutCondition extends ConvergentBailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    public CircleDistanceBailoutCondition(double convergent_bailout) {
        super(convergent_bailout);

        if((ThreadDraw.PERTURBATION_THEORY && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE) || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            if(LibMpfr.LOAD_ERROR == null) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            }
        }
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
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(zold);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(zold, temp1, temp2);
        this.distance = distance.doubleValue();
        return distance.compare(convergent_bailout) <= 0;
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
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        this.distance = distance.doubleValue();
        return distance.compare(convergent_bailout) <= 0;
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

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        DoubleDouble distance = z.distance_squared(root);
        this.distance = distance.doubleValue();
        return distance.compareTo(ddcconvergent_bailout) <= 0;
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        MpfrBigNum distance = z.distance_squared(root, temp1, temp2);
        this.distance = distance.doubleValue();
        return distance.compare(convergent_bailout) <= 0;
    }
}
