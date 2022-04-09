package fractalzoomer.core;

import fractalzoomer.main.app_settings.Settings;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.FixedPrecisionApfloatHelper;

public class MyApfloat extends Apfloat {
    public static int precision;
    public static Apfloat PI;
    public static Apfloat TWO_PI;
    public static Apfloat TWO;
    public static Apfloat ONE;
    public static Apfloat ZERO;
    public static Apfloat SQRT_TWO;
    public static Apfloat RECIPROCAL_LOG_TWO_BASE_TEN;
    public static Apfloat E;
    public static Apfloat MAX_DOUBLE_SIZE;
    public static Apfloat MIN_DOUBLE_SIZE;
    public static Apfloat SA_START_SIZE;
    public static Apfloat NEGATIVE_INFINITY = new Apfloat("-1e5000000000");
    public static FixedPrecisionApfloatHelper fp;


    static {
        precision = 300;
        fp = new FixedPrecisionApfloatHelper(precision);
        PI = fp.pi();
        TWO = new MyApfloat(2);
        ONE = new MyApfloat(1);
        ZERO = new MyApfloat(0);
        E = fp.exp(ONE);
        TWO_PI = fp.multiply(PI, TWO);
        SQRT_TWO = fp.sqrt(TWO);
        Apfloat logTwo = MyApfloat.log(TWO);
        Apfloat ten = new MyApfloat(10.0);
        RECIPROCAL_LOG_TWO_BASE_TEN = MyApfloat.reciprocal(fp.divide(logTwo, MyApfloat.log(ten)));
        MAX_DOUBLE_SIZE = new MyApfloat("1.0e-304");
        MIN_DOUBLE_SIZE = new MyApfloat("1.0e-13");
        SA_START_SIZE = new MyApfloat("1.0e-5");
        BigNum.reinitialize(ThreadDraw.BIGNUM_AUTOMATIC_PRECISION ? fp.divide(MyApfloat.log(fp.pow(ten, precision)), logTwo).doubleValue() : ThreadDraw.BIGNUM_PRECISION);
    }

    public static void setPrecision(int prec, Settings s) {
        precision = prec;
        fp = new FixedPrecisionApfloatHelper(precision);
        PI = fp.pi();
        TWO = new MyApfloat(2);
        ONE = new MyApfloat(1);
        ZERO = new MyApfloat(0);
        E = fp.exp(ONE);
        TWO_PI = fp.multiply(PI, TWO);
        SQRT_TWO = fp.sqrt(TWO);
        Apfloat logTwo = MyApfloat.log(TWO);
        Apfloat ten = new MyApfloat(10.0);
        RECIPROCAL_LOG_TWO_BASE_TEN = MyApfloat.reciprocal(fp.divide(logTwo, MyApfloat.log(ten)));
        MAX_DOUBLE_SIZE = new MyApfloat("1.0e-304");
        MIN_DOUBLE_SIZE = new MyApfloat("1.0e-13");
        SA_START_SIZE = new MyApfloat("1.0e-5");

        s.xCenter = s.xCenter.precision(MyApfloat.precision);
        s.yCenter = s.yCenter.precision(MyApfloat.precision);
        s.size = s.size.precision(MyApfloat.precision);
        s.fns.rotation_center[0] = s.fns.rotation_center[0].precision(MyApfloat.precision);
        s.fns.rotation_center[1] = s.fns.rotation_center[1].precision(MyApfloat.precision);
        s.fns.rotation_vals[0] = s.fns.rotation_vals[0].precision(MyApfloat.precision);
        s.fns.rotation_vals[1] = s.fns.rotation_vals[1].precision(MyApfloat.precision);

        BigNum.reinitialize(ThreadDraw.BIGNUM_AUTOMATIC_PRECISION ? fp.divide(MyApfloat.log(fp.pow(ten, precision)), logTwo).doubleValue() : ThreadDraw.BIGNUM_PRECISION);
    }

