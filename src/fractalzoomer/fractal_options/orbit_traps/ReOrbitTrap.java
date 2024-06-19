
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class ReOrbitTrap extends OrbitTrap {
    private int lineType;

    public ReOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, int lineType, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        this.lineType = lineType;

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        double dist = Math.abs(val.getIm() - applyLineFunction(lineType, val.getRe()) - point.getIm());
        
        if(dist < trapWidth && Math.abs(val.getRe() - point.getRe()) < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
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
