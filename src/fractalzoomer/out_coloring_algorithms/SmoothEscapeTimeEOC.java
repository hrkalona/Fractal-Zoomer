

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.norms.Norm;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona
 */
public class SmoothEscapeTimeEOC extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private double convergent_bailout;
    private int algorithm2;
    private Norm cNormImpl;

    public SmoothEscapeTimeEOC(double bailout, double convergent_bailout, int algorithm, int algorithm2, Norm normImpl) {

        super(bailout, algorithm, normImpl);
        this.convergent_bailout = convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;
        smooth = true;

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
    public double getResult(OutColorData data) {

        return data.iterations + getFractionalPart(data);

    }

    @Override
    public double getFractionalPart(OutColorData data) {
        if (data.escaped) {
            if (algorithm == 0) {
                return getSmoothing1(data.z, data.zold, log_bailout, normImpl) + MAGNET_INCREMENT;
            } else if (algorithm == 2) {
                return getSmoothing3(data.z, data.zold, bailout, normImpl) + MAGNET_INCREMENT;
            } else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;
                return getSmoothing2(data.z, data.zold, log_bailout, usePower, log_power, normImpl) + MAGNET_INCREMENT;
            }
        } else {
            if (algorithm2 == 0) {
                return SmoothEscapeTimeRootFindingMethod.getSmoothing1(data.z, data.zold, data.zold2, log_convergent_bailout, cNormImpl);
            } else {
                return SmoothEscapeTimeRootFindingMethod.getSmoothing2(data.z, data.zold, data.zold2, log_convergent_bailout, cNormImpl);
            }
        }
    }
}