    public static void setBigNumPrecision() {
        TWO = new MyApfloat(2);
        Apfloat logTwo = MyApfloat.log(TWO);
        Apfloat ten = new MyApfloat(10.0);
        BigNum.reinitialize(fp.divide(MyApfloat.log(fp.pow(ten, precision)), logTwo).doubleValue());
    }

    public MyApfloat(double value) {
       super(value, precision);
    }

    public MyApfloat(int value) {
        super((double)value, precision);
    }

    public MyApfloat(String value, int radix) {
        super(value.trim(), precision, radix);
    }

    public MyApfloat(double value, int radix) {
        super(value, precision, radix);
    }

    public MyApfloat(int value, int radix) {
        super((double)value, precision, radix);
    }

    public MyApfloat(String value) {
        super(value.trim(), precision);
    }

    public static Apfloat getPi() {
        return PI;
    }

    public static Apfloat reciprocal(Apfloat val) {
        return fp.divide(MyApfloat.ONE, val);
    }

    public static Apfloat fastSin(Apfloat val) {
        return new MyApfloat(Math.sin(val.doubleValue()));
    }

    public static Apfloat fastCos(Apfloat val) {
        return new MyApfloat(Math.cos(val.doubleValue()));
    }

    public static Apfloat fastExp(Apfloat val) {
        return new MyApfloat(Math.exp(val.doubleValue()));
    }

    public static Apfloat fastLog(Apfloat val) {
        return new MyApfloat(Math.log(val.doubleValue()));
    }

