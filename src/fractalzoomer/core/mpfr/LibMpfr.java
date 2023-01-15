package fractalzoomer.core.mpfr;


import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LibMpfr {
    private static final String linuxLib = "libmpfr.so";

    //When using mingw
    private static final String windowsLib = "libmpfr-6.dll";
    private static final String[] winLibs = new String[] {windowsLib, "libgmp-10.dll", "libwinpthread-1.dll"};

    //Will not work
    //private static final String windowsLib = "cygmpfr-6.dll";
    //private static final String[] winLibs = new String[] {windowsLib, "cyggmp-10.dll", "cygwin1.dll"};
    private static final String[] linuxLibs = new String[] {linuxLib};

    public static Exception LOAD_ERROR = null;

    public static boolean isLong4 = Native.LONG_SIZE == 4;

    public static final int MPFR_RNDN	= 0;//	Round to nearest, with ties to even.
    public static final int MPFR_RNDZ	= 1;//	Round toward zero.
    public static final int MPFR_RNDU	= 2;//	Round toward +Infinity.
    public static final int MPFR_RNDD	= 3;//	Round toward -Infinity.
    public static final int MPFR_RNDA	= 4;//	Round away from zero.
    public static final int MPFR_RNDF	= 5;//	Faithful rounding.
    public static final int MPFR_RNDNA	= -1;//	Round to nearest, with ties away from zero (mpfr_lib.mpfr_round).

    private static final Class SIZE_T_CLASS;

    static {
        if (Native.SIZE_T_SIZE == 4) {
            SIZE_T_CLASS = LibMpfr.SizeT4.class;
        } else if (Native.SIZE_T_SIZE == 8) {
            SIZE_T_CLASS = LibMpfr.SizeT8.class;
        } else {
            throw new AssertionError("Unexpected Native.SIZE_T_SIZE: " + Native.SIZE_T_SIZE);
        }
        System.out.println("Size_t bytes: " + Native.SIZE_T_SIZE);
        System.out.println("  Long bytes: " + Native.LONG_SIZE);

    }

    static {

        if(!Platform.isWindows() && !Platform.isLinux()) {
            LOAD_ERROR = new Exception("Cannot load mpfr if the platform is not windows or linux");
        }
        else {
            try {
                loadLibMpfr();
            } catch (Exception ex) {
                LOAD_ERROR = ex;
            }
        }

        if(LOAD_ERROR != null) {
            System.out.println("Cannot load mpfr: " + LOAD_ERROR.getMessage());
        }

    }

    private static void loadLibMpfr() throws Exception {

        String libName = Platform.isWindows() ? windowsLib : linuxLib;
        String resourcesDir = "/fractalzoomer/native/" + Platform.RESOURCE_PREFIX;

        try {

            String tmpDirsLocation = System.getProperty("java.io.tmpdir");
            Path tmpdir = Files.createTempDirectory(Paths.get(tmpDirsLocation), "fzNativeLibs");
            tmpdir.toFile().deleteOnExit();

            String[] libs = Platform.isWindows() ? winLibs : linuxLibs;

            for(String lib : libs) {
                InputStream in = LibMpfr.class.getResourceAsStream(resourcesDir + "/" + lib);
                Path tgt = tmpdir.resolve(lib);
                Files.copy(in, tgt);
                tgt.toFile().deleteOnExit();
            }

            load(tmpdir.resolve(libName).toAbsolutePath().toString());
            return;
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        } catch (UnsatisfiedLinkError ignored) {
            System.out.println(ignored.getMessage());
        }
        // Fall back to system-wide search.
        try {
            load(libName);
        }
        catch (Exception ex) {
            throw new Exception("Cannot load the library: " + ex.getMessage());
        }
        catch (UnsatisfiedLinkError ex) {
            throw new Exception("Cannot load the library: " + ex.getMessage());
        }

    }

    /** Dummy method to force class initialization. */
    public static void init() {
    }

    private static void load(String name) {
        NativeLibrary library = NativeLibrary.getInstance(name, LibMpfr.class.getClassLoader());
        Native.register(LibMpfr.class, library);
        Native.register(SIZE_T_CLASS, library);


    }

    public static void delete() {
        if(LOAD_ERROR == null) {
            Native.unregister(LibMpfr.class);
            Native.unregister(SIZE_T_CLASS);
        }
    }

    // CHECKSTYLE.OFF: ConstantName match native name
    /**
     * The GMP version number, as a null-terminated string, in the form “i.j.k”. The embedded release
     * is "6.1.1". Note that the format “i.j” was used, before version 4.3.0, when k was zero.
     */
    public static String mpfr_version;
    public static String __gmp_version;
    // CHECKSTYLE.ON: ConstantName

    static {

        if(LOAD_ERROR == null) {

            if(Platform.isWindows()) {
                __gmp_version = NativeLibrary.getProcess() // library is already loaded and linked.
                        .getGlobalVariableAddress("__gmp_version") // &(const char* __gmp_version)
                        .getPointer(0) // const char* __gmp_version
                        .getString(0);
            }



            mpfr_version = mpfr_get_version();

            if(Platform.isWindows()) {
                System.out.println(" GMP Version: " + __gmp_version);
            }
            System.out.println("MPFR Version: " + mpfr_version);
        }
    }

    public static native String mpfr_get_version();

    static class SizeT4 {

    }

    /** Used on systems with 8-byte size_t. */
    static class SizeT8 {

    }

    private LibMpfr() {
    }

    public static native void mpfr_init2(mpfr_t x, long precision);

    public static native void mpfr_set_default_prec(long precision);
    public static native int mpfr_set(mpfr_t rop, mpfr_t op, int rnd);

    //This is supposed to be used with set default prec, but its not working with thread safe
    public static native int mpfr_init_set_str (mpfr_t x, String s, int base, int rnd);

    public static native int mpfr_set_d(mpfr_t rop, double op, int rnd);

    public static native int mpfr_set_ui (mpfr_t rop, long op, int rnd);
    public static native int mpfr_set_si (mpfr_t rop, long  op, int rnd);
    public static native int mpfr_set_flt (mpfr_t rop, float op, int rnd);
    //public static native int mpfr_set_z (mpfr_t rop, mpz_t op, int rnd);

    public static native void mpfr_set_zero (mpfr_t x, int sign);

    public static native void mpfr_set_prec (mpfr_t x, long prec);
    public static native long mpfr_get_prec (mpfr_t x);

    public static native int mpfr_set_str(mpfr_t rop, String s, int base, int rnd);

    public static native void mpfr_clear(mpfr_t op);

    public static native double mpfr_get_d(mpfr_t op, int rnd);

    public static native double mpfr_get_d_2exp (long[] exp, mpfr_t op, int rnd);
    public static native double mpfr_get_d_2exp (int[] exp, mpfr_t op, int rnd);

    public static native float mpfr_get_flt (mpfr_t op, int rnd);

    public static native int mpfr_sqr(mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_mul(mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_mul_ui (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_mul_si (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_mul_d (mpfr_t rop, mpfr_t op1, double op2, int rnd);
    //public static native int mpfr_mul_z (mpfr_t rop, mpfr_t op1, mpz_t op2, int rnd);

    public static native int mpfr_hypot (mpfr_t rop, mpfr_t x, mpfr_t y, int rnd);

    public static native int mpfr_mul_2ui(mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_mul_2si (mpfr_t rop, mpfr_t op1, long op2, int rnd);

    public static native int mpfr_div_2ui(mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_div_2si (mpfr_t rop, mpfr_t op1, long op2, int rnd);

    public static native int mpfr_div (mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_ui_div (mpfr_t rop, long op1, mpfr_t op2, int rnd);
    public static native int mpfr_div_ui (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_si_div (mpfr_t rop, long op1, mpfr_t op2, int rnd);
    public static native int mpfr_div_si (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_d_div (mpfr_t rop, double op1, mpfr_t op2, int rnd);
    public static native int mpfr_div_d (mpfr_t rop, mpfr_t op1, double op2, int rnd);
    //public static native int mpfr_div_z (mpfr_t rop, mpfr_t op1, mpz_t op2, int rnd);

    public static native int mpfr_add(mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_add_ui (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_add_si (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_add_d (mpfr_t rop, mpfr_t op1, double op2, int rnd);
    //public static native int mpfr_add_z (mpfr_t rop, mpfr_t op1, mpz_t op2, int rnd);

    public static native int mpfr_sub(mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_ui_sub (mpfr_t rop, long op1, mpfr_t op2, int rnd);
    public static native int mpfr_sub_ui (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_si_sub (mpfr_t rop, long op1, mpfr_t op2, int rnd);
    public static native int mpfr_sub_si (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_d_sub (mpfr_t rop, double op1, mpfr_t op2, int rnd);
    public static native int mpfr_sub_d (mpfr_t rop, mpfr_t op1, double op2, int rnd);
    //public static native int mpfr_z_sub (mpfr_t rop, mpz_t op1, mpfr_t op2, int rnd);
    //public static native int mpfr_sub_z (mpfr_t rop, mpfr_t op1, mpz_t op2, int rnd);

    public static native int mpfr_sqrt(mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_sqrt_ui (mpfr_t rop, long op, int rnd);
    public static native int mpfr_rec_sqrt (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_cbrt(mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_rootn_ui(mpfr_t rop, mpfr_t op, long n, int rnd);

    public static native int mpfr_neg(mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_cmp(mpfr_t op1, mpfr_t op2);
    public static native int mpfr_cmp_ui (mpfr_t op1, long op2);
    public static native int mpfr_cmp_si (mpfr_t op1, long op2);
    public static native int mpfr_cmp_d (mpfr_t op1, double op2);
    //public static native int mpfr_cmp_z (mpfr_t op1, mpz_t op2);

    public static native int mpfr_cmpabs (mpfr_t op1, mpfr_t op2);

    public static native int mpfr_zero_p (mpfr_t op);
    public static native int mpfr_nan_p (mpfr_t op);
    public static native int mpfr_inf_p (mpfr_t op);
    public static native int mpfr_number_p (mpfr_t op);
    public static native int mpfr_regular_p (mpfr_t op);

    public static native int mpfr_sgn (mpfr_t op);

    public static native int mpfr_greater_p(mpfr_t op1, mpfr_t op2);
    public static native int mpfr_greaterequal_p(mpfr_t op1, mpfr_t op2);
    public static native int mpfr_less_p(mpfr_t op1, mpfr_t op2);
    public static native int mpfr_lessequal_p(mpfr_t op1, mpfr_t op2);
    public static native int mpfr_equal_p(mpfr_t op1, mpfr_t op2);

    public static native int mpfr_log (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_log_ui (mpfr_t rop, long op, int rnd);
    public static native int mpfr_log2 (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_log10 (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_log1p (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_exp (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_exp2 (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_exp10 (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_expm1 (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_cos (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_sin (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_tan (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_sin_cos (mpfr_t sop, mpfr_t cop, mpfr_t op, int rnd);

    public static native int mpfr_sec (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_csc (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_cot (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_acos (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_asin (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_atan (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_atan2 (mpfr_t rop, mpfr_t y, mpfr_t x, int rnd);

    public static native int mpfr_cosh (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_sinh (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_tanh (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_sinh_cosh (mpfr_t sop, mpfr_t cop, mpfr_t op, int rnd);

    public static native int mpfr_sech (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_csch (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_coth (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_acosh (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_asinh (mpfr_t rop, mpfr_t op, int rnd);
    public static native int mpfr_atanh (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_pow (mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_pow_ui (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    public static native int mpfr_pow_si (mpfr_t rop, mpfr_t op1, long op2, int rnd);
    //public static native int mpfr_pow_z (mpfr_t rop, mpfr_t op1, mpz_t op2, int rnd);
    public static native int mpfr_ui_pow_ui (mpfr_t rop, long op1, long op2, int rnd);
    public static native int mpfr_ui_pow (mpfr_t rop, long op1, mpfr_t op2, int rnd);

    public static native int mpfr_abs (mpfr_t rop, mpfr_t op, int rnd);

    public static native int mpfr_min (mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);
    public static native int mpfr_max (mpfr_t rop, mpfr_t op1, mpfr_t op2, int rnd);

    public static native int mpfr_const_log2 (mpfr_t rop, int rnd);
    public static native int mpfr_const_pi (mpfr_t rop, int rnd);
    public static native int mpfr_const_euler (mpfr_t rop, int rnd);
    public static native int mpfr_const_catalan (mpfr_t rop, int rnd);


    //Custom functions implemented for better performance
    public static native void mpfr_fz_square_plus_c (mpfr_t re, mpfr_t im, mpfr_t temp, mpfr_t re_sqr, mpfr_t im_sqr, mpfr_t norm_sqr, mpfr_t cre, mpfr_t cim, int rnd);
    public static native void mpfr_fz_square (mpfr_t re, mpfr_t im, mpfr_t temp, mpfr_t re_sqr, mpfr_t im_sqr, mpfr_t norm_sqr, int rnd);
    public static native void mpfr_fz_norm_square_with_components (mpfr_t re_sqr, mpfr_t im_sqr, mpfr_t norm_sqr, mpfr_t re, mpfr_t im, int rnd);
    public static native void mpfr_fz_get_d (double[] valRe, double[] valIm, mpfr_t re, mpfr_t im, int rnd);
    public static native int mpfr_fz_get_d_2exp (double[] valRe, double[] valIm, long[] expRe, long[] expIm, mpfr_t re, mpfr_t im, int rnd);
    public static native int mpfr_fz_get_d_2exp (double[] valRe, double[] valIm, int[] expRe, int[] expIm, mpfr_t re, mpfr_t im, int rnd);
    public static native void mpfr_fz_square_plus_c_simple (mpfr_t re, mpfr_t im, mpfr_t temp1, mpfr_t temp2, mpfr_t cre, mpfr_t cim, int rnd);
    public static native void mpfr_fz_set (mpfr_t destre, mpfr_t destim, mpfr_t srcre, mpfr_t srcim, int rnd);
    public static native void mpfr_fz_norm_square (mpfr_t norm_sqr, mpfr_t temp1, mpfr_t re, mpfr_t im, int rnd);
    public static native void mpfr_fz_self_add (mpfr_t re, mpfr_t im, mpfr_t val_re, mpfr_t val_im, int rnd);
    public static native void mpfr_fz_self_sub (mpfr_t re, mpfr_t im, mpfr_t val_re, mpfr_t val_im, int rnd);
    public static native void mpfr_fz_rotation (mpfr_t x, mpfr_t y, mpfr_t temp_re, mpfr_t temp_im, mpfr_t f, mpfr_t a, mpfr_t asb, mpfr_t apb, int rnd);
    public static native void mpfr_fz_AsBmC (mpfr_t temp, mpfr_t a, mpfr_t b, int c, int rnd);
    public static native void mpfr_fz_ApBmC (mpfr_t temp, mpfr_t a, mpfr_t b, int c, int rnd);
    public static native void mpfr_fz_ApBmC_DsEmG (mpfr_t temp, mpfr_t temp2, mpfr_t a, mpfr_t b, double c, mpfr_t d, mpfr_t e, double g, int rnd);
    public static native void mpfr_fz_ApBmC_DpEmG (mpfr_t temp, mpfr_t temp2, mpfr_t a, mpfr_t b, mpfr_t c, mpfr_t d, mpfr_t e, mpfr_t g, int rnd);
}
