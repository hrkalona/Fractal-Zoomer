package fractalzoomer.core.unused.mandelbrot_numerics;

import fractalzoomer.core.BigIntNum;
import fractalzoomer.core.BigIntNumComplex;
import org.apfloat.Apfloat;

public class NRZooming {
    private static final int MAX_STEPS = 100;

    public static Apfloat[] calculateCenterAndSize(Apfloat x, Apfloat y, Apfloat radius, int max_iterations) throws Exception {

        BigIntNumComplex center = new BigIntNumComplex(x, y);
        BigIntNum r = new BigIntNum(radius);

        System.out.println(x + " " + y + " " + radius);

        long time = System.currentTimeMillis();
        //int period = BoxPeriod.find(center, r, max_iterations);
        int period = BallPeriod.find(center, r, max_iterations);

        if(period <= 0) {
            throw new Exception("Unable to find period");
        }

        System.out.println("Period " + period + " detected in " + (System.currentTimeMillis() - time) + " ms");

        //System.out.println(period);

        time = System.currentTimeMillis();
        int result = Nucleus.find(center, period, MAX_STEPS, r);

        if(result == Nucleus.FAILED) {
            throw new Exception("Unable to find nucleus");
        }
        System.out.println("Nucleus detected in " + (System.currentTimeMillis() - time) + " ms");

        //System.out.println(center);

        time = System.currentTimeMillis();
        BigIntNum finalRadius = (BigIntNum) Size.calculate(center, period);//(BigIntNum) Size.calculateDomain(center, period);
        System.out.println("Radius detected in " + (System.currentTimeMillis() - time) + " ms");

        if(finalRadius.isZero()) {
            throw new Exception("Unable to find radius");
        }

        finalRadius = finalRadius.mult4();

        //System.out.println(finalRadius);

        return new Apfloat[] {((BigIntNum)center.re()).toApfloat(), ((BigIntNum)center.im()).toApfloat(), finalRadius.toApfloat()};
    }
}
