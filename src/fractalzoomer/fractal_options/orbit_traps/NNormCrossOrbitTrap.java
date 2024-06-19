
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.NormN;

import static fractalzoomer.main.Constants.*;

public class NNormCrossOrbitTrap extends OrbitTrap {
    private Norm normImpl;
    private int lineType;

    public NNormCrossOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, double n_norm, int lineType, boolean countTrapIterations, int lastXItems, double a, double b) {

        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        this.lineType = lineType;
        normImpl = new NormN(n_norm, a, b);

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);

        double dist = Math.abs(normImpl.computeWithRoot(diff) - trapLength);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }
        
        dist = Math.abs(val.getRe() - applyLineFunction(lineType, val.getIm()) - point.getRe());
        if(dist < trapWidth && Math.abs(val.getIm() - point.getIm()) < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

        dist = Math.abs(val.getIm() - applyLineFunction(lineType, val.getRe()) - point.getIm());
        if(dist < trapWidth && Math.abs(val.getRe() - point.getRe()) < trapLength && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 2;
            setTrappedData(val, iteration);
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapWidth;
    }
    
}
