package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

public class MovingAverage2OrbitTrap extends OrbitTrap {
    double sum_re;
    double sum_im;
    double last_avg_re;
    double last_avg_im;

    public MovingAverage2OrbitTrap(int checkType, double pointRe, double pointIm, double trapWidth, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, 0, trapWidth, countTrapIterations, lastXItems);

    }

    @Override
    public void initialize(Complex pixel) {
        super.initialize(pixel);
        sum_re = 0;
        sum_im = 0;
        last_avg_re = 0;
        last_avg_im = 0;
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex sub = val.sub(point);
        sum_re += sub.getAbsRe();
        double sum_avg_re = sum_re / iteration;
        double diff_re = sum_avg_re - last_avg_re;
        last_avg_re = sum_avg_re;


        sum_im += sub.getAbsIm();
        double sum_avg_im = sum_im / iteration;
        double diff_im = sum_avg_im - last_avg_im;
        last_avg_im = sum_avg_im;



        double dist = Math.abs(diff_re);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

        dist = Math.abs(diff_im);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

    }

    @Override
    public double getMaxValue() {
        return trapWidth;
    }
}
