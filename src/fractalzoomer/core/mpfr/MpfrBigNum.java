package fractalzoomer.core.mpfr;

import fractalzoomer.core.*;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.mpir.mpf_t;
import org.apfloat.Apfloat;

import static fractalzoomer.core.mpfr.LibMpfr.*;


public class MpfrBigNum {

    public static MpfrBigNum SQRT_TWO;
    public static MpfrBigNum PI;
    public static MpfrBigNum HALF_PI;

    public static long precision = TaskRender.BIGNUM_PRECISION;

    public static final long intPrec = 32;
    public static final long doublePrec = 53;
    public static final int base = 10;
    public static final int rounding = MPFR_RNDN;

    static {

        try {
            if (!hasError()) {
                SQRT_TWO = new MpfrBigNum(2).sqrt();
                PI = MpfrBigNum.getPi();
                HALF_PI = PI.divide2();

                //Test
                MpfrBigNum a = SQRT_TWO.add(10000);
                a = SQRT_TWO.sub(10000);
                a = SQRT_TWO.mult(SQRT_TWO);
                a = SQRT_TWO.divide(10000);
            }
        }
        catch (Error ex) {
            LibMpfr.delete();
            LOAD_ERROR = new Exception(ex.getMessage());
            System.out.println("Cannot load mpfr: " + LOAD_ERROR.getMessage());
        }


    }

