package fractalzoomer.core.mpir;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import fractalzoomer.core.TaskRender;
import fractalzoomer.utils.NativeLoader;

public class LibMpir {
    public static Exception MPIR_LOAD_ERROR = null;

    public static boolean isLong4 = Native.LONG_SIZE == 4;

    private static NativeLibrary library;

    public static boolean mpirHasError() {
        return MPIR_LOAD_ERROR != null;
    }

    private static final Class SIZE_T_CLASS;

    static {
        if (Native.SIZE_T_SIZE == 4) {
            SIZE_T_CLASS = LibMpir.SizeT4.class;
        } else if (Native.SIZE_T_SIZE == 8) {
            SIZE_T_CLASS = LibMpir.SizeT8.class;
        } else {
            throw new AssertionError("Unexpected Native.SIZE_T_SIZE: " + Native.SIZE_T_SIZE);
        }
    }

    static {

        if(!TaskRender.LOAD_MPIR) {
            MPIR_LOAD_ERROR = new Exception("Disabled loading of mpir");
        }
        else if(!Platform.isWindows()) {
            MPIR_LOAD_ERROR = new Exception("Cannot load mpir if the platform is not windows");
        }
        else if(!Platform.is64Bit()) {
            MPIR_LOAD_ERROR = new Exception("Cannot load mpir if the OS is 32 bit");
        }
        else if(TaskRender.MPIR_WINDOWS_ARCHITECTURE.isEmpty()) {
            MPIR_LOAD_ERROR = new Exception("Cannot load mpir, no available architecture was found");
        }
        else {
            try {
                loadLibMpir();
            } catch (Exception ex) {
                MPIR_LOAD_ERROR = ex;
            }
            catch (Error ex) {
                MPIR_LOAD_ERROR = new Exception(ex.getMessage());
            }
        }

        if(mpirHasError()) {
            System.out.println("Cannot load mpir: " + MPIR_LOAD_ERROR.getMessage());
        }

    }

