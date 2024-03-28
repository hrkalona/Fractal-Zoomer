package fractalzoomer.core.unused.mandelbrot_numerics;

import fractalzoomer.core.*;
import org.apfloat.Apfloat;

public class Nucleus {
    public static final int CONVERGED = 0;
    public static final int FAILED = 1;
    public static final int STEPPED = 2;


    private static int nucleusNaiveStep(GenericComplex c_guess, int period, Object epsilon2) {
        GenericComplex z = null;
        GenericComplex dc = null;

        if(c_guess instanceof Complex) {
            z = new Complex();
            dc = new Complex();
        }

        for (int i = 0; i < period; ++i) {
            if(c_guess instanceof Complex) {
                dc = z.times(dc).times2_mutable().plus_mutable(1);
                z = z.square().plus_mutable(c_guess);
            }
        }

        GenericComplex c_new = c_guess.sub(z.divide(dc));
        GenericComplex d = c_new.sub(c_guess);

        if(c_guess instanceof Complex) {
            if ((double)d.normSquared() <= (double)epsilon2) {
                c_guess.set(c_new);
                return CONVERGED;
            }
        }

        if (d.toComplex().isFinite()) {
            c_guess.set(c_new);
            return STEPPED;
        } else {
            return FAILED;
        }
    }

    @Deprecated
    private static int findNaive(GenericComplex c_guess, int period, int maxsteps, Object radius) {

        Object error2 = null;
        if(radius instanceof Double) {
            error2 = (double)radius / 2;
            error2 = (double)error2 * (double) error2;
        }
        else if(radius instanceof BigIntNum) {
            error2 = ((BigIntNum)radius).divide2();
            error2 = ((BigIntNum)error2).square();
        }

        int result = FAILED;
        for (int i = 0; i < maxsteps; ++i) {
            if (STEPPED != (result = nucleusNaiveStep(c_guess, period, error2))) {
                break;
            }
        }
        return result;
    }

    private static int nucleusStep(GenericComplex c_guess, int period, Object epsilon2) {
        GenericComplex z = null;
        GenericComplex dc = null;
        GenericComplex h = null;
        GenericComplex dh = null;


        if(c_guess instanceof Complex) {
            z = new Complex();
            dc = new Complex();
            h = new Complex(1, 0);
            dh = new Complex();
            //epsilon2 = 1.9721522630525295e-31;
        }
        else if(c_guess instanceof BigIntNumComplex) {
            z = new BigIntNumComplex();
            dc = new BigIntNumComplex();
            h = new BigIntNumComplex(1, 0);
            dh = new BigIntNumComplex();
        }
        else if(c_guess instanceof BigComplex) {
            z = new BigComplex();
            dc = new BigComplex();
            h = new BigComplex(1, 0);
            dh = new BigComplex();
        }
        else if(c_guess instanceof DDComplex) {
            z = new DDComplex();
            dc = new DDComplex();
            h = new DDComplex(1, 0);
            dh = new DDComplex();
        }


        for (int i = 1; i <= period; ++i) {
            if(c_guess instanceof BigComplex) {
                dc = z.times(dc).times2_mutable().plus(MyApfloat.ONE);
            }
            else {
                dc = z.times(dc).times2_mutable().plus_mutable(1);
            }
            z = z.square().plus_mutable(c_guess);

            if(z.toComplex().isInfinite()) {
                return FAILED;
            }
            // reject lower periods
            if (i < period && period % i == 0)
            {
                h = h.times(z);
                dh = dh.plus(dc.divide(z));
            }
        }
        // build function
        dh = dh.times(h);
        GenericComplex g = z;
        GenericComplex dg = dc;
        GenericComplex f = g.divide(h);
        GenericComplex df = dg.times(h).sub_mutable(g.times(dh)).divide_mutable(h.square());
        // newton step
        GenericComplex c_new = c_guess.sub(f.divide(df));
        // check convergence
        GenericComplex d = c_new.sub(c_guess);

        if(c_guess instanceof Complex) {
            if ((double)d.normSquared() <= (double)epsilon2) {
                c_guess.set(c_new);
                return CONVERGED;
            }
        }
        else if(c_guess instanceof BigIntNumComplex) {
            if (((BigIntNum)d.normSquared()).compare((BigIntNum) epsilon2) <= 0) {
                c_guess.set(c_new);
                return CONVERGED;
            }
        }
        else if(c_guess instanceof BigComplex) {
            if (((Apfloat)d.normSquared()).compareTo((Apfloat) epsilon2) <= 0) {
                c_guess.set(c_new);
                return CONVERGED;
            }
        }
        else if(c_guess instanceof DDComplex) {
            if (((DoubleDouble)d.normSquared()).compareTo(epsilon2) <= 0) {
                c_guess.set(c_new);
                return CONVERGED;
            }
        }

        if (d.toComplex().isFinite()) {
            c_guess.set(c_new);
            return STEPPED;
        } else {
            return FAILED;
        }
    }

    public static int find(GenericComplex c_guess, int period, int maxsteps, Object radius) {
        Object error2 = null;
        Object radius2  = null;
        if(radius instanceof Double) {
            radius2 = (double)radius * (double)radius;
            error2 = ((double)radius2 * (double) radius);
        }
        else if(radius instanceof BigIntNum) {
            radius2 = ((BigIntNum) radius).square();
            error2 = ((BigIntNum) radius2).mult((BigIntNum) radius);
        }
        else if(radius instanceof Apfloat) {
            radius2 = MyApfloat.fp.multiply(((Apfloat)radius), ((Apfloat)radius));
            error2 = MyApfloat.fp.multiply((Apfloat)radius2, ((Apfloat)radius));
        }
        else if(radius instanceof DoubleDouble) {
            radius2 = ((DoubleDouble) radius).sqr();
            error2 = ((DoubleDouble) radius2).multiply((DoubleDouble) radius);
        }

        int result = FAILED;
        for (int i = 0; i < maxsteps; ++i) {
            if (STEPPED != (result = nucleusStep(c_guess, period, error2))) {
                break;
            }
        }
        return result;
    }

}
