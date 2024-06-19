
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.NormInfinity;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class PointSquareOrbitTrap extends OrbitTrap {
    private Norm normImpl;

    public PointSquareOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, trapLength, 0.0, countTrapIterations, lastXItems);
        normImpl = new NormInfinity();

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);
        
        double dist = normImpl.computeWithRoot(diff);

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
