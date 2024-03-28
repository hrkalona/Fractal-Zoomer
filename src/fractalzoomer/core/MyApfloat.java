package fractalzoomer.core;

import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.utils.NativeLoader;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.Apint;
import org.apfloat.FixedPrecisionApfloatHelper;

import java.math.BigDecimal;

public class MyApfloat extends Apfloat {
    public static long precision;
    public static Apfloat PI;
    public static Apfloat TWO_PI;
    public static Apint TWO;
    public static Apint ONE;
    public static Apint THREE;
    public static Apint FOUR;
    public static Apint FIVE;
    public static Apint SIX;
    public static Apint SEVEN;
    public static Apint EIGHT;
    public static Apint NINE;
    public static Apint TEN;
    public static Apint TWELVE;
    public static Apint SIXTEEN;
    public static Apint TWENTYFOUR;
    public static Apint THIRTYTWO;
    public static Apint ZERO;
    public static Apfloat SQRT_TWO;
    public static Apfloat LOG_TWO;
    public static Apfloat RECIPROCAL_LOG_TWO_BASE_TEN;
    public static Apfloat E;
    public static Apfloat MAX_DOUBLE_SIZE;
    public static Apfloat MIN_DOUBLE_SIZE;

    public static Apfloat SA_START_SIZE;
    public static Apfloat NEGATIVE_INFINITY = new Apfloat("-1e5000000000");
    public static FixedPrecisionApfloatHelper fp;
    public static long precHeadRoom = 25;
    public static long precHeadRoom2 = 50;
    public static long precHeadRoomSmall = 5;

    public static boolean setAutomaticPrecision = true;
    public static boolean alwaysCheckForDecrease = true;

    public static int THREADS_THRESHOLD = 625;
    public static boolean use_threads = false;

    /*public static long getAutomaticPrecision(String value) {
        Apfloat val = new Apfloat(value, 10000); //Hopefully that is enough
        return Math.abs(val.scale()) + precHeadRoom + 1; // + 30 for some headroom
    }

    public static long getAutomaticPrecision(double value) {
        Apfloat val = new Apfloat(new BigDecimal(value), 10000); //Hopefully that is enough
        return Math.abs(val.scale()) + precHeadRoom + 1; // + 30 for some headroom
    }*/
    public static long getAutomaticPrecision(String[] values, boolean[] preferExponent, int function) {
        long precHeadRoomToUse = Settings.usesPerturbationWithDivision(function) ? precHeadRoom2 : precHeadRoom;
        long prec = getAutomaticPrecisionInternal(values, preferExponent, precHeadRoomToUse);
        return Math.max(prec, precHeadRoomToUse);
    }

