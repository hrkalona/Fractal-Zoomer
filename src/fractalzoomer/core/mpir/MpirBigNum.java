package fractalzoomer.core.mpir;


import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpfr.mpfr_t;
import org.apfloat.Apfloat;

import java.util.Arrays;

import static fractalzoomer.core.mpir.LibMpir.*;


public class MpirBigNum {
    public static long precision = TaskRender.BIGNUM_PRECISION;
    public static int THREADS_THRESHOLD = 3820; // bits of precision
    private static int use_threads = 0;

    public static final long doublePrec = MpfrBigNum.doublePrec;
    public static final int base = MpfrBigNum.base;
    public static final long intPrec = MpfrBigNum.intPrec;

    public static MpirBigNum SQRT_TWO;

    static {

        try {
            if (!hasError()) {
                SQRT_TWO = new MpirBigNum(2).sqrt();
                //Test
                MpirBigNum a = SQRT_TWO.add(10000);
                a = SQRT_TWO.sub(10000);
                a = SQRT_TWO.mult(SQRT_TWO);
                a = SQRT_TWO.divide(10000);
            }
            else {
                int index = Arrays.asList(TaskRender.mpirWinLibs).indexOf(TaskRender.MPIR_LIB);
                if(index != -1) {//Try to fallback to another for the next load
                    TaskRender.MPIR_LIB = TaskRender.mpirWinLibs[(index + 1) % TaskRender.mpirWinLibs.length];
                }
            }
        }
        catch (Error ex) {
            LibMpir.delete();
            LOAD_ERROR = new Exception(ex.getMessage());
            System.out.println("Cannot load mpir: " + LOAD_ERROR.getMessage());

            int index = Arrays.asList(TaskRender.mpirWinLibs).indexOf(TaskRender.MPIR_LIB);
            if(index != -1) {//Try to fallback to another for the next load
                TaskRender.MPIR_LIB = TaskRender.mpirWinLibs[(index + 1) % TaskRender.mpirWinLibs.length];
            }
        }
    }

    public static void reinitialize(double digits) {
        precision = (int)(digits * TaskRender.BIGNUM_PRECISION_FACTOR + 0.5);

        if(!hasError()) {
            SQRT_TWO = new MpirBigNum(2).sqrt();
        }

        use_threads = TaskRender.USE_THREADS_IN_BIGNUM_LIBS && precision >= THREADS_THRESHOLD && Runtime.getRuntime().availableProcessors() >= 2 ? 1 : 0;
    }

    private MpfMemory mpfMemory;

    public MpirBigNum() {
        mpfMemory = new MpfMemory();
    }

    public MpirBigNum(mpf_t op) {
        mpfMemory = new MpfMemory(op);
    }

    public MpirBigNum(mpfr_t op) {
        mpfMemory = new MpfMemory(op);
    }

    public MpirBigNum(MpfrBigNum val) {
        mpfMemory = new MpfMemory(val.getPeer());
    }


    public static MpirBigNum fromApfloat(Apfloat number) {
        if(!LibMpfr.hasError()) {
            try {
                return new MpirBigNum(new MpfrBigNum(number));
            }
            catch (Error e) {
                return new MpirBigNum(number);
            }
        }
        else {
            return new MpirBigNum(number);
        }
    }

    public static MpirBigNum fromString(String number) {
        if(!LibMpfr.hasError()) {
            try {
                return new MpirBigNum(new MpfrBigNum(number));
            }
            catch (Error e) {
                return new MpirBigNum(number);
            }
        }
        else {
            return new MpirBigNum(number);
        }
    }

    private MpirBigNum(Apfloat number) {

        mpfMemory = new MpfMemory(number.toString(true));

    }

    private MpirBigNum(String number) {

        mpfMemory = new MpfMemory(number);

    }

    public MpirBigNum(double number) {
        mpfMemory = new MpfMemory(number);
    }

    public MpirBigNum(int number) {
        mpfMemory = new MpfMemory(number);
    }

    public MpirBigNum(MpirBigNum number) {
        mpfMemory = new MpfMemory(number.mpfMemory.peer);
    }

