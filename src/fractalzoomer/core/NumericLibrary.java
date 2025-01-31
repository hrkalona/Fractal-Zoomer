package fractalzoomer.core;

import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.LibMpir;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import org.apfloat.Apfloat;

public class NumericLibrary {
    public static Apfloat current_size;
    public static int BIGNUM_IMPLEMENTATION = Constants.BIGNUM_AUTOMATIC;
    public static int HIGH_PRECISION_IMPLEMENTATION = Constants.ARBITRARY_AUTOMATIC;

    public static int getHighPrecisionImplementation(Apfloat size, Fractal f) {

        if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_DOUBLEDOUBLE) {
            return Constants.ARBITRARY_DOUBLEDOUBLE;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_BUILT_IN) {
            if(f.supportsBignum()) {
                return Constants.ARBITRARY_BUILT_IN;
            }

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_BIGINT) {
            if(f.supportsBigIntnum()) {
                return Constants.ARBITRARY_BIGINT;
            }

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_MPFR && !LibMpfr.mpfrHasError()) {
            if(f.supportsMpfrBignum()) {
                return Constants.ARBITRARY_MPFR;
            }

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_MPFR && LibMpfr.mpfrHasError()) {

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            if(f.supportsBignum()) {
                return Constants.ARBITRARY_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.ARBITRARY_BIGINT;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_MPIR && !LibMpir.mpirHasError()) {
            if(f.supportsMpirBignum()) {
                return Constants.ARBITRARY_MPIR;
            }

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_MPIR && LibMpir.mpirHasError()) {

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            if(f.supportsBignum()) {
                return Constants.ARBITRARY_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.ARBITRARY_BIGINT;
            }

            return Constants.ARBITRARY_APFLOAT;
        }
        else if(HIGH_PRECISION_IMPLEMENTATION == Constants.ARBITRARY_AUTOMATIC) {

            if(size.doubleValue() > f.getDoubleDoubleLimit()) {
                return Constants.ARBITRARY_DOUBLEDOUBLE;
            }

            if(LibMpfr.isMPIRBased()) {
                if (!LibMpfr.mpfrHasError() && f.supportsMpfrBignum() && (MpfrBigNum.precision >= 1100 || (!f.supportsBigIntnum() && !f.supportsBignum()))) {
                    return Constants.ARBITRARY_MPFR;
                }

                if (!LibMpir.mpirHasError() && f.supportsMpirBignum() && (MpirBigNum.precision >= 1200 || (!f.supportsBigIntnum() && !f.supportsBignum()))) {
                    return Constants.ARBITRARY_MPIR;
                }
            }
            else {
                if (!LibMpir.mpirHasError() && f.supportsMpirBignum() && (MpirBigNum.precision >= 1200 || (!f.supportsBigIntnum() && !f.supportsBignum()))) {
                    return Constants.ARBITRARY_MPIR;
                }

                if (!LibMpfr.mpfrHasError() && f.supportsMpfrBignum() && (MpfrBigNum.precision >= 1350 || (!f.supportsBigIntnum() && !f.supportsBignum()))) {
                    return Constants.ARBITRARY_MPFR;
                }
            }

            if(f.supportsBignum()) {
                return Constants.ARBITRARY_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.ARBITRARY_BIGINT;
            }

            return Constants.ARBITRARY_APFLOAT;
        }

        return Constants.ARBITRARY_APFLOAT;
    }