    private static long getAutomaticPrecisionInternal(String[] values, boolean[] preferExponent, long precHeadRoom) {

        long maxValue = Long.MIN_VALUE;
        long maxValueForExponentPreferal = Long.MIN_VALUE;
        for(int j = 0; j < values.length; j++) {
            String value = values[j].trim();
            Apfloat val = new Apfloat(value, 10000); // try to parse this first

            long digitsBeforeDot = 0;
            long digitsAfterDot = 0;
            boolean foundExp = false;
            boolean foundDot = false;
            long lastNonZeroAfterDot = -1;
            long firstNonZeroBeforeDot = -1;
            String exp = "";
            for (int i = 0; i < value.length(); i++) {
                if (foundExp) {
                    if (Character.isDigit(value.charAt(i))) {
                        exp += value.charAt(i);
                    }
                } else {
                    if (value.charAt(i) == 'e' || value.charAt(i) == 'E') {
                        foundExp = true;
                    }
                    if (value.charAt(i) == '.') {
                        foundDot = true;
                    }
                    else if (Character.isDigit(value.charAt(i))) {
                        if(foundDot) {
                            digitsAfterDot++;
                            if(value.charAt(i) != '0') {
                                lastNonZeroAfterDot = digitsAfterDot;
                            }
                        }
                        else {
                            digitsBeforeDot++;
                            if(firstNonZeroBeforeDot == -1 && value.charAt(i) != '0') {
                                firstNonZeroBeforeDot = digitsBeforeDot;
                            }
                        }
                    }
                }
            }
            long expDigitsCount = 0;

            if (foundExp && exp.length() > 0) {
                expDigitsCount = Long.parseLong(exp);
            }

            long total = 0;
            if(preferExponent[j] && expDigitsCount != 0) {
                total = expDigitsCount;
            }
            else {
                total = (firstNonZeroBeforeDot == -1 ? 0 : digitsBeforeDot - firstNonZeroBeforeDot + 1)
                        + (lastNonZeroAfterDot == -1 ? 0 : lastNonZeroAfterDot)
                        + expDigitsCount;
            }

            if(preferExponent[j]) {
                maxValueForExponentPreferal = Math.max(maxValueForExponentPreferal, total);
            }
            else {
                maxValue = Math.max(maxValue, total);
            }
        }

        if(maxValueForExponentPreferal != Long.MIN_VALUE) {

            if(maxValue <= precision && maxValueForExponentPreferal + precHeadRoom <= precision) {
                if(maxValueForExponentPreferal + precHeadRoom <= maxValue && maxValue - maxValueForExponentPreferal >  2.5 * precHeadRoom) {
                    return maxValueForExponentPreferal + precHeadRoom;
                }
                return Math.max(maxValue, maxValueForExponentPreferal + precHeadRoom);
            }
            if(maxValue > precision && maxValueForExponentPreferal + precHeadRoom <= maxValue) {
                if(maxValue - maxValueForExponentPreferal > 2.5 * precHeadRoom) {
                    return maxValueForExponentPreferal + precHeadRoom;
                }
                return maxValue + precHeadRoomSmall;
            }
            else {
                return maxValueForExponentPreferal + precHeadRoom;
            }
        }
        else {
            return maxValue <= precision ? maxValue : maxValue + precHeadRoomSmall;
        }
    }

    public static boolean shouldSetPrecision(long newPrecision, boolean checkForDecrease, int function) {
        long precHeadRoomToUse = Settings.usesPerturbationWithDivision(function) ? precHeadRoom2 : precHeadRoom;
        return (newPrecision > precision) ||
                (checkForDecrease
                && newPrecision < (precision - (precHeadRoomToUse << 1))
                && newPrecision > precHeadRoomToUse
                );
    }

    public static boolean shouldIncreasePrecision(Apfloat val) {
        return Math.abs(val.scale()) + 1 > precision - 10;
    }

    public static void increasePrecision(Settings s) {
        long precHeadRoomToUse = Settings.usesPerturbationWithDivision(s.fns.function) ? precHeadRoom2 : precHeadRoom;
        setPrecision(precision + precHeadRoomToUse, s);
    }