    public static Apfloat sin(Apfloat val) {

        boolean negate = false;

        /*if (isNaN())  {
            return NaN;
        }*/

        Apfloat TWO_PIFullTimes;
        Apfloat TWO_PIremainder;

        if ((ApfloatMath.abs(val)).compareTo(TWO_PI) > 0) {
            TWO_PIFullTimes = ApfloatMath.truncate(fp.divide(val, TWO_PI));
            TWO_PIremainder = fp.subtract(val, fp.multiply(TWO_PI, TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = val;
        }
        if (TWO_PIremainder.compareTo(PI) > 0) {
            TWO_PIremainder = fp.subtract(TWO_PIremainder, PI);
            negate = true;
        }
        else if (TWO_PIremainder.compareTo(PI.negate()) < 0) {
            TWO_PIremainder = fp.add(TWO_PIremainder, PI);
            negate = true;
        }
        Apfloat msquare = (fp.multiply(TWO_PIremainder, TWO_PIremainder)).negate();
        Apfloat s = TWO_PIremainder;
        Apfloat sOld = s;
        Apfloat t = TWO_PIremainder;
        double n = 1.0;
        do {
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            t = fp.multiply(t, msquare);
            sOld = s;
            s = fp.add(s, t);
        } while (s.compareTo(sOld) != 0);

        if (negate) {
            s = s.negate();
        }
        return s;
    }

    public static Apfloat cos(Apfloat val) {

        boolean negate = false;

        /*if (isNaN()) {
            return NaN;
        }*/

        Apfloat TWO_PIFullTimes;
        Apfloat TWO_PIremainder;
        if ((ApfloatMath.abs(val)).compareTo(TWO_PI) > 0) {
            TWO_PIFullTimes = ApfloatMath.truncate(fp.divide(val, TWO_PI));
            TWO_PIremainder = fp.subtract(val, fp.multiply(TWO_PI, TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = val;
        }
        if (TWO_PIremainder.compareTo(PI) > 0) {
            TWO_PIremainder = fp.subtract(TWO_PIremainder, PI);
            negate = true;
        }
        else if (TWO_PIremainder.compareTo(PI.negate()) < 0) {
            TWO_PIremainder = fp.add(TWO_PIremainder, PI);
            negate = true;
        }
        Apfloat msquare = (fp.multiply(TWO_PIremainder, TWO_PIremainder)).negate();
        Apfloat one = MyApfloat.ONE;
        Apfloat s = one;
        Apfloat sOld = s;
        Apfloat t = one;
        double n = 0.0;
        do {
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            t = fp.multiply(t, msquare);
            sOld = s;
            s = fp.add(s, t);
        } while (s.compareTo(sOld) != 0);
        if (negate) {
            s = s.negate();
        }
        return s;
    }

    public static Apfloat exp(Apfloat val) {

        boolean invert = false;

        /*if (isNaN()) {
            return NaN;
        }*/

        Apfloat x = val;
        Apfloat one = MyApfloat.ONE;
        int intX = x.intValue();
        Apfloat baseVal = one;
        if (intX >= 1) {
            baseVal = fp.pow(E, intX);
        }
        else if (intX <= -1) {
            baseVal = reciprocal(fp.pow(E, -intX));
        }
        Apfloat fractionX = fp.subtract(x, new MyApfloat(intX));
        if (x.compareTo(new MyApfloat(0.0)) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            fractionX = fractionX.negate();
            invert = true;
        }
        Apfloat s = fp.add(one, fractionX);
        Apfloat sOld = s;
        Apfloat t = fractionX;
        double n = 1.0;

        do {
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            t = fp.multiply(t, fractionX);
            sOld = s;
            s = fp.add(s, t);
        } while (s.compareTo(sOld) != 0);
        if (invert) {
            s = MyApfloat.reciprocal(s);
        }
        s = fp.multiply(s, baseVal);
        return s;
    }

    public static Apfloat exp(Apfloat val, int max_iterations) {

        boolean invert = false;

        /*if (isNaN()) {
            return NaN;
        }*/

        Apfloat x = val;
        Apfloat one = MyApfloat.ONE;
        int intX = x.intValue();
        Apfloat baseVal = one;
        if (intX >= 1) {
            baseVal = fp.pow(E, intX);
        }
        else if (intX <= -1) {
            baseVal = reciprocal(fp.pow(E, -intX));
        }
        Apfloat fractionX = fp.subtract(x, new MyApfloat(intX));
        if (x.compareTo(new MyApfloat(0.0)) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            fractionX = fractionX.negate();
            invert = true;
        }
        Apfloat s = fp.add(one, fractionX);
        Apfloat sOld = s;
        Apfloat t = fractionX;
        double n = 1.0;

        int iterations = 0;
        do {
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            t = fp.multiply(t, fractionX);
            sOld = s;
            s = fp.add(s, t);
            iterations++;
        } while (s.compareTo(sOld) != 0 && iterations < max_iterations);
        if (invert) {
            s = MyApfloat.reciprocal(s);
        }
        s = fp.multiply(s, baseVal);
        return s;
    }

    public static Apfloat log(Apfloat val) {

        //if (isNaN()) {
        //    return NaN;
        //}

        Apfloat zero = MyApfloat.ZERO;
        if (val.compareTo(zero) == 0) {
            return NEGATIVE_INFINITY;
        }

        if (val.compareTo(zero) < 0) {
            return null; //NaN
        }

        Apfloat number = val;
        int intPart = 0;
        while (number.compareTo(E) > 0) {
            number = fp.divide(number, E);
            intPart++;
        }

        Apfloat invE = MyApfloat.reciprocal(E);
        while (number.compareTo(invE) < 0) {
            number = fp.multiply(number, E);
            intPart--;
        }

        Apfloat one = MyApfloat.ONE;
        Apfloat num = fp.subtract(number, one);
        Apfloat denom = fp.add(number, one);
        Apfloat ratio = fp.divide(num, denom);
        Apfloat ratioSquare = fp.multiply(ratio, ratio);
        Apfloat s = fp.multiply(MyApfloat.TWO, ratio);
        Apfloat sOld = s;
        Apfloat t = s;
        Apfloat w = s;
        double n = 1.0;

        do {
            n += 2.0;
            t = fp.multiply(t, ratioSquare);
            w = fp.divide(t, new MyApfloat(n));
            sOld = s;
            s = fp.add(s, w);
        } while (s.compareTo(sOld) != 0);
        return fp.add(s, new MyApfloat(intPart));

    }


    public static Apfloat pow(Apfloat v, Apfloat x) {
        boolean invert = false;

        //if (x.isNaN()) {
        //    return NaN;
       // }
        //if (x.isInfinite()) {
        //    return NaN;
       // }
        //if (isNaN()) {
         //   return NaN;
        //}

        Apfloat zero = MyApfloat.ZERO;
        Apfloat one = MyApfloat.ONE;
        if (x.compareTo(zero) == 0) {
            return one;
        }

        if (v.compareTo(zero) <= 0) {
            return zero; // this is NaN
        }


        Apfloat loga = MyApfloat.log(v);
        Apfloat base = fp.multiply(x, loga);
        if (base.compareTo(zero) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            base = base.negate();
            invert = true;
        }
        Apfloat s = fp.add(one, base);
        Apfloat sOld = s;
        Apfloat t = base;
        double n = 1.0;

        do {
            n += 1.0;
            t = fp.divide(t, new MyApfloat(n));
            t = fp.multiply(t, base);
            sOld = s;
            s = fp.add(s, t);
        } while (s.compareTo(sOld) != 0);
        if (invert) {
            s = MyApfloat.reciprocal(s);
        }
        return s;
    }

    public static String toString(Apfloat val, Apfloat size) {
        if(size.compareTo(MIN_DOUBLE_SIZE) < 0) {
            return val.toString();
        }
        return "" + val.doubleValue();
    }

    public static String toStringPretty(Apfloat val, Apfloat size) {
        //if(size.compareTo(MIN_DOUBLE_SIZE) < 0) {
            return val.toString(true);
        //}
        //return "" + val.doubleValue();
    }

    public static String truncateString(String s, int beginChars, int endChars) {
        int l = s.length();
        if(l>beginChars+endChars) return s.substring(0,beginChars)+"..."+s.substring(l-endChars,l);
        else return s;
    }

    public static String toStringTruncated(Apfloat val, Apfloat size) {
        if(size.compareTo(MIN_DOUBLE_SIZE) < 0) {
            return truncateString(val.toString(true), 13,14);
        }
        return "" + val.doubleValue();
    }

    public static String toStringTruncated(Apfloat val, int begin, int end) {
        return truncateString(val.toString(), begin,end);
    }

    public static String toStringTruncatedPretty(Apfloat val, int begin, int end) {
        return truncateString(val.toString(true), begin,end);
    }

    public static void main(String[] args) {

        MyApfloat.precision = 1200;
        MyApfloat.fp = new FixedPrecisionApfloatHelper(MyApfloat.precision);

        Apfloat a = new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        Apfloat b = new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        int runs  = 1000000;
        for(int i = 0; i < runs; i++) {
            fp.multiply(a, b);
        }

        for(int i = 0; i < runs; i++) {
            fp.multiply(a, a);
        }

        for(int i = 0; i < runs; i++) {
            fp.multiply(b, b);
        }

        long time = System.currentTimeMillis();

        for(int i = 0; i < runs; i++) {
            fp.multiply(a, b);
        }

        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        for(int i = 0; i < runs; i++) {
            fp.multiply(b, b);
        }

        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        for(int i = 0; i < runs; i++) {
           // fp.multiply(a, a);
            fp.pow(a, 2);
        }

        System.out.println(System.currentTimeMillis() - time);


        /*Apfloat a = new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        Apfloat b = new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        Apfloat c = new MyApfloat("8.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        Apfloat d = new MyApfloat("-4.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        long runs = 100;




        for(long i = 0; i < runs; i++) {
            fp.exp(c);
            MyApfloat.exp(c);
        }


        for(long i = 0; i < runs; i++) {
            fp.exp(c);
            MyApfloat.exp(c);
        }

        long time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            fp.exp(c);
        }
        System.out.println(System.currentTimeMillis() - time);*/


        /*time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            MyApfloat.exp(c);
        }
        System.out.println(System.currentTimeMillis() - time);


        Apfloat e = new Apfloat(1);

        e.add(e);
        e.subtract(e);
        e.multiply(e);
        e.divide(e);*/
    }

}
