package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

public class SuperFormulaOrbitTrap extends OrbitTrap {
    private double sfm1;
    private double sfm2;
    private double sfn2;
    private double sfn3;
    private double sfa;
    private double sfb;
    private double invsfn1;

    public SuperFormulaOrbitTrap(int checkType, double pointRe, double pointIm, double trapWidth, double sfm1, double sfm2, double sfn1, double sfn2, double sfn3, double sfa, double sfb, boolean countTrapIterations) {

        super(checkType, pointRe, pointIm, 0.0, trapWidth, countTrapIterations);

        this.sfm1 = sfm1;
        this.sfm2 = sfm2;
        this.sfn2 = sfn2;
        this.sfn3 = sfn3;
        this.sfa = sfa;
        this.sfb = sfb;

        invsfn1 = - 1/sfn1;

    }

    @Override
    public void check(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex temp = val.sub(point);

        double phi = temp.arg();
        double norm = temp.norm();

        double r = Math.pow(Math.pow(Math.abs(Math.cos((sfm1 * phi) / 4) / sfa), sfn2) +  Math.pow(Math.abs(Math.sin((sfm2 * phi) / 4) / sfb), sfn3), invsfn1);

        double dist = r - norm;


        if(norm <= r && dist <= trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
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
    public double getDistance() {

        if(!Double.isFinite(distance)) {
            return 0;
        }

        if(distance != Double.MAX_VALUE) {
            return trapWidth - distance;
        }

        return distance;

    }
}
