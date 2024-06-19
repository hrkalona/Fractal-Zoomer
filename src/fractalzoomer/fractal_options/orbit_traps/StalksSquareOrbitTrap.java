
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;
import fractalzoomer.core.norms.NormInfinity;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class StalksSquareOrbitTrap extends OrbitTrap {
    private double stalksradiushigh;
    private double stalksradiuslow;
    private double cnorm;
    private Norm normImpl;
    private Norm normImpl2;
    
    public StalksSquareOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, boolean countTrapIterations, int lastXItems) {
        
        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        normImpl = new NormInfinity();
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

        dist = Math.abs(normImpl.computeWithRoot(diff) - trapLength);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
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
            return trapWidth;
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
