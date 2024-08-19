

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

//import static fractalzoomer.out_coloring_algorithms.SmoothEscapeTime.APPLY_OFFSET_OF_1_IN_SMOOTHING;

/**
 *
 * @author hrkalona
 */
public class SmoothEscapeTimeRootFindingMethod extends OutColorAlgorithm {

    private double log_convergent_bailout;
    private int algorithm;
    private Norm cNormImpl;
    private double convergent_bailout;

    public SmoothEscapeTimeRootFindingMethod(double convergent_bailout, int algorithm) {

        super();
        this.algorithm = algorithm;
        OutUsingIncrement = false;
        smooth = true;
        this.convergent_bailout = convergent_bailout;
    }

    @Override
    public void setNormImpl(Norm normImpl) {
        cNormImpl = normImpl;

        double exp = cNormImpl.getExp();
        if(exp != 2) {
            log_convergent_bailout = exp != 1 ? Math.log(Math.pow(convergent_bailout, exp)) : Math.log(convergent_bailout);
        }
        else {
            log_convergent_bailout = Math.log(convergent_bailout * convergent_bailout);
        }
    }

    @Override
    public double getResult(Object[] object) {


        if(algorithm == 0) {
            return (int)object[0] + getSmoothing1((Complex)object[1], (Complex)object[2], (Complex)object[3], log_convergent_bailout, cNormImpl);
        }
        else {
            return (int)object[0] + getSmoothing2((Complex)object[1], (Complex)object[2], (Complex)object[3], log_convergent_bailout, cNormImpl);
        }

    }

    public static double getSmoothing1(Complex z, Complex zold, Complex zold2, double log_convergent_bailout, Norm cNormImpl) {

        //double offset = APPLY_OFFSET_OF_1_IN_SMOOTHING ? 1 : 0;
        return 1 - fractionalPartConverging1(z, zold, zold2, log_convergent_bailout, cNormImpl);

    }

    public static double getSmoothing2(Complex z, Complex zold, Complex zold2, double log_convergent_bailout, Norm cNormImpl) {

        //double offset = APPLY_OFFSET_OF_1_IN_SMOOTHING ? 1 : 0;
        return 1 - fractionalPartConverging2(z, zold, zold2, log_convergent_bailout, cNormImpl);

    }
}
