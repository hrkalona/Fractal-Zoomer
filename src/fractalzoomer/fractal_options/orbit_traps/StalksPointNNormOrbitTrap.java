
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;
import fractalzoomer.core.norms.NormN;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class StalksPointNNormOrbitTrap extends OrbitTrap {
    private double stalksradiushigh;
    private double stalksradiuslow;
    private double cnorm;
    private Norm normImpl;
    private Norm normImpl2;
    
    public StalksPointNNormOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, double n_norm, boolean countTrapIterations, int lastXItems, double a, double b) {
        
        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        normImpl = new NormN(n_norm, a, b);
        normImpl2 = new Norm2();
        
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);

        double dist = normImpl2.computeWithRoot(diff);

        if (dist <= stalksradiushigh && dist >= stalksradiuslow && iteration > 0 && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

        dist = normImpl.computeWithRoot(diff);

        if(dist < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

    }
    
    @Override
    public void initialize(Complex pixel) {
        
        super.initialize(pixel);
        
        cnorm = pixel.norm();
        stalksradiushigh = cnorm + trapWidth * 0.5;
        stalksradiuslow = cnorm - trapWidth * 0.5;
        
    }

    @Override
    public double getMaxValue() {
        if(trapId == 0) {
            return stalksradiushigh;
        }
        else {
            return trapLength;
        }
    }

    @Override
    public double getMinValue() {
        if(trapId == 0) {
            return stalksradiuslow;
        }
        else {
            return 0;
        }
    }
    
}
