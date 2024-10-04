package fractalzoomer.core.unused.mandelbrot_numerics;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;

public class BoxPeriod {

    GenericComplex[] c;
    GenericComplex[] z;
    int p;

    MpfrBigNum[] temp1;
    MpfrBigNum[] temp2;
    MpfrBigNum[] temp3;
    MpirBigNum[] temp1p;
    MpirBigNum[] temp2p;
    MpirBigNum[] temp3p;

    MpfrBigNum temp4;
    MpfrBigNum temp5;

    MpirBigNum temp4p;
    MpirBigNum temp5p;


    boolean usesMpfr;
    boolean usesMpir;

    public BoxPeriod(GenericComplex center, Object radius) {
        c = new GenericComplex[4];
        z = new GenericComplex[4];

        GenericComplex[] edges = getEdges(radius);
        c[0] = center.plus(edges[0]);
        c[1] = center.plus(edges[1]);
        c[2] = center.plus(edges[2]);
        c[3] = center.plus(edges[3]);


        if(center instanceof Complex) {
            z[0] = new Complex((Complex)c[0]);
            z[1] = new Complex((Complex)c[1]);
            z[2] = new Complex((Complex)c[2]);
            z[3] = new Complex((Complex)c[3]);
        } else if (center instanceof BigComplex) {
            z[0] = new BigComplex((BigComplex)c[0]);
            z[1] = new BigComplex((BigComplex)c[1]);
            z[2] = new BigComplex((BigComplex)c[2]);
            z[3] = new BigComplex((BigComplex)c[3]);
        }
        else if (center instanceof DDComplex) {
            z[0] = new DDComplex((DDComplex)c[0]);
            z[1] = new DDComplex((DDComplex)c[1]);
            z[2] = new DDComplex((DDComplex)c[2]);
            z[3] = new DDComplex((DDComplex)c[3]);
        }
        else if (center instanceof BigNumComplex) {
            z[0] = new BigNumComplex((BigNumComplex)c[0]);
            z[1] = new BigNumComplex((BigNumComplex)c[1]);
            z[2] = new BigNumComplex((BigNumComplex)c[2]);
            z[3] = new BigNumComplex((BigNumComplex)c[3]);
        }
        else if (center instanceof BigIntNumComplex) {
            z[0] = new BigIntNumComplex((BigIntNumComplex)c[0]);
            z[1] = new BigIntNumComplex((BigIntNumComplex)c[1]);
            z[2] = new BigIntNumComplex((BigIntNumComplex)c[2]);
            z[3] = new BigIntNumComplex((BigIntNumComplex)c[3]);
        }
        else if (center instanceof MpfrBigNumComplex) {
            z[0] = new MpfrBigNumComplex((MpfrBigNumComplex)c[0]);
            z[1] = new MpfrBigNumComplex((MpfrBigNumComplex)c[1]);
            z[2] = new MpfrBigNumComplex((MpfrBigNumComplex)c[2]);
            z[3] = new MpfrBigNumComplex((MpfrBigNumComplex)c[3]);
        }
        else if (center instanceof MpirBigNumComplex) {
            z[0] = new MpirBigNumComplex((MpirBigNumComplex)c[0]);
            z[1] = new MpirBigNumComplex((MpirBigNumComplex)c[1]);
            z[2] = new MpirBigNumComplex((MpirBigNumComplex)c[2]);
            z[3] = new MpirBigNumComplex((MpirBigNumComplex)c[3]);
        }

        p = 1;

        usesMpfr = center instanceof MpfrBigNumComplex;
        usesMpir = center instanceof MpirBigNumComplex;

        if(usesMpfr) {
            temp1 = new MpfrBigNum[edges.length];
            temp2 = new MpfrBigNum[edges.length];
            temp3 = new MpfrBigNum[edges.length];

            for(int i = 0; i < edges.length; i++) {
                temp1[i] = new MpfrBigNum();
                temp2[i] = new MpfrBigNum();
            }
            temp4 = new MpfrBigNum();
            temp5 = new MpfrBigNum();

        }
        else if(usesMpir) {
            temp1p = new MpirBigNum[edges.length];
            temp2p = new MpirBigNum[edges.length];
            temp3p = new MpirBigNum[edges.length];

            for(int i = 0; i < edges.length; i++) {
                temp1p[i] = new MpirBigNum();
                temp2p[i] = new MpirBigNum();
                temp3p[i] = new MpirBigNum();
            }

            temp4p = new MpirBigNum();
            temp5p = new MpirBigNum();
        }
    }

