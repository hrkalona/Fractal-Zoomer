package fractalzoomer.core.mpfr;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.ThreadDraw;
import org.apfloat.Apfloat;

import static fractalzoomer.core.mpfr.LibMpfr.*;


public class MpfrBigNum {

    public static MpfrBigNum SQRT_TWO;
    public static MpfrBigNum PI;
    public static MpfrBigNum HALF_PI;

    public static long precision = ThreadDraw.BIGNUM_PRECISION;

    public static final long intPrec = 32;
    public static final long doublePrec = 53;
    public static final int base = 10;
    public static final int rounding = MPFR_RNDN;

    static {

           LibMpfr.init();

           if(LOAD_ERROR == null) {
               SQRT_TWO = new MpfrBigNum(2).sqrt();
               PI = MpfrBigNum.getPi();
               HALF_PI = PI.divide2();
           }

    }

    public static void reinitialize(double digits) {
        precision = (int)(digits * ThreadDraw.BIGNUM_PRECISION_FACTOR + 0.5);

        if(LOAD_ERROR == null) {
            SQRT_TWO = new MpfrBigNum(2).sqrt();
            PI = MpfrBigNum.getPi();
            HALF_PI = PI.divide2();
        }
    }

    private MpfrMemory mpfrMemory;

    public MpfrBigNum() {
        mpfrMemory = new MpfrMemory();
    }

    public MpfrBigNum(mpfr_t op) {
        mpfrMemory = new MpfrMemory(op);
    }

    public MpfrBigNum(Apfloat number) {

        mpfrMemory = new MpfrMemory(number.toString(true));

    }

    public MpfrBigNum(String number) {

        mpfrMemory = new MpfrMemory(number);

    }

    public MpfrBigNum(double number) {
        mpfrMemory = new MpfrMemory(number);
    }

    public MpfrBigNum(int number) {
        mpfrMemory = new MpfrMemory(number);
    }

    public MpfrBigNum(MpfrBigNum number) {
        mpfrMemory = new MpfrMemory(number.mpfrMemory.peer);
    }

    public void set(MpfrBigNum number) {
        mpfrMemory.set(number.mpfrMemory);
    }

    public void set(double number) {
        mpfrMemory.set(number);
    }

    public void set(int number) {
        mpfrMemory.set(number);
    }

    public void set(String number) {
        mpfrMemory.set(number);
    }

    public mpfr_t getPeer() { return mpfrMemory.peer;}

    public MpfrBigNum square() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sqr(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum square(MpfrBigNum result) {
        mpfr_sqr(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum mult(MpfrBigNum b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_mul(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum mult(MpfrBigNum b, MpfrBigNum result) {
        mpfr_mul(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum mult2() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 1, rounding);
        return result;
    }

    public MpfrBigNum mult2(MpfrBigNum result) {
        mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 1, rounding);
        return result;
    }

    public MpfrBigNum mult4() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 2, rounding);
        return result;
    }

    public MpfrBigNum mult4(MpfrBigNum result) {
        mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 2, rounding);
        return result;
    }

    public MpfrBigNum mult(int value) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_mul_si(result.mpfrMemory.peer, mpfrMemory.peer, value, rounding);
        return result;
    }

    public MpfrBigNum mult(int value, MpfrBigNum result) {
        mpfr_mul_si(result.mpfrMemory.peer, mpfrMemory.peer, value, rounding);
        return result;
    }

