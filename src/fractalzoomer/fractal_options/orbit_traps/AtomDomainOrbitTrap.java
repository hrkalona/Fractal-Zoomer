
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class AtomDomainOrbitTrap extends OrbitTrap {
    private double max_distance;
    private double old_distance;
    private Norm normImpl;

    public AtomDomainOrbitTrap(int checkType, double pointRe, double pointIm, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, 0.0, 0.0, countTrapIterations, lastXItems);
        normImpl = new Norm2();
    }
    
    @Override
    public void initialize(Complex pixel) {
        
        super.initialize(pixel);
        max_distance = 0;
        old_distance = Double.MAX_VALUE;
        
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        double dist = normImpl.computeWithRoot(val.sub(point));

        if (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST) {
            old_distance = distance;
            distance = dist;
            if(distance > max_distance) {
                max_distance = distance;
            }
            trapId = 0;
            setTrappedData(val, iteration);
        } else if (checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE) {
            if (dist < distance) {
                old_distance = distance;
                distance = dist;

                if(distance > max_distance) {
                    max_distance = distance;
                }

                trapId = 0;
                setTrappedData(val, iteration);
            }
            else if (dist < old_distance && dist != distance) {
                old_distance = dist;
                countExtraIterations();
            }
        }

    }

    @Override
    public double getMaxValue() {
        if(distance <= old_distance) {
            return old_distance;
        }
        else {
            return max_distance;
        }
    }

}
