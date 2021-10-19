package fractalzoomer.core;

import fractalzoomer.main.app_settings.Settings;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

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


    static {
        precision = 300;
        PI = ApfloatMath.pi(precision);
        E = ApfloatMath.exp(new MyApfloat(1.0));
        TWO = new MyApfloat(2);
        ONE = new MyApfloat(1);
        ZERO = new MyApfloat(0);
        TWO_PI = PI.multiply(TWO);
        SQRT_TWO = ApfloatMath.sqrt(TWO);
        RECIPROCAL_LOG_TWO_BASE_TEN = MyApfloat.reciprocal(MyApfloat.log(TWO).divide(MyApfloat.log(new MyApfloat(10.0))));
        MAX_DOUBLE_SIZE = new MyApfloat("1.0e-305");
        MIN_DOUBLE_SIZE = new MyApfloat("1.0e-13");
        SA_START_SIZE = new MyApfloat("1.0e-5");
    }

    public static void setPrecision(int prec, Settings s) {
        precision = prec;
        PI = ApfloatMath.pi(precision);
        E = ApfloatMath.exp(new MyApfloat(1.0));
        TWO = new MyApfloat(2);
        ONE = new MyApfloat(1);
        ZERO = new MyApfloat(0);
        TWO_PI = PI.multiply(TWO);
        SQRT_TWO = ApfloatMath.sqrt(TWO);
        RECIPROCAL_LOG_TWO_BASE_TEN = MyApfloat.reciprocal(MyApfloat.log(TWO).divide(MyApfloat.log(new MyApfloat(10.0))));
        MAX_DOUBLE_SIZE = new MyApfloat("1.0e-305");
        MIN_DOUBLE_SIZE = new MyApfloat("1.0e-13");
        SA_START_SIZE = new MyApfloat("1.0e-5");

        s.xCenter = s.xCenter.precision(MyApfloat.precision);
        s.yCenter = s.yCenter.precision(MyApfloat.precision);
        s.size = s.size.precision(MyApfloat.precision);
        s.fns.rotation_center[0] = s.fns.rotation_center[0].precision(MyApfloat.precision);
        s.fns.rotation_center[1] = s.fns.rotation_center[1].precision(MyApfloat.precision);
        s.fns.rotation_vals[0] = s.fns.rotation_vals[0].precision(MyApfloat.precision);
        s.fns.rotation_vals[1] = s.fns.rotation_vals[1].precision(MyApfloat.precision);
    }

    public MyApfloat(double value) {
       super(value, precision);
    }

    public MyApfloat(int value) {
        super((double)value, precision);
    }

    public MyApfloat(String value, int radix) {
        super(value, precision, radix);
    }

    public MyApfloat(double value, int radix) {
        super(value, precision, radix);
    }

    public MyApfloat(int value, int radix) {
        super((double)value, precision, radix);
    }

    public MyApfloat(String value) {
        super(value, precision);
    }

    public static Apfloat getPi() {
        return PI;
    }

    public static Apfloat reciprocal(Apfloat val) {
        return new MyApfloat(1.0).divide(val);
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
            TWO_PIFullTimes = ApfloatMath.truncate(val.divide(TWO_PI));
            TWO_PIremainder = val.subtract(TWO_PI.multiply(TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = val;
        }
        if (TWO_PIremainder.compareTo(PI) > 0) {
            TWO_PIremainder = TWO_PIremainder.subtract(PI);
            negate = true;
        }
        else if (TWO_PIremainder.compareTo(PI.negate()) < 0) {
            TWO_PIremainder = TWO_PIremainder.add(PI);
            negate = true;
        }
        Apfloat msquare = (TWO_PIremainder.multiply(TWO_PIremainder)).negate();
        Apfloat s = TWO_PIremainder;
        Apfloat sOld = s;
        Apfloat t = TWO_PIremainder;
        double n = 1.0;
        do {
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
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
            TWO_PIFullTimes = ApfloatMath.truncate(val.divide(TWO_PI));
            TWO_PIremainder = val.subtract(TWO_PI.multiply(TWO_PIFullTimes));
        }
        else {
            TWO_PIremainder = val;
        }
        if (TWO_PIremainder.compareTo(PI) > 0) {
            TWO_PIremainder = TWO_PIremainder.subtract(PI);
            negate = true;
        }
        else if (TWO_PIremainder.compareTo(PI.negate()) < 0) {
            TWO_PIremainder = TWO_PIremainder.add(PI);
            negate = true;
        }
        Apfloat msquare = (TWO_PIremainder.multiply(TWO_PIremainder)).negate();
        Apfloat one = new MyApfloat(1.0);
        Apfloat s = one;
        Apfloat sOld = s;
        Apfloat t = one;
        double n = 0.0;
        do {
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
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
        Apfloat one = new MyApfloat(1.0);
        int intX = x.intValue();
        Apfloat baseVal = one;
        if (intX >= 1) {
            /*for (int i = 1; i <= intX; i++) {
                baseVal = baseVal.multiply(E);
            }*/
            baseVal = ApfloatMath.pow(E, intX);
        }
        else if (intX <= -1) {
            /*for (int i = 1; i <= Math.abs(intX); i++) {
                baseVal = baseVal.divide(E);
            }*/
            baseVal = reciprocal(ApfloatMath.pow(E, -intX));
        }
        Apfloat fractionX = x.subtract(new MyApfloat(intX));
        if (x.compareTo(new MyApfloat(0.0)) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            fractionX = fractionX.negate();
            invert = true;
        }
        Apfloat s = (one).add(fractionX);
        Apfloat sOld = s;
        Apfloat t = fractionX;
        double n = 1.0;

        do {
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            t = t.multiply(fractionX);
            sOld = s;
            s = s.add(t);
        } while (s.compareTo(sOld) != 0);
        if (invert) {
            s = MyApfloat.reciprocal(s);
        }
        s = s.multiply(baseVal);
        return s;
    }

    public static Apfloat exp(Apfloat val, int max_iterations) {

        boolean invert = false;

        /*if (isNaN()) {
            return NaN;
        }*/

        Apfloat x = val;
        Apfloat one = new MyApfloat(1.0);
        int intX = x.intValue();
        Apfloat baseVal = one;
        if (intX >= 1) {
            /*for (int i = 1; i <= intX; i++) {
                baseVal = baseVal.multiply(E);
            }*/
            baseVal = ApfloatMath.pow(E, intX);
        }
        else if (intX <= -1) {
            /*for (int i = 1; i <= Math.abs(intX); i++) {
                baseVal = baseVal.divide(E);
            }*/
            baseVal = reciprocal(ApfloatMath.pow(E, -intX));
        }
        Apfloat fractionX = x.subtract(new MyApfloat(intX));
        if (x.compareTo(new MyApfloat(0.0)) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            fractionX = fractionX.negate();
            invert = true;
        }
        Apfloat s = (one).add(fractionX);
        Apfloat sOld = s;
        Apfloat t = fractionX;
        double n = 1.0;

        int iterations = 0;
        do {
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            t = t.multiply(fractionX);
            sOld = s;
            s = s.add(t);
            iterations++;
        } while (s.compareTo(sOld) != 0 && iterations < max_iterations);
        if (invert) {
            s = MyApfloat.reciprocal(s);
        }
        s = s.multiply(baseVal);
        return s;
    }

    public static Apfloat log(Apfloat val) {

        //if (isNaN()) {
        //    return NaN;
        //}

        MyApfloat zero = new MyApfloat(0.0);
        if (val.compareTo(zero) == 0) {
            return NEGATIVE_INFINITY;
        }

        if (val.compareTo(zero) < 0) {
            return null; //NaN
        }

        Apfloat number = val;
        int intPart = 0;
        while (number.compareTo(E) > 0) {
            number = number.divide(E);
            intPart++;
        }

        Apfloat invE = MyApfloat.reciprocal(E);
        while (number.compareTo(invE) < 0) {
            number = number.multiply(E);
            intPart--;
        }

        MyApfloat one = new MyApfloat(1.0);
        Apfloat num = number.subtract(one);
        Apfloat denom = number.add(one);
        Apfloat ratio = num.divide(denom);
        Apfloat ratioSquare = ratio.multiply(ratio);
        Apfloat s = new MyApfloat(2.0).multiply(ratio);
        Apfloat sOld = s;
        Apfloat t = s;
        Apfloat w = s;
        double n = 1.0;

        do {
            n += 2.0;
            t = t.multiply(ratioSquare);
            w = t.divide(new MyApfloat(n));
            sOld = s;
            s = s.add(w);
        } while (s.compareTo(sOld) != 0);
        return s.add(new MyApfloat(intPart));

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

        Apfloat zero = new MyApfloat(0.0);
        Apfloat one = new MyApfloat(1.0);
        if (x.compareTo(zero) == 0) {
            return one;
        }

        if (v.compareTo(zero) <= 0) {
            return zero; // this is NaN
        }


        Apfloat loga = MyApfloat.log(v);
        Apfloat base = x.multiply(loga);
        if (base.compareTo(zero) < 0) {
            // Much greater precision if all numbers in the series have the same sign.
            base = base.negate();
            invert = true;
        }
        Apfloat s = one.add(base);
        Apfloat sOld = s;
        Apfloat t = base;
        double n = 1.0;

        do {
            n += 1.0;
            t = t.divide(new MyApfloat(n));
            t = t.multiply(base);
            sOld = s;
            s = s.add(t);
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

        Apfloat a = new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        Apfloat b = new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        Apfloat c = new MyApfloat("8.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        Apfloat d = new MyApfloat("-4.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        long runs = 100;




        for(long i = 0; i < runs; i++) {
            ApfloatMath.exp(c);
            MyApfloat.exp(c);
        }


        for(long i = 0; i < runs; i++) {
            ApfloatMath.exp(c);
            MyApfloat.exp(c);
        }

        long time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            ApfloatMath.exp(c);
        }
        System.out.println(System.currentTimeMillis() - time);


        time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            MyApfloat.exp(c);
        }
        System.out.println(System.currentTimeMillis() - time);


    }

}
