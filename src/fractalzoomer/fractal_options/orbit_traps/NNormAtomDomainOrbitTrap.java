
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.NormN;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class NNormAtomDomainOrbitTrap extends OrbitTrap {
    private Norm normImpl;
    private double max_distance;
    private double old_distance;

    public NNormAtomDomainOrbitTrap(int checkType, double pointRe, double pointIm, double n_norm, boolean countTrapIterations, int lastXItems, double a, double b) {

        super(checkType, pointRe, pointIm, 0.0, 0.0, countTrapIterations, lastXItems);
        normImpl = new NormN(n_norm, a, b);

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

        Complex diff = val.sub(point);

        double dist = normImpl.computeWithRoot(diff);

        if (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST) {
            old_distance = distance;
            if(distance > max_distance) {
                max_distance = distance;
            }
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        } else if (checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE) {
            if (dist < distance) {
                old_distance = distance;
                if(distance > max_distance) {
                    max_distance = distance;
                }
                distance = dist;
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
