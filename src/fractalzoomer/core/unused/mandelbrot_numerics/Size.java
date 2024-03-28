package fractalzoomer.core.unused.mandelbrot_numerics;

import fractalzoomer.core.*;
import org.apfloat.Apfloat;

public class Size {

    public static Object calculateDomain(GenericComplex nucleus, int period)
    {
        GenericComplex z = nucleus;
        GenericComplex dc = null;

        if(nucleus instanceof Complex) {
            dc = new Complex(1, 0);
        }
        else if(nucleus instanceof BigIntNumComplex) {
            dc = new BigIntNumComplex(1, 0);
        }
        else if(nucleus instanceof DDComplex) {
            dc = new DDComplex(1, 0);
        }
        else if(nucleus instanceof BigComplex) {
            dc = new BigComplex(1, 0);
        }

        Object zq2 = z.normSquared();
        for (int q = 2; q <= period; ++q)
        {
            if(dc instanceof BigComplex) {
                dc = z.times(dc).times2_mutable().plus(MyApfloat.ONE);
            }
            else {
                dc = z.times(dc).times2_mutable().plus_mutable(1);
            }
            z = z.square().plus_mutable(nucleus);
            Object zp2 = z.normSquared();

            if(q < period) {
                if (zp2 instanceof Double) {
                    if((double) zp2 < (double) zq2) {
                        zq2 = zp2;
                    }
                } else if (zp2 instanceof BigIntNum) {
                    if(((BigIntNum) zp2).compare((BigIntNum) zq2) < 0) {
                        zq2 = zp2;
                    }
                }
                else if (zp2 instanceof DoubleDouble) {
                    if(((DoubleDouble) zp2).compareTo(zq2) < 0) {
                        zq2 = zp2;
                    }
                }
                else if (zp2 instanceof Apfloat) {
                    if(((Apfloat) zp2).compareTo((Apfloat)zq2) < 0) {
                        zq2 = zp2;
                    }
                }
            }
        }

        if (zq2 instanceof Double) {
            return Math.sqrt((double)zq2) / (double)dc.Norm();
        } else if (zq2 instanceof BigIntNum) {
            return ((BigIntNum)zq2).sqrt().divide((BigIntNum) dc.Norm());
        }
        else if (zq2 instanceof DoubleDouble) {
            return ((DoubleDouble)zq2).sqrt().divide((DoubleDouble) dc.Norm());
        }
        else if (zq2 instanceof Apfloat) {
            return MyApfloat.fp.divide(MyApfloat.fp.sqrt((Apfloat) zq2), (Apfloat) dc.Norm());
        }

        return null;

    }

    public static Object calculate(GenericComplex nucleus, int period) {
        GenericComplex l = null;
        GenericComplex b = null;
        GenericComplex z = null;

        if(nucleus instanceof Complex) {
            z = new Complex();
            l = new Complex(1, 0);
            b = new Complex(1, 0);
        }
        else if(nucleus instanceof BigIntNumComplex) {
            z = new BigIntNumComplex();
            l = new BigIntNumComplex(1, 0);
            b = new BigIntNumComplex(1, 0);
        }
        else if(nucleus instanceof BigComplex) {
            z = new BigComplex();
            l = new BigComplex(1, 0);
            b = new BigComplex(1, 0);
        }
        else if(nucleus instanceof DDComplex) {
            z = new DDComplex();
            l = new DDComplex(1, 0);
            b = new DDComplex(1, 0);
        }

        for (int i = 1; i < period; ++i) {
            z = z.square().plus_mutable(nucleus);
            l = z.times2().times_mutable(l);
            b = b.plus(l.reciprocal());
        }
        return b.times(l.square()).reciprocal().Norm();
    }

}
