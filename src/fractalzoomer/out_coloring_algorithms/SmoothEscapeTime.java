

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona
 */
public class SmoothEscapeTime extends OutColorAlgorithm {
    //public static boolean APPLY_OFFSET_OF_1_IN_SMOOTHING = false;

    protected double log_bailout;
    protected int algorithm;
    protected double log_power;
    protected boolean usePower;
    protected double bailout;
    protected Norm normImpl;

    public SmoothEscapeTime(double bailout, int algorithm, Norm normImpl) {
        super();

        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }

        this.algorithm = algorithm;
        OutUsingIncrement = false;
        usePower = false;
        this.bailout = bailout;
        smooth = true;
        this.normImpl = normImpl;

    }

    public SmoothEscapeTime(double bailout, int algorithm, double log_power, Norm normImpl) {

        super();

        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }

        this.algorithm = algorithm;
        OutUsingIncrement = false;
        this.log_power = log_power;
        usePower = true;
        smooth = true;
        this.bailout = bailout;
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {

        return (int)object[0] + getFractionalPart(object);

    }

    @Override
    public double getFractionalPart(Object[] object) {
        if(algorithm == 0 && !usePower) {
            return getSmoothing1((Complex)object[1], (Complex)object[2], log_bailout, normImpl);
        }
        else if(algorithm == 2 && !usePower) {
            return getSmoothing3((Complex)object[1], (Complex)object[2], bailout, normImpl);
        }
        else {
            //double temp2 = ((Complex)object[1]).norm_squared();
            //return  1 - Math.log(Math.log(temp2) / log_bailout_squared) / log_power;

            return getSmoothing2((Complex)object[1], (Complex)object[2], log_bailout, usePower, log_power, normImpl);
        }
    }

    protected static double getSmoothing1(Complex z, Complex zold, double log_bailout_squared, Norm normImpl) {

        //double offset = APPLY_OFFSET_OF_1_IN_SMOOTHING ? 1 : 0;
        return 1 - fractionalPartEscaping1(z, zold, log_bailout_squared, normImpl);

    }

    protected static double getSmoothing2(Complex z, Complex zold, double log_bailout, boolean usePower, double log_power, Norm normImpl) {

        //double offset = APPLY_OFFSET_OF_1_IN_SMOOTHING ? 1 : 0;
        if(usePower) {
            return 1 - fractionalPartEscapingWithPower(z, log_bailout, log_power, normImpl);
        }

        return 1 - fractionalPartEscaping2(z, zold, log_bailout, normImpl);

    }

    protected static double getSmoothing3(Complex z, Complex zold, double bailout, Norm normImpl) {
        //double offset = APPLY_OFFSET_OF_1_IN_SMOOTHING ? 1 : 0;
        return 1 - fractionalPartEscaping3(z, zold, bailout, normImpl);
    }
}
