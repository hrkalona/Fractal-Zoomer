
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class PointOrbitTrap extends OrbitTrap {
    private Norm normImpl;

    public PointOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, trapLength, 0.0, countTrapIterations, lastXItems);
        normImpl = new Norm2();

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        double dist = normImpl.computeWithRoot(val.sub(point));

        if(dist < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapLength;
    }
    
}