    public static void reinitialize(double digits) {
        precision = (int)(digits * TaskRender.BIGNUM_PRECISION_FACTOR + 0.5);

        if(!hasError()) {
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

    public MpfrBigNum(mpf_t op) {
        mpfrMemory = new MpfrMemory(op);
    }

    public MpfrBigNum(MpirBigNum val) {
        mpfrMemory = new MpfrMemory(val.getPeer());
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

    public MpfrBigNum shift2toi(long val) {
        MpfrBigNum result = new MpfrBigNum();
        if(val < 0) {
            mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, -val, rounding);
        }
        else {
            mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, val, rounding);
        }
        return result;
    }

    public MpfrBigNum shift2toi(long val, MpfrBigNum result) {
        if(val < 0) {
            mpfr_div_2ui(result.mpfrMemory.peer, mpfrMemory.peer, -val, rounding);
        }
        else {
            mpfr_mul_2ui(result.mpfrMemory.peer, mpfrMemory.peer, val, rounding);
        }
        return result;
    }

    public MpfrBigNum sqrt() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_sqrt(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sqrt_reciprocal() {
        MpfrBigNum result = new MpfrBigNum();
        mpfr_rec_sqrt(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
        return result;
    }

    public MpfrBigNum sqrt_reciprocal(MpfrBigNum result) {
        mpfr_rec_sqrt(result.mpfrMemory.peer, mpfrMemory.peer, rounding);
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
        return mpfr_sgn(mpfrMemory.peer) > 0;
    }

    public boolean isZero() {
        return mpfr_zero_p(mpfrMemory.peer) != 0;
    }

    public boolean isNaN() {
        return mpfr_nan_p(mpfrMemory.peer) != 0;
    }

    public boolean isOne() {
        return mpfr_cmp_ui(mpfrMemory.peer, 1) == 0;
    }

    public boolean isNegative() {
        return mpfr_sgn(mpfrMemory.peer) < 0;
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
        if(isLong4) {
            int[] exp = new int[1];
            double d = mpfr_get_d_2exp(exp, mpfrMemory.peer, rounding);

            if(d == 0) {
                return new MantExp();
            }

            return new MantExp(exp[0], d);
        }
        else {
            long[] exp = new long[1];
            double d = mpfr_get_d_2exp(exp, mpfrMemory.peer, rounding);

            if(d == 0) {
                return new MantExp();
            }

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

    public static MpfrBigNum min(MpfrBigNum x, MpfrBigNum y) {
        if(mpfr_cmp(x.mpfrMemory.peer, y.mpfrMemory.peer) <= 0) {
            return x;
        }
        else {
            return y;
        }
    }
    public static void set(MpfrBigNum destre, MpfrBigNum destim, MpfrBigNum srcre, MpfrBigNum srcim) {
        mpfr_fz_set(destre.mpfrMemory.peer, destim.mpfrMemory.peer, srcre.mpfrMemory.peer, srcim.mpfrMemory.peer, rounding);
    }

    public static double[] get_d (MpfrBigNum re, MpfrBigNum im) {
        double[] reD = new double[1];
        double[] imD = new double[1];
        mpfr_fz_get_d(reD, imD, re.mpfrMemory.peer, im.mpfrMemory.peer, rounding);
        return new double[] {reD[0], imD[0]};
    }

    public static MantExp[] get_d_2exp (MpfrBigNum re, MpfrBigNum im) {

        double[] reD = new double[1];
        double[] imD = new double[1];
        if(isLong4) {

            int[] reExp = new int[1];
            int[] imExp = new int[1];
            mpfr_fz_get_d_2exp(reD, imD, reExp, imExp, re.mpfrMemory.peer, im.mpfrMemory.peer, rounding);

            double v1 = reD[0], v2 = imD[0];
            if(v1 == 0 && v2 == 0) {
                return new MantExp[] {new MantExp(), new MantExp()};
            }
            else if(v1 == 0) {
                return new MantExp[] {new MantExp(), new MantExp(imExp[0], v2)};
            }
            else if(v2 == 0) {
                return new MantExp[] {new MantExp(reExp[0], v1), new MantExp()};
            }
            else {
                return new MantExp[] {new MantExp(reExp[0], v1), new MantExp(imExp[0], v2)};
            }
        }
        else {
            long[] reExp = new long[1];
            long[] imExp = new long[1];
            mpfr_fz_get_d_2exp(reD, imD, reExp, imExp, re.mpfrMemory.peer, im.mpfrMemory.peer, rounding);

            double v1 = reD[0], v2 = imD[0];
            if(v1 == 0 && v2 == 0) {
                return new MantExp[] {new MantExp(), new MantExp()};
            }
            else if(v1 == 0) {
                return new MantExp[] {new MantExp(), new MantExp(imExp[0], v2)};
            }
            else if(v2 == 0) {
                return new MantExp[] {new MantExp(reExp[0], v1), new MantExp()};
            }
            else {
                return new MantExp[] {new MantExp(reExp[0], v1), new MantExp(imExp[0], v2)};
            }
        }


    }

    public static void z_sqr_p_c(MpfrBigNum re, MpfrBigNum im, MpfrBigNum temp, MpfrBigNum reSqr, MpfrBigNum imSqr, MpfrBigNum normSqr, MpfrBigNum cre, MpfrBigNum cim) {

        mpfr_fz_square_plus_c(re.mpfrMemory.peer, im.mpfrMemory.peer, temp.mpfrMemory.peer, reSqr.mpfrMemory.peer, imSqr.mpfrMemory.peer, normSqr.mpfrMemory.peer, cre.mpfrMemory.peer, cim.mpfrMemory.peer, rounding);

    }

    public static void z_sqr_p_c(MpfrBigNum re, MpfrBigNum im, MpfrBigNum temp1, MpfrBigNum temp2, MpfrBigNum cre, MpfrBigNum cim) {

        mpfr_fz_square_plus_c_simple(re.mpfrMemory.peer, im.mpfrMemory.peer, temp1.mpfrMemory.peer, temp2.mpfrMemory.peer, cre.mpfrMemory.peer, cim.mpfrMemory.peer, rounding);

    }

    public static void z_sqr(MpfrBigNum re, MpfrBigNum im, MpfrBigNum temp, MpfrBigNum reSqr, MpfrBigNum imSqr, MpfrBigNum normSqr) {

        mpfr_fz_square(re.mpfrMemory.peer, im.mpfrMemory.peer, temp.mpfrMemory.peer, reSqr.mpfrMemory.peer, imSqr.mpfrMemory.peer, normSqr.mpfrMemory.peer, rounding);

    }

    static double[] valRe = new double[1];
    static double[] valIm = new double[1];
    static double[] mantissaRe = new double[1];
    static double[] mantissaIm = new double[1];

    static long[] reExpL = new long[1];
    static long[] imExpL = new long[1];
    static int[] reExp = new int[1];
    static int[] imExp = new int[1];

    public static void z_sqr_p_c_with_reduction(MpfrBigNum re, MpfrBigNum im, MpfrBigNum temp1, MpfrBigNum temp2, MpfrBigNum cre, MpfrBigNum cim, boolean deepZoom, Complex cz, MantExpComplex mcz) {

        if(deepZoom) {
            if(isLong4) {
                mpfr_fz_square_plus_c_simple_with_reduction_deep(re.mpfrMemory.peer, im.mpfrMemory.peer, temp1.mpfrMemory.peer, temp2.mpfrMemory.peer, cre.mpfrMemory.peer, cim.mpfrMemory.peer, rounding, mantissaRe, mantissaIm, reExp, imExp);
                mcz.set(reExp[0], imExp[0], mantissaRe[0], mantissaIm[0]);
            }
            else {
                mpfr_fz_square_plus_c_simple_with_reduction_deep(re.mpfrMemory.peer, im.mpfrMemory.peer, temp1.mpfrMemory.peer, temp2.mpfrMemory.peer, cre.mpfrMemory.peer, cim.mpfrMemory.peer, rounding, mantissaRe, mantissaIm, reExpL, imExpL);
                mcz.set(reExpL[0], imExpL[0], mantissaRe[0], mantissaIm[0]);
            }
        }
        else {
            mpfr_fz_square_plus_c_simple_with_reduction_not_deep(re.mpfrMemory.peer, im.mpfrMemory.peer, temp1.mpfrMemory.peer, temp2.mpfrMemory.peer, cre.mpfrMemory.peer, cim.mpfrMemory.peer, rounding, valRe, valIm);
            cz.assign(valRe[0], valIm[0]);
        }

    }

    public static void norm_sqr_with_components( MpfrBigNum reSqr, MpfrBigNum imSqr, MpfrBigNum normSqr, MpfrBigNum re, MpfrBigNum im) {

        mpfr_fz_norm_square_with_components(reSqr.mpfrMemory.peer, imSqr.mpfrMemory.peer, normSqr.mpfrMemory.peer, re.mpfrMemory.peer, im.mpfrMemory.peer, rounding);

    }

    public static void norm_sqr( MpfrBigNum normSqr, MpfrBigNum temp1, MpfrBigNum re, MpfrBigNum im) {

        mpfr_fz_norm_square(normSqr.mpfrMemory.peer, temp1.mpfrMemory.peer, re.mpfrMemory.peer, im.mpfrMemory.peer, rounding);

    }

    public static void self_add(MpfrBigNum re, MpfrBigNum im, MpfrBigNum val_re, MpfrBigNum val_im) {
        mpfr_fz_self_add(re.mpfrMemory.peer, im.mpfrMemory.peer, val_re.mpfrMemory.peer, val_im.mpfrMemory.peer, rounding);
    }

    public static void self_sub(MpfrBigNum re, MpfrBigNum im, MpfrBigNum val_re, MpfrBigNum val_im) {
        mpfr_fz_self_sub(re.mpfrMemory.peer, im.mpfrMemory.peer, val_re.mpfrMemory.peer, val_im.mpfrMemory.peer, rounding);
    }

    public static void rotation(MpfrBigNum x, MpfrBigNum y, MpfrBigNum tempRe, MpfrBigNum tempIm, MpfrBigNum F, MpfrBigNum A, MpfrBigNum AsB, MpfrBigNum ApB) {
        mpfr_fz_rotation(x.mpfrMemory.peer, y.mpfrMemory.peer, tempRe.mpfrMemory.peer, tempIm.mpfrMemory.peer, F.mpfrMemory.peer, A.mpfrMemory.peer, AsB.mpfrMemory.peer, ApB.mpfrMemory.peer, rounding);
    }

    public static void AsBmC(MpfrBigNum temp, MpfrBigNum A, MpfrBigNum B, int C) {
        mpfr_fz_AsBmC(temp.mpfrMemory.peer, A.mpfrMemory.peer, B.mpfrMemory.peer, C, rounding);
    }

    public static void ApBmC(MpfrBigNum temp, MpfrBigNum A, MpfrBigNum B, int C) {
        mpfr_fz_ApBmC(temp.mpfrMemory.peer, A.mpfrMemory.peer, B.mpfrMemory.peer, C, rounding);
    }

    public static void ApBmC_DsEmG(MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum A, MpfrBigNum B, double C, MpfrBigNum D, MpfrBigNum E, double G) {
        mpfr_fz_ApBmC_DsEmG(temp.mpfrMemory.peer, temp2.mpfrMemory.peer, A.mpfrMemory.peer, B.mpfrMemory.peer, C, D.mpfrMemory.peer, E.mpfrMemory.peer, G, rounding);
    }

    public static void ApBmC_DpEmG(MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum A, MpfrBigNum B, MpfrBigNum C, MpfrBigNum D, MpfrBigNum E, MpfrBigNum G) {
        mpfr_fz_ApBmC_DpEmG(temp.mpfrMemory.peer, temp2.mpfrMemory.peer, A.mpfrMemory.peer, B.mpfrMemory.peer, C.mpfrMemory.peer, D.mpfrMemory.peer, E.mpfrMemory.peer, G.mpfrMemory.peer, rounding);
    }

    public static void r_ball_pow2(MpfrBigNum r, MpfrBigNum az, MpfrBigNum r0, MpfrBigNum azsquare) {

        mpfr_fz_r_ball_pow2(r.mpfrMemory.peer, az.mpfrMemory.peer, r0.mpfrMemory.peer, azsquare.mpfrMemory.peer, rounding);

    }

    @Override
    public String toString() {
        return "" + doubleValue();
    }

    public String toFullString() {
        int n = (int)MyApfloat.precision * 2;
        byte[] buf = new byte[n];
        String template = "%." + MyApfloat.precision + "R*g";
        mpfr_snprintf(buf, n, template, rounding, mpfrMemory.peer);
        return new String(buf).trim();
    }

    public Apfloat toApfloat() { return new MyApfloat(toFullString());}

    public int signum() {
        return mpfr_sgn(mpfrMemory.peer);
    }

    /*public static void main(String[] args) {
        String Re = "-1.7685653943536636812525937129345323689264178203655023808403568327813984751602057983694075544809385380635853233562272021595486038792597346267621026418533261924962244609714446430169626331467057579947033159779441985348128008304981334471441266997824980553144563791567792114724296121872944832614093450944724492334683449190475005836849397812504637683710510408861059046995537629068401998856337076707729082352934525561410799942777533219989198396355688230764560224323159046285245196155466314399804239661358274503819615557368332052446821888562141313581523317892262380088027074432482039394916527640029121955174213203521566050696042500969919946717868654263385845711621571084795576591138961066064076009303183875949501143195217469003173308513486183081233774459137115288547807735477262202007250717008710283162223251492449408111203225925774749231087606075791025786880373414419640567812051244300178691506999924356763498211814367907336500103294768040948403104863777932123346593677739181711573037377537612597105572567432844531248119698069826986109774175430124235000261952935148608089775593025955571455701166888054695292300393363716767974622037919908584052332982637466";
   String Im = "0.00149693415390767795776818884840489556855946301445691471574014563855527433886417969977385819538260268120841953162872636930325763746322273045770475720864573841501787930094585669029854545526055550254240550601638349230447392478835897915689588386917873306732459133130195499040290663241163281171562214964938877814041525983714426684720617999806166857035264185620487882712073265176954914054913266203287997924901540871019242527521230712886590484380712839459054394699971951683593643432733875864612142164058384584027531954686991700717520592706134315477867770419967332102686480959769035927998828366145957010260008071330081671951130257876517738836139132327131150083875547829353693231330986024536074662266149266972020406424662729505261246207754916338512723205243386084554727716044392705072728590247105881028092304993724655676823686703579759639901910397135711042548453158584111749222905493046484296618244721966973379997931675069363108125568864266991641443350605262290076130999673222331940884558082142583551902556005768303536299446355536559649684565312212482597275388117026700207573378170627060834006934127513560312023382257072757055987599151386137785304306581858";

        int MaximumReferenceIterations = 1500000;


        int M = MaximumReferenceIterations - 1;

        long prec = 3893;

        MpfrBigNum.precision = prec;

        MpfrMemory Cx = new MpfrMemory(Re);
        MpfrMemory Cy = new MpfrMemory(Im);
        MpfrMemory Zx = new MpfrMemory();
        MpfrMemory Zy = new MpfrMemory();
        MpfrMemory Zx2 = new MpfrMemory();
        MpfrMemory Zy2 = new MpfrMemory();
        MpfrMemory Z2 = new MpfrMemory();
        MpfrMemory z_0 = new MpfrMemory();

        mpfr_sqr(Zx2.peer, Zx.peer, MPFR_RNDN);
        mpfr_sqr(Zy2.peer, Zy.peer, MPFR_RNDN);
        mpfr_add(Z2.peer, Zx2.peer, Zy2.peer, MPFR_RNDN);
        // calculate reference in high precision

        long start = System.currentTimeMillis();
        for (int i = 0; i < MaximumReferenceIterations; ++i)
        {
            //mpfr_get_d(Zx.peer, MPFR_RNDN);
           // mpfr_get_d(Zy.peer, MPFR_RNDN);

            double[] dre = new double[1];
            double[] dim = new double[1];
            mpfr_fz_get_d(dre, dim, Zx.peer, Zy.peer, MPFR_RNDN);
            //mpfr_get_d_2exp(exp, Zx.peer, MPFR_RNDN);
            //mpfr_get_d_2exp(exp, Zy.peer, MPFR_RNDN);

            double[] dre2 = new double[1];
            double[] dim2 = new double[1];
            long[] expre = new long[1];
            long[] expim = new long[1];
            mpfr_fz_get_d_2exp(dre2, dim2, expre, expim, Zx.peer, Zy.peer, MPFR_RNDN);
            // z = z^2 + c
//            mpfr_add(z_0.peer, Zx.peer, Zy.peer, MPFR_RNDN);
//            mpfr_sqr(z_0.peer, z_0.peer, MPFR_RNDN);
//            mpfr_sub(Zx.peer, Zx2.peer, Zy2.peer, MPFR_RNDN);
//            mpfr_sub(Zy.peer, z_0.peer, Z2.peer, MPFR_RNDN);
//            mpfr_add(Zx.peer, Zx.peer, Cx.peer, MPFR_RNDN);
//            mpfr_add(Zy.peer, Zy.peer, Cy.peer, MPFR_RNDN);

            mpfr_fz_square_plus_c(Zx.peer, Zy.peer, z_0.peer, Zx2.peer, Zy2.peer, Z2.peer, Cx.peer, Cy.peer, MPFR_RNDN);

            //mpfr_sqr(Zx2.peer, Zx.peer, MPFR_RNDN);
            //mpfr_sqr(Zy2.peer, Zy.peer, MPFR_RNDN);
            //mpfr_add(Z2.peer, Zx2.peer, Zy2.peer, MPFR_RNDN);

            mpfr_fz_norm_square_with_components(Zx2.peer, Zy2.peer, Z2.peer, Zx.peer, Zy.peer, MPFR_RNDN);
            // if |z|^2 > er2
            if (mpfr_cmp_d(Z2.peer, 4.0) >= 0)
            {
                M = i;
                break;
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(M);
    }*/
}
