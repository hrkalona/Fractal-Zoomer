
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class GoldenRatioSpiralOrbitTrap extends OrbitTrap {
    private double phi;

    public GoldenRatioSpiralOrbitTrap(int checkType, double pointRe, double pointIm, double trapWidth, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, 0, trapWidth, countTrapIterations, lastXItems);
        phi = 0.5 * (1 + Math.sqrt(5));

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }
            
        Complex temp = val.sub(point);
        double dist = Math.log(temp.norm())/(4 * Math.log(phi)) - (temp.arg())/(2 * Math.PI);
        dist = 18 * Math.abs(dist - Math.round(dist));
   
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

    @Override
    public void initialize(Complex pixel) {

        super.initialize(pixel);

        trapped = false;

    }
    
}
