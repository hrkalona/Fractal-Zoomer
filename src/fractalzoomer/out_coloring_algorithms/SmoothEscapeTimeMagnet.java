

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona
 */
public class SmoothEscapeTimeMagnet extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private double convergent_bailout;
    private int algorithm2;
    private Norm cNormImpl;

    public SmoothEscapeTimeMagnet(double bailout, double convergent_bailout, int algorithm, int algorithm2, Norm normImpl) {

        super(bailout, algorithm, normImpl);
        this.convergent_bailout = convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;

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

        return (int)object[0] + getFractionalPart(object);

    }

    @Override
    public double getFractionalPart(Object[] object) {
        if((boolean)object[2]) {

            if(algorithm == 0) {
                return getSmoothing1((Complex)object[1], (Complex)object[3], log_bailout, normImpl) + MAGNET_INCREMENT;
            }
            else if(algorithm == 2) {
                return getSmoothing3((Complex)object[1], (Complex)object[3], bailout, normImpl) + MAGNET_INCREMENT;
            }
            else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;

                return getSmoothing2((Complex)object[1], (Complex)object[3], log_bailout, usePower, log_power, normImpl) + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                return getConvSmoothing1((Complex)object[1], (Complex)object[3], log_convergent_bailout, cNormImpl);
            }
            else {
                return getConvSmoothing2((Complex)object[1], (Complex)object[3], log_convergent_bailout, cNormImpl);
            }
        }
    }

    private static double getConvSmoothing1(Complex z, Complex zold, double log_convergent_bailout, Norm cNormImpl) {

        double temp = Math.log(cNormImpl.computeWithoutRoot(zold.sub(1)));
        return (log_convergent_bailout - temp) / (Math.log(cNormImpl.computeWithoutRoot(z.sub(1))) - temp);

    }

    private static double getConvSmoothing2(Complex z, Complex zold, double log_convergent_bailout, Norm cNormImpl) {

        double temp4 = Math.log(cNormImpl.computeWithoutRoot(z.sub(1)));

        double power = temp4 / Math.log(cNormImpl.computeWithoutRoot(zold.sub(1)));

        return Math.log(log_convergent_bailout / temp4) / Math.log(power);

    }
}