    public MpfrBigNum mult(double value) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_mul_d(result.mpfrMemory.peer, mpfrMemory.peer, value, rounding);
        return result;
    }

    public MpfrBigNum mult(double value, MpfrBigNum result) {
        mpfr_mul_d(result.mpfrMemory.peer, mpfrMemory.peer, value, rounding);
        return result;
    }

    public MpfrBigNum divide(MpfrBigNum b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_div(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum divide(MpfrBigNum b, MpfrBigNum result) {
        mpfr_div(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum divide(int b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_div_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum divide(int b, MpfrBigNum result) {
        mpfr_div_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum divide(double b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_div_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum divide(double b, MpfrBigNum result) {
        mpfr_div_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }


    public MpfrBigNum r_divide(int b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_si_div(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_divide(int b, MpfrBigNum result) {
        mpfr_si_div(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_divide(double b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_d_div(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_divide(double b, MpfrBigNum result) {
        mpfr_d_div(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum reciprocal() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_ui_div(result.mpfrMemory.peer, 1, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum reciprocal(MpfrBigNum result) {
        mpfr_ui_div(result.mpfrMemory.peer, 1, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum divide2() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 1, rounding);
        return result;
    }

    public MpfrBigNum divide2(MpfrBigNum result) {
        mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 1, rounding);
        return result;
    }

    public MpfrBigNum divide4() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 2, rounding);
        return result;
    }

    public MpfrBigNum divide4(MpfrBigNum result) {
        mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, 2, rounding);
        return result;
    }

    public MpfrBigNum sqrt() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sqrt(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sqrt(MpfrBigNum result) {
        mpfr_sqrt(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum add(MpfrBigNum b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_add(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum add(MpfrBigNum b, MpfrBigNum result) {
        mpfr_add(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum add(double b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_add_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum add(double b, MpfrBigNum result) {
        mpfr_add_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum add(int b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_add_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum add(int b, MpfrBigNum result) {
        mpfr_add_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum sub(MpfrBigNum b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sub(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sub(MpfrBigNum b, MpfrBigNum result) {
        mpfr_sub(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sub(double b, MpfrBigNum result) {
        mpfr_sub_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum sub(double b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sub_d(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum sub(int b, MpfrBigNum result) {
        mpfr_sub_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum sub(int b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sub_si(result.mpfrMemory.peer, mpfrMemory.peer, b, rounding);
        return result;
    }

    public MpfrBigNum r_sub(double b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_d_sub(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_sub(double b, MpfrBigNum result) {
        mpfr_d_sub(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_sub(int b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_si_sub(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum r_sub(int b, MpfrBigNum result) {
        mpfr_si_sub(result.mpfrMemory.peer, b, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum negate() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_neg(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum negate(MpfrBigNum result) {
        mpfr_neg(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum abs() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_abs(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum abs(MpfrBigNum result) {
        mpfr_abs(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum log() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_log(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum log(MpfrBigNum result) {
        mpfr_log(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum exp() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_exp(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum exp(MpfrBigNum result) {
        mpfr_exp(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum cos() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_cos(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum cos(MpfrBigNum result) {
        mpfr_cos(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sin() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sin(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sin(MpfrBigNum result) {
        mpfr_sin(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum[] sin_cos() {
        MpfrBigNum[] results = new MpfrBigNum[2];
        results[0] = new MpfrBigNum();//sin
        results[1] = new MpfrBigNum();//cos

        mpfr_sin_cos(results[0].mpfrMemory.peer, results[1].mpfrMemory.peer, mpfrMemory.peer, rounding);
        return results;
    }

    public void sin_cos(MpfrBigNum sin, MpfrBigNum cos) {
        mpfr_sin_cos(sin.mpfrMemory.peer, cos.mpfrMemory.peer, mpfrMemory.peer, rounding);
    }

    public MpfrBigNum pow(MpfrBigNum b) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_pow(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum pow(MpfrBigNum b, MpfrBigNum result) {
        mpfr_pow(result.mpfrMemory.peer, mpfrMemory.peer, b.mpfrMemory.peer, rounding);
        return result;
    }

    public boolean isPositive() {
        int sign = mpfr_sgn(mpfrMemory.peer);
        return sign > 0;
    }

    public boolean isZero() {
        return mpfr_zero_p(mpfrMemory.peer) != 0;
    }

    public boolean isNaN() {
        return mpfr_nan_p(mpfrMemory.peer) != 0;
    }

    public boolean isOne() {
        int sign = mpfr_cmp_ui(mpfrMemory.peer, 1);
        return sign == 0;
    }

    public boolean isNegative() {
        int sign = mpfr_sgn(mpfrMemory.peer);
        return sign < 0;
    }

    public int compare(MpfrBigNum other) {
        return mpfr_cmp(mpfrMemory.peer, other.mpfrMemory.peer);
    }

    public int compare(double other) {
        return mpfr_cmp_d(mpfrMemory.peer, other);
    }

    public double doubleValue() {
        return mpfr_get_d(mpfrMemory.peer, rounding);
    }

    public MantExp getMantExp() {
        if(mpfr_zero_p(mpfrMemory.peer) == 1) {
            return new MantExp();
        }

        if(isLong4) {
            int[] exp = new int[1];
            double d = mpfr_get_d_2exp(exp, mpfrMemory.peer, rounding);
            return new MantExp(exp[0], d);
        }
        else {
            long[] exp = new long[1];
            double d = mpfr_get_d_2exp(exp, mpfrMemory.peer, rounding);
            return new MantExp(exp[0], d);
        }

    }

    public static MpfrBigNum getMax() {
        return new MpfrBigNum(Double.MAX_VALUE);
    }

    public static MpfrBigNum getPi() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_const_pi(result.mpfrMemory.peer, rounding);
        return result;
    }

    public static MpfrBigNum atan2(MpfrBigNum y, MpfrBigNum x) {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_atan2(result.mpfrMemory.peer, y.mpfrMemory.peer, x.mpfrMemory.peer, rounding);
        return result;
    }

    public static MpfrBigNum atan2(MpfrBigNum y, MpfrBigNum x, MpfrBigNum result) {
        mpfr_atan2(result.mpfrMemory.peer, y.mpfrMemory.peer, x.mpfrMemory.peer, rounding);
        return result;
    }

    public static MpfrBigNum max(MpfrBigNum x, MpfrBigNum y) {
        if(mpfr_cmp(x.mpfrMemory.peer, y.mpfrMemory.peer) >= 0) {
            return x;
        }
        else {
            return y;
        }
    }

    @Override
    public String toString() {
        return "" + doubleValue();
    }
}
