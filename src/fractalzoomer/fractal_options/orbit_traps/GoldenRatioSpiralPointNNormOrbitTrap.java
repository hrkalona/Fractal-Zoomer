
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.NormN;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class GoldenRatioSpiralPointNNormOrbitTrap extends OrbitTrap {
    private double phi;
    private Norm normImpl;
    
    public GoldenRatioSpiralPointNNormOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, double n_norm, boolean countTrapIterations, int lastXItems, double a, double b) {

        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        phi = 0.5 * (1 + Math.sqrt(5));
        normImpl = new NormN(n_norm, a, b);

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex temp = val.sub(point);

        {
            double dist = Math.log(temp.norm()) / (4 * Math.log(phi)) - (temp.arg()) / (2 * Math.PI);
            dist = 18 * Math.abs(dist - Math.round(dist));

            if (dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
                distance = dist;
                trapId = 0;
                setTrappedData(val, iteration);
            }
        }
        
        double dist = normImpl.computeWithRoot(temp);

        if(dist < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapId == 1 ? trapLength : trapWidth;
    }
    
}