    public void set(MpirBigNum number) {
        mpfMemory.set(number.mpfMemory);
    }

    public void set(double number) {
        mpfMemory.set(number);
    }

    public void set(int number) {
        mpfMemory.set(number);
    }

    public void set(String number) {
        mpfMemory.set(number);
    }

    public mpf_t getPeer() { return mpfMemory.peer;}

    public MpirBigNum square(MpirBigNum result) {
        __gmpf_mul(result.mpfMemory.peer, mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum square() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_mul(result.mpfMemory.peer, mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum add(MpirBigNum b, MpirBigNum result) {
        __gmpf_add(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum add(MpirBigNum b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_add(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum add(int b, MpirBigNum result) {
        __gmpf_add_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum add(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_add_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum sub(MpirBigNum b, MpirBigNum result) {
        __gmpf_sub(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum sub(MpirBigNum b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_sub(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum sub(int b, MpirBigNum result) {
        __gmpf_sub_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum sub(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_sub_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum r_sub(int b, MpirBigNum result) {
        __gmpf_ui_sub(result.mpfMemory.peer, b, mpfMemory.peer);
        return result;
    }

    public MpirBigNum r_sub(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_ui_sub(result.mpfMemory.peer, b, mpfMemory.peer);
        return result;
    }

    public MpirBigNum mult(MpirBigNum b, MpirBigNum result) {
        __gmpf_mul(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum mult(MpirBigNum b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_mul(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum mult(int b, MpirBigNum result) {
        __gmpf_mul_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum mult(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_mul_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum mult2(MpirBigNum result) {
        __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, 1);
        return result;
    }

    public MpirBigNum mult2() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, 1);
        return result;
    }

    public MpirBigNum mult4() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, 2);
        return result;
    }

    public MpirBigNum mult4(MpirBigNum result) {
        __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, 2);
        return result;
    }


    public MpirBigNum divide(MpirBigNum b, MpirBigNum result) {
        __gmpf_div(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum divide(MpirBigNum b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_div(result.mpfMemory.peer, mpfMemory.peer, b.mpfMemory.peer);
        return result;
    }

    public MpirBigNum divide(int b, MpirBigNum result) {
        __gmpf_div_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum divide(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_div_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum r_divide(int b, MpirBigNum result) {
        __gmpf_ui_div(result.mpfMemory.peer, b, mpfMemory.peer);
        return result;
    }

    public MpirBigNum r_divide(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_ui_div(result.mpfMemory.peer, b, mpfMemory.peer);
        return result;
    }

    public MpirBigNum reciprocal(MpirBigNum result) {
        __gmpf_ui_div(result.mpfMemory.peer, 1, mpfMemory.peer);
        return result;
    }

    public MpirBigNum reciprocal() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_ui_div(result.mpfMemory.peer, 1, mpfMemory.peer);
        return result;
    }

    public MpirBigNum divide2(MpirBigNum result) {
        __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, 1);
        return result;
    }

    public MpirBigNum divide2() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, 1);
        return result;
    }

    public MpirBigNum divide4() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, 2);
        return result;
    }

    public MpirBigNum divide4(MpirBigNum result) {
        __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, 2);
        return result;
    }

    public MpirBigNum shift2toi(long val) {
        MpirBigNum result = new MpirBigNum();
        if(val < 0) {
            __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, -val);
        }
        else {
            __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, val);
        }
        return result;
    }

    public MpirBigNum shift2toi(long val, MpirBigNum result) {
        if(val < 0) {
            __gmpf_div_2exp(result.mpfMemory.peer, mpfMemory.peer, -val);
        }
        else {
            __gmpf_mul_2exp(result.mpfMemory.peer, mpfMemory.peer, val);
        }
        return result;
    }

    public MpirBigNum pow(int b) {
        MpirBigNum result = new MpirBigNum();
        __gmpf_pow_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum pow(int b, MpirBigNum result) {
        __gmpf_pow_ui(result.mpfMemory.peer, mpfMemory.peer, b);
        return result;
    }

    public MpirBigNum abs(MpirBigNum result) {
        __gmpf_abs(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum abs() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_abs(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum sqrt(MpirBigNum result) {
        __gmpf_sqrt(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum sqrt() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_sqrt(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum negate(MpirBigNum result) {
        __gmpf_neg(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public MpirBigNum negate() {
        MpirBigNum result = new MpirBigNum();
        __gmpf_neg(result.mpfMemory.peer, mpfMemory.peer);
        return result;
    }

    public int compare(MpirBigNum other) {
        return __gmpf_cmp(mpfMemory.peer, other.mpfMemory.peer);
    }

    public int compare(double other) {
        return __gmpf_cmp_d(mpfMemory.peer, other);
    }

    public boolean isPositive() {
        return __gmpf_cmp_ui(mpfMemory.peer, 0) > 0;
    }

    public boolean isZero() {
        return __gmpf_cmp_ui(mpfMemory.peer, 0) == 0;
    }

    public boolean isOne() {
        return __gmpf_cmp_ui(mpfMemory.peer, 1) == 0;
    }

    public boolean isNegative() {
        return __gmpf_cmp_ui(mpfMemory.peer, 0) < 0;
    }

    public double doubleValue() {
        return __gmpf_get_d(mpfMemory.peer);
    }

    public MantExp getMantExp() {

        if(isLong4) {
            int[] exp = new int[1];
            double d = __gmpf_get_d_2exp(exp, mpfMemory.peer);
            if(d == 0) {
                return new MantExp();
            }
            return new MantExp(exp[0], d);
        }
        else {
            long[] exp = new long[1];
            double d = __gmpf_get_d_2exp(exp, mpfMemory.peer);
            if(d == 0) {
                return new MantExp();
            }
            return new MantExp(exp[0], d);
        }

    }

    public static MpirBigNum max(MpirBigNum x, MpirBigNum y) {
        if(__gmpf_cmp(x.mpfMemory.peer, y.mpfMemory.peer) >= 0) {
            return x;
        }
        else {
            return y;
        }
    }

    public static MpirBigNum min(MpirBigNum x, MpirBigNum y) {
        if(__gmpf_cmp(x.mpfMemory.peer, y.mpfMemory.peer) <= 0) {
            return x;
        }
        else {
            return y;
        }
    }

    public static MpirBigNum getMax() {
        return new MpirBigNum(Double.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "" + doubleValue();
    }

    public static void set(MpirBigNum destre, MpirBigNum destim, MpirBigNum srcre, MpirBigNum srcim) {
        mpir_fz_set(destre.mpfMemory.peer, destim.mpfMemory.peer, srcre.mpfMemory.peer, srcim.mpfMemory.peer);
    }

    public static double[] get_d (MpirBigNum re, MpirBigNum im) {
        double[] reD = new double[1];
        double[] imD = new double[1];
        mpir_fz_get_d(reD, imD, re.mpfMemory.peer, im.mpfMemory.peer);
        return new double[] {reD[0], imD[0]};
    }

    public static MantExp[] get_d_2exp (MpirBigNum re, MpirBigNum im) {

        double[] reD = new double[1];
        double[] imD = new double[1];
        if(isLong4) {

            int[] reExp = new int[1];
            int[] imExp = new int[1];
            mpir_fz_get_d_2exp(reD, imD, reExp, imExp, re.mpfMemory.peer, im.mpfMemory.peer);

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
            mpir_fz_get_d_2exp(reD, imD, reExp, imExp, re.mpfMemory.peer, im.mpfMemory.peer);
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

    public static void z_sqr_p_c(MpirBigNum re, MpirBigNum im, MpirBigNum temp, MpirBigNum reSqr, MpirBigNum imSqr, MpirBigNum normSqr, MpirBigNum cre, MpirBigNum cim) {

        mpir_fz_square_plus_c(re.mpfMemory.peer, im.mpfMemory.peer, temp.mpfMemory.peer, reSqr.mpfMemory.peer, imSqr.mpfMemory.peer, normSqr.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer);

    }

    public static void z_sqr_p_c(MpirBigNum re, MpirBigNum im, MpirBigNum temp1, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum cre, MpirBigNum cim) {

        mpir_fz_square_plus_c_simple(re.mpfMemory.peer, im.mpfMemory.peer, temp1.mpfMemory.peer, temp2.mpfMemory.peer, temp3.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer, 1, use_threads);

    }

    static double[] valRe = new double[1];
    static double[] valIm = new double[1];
    static double[] mantissaRe = new double[1];
    static double[] mantissaIm = new double[1];

    static long[] reExpL = new long[1];
    static long[] imExpL = new long[1];
    static int[] reExp = new int[1];
    static int[] imExp = new int[1];

    public static void z_sqr_p_c_with_reduction(MpirBigNum re, MpirBigNum im, MpirBigNum temp1, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum cre, MpirBigNum cim, boolean deepZoom, Complex cz, MantExpComplex mcz) {

        if(deepZoom) {
            if(isLong4) {
                mpir_fz_square_plus_c_simple_with_reduction_deep(re.mpfMemory.peer, im.mpfMemory.peer, temp1.mpfMemory.peer, temp2.mpfMemory.peer, temp3.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer, 1, use_threads, mantissaRe, mantissaIm, reExp, imExp);
                mcz.set(reExp[0], imExp[0], mantissaRe[0], mantissaIm[0]);
            }
            else {
                mpir_fz_square_plus_c_simple_with_reduction_deep(re.mpfMemory.peer, im.mpfMemory.peer, temp1.mpfMemory.peer, temp2.mpfMemory.peer, temp3.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer, 1, use_threads, mantissaRe, mantissaIm, reExpL, imExpL);
                mcz.set(reExpL[0], imExpL[0], mantissaRe[0], mantissaIm[0]);
            }
        }
        else {
            mpir_fz_square_plus_c_simple_with_reduction_not_deep(re.mpfMemory.peer, im.mpfMemory.peer, temp1.mpfMemory.peer, temp2.mpfMemory.peer, temp3.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer, 1, use_threads, valRe, valIm);
            cz.assign(valRe[0], valIm[0]);
        }

    }

    public static void z_sqr_p_c_no_threads(MpirBigNum re, MpirBigNum im, MpirBigNum temp1, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum cre, MpirBigNum cim) {

        mpir_fz_square_plus_c_simple(re.mpfMemory.peer, im.mpfMemory.peer, temp1.mpfMemory.peer, temp2.mpfMemory.peer, temp3.mpfMemory.peer, cre.mpfMemory.peer, cim.mpfMemory.peer, 1, 0);

    }

    public static void z_sqr(MpirBigNum re, MpirBigNum im, MpirBigNum temp, MpirBigNum reSqr, MpirBigNum imSqr, MpirBigNum normSqr) {

        mpir_fz_square(re.mpfMemory.peer, im.mpfMemory.peer, temp.mpfMemory.peer, reSqr.mpfMemory.peer, imSqr.mpfMemory.peer, normSqr.mpfMemory.peer);

    }

    public static void norm_sqr_with_components( MpirBigNum reSqr, MpirBigNum imSqr, MpirBigNum normSqr, MpirBigNum re, MpirBigNum im) {

        mpir_fz_norm_square_with_components(reSqr.mpfMemory.peer, imSqr.mpfMemory.peer, normSqr.mpfMemory.peer, re.mpfMemory.peer, im.mpfMemory.peer, use_threads);

    }

    public static void norm_sqr( MpirBigNum normSqr, MpirBigNum temp1, MpirBigNum re, MpirBigNum im) {

        mpir_fz_norm_square(normSqr.mpfMemory.peer, temp1.mpfMemory.peer, re.mpfMemory.peer, im.mpfMemory.peer, use_threads);

    }

    public static void norm_sqr_no_threads( MpirBigNum normSqr, MpirBigNum temp1, MpirBigNum re, MpirBigNum im) {

        mpir_fz_norm_square(normSqr.mpfMemory.peer, temp1.mpfMemory.peer, re.mpfMemory.peer, im.mpfMemory.peer, 0);

    }

    public static void self_add(MpirBigNum re, MpirBigNum im, MpirBigNum val_re, MpirBigNum val_im) {
        mpir_fz_self_add(re.mpfMemory.peer, im.mpfMemory.peer, val_re.mpfMemory.peer, val_im.mpfMemory.peer);
    }

    public static void self_sub(MpirBigNum re, MpirBigNum im, MpirBigNum val_re, MpirBigNum val_im) {
        mpir_fz_self_sub(re.mpfMemory.peer, im.mpfMemory.peer, val_re.mpfMemory.peer, val_im.mpfMemory.peer);
    }

    public static void rotation(MpirBigNum x, MpirBigNum y, MpirBigNum tempRe, MpirBigNum tempIm, MpirBigNum F, MpirBigNum A, MpirBigNum AsB, MpirBigNum ApB) {
        mpir_fz_rotation(x.mpfMemory.peer, y.mpfMemory.peer, tempRe.mpfMemory.peer, tempIm.mpfMemory.peer, F.mpfMemory.peer, A.mpfMemory.peer, AsB.mpfMemory.peer, ApB.mpfMemory.peer);
    }

    public static void AsBmC(MpirBigNum temp, MpirBigNum A, MpirBigNum B, int C) {
        mpir_fz_AsBmC(temp.mpfMemory.peer, A.mpfMemory.peer, B.mpfMemory.peer, C);
    }

    public static void ApBmC(MpirBigNum temp, MpirBigNum A, MpirBigNum B, int C) {
        mpir_fz_ApBmC(temp.mpfMemory.peer, A.mpfMemory.peer, B.mpfMemory.peer, C);
    }

    public static void ApBmC_DsEmG(MpirBigNum temp, MpirBigNum temp2, MpirBigNum A, MpirBigNum B, MpirBigNum C, MpirBigNum D, MpirBigNum E, MpirBigNum G) {
        mpir_fz_ApBmC_DsEmG(temp.mpfMemory.peer, temp2.mpfMemory.peer, A.mpfMemory.peer, B.mpfMemory.peer, C.mpfMemory.peer, D.mpfMemory.peer, E.mpfMemory.peer, G.mpfMemory.peer);
    }

    public static void ApBmC_DpEmG(MpirBigNum temp, MpirBigNum temp2, MpirBigNum A, MpirBigNum B, MpirBigNum C, MpirBigNum D, MpirBigNum E, MpirBigNum G) {
        mpir_fz_ApBmC_DpEmG(temp.mpfMemory.peer, temp2.mpfMemory.peer, A.mpfMemory.peer, B.mpfMemory.peer, C.mpfMemory.peer, D.mpfMemory.peer, E.mpfMemory.peer, G.mpfMemory.peer);
    }

    public static void r_ball_pow2(MpirBigNum r, MpirBigNum az, MpirBigNum r0, MpirBigNum azsquare) {

        mpir_fz_r_ball_pow2(r.mpfMemory.peer, az.mpfMemory.peer, r0.mpfMemory.peer, azsquare.mpfMemory.peer);

    }

    public String toFullString() {
//        int n = (int) MyApfloat.precision * 2;
//        byte[] buf = new byte[n];
//        String template = "%." + MyApfloat.precision + "f";
//        __gmp_snprintf(buf, n, template, mpfMemory.peer);
//        return new String(buf).trim();
        return new MpfrBigNum(this).toFullString();
    }

    public Apfloat toApfloat() { return new MyApfloat(toFullString());}

    public int signum() {
        if(isZero()) {
            return 0;
        }
        else if(isPositive()) {
            return 1;
        }
        return - 1;
    }

    public static void main(String[] args) {
        MpfrBigNum b = new MpfrBigNum("1.536436437574574384584385466456436634574743734743754365532432523531");
        MpirBigNum a = new MpirBigNum(b);
        System.out.println(a.doubleValue());
        System.out.println(b.doubleValue());
    }

}
