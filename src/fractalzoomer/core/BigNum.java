package fractalzoomer.core;

import org.apfloat.Apfloat;

public abstract class BigNum {
    protected int sign;
    protected boolean isOne;
    protected long scale;
    protected int offset;
    protected int bitOffset;

    //protected static boolean use_threads2 = false;

    public static int SQRT_MAX_ITERATIONS = 30;


//    public static boolean useThreads2() {
//        if(TaskDraw.BUILT_IT_BIGNUM_IMPLEMENTATION == 1) {
//            return BigNum64.use_threads2;
//        }
//        return use_threads2;
//    }

    private static int automaticBignumLib;


    public static void reinitialize(double digits) {
        BigNum30.reinitialize(digits);
        BigNum60.reinitialize(digits);
        BigNum32.reinitialize(digits);
        BigNum64.reinitialize(digits);

        automaticBignumLib = BigNum30.fracDigits >= 62 ? 0 : 1;
    }

    public static void reinitializeTest(double digits) {
        BigNum30.reinitializeTest(digits);
        BigNum60.reinitializeTest(digits);
        BigNum32.reinitializeTest(digits);
        BigNum64.reinitializeTest(digits);
    }

    private static final String ZEROS = "0000000000000000000000000000000000000000000000000000000000000000";
    public static String toHexString(long l) {
        String s = Long.toHexString(l);
        return ZEROS.substring(0,16-s.length())+s;
    }

    public static String toHexString(int i) {
        String s = Integer.toHexString(i).toUpperCase();
        return ZEROS.substring(0,8-s.length())+s;
    }

    protected static int getImplementation() {

        if(TaskDraw.BUILT_IT_BIGNUM_IMPLEMENTATION == 0) {
            return automaticBignumLib;
        }

        return TaskDraw.BUILT_IT_BIGNUM_IMPLEMENTATION - 1;

    }

    public static boolean useThreads() {
        int impl = getImplementation();

        if(impl == 0) {
            return BigNum30.use_threads;
        }
        else if(impl == 1) {
            return BigNum60.use_threads;
        }
        else if (impl == 2){
            return BigNum32.use_threads;
        }
        else {
            return BigNum64.use_threads;
        }
    }

    public static BigNum create() {

        int impl = getImplementation();
        if(impl == 0) {
            return new BigNum30();
        }
        else if(impl == 1) {
            return new BigNum60();
        }
        else if(impl == 2) {
            return new BigNum32();
        }
        else {
            return new BigNum64();
        }

    }

    public static BigNum copy(BigNum other) {
        if(other instanceof BigNum30) {
            return new BigNum30((BigNum30) other);
        }
        else if(other instanceof BigNum60) {
            return new BigNum60((BigNum60) other);
        }
        else if (other instanceof BigNum32){
            return new BigNum32((BigNum32) other);
        }
        else {
            return new BigNum64((BigNum64) other);
        }
    }

    public static BigNum create(int val) {
        int impl = getImplementation();
        if(impl == 0) {
            return new BigNum30(val);
        }
        else if(impl == 1) {
            return new BigNum60(val);
        }
        else if(impl == 2) {
            return new BigNum32(val);
        }
        else {
            return new BigNum64(val);
        }
    }

    public static BigNum create(double val) {
        int impl = getImplementation();
        if(impl == 0) {
            return new BigNum30(val);
        }
        else if(impl == 1) {
            return new BigNum60(val);
        }
        else if(impl == 2) {
            return new BigNum32(val);
        }
        else {
            return new BigNum64(val);
        }
    }

    public static BigNum create(Apfloat val) {
        int impl = getImplementation();
        if(impl == 0) {
            return new BigNum30(val);
        }
        else if(impl == 1) {
            return new BigNum60(val);
        }
        else if(impl == 2) {
            return new BigNum32(val);
        }
        else {
            return new BigNum64(val);
        }
    }

    public static BigNum create(String val) {
        int impl = getImplementation();
        if(impl == 0) {
            return new BigNum30(val);
        }
        else if(impl == 1) {
            return new BigNum60(val);
        }
        else if(impl == 2) {
            return new BigNum32(val);
        }
        else {
            return new BigNum64(val);
        }
    }

    public static long getPrecision() {

        int impl = getImplementation();

        if(impl == 0) {
            return BigNum30.getPrecision();
        }
        else if(impl == 1) {
            return BigNum60.getPrecision();
        }
        else if(impl == 2) {
            return BigNum32.getPrecision();
        }
        else {
            return BigNum64.getPrecision();
        }

    }

    public static String getName() {
        int impl = getImplementation();

        if(impl == 0) {
            return BigNum30.getName();
        }
        else if(impl == 1) {
            return BigNum60.getName();
        }
        else if(impl == 2) {
            return BigNum32.getName();
        }
        else {
            return BigNum64.getName();
        }
    }

    public static BigNum max(BigNum a, BigNum b) {
        if(a instanceof BigNum30) {
            return BigNum30.max((BigNum30) a, (BigNum30) b);
        }
        else if(a instanceof BigNum60) {
            return BigNum60.max((BigNum60) a, (BigNum60) b);
        }
        else if(a instanceof BigNum32) {
            return BigNum32.max((BigNum32) a, (BigNum32) b);
        }
        else {
            return BigNum64.max((BigNum64) a, (BigNum64) b);
        }
    }


    public abstract BigNum negate();
    public abstract BigNum abs();
    public abstract BigNum abs_mutable();

    public abstract BigNum square();

    public abstract BigNum squareFull();
    public abstract BigNum mult(int value);

    public abstract BigNum mult2();
    public abstract BigNum mult4();
    public abstract BigNum divide2();
    public abstract BigNum divide4();
    public abstract BigNum mult(BigNum b);
    public abstract int compare(BigNum other);
    public abstract int compareBothPositive(BigNum other);

    public abstract void negSelf();

    public abstract BigNum sub(BigNum a);
    public abstract BigNum sub(int a);
    public abstract BigNum add(BigNum a);
    public abstract BigNum add(int a);

    public abstract BigNum squareFullGolden();
    public abstract long getScale();

    public abstract double doubleValueOld();
    public abstract double doubleValue();

    public abstract MantExp getMantExp();

    public abstract BigNum mult2toi(long power);
    public abstract BigNum div2toi(long power);
    public abstract BigNum shift2toi(long power);

    public abstract BigNum sqrt();
    public boolean isPositive() {
        //return  digits[0] > 0;
        return sign == 1;
    }

    public boolean isZero() {
        return sign == 0;
    }

    public boolean isOne() {
        return sign == 1 && isOne;
    }

    public boolean isNegative() {
        return sign == -1;
        //return  digits[0] < 0;
    }

    public abstract String bits();

    public Apfloat toApfloat() { return new MyApfloat(bits(), 2).toRadix(10);}
}