    public static int getBignumImplementation(Apfloat size, Fractal f) {

        double dsize = size.doubleValue();
        if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_DOUBLE) {
            return Constants.BIGNUM_DOUBLE;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_DOUBLEDOUBLE) {
            return Constants.BIGNUM_DOUBLEDOUBLE;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_BUILT_IN) {
            if(f.supportsBignum()) {
                return Constants.BIGNUM_BUILT_IN;
            }

            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_BIGINT) {
            if(f.supportsBigIntnum()) {
                return Constants.BIGNUM_BIGINT;
            }

            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPFR && !LibMpfr.mpfrHasError()) {
            if(f.supportsMpfrBignum()) {
                return Constants.BIGNUM_MPFR;
            }

            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPFR && LibMpfr.mpfrHasError()) {
            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            if(f.supportsBignum()) {
                return Constants.BIGNUM_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.BIGNUM_BIGINT;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPIR && !LibMpir.mpirHasError()) {
            if(f.supportsMpirBignum()) {
                return Constants.BIGNUM_MPIR;
            }

            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_MPIR && LibMpir.mpirHasError()) {
            if(f.supportsDouble() && dsize > f.getDoubleLimit()) {
                return Constants.BIGNUM_DOUBLE;
            }

            if(f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                return Constants.BIGNUM_DOUBLEDOUBLE;
            }

            if(f.supportsBignum()) {
                return Constants.BIGNUM_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.BIGNUM_BIGINT;
            }

            return Constants.BIGNUM_APFLOAT;
        }
        else if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_AUTOMATIC || BIGNUM_IMPLEMENTATION == Constants.BIGNUM_AUTOMATIC_ONLY_BIGNUM) {
            if(BIGNUM_IMPLEMENTATION == Constants.BIGNUM_AUTOMATIC) {
                if (f.supportsDouble() && dsize > f.getDoubleLimit()) {
                    return Constants.BIGNUM_DOUBLE;
                }

                if (f.supportsDoubleDouble() && dsize > f.getDoubleDoubleLimit()) {
                    return Constants.BIGNUM_DOUBLEDOUBLE;
                }
            }

            if(LibMpfr.isMPIRBased()) {
                if(!LibMpfr.mpfrHasError() && f.supportsMpfrBignum() && (MpfrBigNum.precision >= 1100 || (!f.supportsBigIntnum() && !f.supportsBignum()))) { //(f.supportsPeriod() && DETECT_PERIOD && MpfrBigNum.precision >= 450)
                    return Constants.BIGNUM_MPFR;
                }

                if(!LibMpir.mpirHasError() && f.supportsMpirBignum() && (MpirBigNum.precision >= 1200 || (!f.supportsBigIntnum() && !f.supportsBignum()))) { //(f.supportsPeriod() && DETECT_PERIOD && MpfrBigNum.precision >= 450)
                    return Constants.BIGNUM_MPIR;
                }
            }
            else {
                if(!LibMpir.mpirHasError() && f.supportsMpirBignum() && (MpirBigNum.precision >= 1200 || (!f.supportsBigIntnum() && !f.supportsBignum()))) { //(f.supportsPeriod() && DETECT_PERIOD && MpfrBigNum.precision >= 450)
                    return Constants.BIGNUM_MPIR;
                }

                if(!LibMpfr.mpfrHasError() && f.supportsMpfrBignum() && (MpfrBigNum.precision >= 1350 || (!f.supportsBigIntnum() && !f.supportsBignum()))) { //(f.supportsPeriod() && DETECT_PERIOD && MpfrBigNum.precision >= 450)
                    return Constants.BIGNUM_MPFR;
                }
            }

            if(f.supportsBignum()) {
                return Constants.BIGNUM_BUILT_IN;
            }

            if(f.supportsBigIntnum()) {
                return Constants.BIGNUM_BIGINT;
            }

            return Constants.BIGNUM_APFLOAT;
        }

        return Constants.BIGNUM_APFLOAT;
    }

    public static boolean allocateMPFR(Fractal f) {
        if(f == null) {
            return false;
        }
        if(TaskRender.HIGH_PRECISION_CALCULATION) {
            int hp = getHighPrecisionImplementation(current_size, f);
            return hp == Constants.ARBITRARY_MPFR;
        }

        int bn = getBignumImplementation(current_size, f);
        return bn == Constants.BIGNUM_MPFR;
    }

    public static boolean allocateMPIR(Fractal f) {
        if(f == null) {
            return false;
        }

        if(TaskRender.HIGH_PRECISION_CALCULATION) {
            int hp = getHighPrecisionImplementation(current_size, f);
            return hp == Constants.ARBITRARY_MPIR;
        }

        int bn = getBignumImplementation(current_size, f);
        return bn == Constants.BIGNUM_MPIR;
    }

    public static void deleteLibs() {
        LibMpfr.delete();
        LibMpir.delete();
    }
}
