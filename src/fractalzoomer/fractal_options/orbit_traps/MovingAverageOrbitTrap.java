package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;

import static fractalzoomer.main.Constants.*;

public class MovingAverageOrbitTrap extends OrbitTrap {
    double sum_norm;
    double last_avg;
    private Norm normImpl;

    public MovingAverageOrbitTrap(int checkType, double pointRe, double pointIm, double trapWidth, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, 0, trapWidth, countTrapIterations, lastXItems);
        normImpl = new Norm2();

    }

    @Override
    public void initialize(Complex pixel) {
        super.initialize(pixel);
        sum_norm = 0;
        last_avg = 0;
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        sum_norm += normImpl.computeWithRoot(val.sub(point));
        double sum_avg = sum_norm / iteration;
        double diff = sum_avg - last_avg;
        last_avg = sum_avg;

        double dist = Math.abs(diff);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

    }

    @Override
    public double getMaxValue() {
        return trapWidth;
    }
}