    Object cross(GenericComplex a, GenericComplex b) {

        if(a instanceof Complex) {
            double aim = (double)a.im();
            double are = (double)a.re();
            double bre = (double)b.re();
            double bim = (double)b.im();
            return  aim * bre - are * bim;
        } else if (a instanceof BigComplex) {
            Apfloat aim = (Apfloat)a.im();
            Apfloat are = (Apfloat)a.re();
            Apfloat bre = (Apfloat)b.re();
            Apfloat bim = (Apfloat)b.im();
            return  MyApfloat.fp.subtract(MyApfloat.fp.multiply(aim, bre), MyApfloat.fp.multiply(are, bim));
        }
        else if (a instanceof DDComplex) {
            DoubleDouble aim = (DoubleDouble)a.im();
            DoubleDouble are = (DoubleDouble)a.re();
            DoubleDouble bre = (DoubleDouble)b.re();
            DoubleDouble bim = (DoubleDouble)b.im();
            return  aim.multiply(bre).subtract(are.multiply(bim));
        }
        else if (a instanceof BigNumComplex) {
            BigNum aim = (BigNum)a.im();
            BigNum are = (BigNum)a.re();
            BigNum bre = (BigNum)b.re();
            BigNum bim = (BigNum)b.im();
            return  aim.mult(bre).sub(are.mult(bim));
        }
        else if (a instanceof BigIntNumComplex) {
            BigIntNum aim = (BigIntNum)a.im();
            BigIntNum are = (BigIntNum)a.re();
            BigIntNum bre = (BigIntNum)b.re();
            BigIntNum bim = (BigIntNum)b.im();
            return  aim.mult(bre).sub(are.mult(bim));
        }
        else if (a instanceof MpfrBigNumComplex) {
            MpfrBigNum aim = (MpfrBigNum)a.im();
            MpfrBigNum are = (MpfrBigNum)a.re();
            MpfrBigNum bre = (MpfrBigNum)b.re();
            MpfrBigNum bim = (MpfrBigNum)b.im();
            aim.mult(bre, temp4);
            are.mult(bim, temp5);
            temp4.sub(temp5, temp4);
            return temp4;
        }
        else if (a instanceof MpirBigNumComplex) {
            MpirBigNum aim = (MpirBigNum)a.im();
            MpirBigNum are = (MpirBigNum)a.re();
            MpirBigNum bre = (MpirBigNum)b.re();
            MpirBigNum bim = (MpirBigNum)b.im();
            aim.mult(bre, temp4p);
            are.mult(bim, temp5p);
            temp4p.sub(temp5p, temp4p);
            return temp4p;
        }
        return null;

    }

    int crosses_positive_real_axis(GenericComplex a, GenericComplex b) {
        if (signum(a.im()) != signum(b.im())) {
            GenericComplex d = b.sub(a);
            double s = signum(d.im());
            double t = signum(cross(d, a));
            return s == t ? 1 : 0;
        }
        return 0;
    }

    static int signum(Object a) {
        if( a instanceof Double) {
            return (int)Math.signum((double)a);
        }
        else if(a instanceof Apfloat) {
            return ((Apfloat)a).signum();
        }
        else if(a instanceof DoubleDouble) {
            return ((DoubleDouble)a).signum();
        }
        else if(a instanceof BigNum) {
            return ((BigNum)a).signum();
        }
        else if(a instanceof BigIntNum) {
            return ((BigIntNum)a).signum();
        }
        else if(a instanceof MpfrBigNum) {
            return ((MpfrBigNum)a).signum();
        }
        else if(a instanceof MpirBigNum) {
            return ((MpirBigNum)a).signum();
        }

        return 0;
    }

    boolean surrounds_origin(GenericComplex a, GenericComplex b, GenericComplex c, GenericComplex d) {

        int qa = signum(a.re()) + 3 * signum(a.im());
        int qb = signum(b.re()) + 3 * signum(b.im());
        int qc = signum(c.re()) + 3 * signum(c.im());
        int qd = signum(d.re()) + 3 * signum(d.im());

        if (qa == qb && qb == qc && qc == qd && qd != 0) {
            // all in same quadrant and not 0
            return false;
        }

        return
                (( crosses_positive_real_axis(a, b)
                        + crosses_positive_real_axis(b, c)
                        + crosses_positive_real_axis(c, d)
                        + crosses_positive_real_axis(d, a)
                ) & 1) == 1;
    }