    static {
        precision = 300;
        fp = new FixedPrecisionApfloatHelper(precision);
        PI = fp.pi();
        TWO = new Apint(2);
        ONE = Apint.ONE;
        THREE = new Apint(3);
        SIX = new Apint(6);
        FOUR = new Apint(4);
        FIVE = new Apint(5);
        SEVEN = new Apint(7);
        EIGHT = new Apint(8);
        NINE = new Apint(9);
        TEN = new Apint(10);
        ZERO = Apint.ZERO;
        TWELVE = new Apint(12);
        SIXTEEN = new Apint(16);
        TWENTYFOUR = new Apint(24);
        THIRTYTWO = new Apint(32);
        E = fp.e();
        TWO_PI = fp.multiply(PI, TWO);
        SQRT_TWO = fp.sqrt(TWO);
        LOG_TWO = fp.log(TWO);
        RECIPROCAL_LOG_TWO_BASE_TEN = fp.divide(fp.log(TEN), LOG_TWO);
        MAX_DOUBLE_SIZE = new MyApfloat(1.0e-300);
        MIN_DOUBLE_SIZE = new MyApfloat(5.0e-13);
        SA_START_SIZE = new MyApfloat(1.0e-5);

        NativeLoader.init();

        if(TaskRender.BIGNUM_AUTOMATIC_PRECISION) {
            double temp = fp.divide(fp.log(fp.pow(TEN, precision)), LOG_TWO).doubleValue();

            BigNum.reinitialize(temp);
            BigIntNum.reinitialize(temp);
            MpfrBigNum.reinitialize(temp);
            MpirBigNum.reinitialize(temp);
        }
        else {
            BigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            BigIntNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            MpfrBigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            MpirBigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
        }

        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    public static void setPrecision(long prec) {
        precision = prec;
        fp = new FixedPrecisionApfloatHelper(precision);
        PI = fp.pi();
        E = fp.e();
        TWO_PI = fp.multiply(PI, TWO);
        SQRT_TWO = fp.sqrt(TWO);
        LOG_TWO = fp.log(TWO);
        RECIPROCAL_LOG_TWO_BASE_TEN = fp.divide(fp.log(TEN), LOG_TWO);
        MAX_DOUBLE_SIZE = new MyApfloat(1.0e-300);
        MIN_DOUBLE_SIZE = new MyApfloat(5.0e-13);
        SA_START_SIZE = new MyApfloat(1.0e-5);

        if(TaskRender.BIGNUM_AUTOMATIC_PRECISION) {
            double temp = fp.divide(fp.log(fp.pow(TEN, precision)), LOG_TWO).doubleValue();

            BigNum.reinitialize(temp);
            BigIntNum.reinitialize(temp);
            MpfrBigNum.reinitialize(temp);
            MpirBigNum.reinitialize(temp);
        }
        else {
            BigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            BigIntNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            MpfrBigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
            MpirBigNum.reinitialize(TaskRender.BIGNUM_PRECISION);
        }

        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2;
    }

    public static void setPrecision(long prec, Settings s) {
        setPrecision(prec);

        s.xCenter = s.xCenter.precision(MyApfloat.precision);
        s.yCenter = s.yCenter.precision(MyApfloat.precision);
        s.size = s.size.precision(MyApfloat.precision);
        s.fns.rotation_center[0] = s.fns.rotation_center[0].precision(MyApfloat.precision);
        s.fns.rotation_center[1] = s.fns.rotation_center[1].precision(MyApfloat.precision);
        s.fns.rotation_vals[0] = s.fns.rotation_vals[0].precision(MyApfloat.precision);
        s.fns.rotation_vals[1] = s.fns.rotation_vals[1].precision(MyApfloat.precision);

    }

    public static void setBigNumPrecision() {
        double temp = fp.divide(fp.log(fp.pow(TEN, precision)), LOG_TWO).doubleValue();
        BigNum.reinitialize(temp);
        BigIntNum.reinitialize(temp);
        MpfrBigNum.reinitialize(temp);
        MpirBigNum.reinitialize(temp);
    }

    public MyApfloat(double value) {
       super(new BigDecimal(value), precision);
    }

    public MyApfloat(long value) {
        super(value, precision);
    }

    public MyApfloat(int value) {
        super(value, precision);
    }

    public MyApfloat(String value) {
        super(value.trim(), precision);
    }

    public MyApfloat(String value, int radix) {
        super(value.trim(), precision, radix);
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
        if (x.compareTo(ZERO) < 0) {
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

    /*@Deprecated
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


    @Deprecated
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


        Apfloat loga = fp.log(v);
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
    }*/

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

    public static String toStringPretty(Apfloat val) {
        return val.toString(true);
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

        MyApfloat.precision = 50;
        MyApfloat.fp = new FixedPrecisionApfloatHelper(MyApfloat.precision);

        //System.out.println(getAutomaticPrecision(new String[]{"0.003236469856558749616219364295756151228386738578675", "-0.8421534199886435385336059377278168189344067258883", "6.82121026329696178436279296875e-13" }, new boolean[] {false, false, true}));

        //Apfloat a = new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
        //Apfloat b = new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529");

        /*int runs  = 1000000;
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

        System.out.println(System.currentTimeMillis() - time);*/


       /* Apfloat a = new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332");
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
        System.out.println(System.currentTimeMillis() - time);


        time = System.currentTimeMillis();
        for(long i = 0; i < runs; i++) {
            MyApfloat.exp(c);
        }
        System.out.println(System.currentTimeMillis() - time);*/


        /*Apfloat e = new Apfloat(1);

        e.add(e);
        e.subtract(e);
        e.multiply(e);
        e.divide(e);*/
    }

}