    private static void loadLibMpir() throws Exception {

        String libName = TaskRender.MPIR_WINDOWS_ARCHITECTURE + "/" + Platform.RESOURCE_PREFIX + "/" + NativeLoader.mpirWinLib;

        System.out.println("Loading " + libName);

        try {
            load(NativeLoader.tmpdir.resolve(libName).toAbsolutePath().toString());
            return;
        }
        catch (UnsatisfiedLinkError e) {
            System.out.println(e.getMessage());
            MPIR_LOAD_ERROR = new Exception(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            MPIR_LOAD_ERROR = e;
        }
        // Fall back to system-wide search.
//        try {
//            load(libName);
//        }
//        catch (UnsatisfiedLinkError ex) {
//            throw new Exception("Cannot load the library: " + ex.getMessage());
//        }
//        catch (Exception ex) {
//            throw new Exception("Cannot load the library: " + ex.getMessage());
//        }

    }

    /** Dummy method to force class initialization. */
    public static void init() {
    }

    private static void load(String name) {
        library = NativeLibrary.getInstance(name, LibMpir.class.getClassLoader());
        Native.register(LibMpir.class, library);
        Native.register(SIZE_T_CLASS, library);
    }

    public static void delete() {
        if(!mpirHasError()) {
            Memory.disposeAll();
            Native.unregister(LibMpir.class);
            Native.unregister(SIZE_T_CLASS);
            library.close();
            //library = null;
        }
    }

    // CHECKSTYLE.OFF: ConstantName match native name
    /**
     * The GMP version number, as a null-terminated string, in the form “i.j.k”. The embedded release
     * is "6.1.1". Note that the format “i.j” was used, before version 4.3.0, when k was zero.
     */
    public static  String __mpir_version;
    //public static final String __gmp_version;
    // CHECKSTYLE.ON: ConstantName

    static {
//        __gmp_version = NativeLibrary.getProcess() // library is already loaded and linked.
//                .getGlobalVariableAddress("__gmp_version") // &(const char* __gmp_version)
//                .getPointer(0) // const char* __gmp_version
//                .getString(0);

        if(!mpirHasError()) {
            __mpir_version = NativeLibrary.getProcess() // library is already loaded and linked.
                    .getGlobalVariableAddress("__mpir_version") // &(const char* __gmp_version)
                    .getPointer(0) // const char* __mpir_version
                    .getString(0);

            //System.out.println(__gmp_version);
            System.out.println("MPIR Version: " + __mpir_version);
        }
    }

    static class SizeT4 {

    }

    /** Used on systems with 8-byte size_t. */
    static class SizeT8 {

    }

    private LibMpir() {
    }

    public static native void __gmpf_init2(mpf_t x, long precision);
    public static native int __gmpf_set(mpf_t rop, mpf_t op);
    public static native int __gmpf_set_d(mpf_t rop, double op);

    public static native int __gmpf_set_si (mpf_t rop, long  op);

    public static native int __gmpf_set_str(mpf_t rop, String s, int base);

    public static native void __gmpf_clear(mpf_t op);

    public static native int __gmpf_add(mpf_t rop, mpf_t op1, mpf_t op2);
    public static native int __gmpf_add_ui(mpf_t rop, mpf_t op1, long op2);
    public static native int __gmpf_sub(mpf_t rop, mpf_t op1, mpf_t op2);

    public static native int __gmpf_sub_ui(mpf_t rop, mpf_t op1, long op2);
    public static native int __gmpf_ui_sub(mpf_t rop, long op1, mpf_t op2);
    public static native int __gmpf_mul(mpf_t rop, mpf_t op1, mpf_t op2);

    public static native int __gmpf_mul_ui(mpf_t rop, mpf_t op1, long op2);

    public static native int __gmpf_div(mpf_t rop, mpf_t op1, mpf_t op2);

    public static native int __gmpf_div_ui(mpf_t rop, mpf_t op1, long op2);

    public static native int __gmpf_ui_div(mpf_t rop, long op1, mpf_t op2);

    public static native int __gmpf_sqrt(mpf_t rop, mpf_t op1);

    public static native int __gmpf_sqrt_ui(mpf_t rop, long op1);

    public static native int __gmpf_abs(mpf_t rop, mpf_t op1);

    public static native int __gmpf_neg(mpf_t rop, mpf_t op1);

    public static native int __gmpf_pow_ui(mpf_t rop, mpf_t op1, long op2);

    public static native int __gmpf_mul_2exp(mpf_t rop, mpf_t op1, long op2);

    public static native int __gmpf_div_2exp(mpf_t rop, mpf_t op1, long op2);

    public static native int __gmpf_cmp(mpf_t op1, mpf_t op2);
    public static native int __gmpf_cmp_d(mpf_t op1, double op2);

    public static native int __gmpf_cmp_ui(mpf_t op1, long op2);

    public static native int __gmpf_cmp_si(mpf_t op1, long op2);


    public static native double __gmpf_get_d(mpf_t op);

    public static native long __gmpf_get_si (mpf_t op);
    public static native long __gmpf_get_ui (mpf_t op);

    public static native double __gmpf_get_d_2exp (long[] exp, mpf_t op);

    public static native double __gmpf_get_d_2exp (int[] exp, mpf_t op);

    public static native void __gmpf_ceil (mpf_t rop, mpf_t op);
    public static native void __gmpf_floor (mpf_t rop, mpf_t op);
    public static native void __gmpf_trunc (mpf_t rop, mpf_t op);

    public static native void mpir_fz_square_plus_c (mpf_t re, mpf_t im, mpf_t temp, mpf_t re_sqr, mpf_t im_sqr, mpf_t norm_sqr, mpf_t cre, mpf_t cim);
    public static native void mpir_fz_square (mpf_t re, mpf_t im, mpf_t temp, mpf_t re_sqr, mpf_t im_sqr, mpf_t norm_sqr);
    public static native void mpir_fz_norm_square_with_components (mpf_t re_sqr, mpf_t im_sqr, mpf_t norm_sqr, mpf_t re, mpf_t im, int use_threads);
    public static native void mpir_fz_get_d (double[] valRe, double[] valIm, mpf_t re, mpf_t im);
    public static native void mpir_fz_get_d_2exp (double[] valRe, double[] valIm, long[] expRe, long[] expIm, mpf_t re, mpf_t im);
    public static native void mpir_fz_get_d_2exp (double[] valRe, double[] valIm, int[] expRe, int[] expIm, mpf_t re, mpf_t im);
    public static native void mpir_fz_square_plus_c_simple (mpf_t re, mpf_t im, mpf_t temp1, mpf_t temp2, mpf_t temp3, mpf_t cre, mpf_t cim, int algorithm, int use_threads);
    public static native void mpir_fz_square_plus_c_simple_with_reduction_not_deep (mpf_t re, mpf_t im, mpf_t temp1, mpf_t temp2, mpf_t temp3, mpf_t cre, mpf_t cim, int algorithm, int use_threads, double[] valRe, double[] valIm);
    public static native void mpir_fz_square_plus_c_simple_with_reduction_deep (mpf_t re, mpf_t im, mpf_t temp1, mpf_t temp2, mpf_t temp3, mpf_t cre, mpf_t cim, int algorithm, int use_threads, double[] mantissaRe, double[] mantissaIm, long[] expRe, long[] expIm);
    public static native void mpir_fz_square_plus_c_simple_with_reduction_deep (mpf_t re, mpf_t im, mpf_t temp1, mpf_t temp2, mpf_t temp3, mpf_t cre, mpf_t cim, int algorithm, int use_threads, double[] mantissaRe, double[] mantissaIm, int[] expRe, int[] expIm);
    public static native void mpir_fz_set (mpf_t destre, mpf_t destim, mpf_t srcre, mpf_t srcim);
    public static native void mpir_fz_norm_square (mpf_t norm_sqr, mpf_t temp1, mpf_t re, mpf_t im, int use_threads);
    public static native void mpir_fz_self_add (mpf_t re, mpf_t im, mpf_t val_re, mpf_t val_im);
    public static native void mpir_fz_self_sub (mpf_t re, mpf_t im, mpf_t val_re, mpf_t val_im);
    public static native void mpir_fz_rotation (mpf_t x, mpf_t y, mpf_t temp_re, mpf_t temp_im, mpf_t f, mpf_t a, mpf_t asb, mpf_t apb);
    public static native void mpir_fz_AsBmC (mpf_t temp, mpf_t a, mpf_t b, int c);
    public static native void mpir_fz_ApBmC (mpf_t temp, mpf_t a, mpf_t b, int c);
    public static native void mpir_fz_ApBmC_DsEmG (mpf_t temp, mpf_t temp2, mpf_t a, mpf_t b, mpf_t c, mpf_t d, mpf_t e, mpf_t g);
    public static native void mpir_fz_ApBmC_DpEmG (mpf_t temp, mpf_t temp2, mpf_t a, mpf_t b, mpf_t c, mpf_t d, mpf_t e, mpf_t g);
    public static native void mpir_fz_r_ball_pow2 (mpf_t r, mpf_t az, mpf_t r0, mpf_t azsquare);

    public static native int __gmp_snprintf (byte[] buf, int size, String template, mpf_t value);

}