    static GenericComplex[] getEdges(Object radiusIn) {

        if(radiusIn instanceof Double) {
            double radius = (double)radiusIn;
            return new GenericComplex[] {new Complex(-radius, -radius), new Complex(radius, -radius), new Complex(radius, radius),  new Complex(-radius, radius)};
        }
        else if(radiusIn instanceof Apfloat) {
            Apfloat radius = (Apfloat) radiusIn;
            return new GenericComplex[] {new BigComplex(radius.negate(), radius.negate()), new BigComplex(radius, radius.negate()), new BigComplex(radius, radius),  new BigComplex(radius.negate(), radius)};
        }
        else if(radiusIn instanceof DoubleDouble) {
            DoubleDouble radius = (DoubleDouble) radiusIn;
            return new GenericComplex[] {new DDComplex(radius.negate(), radius.negate()), new DDComplex(radius, radius.negate()), new DDComplex(radius, radius),  new DDComplex(radius.negate(), radius)};
        }
        else if(radiusIn instanceof BigNum) {
            BigNum radius = (BigNum) radiusIn;
            return new GenericComplex[] {new BigNumComplex(radius.negate(), radius.negate()), new BigNumComplex(radius, radius.negate()), new BigNumComplex(radius, radius),  new BigNumComplex(radius.negate(), radius)};
        }
        else if(radiusIn instanceof BigIntNum) {
            BigIntNum radius = (BigIntNum) radiusIn;
            return new GenericComplex[] {new BigIntNumComplex(radius.negate(), radius.negate()), new BigIntNumComplex(radius, radius.negate()), new BigIntNumComplex(radius, radius),  new BigIntNumComplex(radius.negate(), radius)};
        }
        else if(radiusIn instanceof MpfrBigNum) {
            MpfrBigNum radius = (MpfrBigNum) radiusIn;
            return new GenericComplex[] {new MpfrBigNumComplex(new MpfrBigNum(radius).negate(), new MpfrBigNum(radius).negate()), new MpfrBigNumComplex(new MpfrBigNum(radius), new MpfrBigNum(radius).negate()), new MpfrBigNumComplex(new MpfrBigNum(radius), new MpfrBigNum(radius)),  new MpfrBigNumComplex(new MpfrBigNum(radius).negate(), new MpfrBigNum(radius))};
        }
        else if(radiusIn instanceof MpirBigNum) {
            MpirBigNum radius = (MpirBigNum) radiusIn;
            return new GenericComplex[] {new MpirBigNumComplex(new MpirBigNum(radius).negate(), new MpirBigNum(radius).negate()), new MpirBigNumComplex(new MpirBigNum(radius), new MpirBigNum(radius).negate()), new MpirBigNumComplex(new MpirBigNum(radius), new MpirBigNum(radius)),  new MpirBigNumComplex(new MpirBigNum(radius).negate(), new MpirBigNum(radius))};
        }
        return null;
    }

    private boolean step() {
        boolean ok = true;

        for (int i = 0; i < 4; ++i) {
            if(usesMpfr) {
                z[i] = z[i].square_plus_c_mutable(c[i], temp1[i], temp2[i], temp3[i]);
            }
            else if(usesMpir) {
                z[i] = z[i].square_plus_c_mutable(c[i], temp1p[i], temp2p[i], temp3p[i]);
            }
            else {
                z[i] = z[i].square().plus_mutable(c[i]);
            }
            ok = ok && z[i].toComplex().isFinite();
        }
        p++;
        return ok;
    }

    private boolean hasPeriod() {
        return surrounds_origin(z[0], z[1], z[2], z[3]);
    }

    public static int find(GenericComplex center, Object radius, int maxperiod) {
        BoxPeriod box = new BoxPeriod(center, radius);

        int period = 0;
        for (int i = 0; i < maxperiod; ++i) {
            if (box.hasPeriod()) {
                period = box.p;
                break;
            }
            if (!box.step()) {
                break;
            }
        }
        return period;
    }

    public static void main(String[] args) {
        MyApfloat.setPrecision(1000);
        BigIntNumComplex z = new BigIntNumComplex(new MyApfloat("-1.7433368172466540633688789608412072917946926833886892944999891365459265444904313826062542786747851629283684764866082989426532645315567330071729070071432211550350328587578842327647358471687244474424005602486431598663330078125"),
                new MyApfloat("-1.2391289840807778800108896333130291059286933596509919416721061264755272201384647324416062113476281418382831615546334070394222187330472930324959957454756020230826984374999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999e-7"));

        BigIntNum r = new BigIntNum(new MyApfloat("2.7864008441199734914428497335187033366851915944184053584042430359459606300384634264129458328973637742340067903858262242130394001970899041834704904722215060397851562499999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999e-14"));

        //MpfrBigNum r2 = new MpfrBigNum(new MyApfloat("2.5620330788506154104770818136626e-150"));
        int max_iter = 100000;

        //System.out.println(BoxPeriod.find(z2, r2, max_iter));
        int period = BoxPeriod.find(z, r, max_iter);
        System.out.println(period);

//        int period2 = BallPeriod.find(z, r, max_iter);
//        System.out.println(period2);

        BigIntNumComplex guess1 = new BigIntNumComplex(z);
        System.out.println(Nucleus.find(guess1, period, 3, r));
        System.out.println(guess1.toBigComplex());

        System.out.println(Size.calculate(guess1, period));

//        BigIntNumComplex guess2 = new BigIntNumComplex(z);
//        System.out.println(Nucleus.m_d_nucleus_naive(guess2, period, 1000000));
//        System.out.println(z + " " + guess2);
//
//        double v = 0;
//        long v1 = Double.doubleToRawLongBits(v);
//
//       v1 = v1 | 1;
//       System.out.println(Double.longBitsToDouble(v1));
//
//
//       double r2 = 4.440892098500626e-16;;
//       System.out.println(Math.getExponent(r2));
//       System.out.println(Double.doubleToRawLongBits(r2) & 0x000fffffffffffffL);
//        System.out.println((Double.doubleToRawLongBits(r2) & 0x7ff0000000000000L) >> 52);
//
//        double x = 1;
//        int mantissa_bits = 52;
//        for(int i = 0; i < mantissa_bits - 1; i++) {
//            x = x / 2;
//        }
//        System.out.println(x);

    }
}
