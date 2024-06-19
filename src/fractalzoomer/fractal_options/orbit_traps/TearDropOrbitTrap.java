
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class TearDropOrbitTrap extends OrbitTrap {

    public TearDropOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);

    }

    private double getDistance(Complex val) {
        Complex p = val.absre();

        double r1 = point.getRe(), r2 = point.getIm();

        double h = trapLength;

        double b = (r1-r2)/ h;
        double a = Math.sqrt(1.0-b*b);
        double k = p.getRe() * -b + p.getIm() * a;
        if( k < 0.0 ) return p.norm() - r1;
        if( k > a*h ) return p.distance(new Complex(0.0, h)) - r2;

        return p.getRe() * a + p.getIm() * b - r1;
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        double dist = getDistance(val);

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

}
