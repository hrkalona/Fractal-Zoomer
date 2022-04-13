package fractalzoomer.core.unused;

import fractalzoomer.core.MantExp;

public class MantExpSoft {
    public static final long MIN_SMALL_EXPONENT = -1023;
    public static final long MIN_BIG_EXPONENT = Long.MIN_VALUE >> 3;
    public static final MantExpSoft ZERO = new MantExpSoft(0, MIN_BIG_EXPONENT, false);

    private static final int MANTISSA_BITS = 30;

    int mantissa;
    long exp;
    boolean sign;

    public MantExpSoft() {
        mantissa = 0;
        exp = MIN_BIG_EXPONENT;
        sign = false;
    }

    public MantExpSoft(int mantissa, long exp, boolean sign) {
        this.mantissa = mantissa;
        this.exp = exp < MantExpSoft.MIN_BIG_EXPONENT ? MantExpSoft.MIN_BIG_EXPONENT : exp;
        this.sign = sign;
    }

    public MantExpSoft(double number) {
        if(number == 0) {
            mantissa = ZERO.mantissa;
            exp = ZERO.exp;
            sign = ZERO.sign;
            return;
        }

        long bits = Double.doubleToRawLongBits(number);
        long f_exp = ((bits & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;
        mantissa = (int)((bits & 0x000fffffffffffffL) >> 22) | 0x40000000; // 52 - MANTISSA_BITS - 2 , we also add the implied 1.xxx
        exp = f_exp;
        sign =  (int)((bits & 0x8000000000000000L) >>> 63)  != 0;
    }

    public double toDouble()
    {
        return toDouble(mantissa, exp, sign);
    }

    public static double toDouble(int mantissa, long exp, boolean sign)
    {
        if(mantissa == 0) {
            return 0.0;
        }

        //52 - MANTISSA_BITS - 2 = 22
        long bits = ((((long)mantissa) << 22) & 0x000fffffffffffffL) | 0x3FF0000000000000L;
        double val = Double.longBitsToDouble(bits);
        long bitsNew = Double.doubleToRawLongBits(val);
        long f_exp = ((bitsNew & 0x7FF0000000000000L) >> 52) + MIN_SMALL_EXPONENT;

        long sum_exp = exp + f_exp;

        if (sum_exp <= MIN_SMALL_EXPONENT) {
            return 0.0;
        }
        else if (sum_exp >= 1024) {
            return mantissa / 0.0;
        }

        return Double.longBitsToDouble((bits & 0x800FFFFFFFFFFFFFL) | ((sum_exp - MIN_SMALL_EXPONENT) << 52) | (((long)(sign ? 0x1 : 0x0)) << 63));
    }

    @Override
    public String toString() {

        return (sign ? "-" : "") + mantissa + "*2^" + exp + " = " + toDouble();
    }

    public MantExpSoft multiply(MantExpSoft factor) {

        long mantissa = ((long)this.mantissa) * ((long)factor.mantissa) >>> MANTISSA_BITS;
        long exp = this.exp + factor.exp;

        if((mantissa & 0x40000000L) == 0) {
            mantissa = mantissa << 1;
            exp--;
        }

        MantExpSoft res = new MantExpSoft((int)mantissa, exp, this.sign ^ factor.sign);

        return res;
    }

    public MantExpSoft multiply(double factor) {
        MantExpSoft factorMant = new MantExpSoft(factor);
        return multiply(factorMant);
    }

   public static void main(String[] args) {


        MantExpSoft a = new MantExpSoft(-2.8);
        System.out.println(a);

       MantExpSoft b = new MantExpSoft(0.5);
       System.out.println(b);

       System.out.println(a.multiply(b));


       MantExp a1 = new MantExp(-2.8);
       MantExp b1 = new MantExp(0.5);

       System.out.println(a1.multiply(b1));

       int runs = 100000;

       for(int i = 0; i < runs; i++) {
           a.multiply(b);
       }

       for(int i = 0; i < runs; i++) {
           a1.multiply(b1);
       }

       long time1 = 0, time2 = 0;
       for(int k = 0; k < 10; k++) {
           long time = System.currentTimeMillis();
           for (int i = 0; i < runs; i++) {
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
               a.multiply(b);
           }
           time1 += System.currentTimeMillis() - time;

           time = System.currentTimeMillis();
           for (int i = 0; i < runs; i++) {
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
               a1.multiply(b1);
           }

           time2 += System.currentTimeMillis()-time;
       }
       System.out.println("SOFT: " +time1 / 10.0);
       System.out.println("MANT: " + time2 / 10.0);

    }
}
