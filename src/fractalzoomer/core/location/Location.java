package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.core.antialiasing.GaussianAntialiasingAlgorithm;
import fractalzoomer.core.location.delta.*;
import fractalzoomer.core.location.normal.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.unused.BigDecNum;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import fractalzoomer.utils.PixelOffset;
import org.apfloat.Apfloat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Location {

    protected Fractal fractal;

    protected GenericComplex reference;

    protected int indexX;
    protected int indexY;

    public static PixelOffset offset;
    protected boolean aaJitter;
    protected int extraSamplesNumber;

    public static int NUMBER_OF_AA_JITTER_KERNELS = 50;
    private static double[][] aaJitterKernelX;
    private static double[][] aaJitterKernelY;

    protected double[][][] precalculatedJitterDataDouble;
    protected MantExp[][][] precalculatedJitterDataMantexp;
    protected double[][][] precalculatedJitterDataPolarDouble;
    protected Apfloat[][][] precalculatedJitterDataApfloat;

    protected Apfloat[][][] precalculatedJitterDataPolarApfloat;
    protected MpfrBigNum[][][] precalculatedJitterDataMpfrBigNum;
    protected MpfrBigNum[][][] precalculatedJitterDataPolarMpfrBigNum;
    protected MpirBigNum[][][] precalculatedJitterDataMpirBigNum;
    protected DoubleDouble[][][] precalculatedJitterDataDoubleDouble;
    protected DoubleDouble[][][] precalculatedJitterDataPolarDoubleDouble;

    protected BigNum[][][] precalculatedJitterDataBigNum;

    protected BigIntNum[][][] precalculatedJitterDataBigIntNum;


    public static final int MAX_AA_SAMPLES_24 = 24;
    public static final int MAX_AA_SAMPLES_48 = 48;
    public static final int MAX_AA_SAMPLES_80 = 80;
    public static final int MAX_AA_SAMPLES_120 = 120;
    public static final int MAX_AA_SAMPLES_168 = 168;
    public static final int MAX_AA_SAMPLES_224 = 224;
    public static final int MAX_AA_SAMPLES_288 = 288;

    public static double AA_JITTER_SIZE = 0.25;


    static {
        offset = new PixelOffset();

        setJitter(2);
    }

    public static void setJitter(int seed) {
        Random r;
        if(seed == 0) {
            r = new Random();
        }
        else {
            r = new Random(seed);
        }

        aaJitterKernelX = new double[NUMBER_OF_AA_JITTER_KERNELS][MAX_AA_SAMPLES_288];
        aaJitterKernelY = new double[NUMBER_OF_AA_JITTER_KERNELS][MAX_AA_SAMPLES_288];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < MAX_AA_SAMPLES_288; i++) {
                aaJitterKernelX[k][i] = (r.nextDouble() - 0.5) * 2 * AA_JITTER_SIZE;
                aaJitterKernelY[k][i] = (r.nextDouble() - 0.5) * 2 * AA_JITTER_SIZE;
            }
        }
    }

    public Location() {
        indexX = Integer.MIN_VALUE;
        indexY = Integer.MIN_VALUE;
    }

    public Location(Location other) {
        this.aaJitter = other.aaJitter;
        indexX = Integer.MIN_VALUE;
        indexY = Integer.MIN_VALUE;
        precalculatedJitterDataDouble = other.precalculatedJitterDataDouble;
        precalculatedJitterDataApfloat = other.precalculatedJitterDataApfloat;
        precalculatedJitterDataDoubleDouble = other.precalculatedJitterDataDoubleDouble;
        precalculatedJitterDataMpfrBigNum = other.precalculatedJitterDataMpfrBigNum;
        precalculatedJitterDataMpirBigNum = other.precalculatedJitterDataMpirBigNum;
        precalculatedJitterDataBigNum = other.precalculatedJitterDataBigNum;
        precalculatedJitterDataBigIntNum = other.precalculatedJitterDataBigIntNum;
        precalculatedJitterDataPolarDouble = other.precalculatedJitterDataPolarDouble;
        precalculatedJitterDataPolarApfloat = other.precalculatedJitterDataPolarApfloat;
        precalculatedJitterDataPolarDoubleDouble = other.precalculatedJitterDataPolarDoubleDouble;
        precalculatedJitterDataPolarMpfrBigNum = other.precalculatedJitterDataPolarMpfrBigNum;
        precalculatedJitterDataMantexp = other.precalculatedJitterDataMantexp;
        extraSamplesNumber = other.extraSamplesNumber;
    }

    public boolean isPolar() {return false;}


    //public BigPoint getPoint(int x, int y) {return  null;}
    public GenericComplex getComplex(int x, int y) {return  null;}
    public void precalculateY(int y) {}
    public GenericComplex getComplexWithX(int x) {return  null;}
    public void precalculateX(int x) {}
    public GenericComplex getComplexWithY(int y) {return  null;}
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        this.aaJitter = jitter;
        extraSamplesNumber = numberOfExtraSamples;
    }

    public int getExtraSamplesNumber() {
        return extraSamplesNumber;
    }

    public GenericComplex getAntialiasingComplex(int sample, int loc) {return  null;}
    public void setReference(GenericComplex ref) {
        reference = ref;
        fractal.initializeReferenceDecompressor();
    }

    public static Location getInstanceForDrawing(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js, boolean polar, boolean highPresicion) {


        if(highPresicion && TaskDraw.HIGH_PRECISION_CALCULATION) {
            int lib = TaskDraw.getHighPrecisionLibrary(size, fractal);
            if(polar) {
                if(lib == Constants.ARBITRARY_MPFR || (lib == Constants.ARBITRARY_MPIR && !LibMpfr.hasError())) {
                    return new PolarLocationNormalMpfrBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else if(lib == Constants.ARBITRARY_DOUBLEDOUBLE) {
                    return new PolarLocationNormalDoubleDoubleArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new PolarLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
            }
            else {
                if(lib == Constants.ARBITRARY_MPFR) {
                    return new CartesianLocationNormalMpfrBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(lib == Constants.ARBITRARY_MPIR) {
                    return new CartesianLocationNormalMpirBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(lib == Constants.ARBITRARY_BUILT_IN) {
                    return new CartesianLocationNormalBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(lib == Constants.ARBITRARY_BIGINT) {
                    return new CartesianLocationNormalBigIntNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(lib == Constants.ARBITRARY_DOUBLEDOUBLE) {
                    return new CartesianLocationNormalDoubleDoubleArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
            }
        }

        int bignumLib = TaskDraw.getBignumLibrary(size, fractal);
        boolean isDeep = TaskDraw.useExtendedRange(size, fractal);

        if(polar) {
            if(highPresicion && isDeep) {
                if(bignumLib == Constants.BIGNUM_MPFR || (bignumLib == Constants.BIGNUM_MPIR && !LibMpfr.hasError())) {
                    return new PolarLocationDeltaDeepMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new PolarLocationDeltaDeepApfloat(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
            }
            else if(highPresicion) {
                if (bignumLib == Constants.BIGNUM_MPFR || (bignumLib == Constants.BIGNUM_MPIR && !LibMpfr.hasError())) {
                    return new PolarLocationDeltaMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                    return new PolarLocationDeltaDoubleDouble(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_DOUBLE) {
                    return new PolarLocationDeltaDouble(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new PolarLocationDeltaApfloat(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
            }
            else {
                return new PolarLocationNormalDouble(xCenter, yCenter, size, height_ratio, image_size, circle_period, fractal, js);
            }
        }
        else {
            if(highPresicion && isDeep) {

                if(TaskDraw.USE_FAST_DELTA_LOCATION && fractal.usesDefaultPlane() && !Rotation.usesRotation(rotation_center, rotation_vals)) {
                    return new CartesianLocationDeltaDeep(xCenter, yCenter, size, height_ratio, image_size, js, fractal, bignumLib);
                }

                if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                    return new CartesianLocationDeltaDeepBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(bignumLib == Constants.BIGNUM_BIGINT) {
                    return new CartesianLocationDeltaDeepBigIntNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(bignumLib == Constants.BIGNUM_MPIR) {
                    return new CartesianLocationDeltaDeepMpirBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(bignumLib == Constants.BIGNUM_MPFR) {
                    return new CartesianLocationDeltaDeepMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new CartesianLocationDeltaDeepApfloat(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
            }
            else if(highPresicion) {
                if(TaskDraw.USE_FAST_DELTA_LOCATION
                        && fractal.usesDefaultPlane() && !Rotation.usesRotation(rotation_center, rotation_vals)
                     && size.doubleValue() / offset.getImageSize(image_size) > 1e-305) {
                    return new CartesianLocationDelta(xCenter, yCenter, size, height_ratio, image_size, js, fractal, bignumLib);
                }

                if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                    return new CartesianLocationDeltaBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if(bignumLib == Constants.BIGNUM_BIGINT) {
                    return new CartesianLocationDeltaBigIntNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_MPFR) {
                    return new CartesianLocationDeltaMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_MPIR) {
                    return new CartesianLocationDeltaMpirBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                    return new CartesianLocationDeltaDoubleDouble(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else if (bignumLib == Constants.BIGNUM_DOUBLE) {
                    return new CartesianLocationDeltaDouble(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new CartesianLocationDeltaApfloat(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
            }
            else {
                return new CartesianLocationNormalDouble(xCenter, yCenter, size, height_ratio, image_size, fractal, js);
            }
        }

    }

    public static Location getCopy(Location loc) {
        if(loc instanceof CartesianLocationDeltaApfloat) {
            return new CartesianLocationDeltaApfloat((CartesianLocationDeltaApfloat)loc);
        }
        else if(loc instanceof PolarLocationDeltaApfloat) {
            return new PolarLocationDeltaApfloat((PolarLocationDeltaApfloat)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeepApfloat) {
            return new CartesianLocationDeltaDeepApfloat((CartesianLocationDeltaDeepApfloat)loc);
        }
        else if(loc instanceof PolarLocationDeltaDeepApfloat) {
            return new PolarLocationDeltaDeepApfloat((PolarLocationDeltaDeepApfloat)loc);
        }
        else if(loc instanceof CartesianLocationDeltaBigNum) {
            return new CartesianLocationDeltaBigNum((CartesianLocationDeltaBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeepBigNum) {
            return new CartesianLocationDeltaDeepBigNum((CartesianLocationDeltaDeepBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaBigIntNum) {
            return new CartesianLocationDeltaBigIntNum((CartesianLocationDeltaBigIntNum)loc);
        }
        else if(loc instanceof CartesianLocationDelta) {
            return new CartesianLocationDelta((CartesianLocationDelta)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeepBigIntNum) {
            return new CartesianLocationDeltaDeepBigIntNum((CartesianLocationDeltaDeepBigIntNum)loc);
        }
        else if(loc instanceof CartesianLocationNormalDouble) {
            return new CartesianLocationNormalDouble((CartesianLocationNormalDouble)loc);
        }
        else if(loc instanceof PolarLocationNormalDouble) {
            return new PolarLocationNormalDouble((PolarLocationNormalDouble)loc);
        }
        else if(loc instanceof CartesianLocationDeltaMpfrBigNum) {
            return new CartesianLocationDeltaMpfrBigNum((CartesianLocationDeltaMpfrBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeepMpfrBigNum) {
            return new CartesianLocationDeltaDeepMpfrBigNum((CartesianLocationDeltaDeepMpfrBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaMpirBigNum) {
            return new CartesianLocationDeltaMpirBigNum((CartesianLocationDeltaMpirBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeepMpirBigNum) {
            return new CartesianLocationDeltaDeepMpirBigNum((CartesianLocationDeltaDeepMpirBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDeep) {
            return new CartesianLocationDeltaDeep((CartesianLocationDeltaDeep)loc);
        }
        else if(loc instanceof PolarLocationDeltaMpfrBigNum) {
            return new PolarLocationDeltaMpfrBigNum((PolarLocationDeltaMpfrBigNum)loc);
        }
        else if(loc instanceof PolarLocationDeltaDeepMpfrBigNum) {
            return new PolarLocationDeltaDeepMpfrBigNum((PolarLocationDeltaDeepMpfrBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDouble) {
            return new CartesianLocationDeltaDouble((CartesianLocationDeltaDouble)loc);
        }
        else if(loc instanceof PolarLocationDeltaDouble) {
            return new PolarLocationDeltaDouble((PolarLocationDeltaDouble)loc);
        }
        else if(loc instanceof CartesianLocationDeltaDoubleDouble) {
            return new CartesianLocationDeltaDoubleDouble((CartesianLocationDeltaDoubleDouble)loc);
        }
        else if(loc instanceof PolarLocationDeltaDoubleDouble) {
            return new PolarLocationDeltaDoubleDouble((PolarLocationDeltaDoubleDouble)loc);
        }



        //Add arbitrary at the end
        else if(loc instanceof CartesianLocationNormalApfloatArbitrary) {
            return new CartesianLocationNormalApfloatArbitrary((CartesianLocationNormalApfloatArbitrary)loc);
        }
        else if(loc instanceof PolarLocationNormalApfloatArbitrary) {
            return new PolarLocationNormalApfloatArbitrary((PolarLocationNormalApfloatArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationNormalBigNumArbitrary) {
            return new CartesianLocationNormalBigNumArbitrary((CartesianLocationNormalBigNumArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationNormalBigIntNumArbitrary) {
            return new CartesianLocationNormalBigIntNumArbitrary((CartesianLocationNormalBigIntNumArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationNormalMpfrBigNumArbitrary) {
            return new CartesianLocationNormalMpfrBigNumArbitrary((CartesianLocationNormalMpfrBigNumArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationNormalMpirBigNumArbitrary) {
            return new CartesianLocationNormalMpirBigNumArbitrary((CartesianLocationNormalMpirBigNumArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationNormalDoubleDoubleArbitrary) {
            return new CartesianLocationNormalDoubleDoubleArbitrary((CartesianLocationNormalDoubleDoubleArbitrary)loc);
        }
        else if(loc instanceof PolarLocationNormalMpfrBigNumArbitrary) {
            return new PolarLocationNormalMpfrBigNumArbitrary((PolarLocationNormalMpfrBigNumArbitrary)loc);
        }
        else if(loc instanceof PolarLocationNormalDoubleDoubleArbitrary) {
            return new PolarLocationNormalDoubleDoubleArbitrary((PolarLocationNormalDoubleDoubleArbitrary)loc);
        }

        return null;
    }

    private HashMap<Long, Apfloat> twoToExpReciprocals = new HashMap<>();

    public MantExp getMantExp(Apfloat c) {
        double mantissa = 0;
        long exponent = 0;
        if(c.compareTo(Apfloat.ZERO) == 0) {
            mantissa = MantExp.ZERO.getMantissa();
            exponent = MantExp.ZERO.getExp();
        }
        else {
            Apfloat exp = MyApfloat.fp.multiply(new MyApfloat(c.scale() - 1), MyApfloat.RECIPROCAL_LOG_TWO_BASE_TEN);
            long long_exp = 0;

            double double_exp = exp.doubleValue();

            if(double_exp < 0) {
                long_exp = (long)(double_exp - 0.5);
            }
            else {
                long_exp = (long)(double_exp + 0.5);
            }

            Apfloat twoToExpReciprocal = twoToExpReciprocals.get(long_exp);

            if(twoToExpReciprocal == null) {

                if(double_exp < 0) {
                    twoToExpReciprocal = MyApfloat.fp.pow(MyApfloat.TWO, -long_exp);
                }
                else {
                    twoToExpReciprocal = MyApfloat.reciprocal(MyApfloat.fp.pow(MyApfloat.TWO, long_exp));
                }

                twoToExpReciprocals.put(long_exp, twoToExpReciprocal);
            }

            mantissa = MyApfloat.fp.multiply(c, twoToExpReciprocal).doubleValue();
            exponent = long_exp;
        }

        return new MantExp(exponent, mantissa);
    }


    private HashMap<Integer, BigDecNum> twoToExpReciprocalsBD = new HashMap<>();

    public MantExp getMantExp(BigDecNum c) {
        double mantissa = 0;
        long exponent = 0;
        if(c.isZero()) {
            mantissa = MantExp.ZERO.getMantissa();
            exponent = MantExp.ZERO.getExp();
        }
        else {
            BigDecNum exp = BigDecNum.RECIPROCAL_LOG_TWO_BASE_TEN.mult(c.scale());
            int long_exp = 0;

            double double_exp = exp.doubleValue();

            if(double_exp < 0) {
                long_exp = (int)(double_exp - 0.5);
            }
            else {
                long_exp = (int)(double_exp + 0.5);
            }

            BigDecNum twoToExpReciprocal = twoToExpReciprocalsBD.get(long_exp);

            if(twoToExpReciprocal == null) {

                if(double_exp < 0) {
                    twoToExpReciprocal = BigDecNum.TWO.pow(-long_exp);
                }
                else {
                    twoToExpReciprocal = BigDecNum.TWO.pow(long_exp).reciprocal();
                }

                twoToExpReciprocalsBD.put(long_exp, twoToExpReciprocal);
            }

            mantissa = c.mult(twoToExpReciprocal).doubleValue();
            exponent = long_exp;
        }

        return new MantExp(exponent, mantissa);
    }

    public MantExpComplex getMantExpComplex(GenericComplex c) {

        if(c instanceof MpirBigNumComplex) {
            MpirBigNumComplex cn = (MpirBigNumComplex)c;
            return MantExpComplex.create(cn);
        }
        else if(c instanceof MpfrBigNumComplex) {
            MpfrBigNumComplex cn = (MpfrBigNumComplex)c;
            return MantExpComplex.create(cn);
        }
        else if(c instanceof BigNumComplex) {
            BigNumComplex cn = (BigNumComplex)c;
            return MantExpComplex.create(cn);
        }
        else if(c instanceof BigIntNumComplex) {
            BigIntNumComplex cn = (BigIntNumComplex)c;
            return MantExpComplex.create(cn);
        }
        else if (c instanceof BigComplex){
            BigComplex cn = (BigComplex)c;
            return MantExpComplex.create(getMantExp(cn.getRe()), getMantExp(cn.getIm()));
        }
        else if (c instanceof DDComplex){
            DDComplex cn = (DDComplex)c;
            return MantExpComplex.create(cn);
        }
//        else if (c instanceof BigDecNumComplex){
//            BigDecNumComplex cn = (BigDecNumComplex)c;
//            return MantExpComplex.create(getMantExp(cn.getRe()), getMantExp(cn.getIm()));
//        }
        else {
            return MantExpComplex.create((Complex) c);
        }
    }

    public GenericComplex getReferencePoint() {
        return null;
    }

    public MantExp getMaxSizeInImage() {
        return null;
    }

    public MantExp getSize() {
        return null;
    }


    private void precalculateJitterToAntialiasingStepsBigNum(BigNum[][] steps, BigNum ddx_antialiasing_size, BigNum ddy_antialiasing_size) {

        BigNum[] temp_x = steps[0];
        BigNum[] temp_y = steps[1];

        precalculatedJitterDataBigNum = new BigNum[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataBigNum[k][0][i] = temp_x[i].add(ddx_antialiasing_size.mult(BigNum.create(aaJitterKernelX[k][i])));
                precalculatedJitterDataBigNum[k][1][i] = temp_y[i].add(ddy_antialiasing_size.mult(BigNum.create(aaJitterKernelY[k][i])));
            }
        }

    }

    private void precalculateJitterToAntialiasingStepsBigIntNum(BigIntNum[][] steps, BigIntNum ddx_antialiasing_size, BigIntNum ddy_antialiasing_size) {

        BigIntNum[] temp_x = steps[0];
        BigIntNum[] temp_y = steps[1];

        precalculatedJitterDataBigIntNum = new BigIntNum[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataBigIntNum[k][0][i] = temp_x[i].add(ddx_antialiasing_size.mult(aaJitterKernelX[k][i]));
                precalculatedJitterDataBigIntNum[k][1][i] = temp_y[i].add(ddy_antialiasing_size.mult(aaJitterKernelY[k][i]));
            }
        }

    }

    public BigNum[][] createAntialiasingStepsBigNum(BigNum bntemp_size_image_size_x, BigNum bntemp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        BigNum ddx_antialiasing_size;

        BigNum ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size = bntemp_size_image_size_x.shift2toi(-4);
            ddy_antialiasing_size = bntemp_size_image_size_y.shift2toi(-4);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            BigNum oneFourteenth = BigNum.create(MyApfloat.reciprocal(new MyApfloat(14.0)));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneFourteenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            BigNum oneTwelveth = BigNum.create(MyApfloat.reciprocal(new MyApfloat(12.0)));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneTwelveth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            BigNum oneTenth = BigNum.create(MyApfloat.reciprocal(new MyApfloat(10.0)));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneTenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size = bntemp_size_image_size_x.shift2toi(-3);
            ddy_antialiasing_size = bntemp_size_image_size_y.shift2toi(-3);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            BigNum oneSixth = BigNum.create(MyApfloat.reciprocal(new MyApfloat(6.0)));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneSixth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneSixth);
        }
        else {
            ddx_antialiasing_size = bntemp_size_image_size_x.divide4();
            ddy_antialiasing_size = bntemp_size_image_size_y.divide4();
        }

        BigNum[][] steps = createAntialiasingStepsBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public BigIntNum[][] createAntialiasingStepsBigIntNum(BigIntNum bntemp_size_image_size_x, BigIntNum bntemp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        BigIntNum ddx_antialiasing_size;

        BigIntNum ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size = bntemp_size_image_size_x.shift2toi(-4);
            ddy_antialiasing_size = bntemp_size_image_size_y.shift2toi(-4);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            BigIntNum oneFourteenth = new BigIntNum(1.0).divide(new BigIntNum(14.0));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneFourteenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            BigIntNum oneTwelveth = new BigIntNum(1.0).divide(new BigIntNum(12.0));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneTwelveth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            BigIntNum oneTenth = new BigIntNum(1.0).divide(new BigIntNum(10.0));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneTenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size = bntemp_size_image_size_x.shift2toi(-3);
            ddy_antialiasing_size = bntemp_size_image_size_y.shift2toi(-3);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            BigIntNum oneSixth = new BigIntNum(1.0).divide(new BigIntNum(6.0));

            ddx_antialiasing_size = bntemp_size_image_size_x.mult(oneSixth);
            ddy_antialiasing_size = bntemp_size_image_size_y.mult(oneSixth);
        }
        else {
            ddx_antialiasing_size = bntemp_size_image_size_x.divide4();
            ddy_antialiasing_size = bntemp_size_image_size_y.divide4();
        }

        BigIntNum[][] steps = createAntialiasingStepsBigIntNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsBigIntNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public BigNum[][] createAntialiasingStepsBigNumGrid(BigNum ddx_antialiasing_size, BigNum ddy_antialiasing_size, boolean adaptive, int max_samples) {

        BigNum ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        BigNum ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;

        ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();
        ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = ddx_antialiasing_size.mult(3);
            ddy_antialiasing_size_x3 = ddy_antialiasing_size.mult(3);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = ddx_antialiasing_size.mult4();
            ddy_antialiasing_size_x4 = ddy_antialiasing_size.mult4();
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = ddx_antialiasing_size.mult(5);
            ddy_antialiasing_size_x5 = ddy_antialiasing_size.mult(5);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = ddx_antialiasing_size.mult(6);
            ddy_antialiasing_size_x6 = ddy_antialiasing_size.mult(6);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = ddx_antialiasing_size.mult(7);
            ddy_antialiasing_size_x7 = ddy_antialiasing_size.mult(7);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = ddx_antialiasing_size.shift2toi(3);
            ddy_antialiasing_size_x8 = ddy_antialiasing_size.shift2toi(3);
        }

        BigNum zero = BigNum.create();

        BigNum[][] data;
        if(!adaptive) {

            BigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            BigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data = new BigNum[][] {temp_x, temp_y};
        }
        else {

            BigNum[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            BigNum[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new BigNum[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            BigNum[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            BigNum[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            BigNum[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            BigNum[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            BigNum[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            BigNum[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            BigNum[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    public BigIntNum[][] createAntialiasingStepsBigIntNumGrid(BigIntNum ddx_antialiasing_size, BigIntNum ddy_antialiasing_size, boolean adaptive, int max_samples) {

        BigIntNum ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        BigIntNum ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;


        ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();
        ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = ddx_antialiasing_size.mult(3);
            ddy_antialiasing_size_x3 = ddy_antialiasing_size.mult(3);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = ddx_antialiasing_size.mult4();
            ddy_antialiasing_size_x4 = ddy_antialiasing_size.mult4();
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = ddx_antialiasing_size.mult(5);
            ddy_antialiasing_size_x5 = ddy_antialiasing_size.mult(5);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = ddx_antialiasing_size.mult(6);
            ddy_antialiasing_size_x6 = ddy_antialiasing_size.mult(6);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = ddx_antialiasing_size.mult(7);
            ddy_antialiasing_size_x7 = ddy_antialiasing_size.mult(7);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = ddx_antialiasing_size.shift2toi(3);
            ddy_antialiasing_size_x8 = ddy_antialiasing_size.shift2toi(3);
        }

        BigIntNum zero = new BigIntNum();

        BigIntNum[][] data;
        if(!adaptive) {

            BigIntNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            BigIntNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data = new BigIntNum[][] {temp_x, temp_y};
        }
        else {

            BigIntNum[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            BigIntNum[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new BigIntNum[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            BigIntNum[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            BigIntNum[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            BigIntNum[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            BigIntNum[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            BigIntNum[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            BigIntNum[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            BigIntNum[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    private void precalculateJitterToAntialiasingStepsDoubleDouble(DoubleDouble[][] steps, DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size) {

        DoubleDouble[] temp_x = steps[0];
        DoubleDouble[] temp_y = steps[1];

        precalculatedJitterDataDoubleDouble = new DoubleDouble[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataDoubleDouble[k][0][i] = temp_x[i].add(ddx_antialiasing_size.multiply(new DoubleDouble(aaJitterKernelX[k][i])));
                precalculatedJitterDataDoubleDouble[k][1][i] = temp_y[i].add(ddy_antialiasing_size.multiply(new DoubleDouble(aaJitterKernelY[k][i])));
            }
        }

    }

    public DoubleDouble[][] createAntialiasingStepsDoubleDouble(DoubleDouble bntemp_size_image_size_x, DoubleDouble bntemp_size_image_size_y, boolean adaptive, boolean jitter, int samples)  {

        DoubleDouble ddx_antialiasing_size;

        DoubleDouble ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            DoubleDouble oneSixteenth = new DoubleDouble(0.0625);

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(oneSixteenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(oneSixteenth);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            DoubleDouble oneFourteenth = new DoubleDouble(1).divide(new DoubleDouble(14));

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(oneFourteenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            DoubleDouble oneTwelveth = new DoubleDouble(1).divide(new DoubleDouble(12));

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(oneTwelveth);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            DoubleDouble oneTenth = new DoubleDouble(1).divide(new DoubleDouble(10));

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(oneTenth);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            DoubleDouble point125 = new DoubleDouble(0.125);

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(point125);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(point125);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            DoubleDouble oneSixth = new DoubleDouble(1).divide(new DoubleDouble(6));

            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(oneSixth);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(oneSixth);
        }
        else {
            DoubleDouble point25 = new DoubleDouble(0.25);
            ddx_antialiasing_size = bntemp_size_image_size_x.multiply(point25);
            ddy_antialiasing_size = bntemp_size_image_size_y.multiply(point25);
        }

        DoubleDouble[][] steps = createAntialiasingStepsDoubleDoubleGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsDoubleDouble(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public DoubleDouble[][] createAntialiasingStepsDoubleDoubleGrid(DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size, boolean adaptive, int max_samples) {

        DoubleDouble ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        DoubleDouble ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;

        ddx_antialiasing_size_x2 = ddx_antialiasing_size.multiply(2);
        ddy_antialiasing_size_x2 = ddy_antialiasing_size.multiply(2);

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = ddx_antialiasing_size.multiply(3);
            ddy_antialiasing_size_x3 = ddy_antialiasing_size.multiply(3);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = ddx_antialiasing_size.multiply(4);
            ddy_antialiasing_size_x4 = ddy_antialiasing_size.multiply(4);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = ddx_antialiasing_size.multiply(5);
            ddy_antialiasing_size_x5 = ddy_antialiasing_size.multiply(5);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = ddx_antialiasing_size.multiply(6);
            ddy_antialiasing_size_x6 = ddy_antialiasing_size.multiply(6);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = ddx_antialiasing_size.multiply(7);
            ddy_antialiasing_size_x7 = ddy_antialiasing_size.multiply(7);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = ddx_antialiasing_size.multiply(8);
            ddy_antialiasing_size_x8 = ddy_antialiasing_size.multiply(8);
        }


        DoubleDouble zero = new DoubleDouble();

        DoubleDouble[][] data;
        if(!adaptive) {

            DoubleDouble[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            DoubleDouble[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data = new DoubleDouble[][] {temp_x, temp_y};
        }
        else {

            DoubleDouble[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            DoubleDouble[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new DoubleDouble[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            DoubleDouble[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            DoubleDouble[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            DoubleDouble[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            DoubleDouble[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            DoubleDouble[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            DoubleDouble[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    public static Object[] getGaussianCoefficients(int samples, double sigmaR) {

        int[] X = new int[] {0, -1, 1, 1, -1};
        int[] Y = new int[] {0, -1, -1, 1, 1};

        List<Integer> Xlist = Arrays.stream(X).boxed().collect(Collectors.toList());
        List<Integer> Ylist = Arrays.stream(Y).boxed().collect(Collectors.toList());
        int size = 3;

        if (samples > 5) {
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(0);
            Xlist.add(0);

            Ylist.add(0);
            Ylist.add(0);
            Ylist.add(-1);
            Ylist.add(1);
            size = 3;
        }

        if(samples > 9) {
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(2);

            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            size = 5;
        }

        if(samples > 17) {
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);

            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-1);
            Ylist.add(1);
            size = 5;
        }

        if(samples > MAX_AA_SAMPLES_24 + 1) {
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(3);

            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);

            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(3);
            Xlist.add(3);



            Ylist.add(-3);
            Ylist.add(0);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(0);
            Ylist.add(3);

            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-2);
            Ylist.add(2);

            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-1);
            Ylist.add(1);
            size = 7;
        }


        if(samples > MAX_AA_SAMPLES_48 + 1) {
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);

            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);


            Ylist.add(-4);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(4);

            Ylist.add(-3);
            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(3);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-3);
            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(3);
            size = 9;
        }

        if(samples > MAX_AA_SAMPLES_80 + 1) {
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(5);


            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(5);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);

            size = 11;
        }

        if(samples > MAX_AA_SAMPLES_120 + 1) {
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(6);


            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(6);
            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);

            size = 13;
        }

        if(samples > MAX_AA_SAMPLES_168 + 1) {
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(7);


            Ylist.add(-7);
            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(7);
            Ylist.add(-7);
            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);
            Ylist.add(7);

            size = 15;
        }

        if(samples > MAX_AA_SAMPLES_224 + 1) {
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-8);
            Xlist.add(-7);
            Xlist.add(-7);
            Xlist.add(-6);
            Xlist.add(-6);
            Xlist.add(-5);
            Xlist.add(-5);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(5);
            Xlist.add(5);
            Xlist.add(6);
            Xlist.add(6);
            Xlist.add(7);
            Xlist.add(7);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);
            Xlist.add(8);


            Ylist.add(-8);
            Ylist.add(-7);
            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);
            Ylist.add(7);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(8);
            Ylist.add(-8);
            Ylist.add(-7);
            Ylist.add(-6);
            Ylist.add(-5);
            Ylist.add(-4);
            Ylist.add(-3);
            Ylist.add(-2);
            Ylist.add(-1);
            Ylist.add(0);
            Ylist.add(1);
            Ylist.add(2);
            Ylist.add(3);
            Ylist.add(4);
            Ylist.add(5);
            Ylist.add(6);
            Ylist.add(7);
            Ylist.add(8);

            size = 17;
        }

        X = Xlist.stream().mapToInt(i -> i).toArray();
        Y = Ylist.stream().mapToInt(i -> i).toArray();

        return new Object[] {GaussianAntialiasingAlgorithm.createGaussianKernel(size, X, Y, sigmaR), size};
    }

    private void precalculateJitterToAntialiasingStepsDouble(double[][] steps, double x_antialiasing_size, double y_antialiasing_size) {
        double[] temp_x = steps[0];
        double[] temp_y = steps[1];

        precalculatedJitterDataDouble = new double[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataDouble[k][0][i] = temp_x[i] + aaJitterKernelX[k][i] * x_antialiasing_size;
                precalculatedJitterDataDouble[k][1][i] = temp_y[i] + aaJitterKernelY[k][i] * y_antialiasing_size;
            }
        }

    }

    private void precalculateJitterToAntialiasingStepsMantexp(MantExp[][] steps, MantExp x_antialiasing_size, MantExp y_antialiasing_size) {
        MantExp[] temp_x = steps[0];
        MantExp[] temp_y = steps[1];

        precalculatedJitterDataMantexp = new MantExp[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataMantexp[k][0][i] = temp_x[i].add(x_antialiasing_size.multiply(aaJitterKernelX[k][i]));
                precalculatedJitterDataMantexp[k][0][i].Normalize();
                precalculatedJitterDataMantexp[k][1][i] = temp_y[i].add(y_antialiasing_size.multiply(aaJitterKernelY[k][i]));
                precalculatedJitterDataMantexp[k][1][i].Normalize();
            }
        }

    }

    public double[][] createAntialiasingStepsDouble(double temp_size_image_size_x, double temp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        double x_antialiasing_size;
        double y_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            x_antialiasing_size = temp_size_image_size_x * 0.0625;
            y_antialiasing_size = temp_size_image_size_y * 0.0625;
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            double oneFourteenth = 1.0 / 14;
            x_antialiasing_size = temp_size_image_size_x * oneFourteenth;
            y_antialiasing_size = temp_size_image_size_y * oneFourteenth;
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            double oneTwelveth = 1.0 / 12;
            x_antialiasing_size = temp_size_image_size_x * oneTwelveth;
            y_antialiasing_size = temp_size_image_size_y * oneTwelveth;
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            double oneTenth = 1.0 / 10;
            x_antialiasing_size = temp_size_image_size_x * oneTenth;
            y_antialiasing_size = temp_size_image_size_y * oneTenth;
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            x_antialiasing_size = temp_size_image_size_x * 0.125;
            y_antialiasing_size = temp_size_image_size_y * 0.125;
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            double oneSixth = 1.0 / 6;
            x_antialiasing_size = temp_size_image_size_x * oneSixth;
            y_antialiasing_size = temp_size_image_size_y * oneSixth;
        }
        else {
            x_antialiasing_size = temp_size_image_size_x * 0.25;
            y_antialiasing_size = temp_size_image_size_y * 0.25;
        }

        double[][] steps = createAntialiasingStepsDoubleGrid(x_antialiasing_size,  y_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsDouble(steps, x_antialiasing_size, y_antialiasing_size);
        }

        return steps;
    }

    public MantExp[][] createAntialiasingStepsMantexp(MantExp temp_size_image_size_x, MantExp temp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        MantExp x_antialiasing_size;

        MantExp y_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            x_antialiasing_size = temp_size_image_size_x.divide4().divide4_mutable();
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.divide4().divide4_mutable();
            y_antialiasing_size.Normalize();
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            MantExp oneFourteenth = MantExp.ONE.divide(MantExp.FOURTEEN);
            oneFourteenth.Normalize();

            x_antialiasing_size = temp_size_image_size_x.multiply(oneFourteenth);
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.multiply(oneFourteenth);
            y_antialiasing_size.Normalize();
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            MantExp oneTwelveth = MantExp.ONE.divide(MantExp.TWELVE);
            oneTwelveth.Normalize();

            x_antialiasing_size = temp_size_image_size_x.multiply(oneTwelveth);
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.multiply(oneTwelveth);
            y_antialiasing_size.Normalize();
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            MantExp oneTenth = MantExp.ONE.divide(MantExp.TEN);
            oneTenth.Normalize();

            x_antialiasing_size = temp_size_image_size_x.multiply(oneTenth);
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.multiply(oneTenth);
            y_antialiasing_size.Normalize();
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            x_antialiasing_size = temp_size_image_size_x.divide4().divide2_mutable();
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.divide4().divide2_mutable();
            y_antialiasing_size.Normalize();
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            MantExp oneSixth = MantExp.ONE.divide(MantExp.SIX);
            oneSixth.Normalize();

            x_antialiasing_size = temp_size_image_size_x.multiply(oneSixth);
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.multiply(oneSixth);
            y_antialiasing_size.Normalize();
        }
        else {
            x_antialiasing_size = temp_size_image_size_x.divide4();
            x_antialiasing_size.Normalize();
            y_antialiasing_size = temp_size_image_size_y.divide4();
            y_antialiasing_size.Normalize();
        }

        MantExp[][] steps = createAntialiasingStepsMantexpGrid(x_antialiasing_size,  y_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsMantexp(steps, x_antialiasing_size, y_antialiasing_size);
        }

        return steps;
    }

    public double[][] createAntialiasingStepsDoubleGrid(double x_antialiasing_size, double y_antialiasing_size, boolean adaptive, int max_samples)  {

        double x_antialiasing_size_x2, x_antialiasing_size_x3 = 0, x_antialiasing_size_x4 = 0, x_antialiasing_size_x5 = 0, x_antialiasing_size_x6 = 0, x_antialiasing_size_x7 = 0, x_antialiasing_size_x8 = 0;
        double y_antialiasing_size_x2, y_antialiasing_size_x3 = 0, y_antialiasing_size_x4 = 0, y_antialiasing_size_x5 = 0, y_antialiasing_size_x6 = 0, y_antialiasing_size_x7 = 0, y_antialiasing_size_x8 = 0;

        x_antialiasing_size_x2 = 2 * x_antialiasing_size;
        y_antialiasing_size_x2 = 2 * y_antialiasing_size;

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            x_antialiasing_size_x3 = 3 * x_antialiasing_size;
            y_antialiasing_size_x3 = 3 * y_antialiasing_size;
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            x_antialiasing_size_x4 = 4 * x_antialiasing_size;
            y_antialiasing_size_x4 = 4 * y_antialiasing_size;
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            x_antialiasing_size_x5 = 5 * x_antialiasing_size;
            y_antialiasing_size_x5 = 5 * y_antialiasing_size;
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            x_antialiasing_size_x6 = 6 * x_antialiasing_size;
            y_antialiasing_size_x6 = 6 * y_antialiasing_size;
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            x_antialiasing_size_x7 = 7 * x_antialiasing_size;
            y_antialiasing_size_x7 = 7 * y_antialiasing_size;
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            x_antialiasing_size_x8 = 8 * x_antialiasing_size;
            y_antialiasing_size_x8 = 8 * y_antialiasing_size;
        }

        double[][] data;
        if (!adaptive) {
            double[] temp_x = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
            double[] temp_y = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
                    0, 0, -y_antialiasing_size, y_antialiasing_size,
                    -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
                    -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

            data  = new double[][]{temp_x, temp_y};
        } else {
            double[] temp_x = {-x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2, -x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, x_antialiasing_size_x2, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2, -x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, 0, 0

            };


            double[] temp_y = {-y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, y_antialiasing_size_x2,
                    0, 0, -y_antialiasing_size_x2, y_antialiasing_size_x2,
                    -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, y_antialiasing_size_x2,
                    -y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size, 0, 0, -y_antialiasing_size, y_antialiasing_size


            };

            data  = new double[][]{temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
                double[] temp_x = {-x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x3, 0, 0, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x3,
                -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x2, -x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3,
                -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x3, x_antialiasing_size_x3
                };
                double[] temp_y = {-y_antialiasing_size_x3, 0, y_antialiasing_size_x3, -y_antialiasing_size_x3, y_antialiasing_size_x3, -y_antialiasing_size_x3, 0, y_antialiasing_size_x3,
                -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x3, y_antialiasing_size_x3, -y_antialiasing_size_x3, y_antialiasing_size_x3, -y_antialiasing_size_x2, y_antialiasing_size_x2,
                -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x3, y_antialiasing_size_x3, -y_antialiasing_size_x3, y_antialiasing_size_x3, -y_antialiasing_size, y_antialiasing_size
                };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {-x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4,
            -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4
            };

            double[] temp_y = {-y_antialiasing_size_x4, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, y_antialiasing_size_x4,
            -y_antialiasing_size_x3, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size_x3, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x4, y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {-x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, 0, 0, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5 };
            double[] temp_y = {-y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, y_antialiasing_size_x5, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5 };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {-x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, 0, 0, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, };
            double[] temp_y = {-y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, y_antialiasing_size_x6, -y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {-x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, 0, 0, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, };
            double[] temp_y = {-y_antialiasing_size_x7, -y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, y_antialiasing_size_x7, -y_antialiasing_size_x7, -y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {-x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x8, -x_antialiasing_size_x7, -x_antialiasing_size_x7, -x_antialiasing_size_x6, -x_antialiasing_size_x6, -x_antialiasing_size_x5, -x_antialiasing_size_x5, -x_antialiasing_size_x4, -x_antialiasing_size_x4, -x_antialiasing_size_x3, -x_antialiasing_size_x3, -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, 0, 0, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, };
            double[] temp_y = {-y_antialiasing_size_x8, -y_antialiasing_size_x7, -y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, y_antialiasing_size_x8, -y_antialiasing_size_x8, -y_antialiasing_size_x7, -y_antialiasing_size_x6, -y_antialiasing_size_x5, -y_antialiasing_size_x4, -y_antialiasing_size_x3, -y_antialiasing_size_x2, -y_antialiasing_size, 0, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    double[] merge(double[] a, double[] b) {
        double[] merge = new double[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    Apfloat[] merge(Apfloat[] a, Apfloat[] b) {
        Apfloat[] merge = new Apfloat[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    MantExp[] merge(MantExp[] a, MantExp[] b) {
        MantExp[] merge = new MantExp[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    DoubleDouble[] merge(DoubleDouble[] a, DoubleDouble[] b) {
        DoubleDouble[] merge = new DoubleDouble[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    BigNum[] merge(BigNum[] a, BigNum[] b) {
        BigNum[] merge = new BigNum[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    BigIntNum[] merge(BigIntNum[] a, BigIntNum[] b) {
        BigIntNum[] merge = new BigIntNum[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    MpfrBigNum[] merge(MpfrBigNum[] a, MpfrBigNum[] b) {
        MpfrBigNum[] merge = new MpfrBigNum[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }

    MpirBigNum[] merge(MpirBigNum[] a, MpirBigNum[] b) {
        MpirBigNum[] merge = new MpirBigNum[a.length + b.length];
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(b, 0, merge, a.length, b.length);
        return merge;
    }


    public MantExp[][] createAntialiasingStepsMantexpGrid(MantExp x_antialiasing_size, MantExp y_antialiasing_size, boolean adaptive, int max_samples) {

        MantExp x_antialiasing_size_x2, x_antialiasing_size_x3 = null, x_antialiasing_size_x4 = null, x_antialiasing_size_x5 = null, x_antialiasing_size_x6 = null, x_antialiasing_size_x7 = null, x_antialiasing_size_x8 = null;

        MantExp y_antialiasing_size_x2, y_antialiasing_size_x3 = null, y_antialiasing_size_x4 = null, y_antialiasing_size_x5 = null, y_antialiasing_size_x6 = null, y_antialiasing_size_x7 = null, y_antialiasing_size_x8 = null;

        x_antialiasing_size_x2 = x_antialiasing_size.multiply2();
        x_antialiasing_size_x2.Normalize();
        y_antialiasing_size_x2 = y_antialiasing_size.multiply2();
        y_antialiasing_size_x2.Normalize();

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            x_antialiasing_size_x3 = x_antialiasing_size.multiply(3);
            x_antialiasing_size_x3.Normalize();
            y_antialiasing_size_x3 = y_antialiasing_size.multiply(3);
            y_antialiasing_size_x3.Normalize();
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            x_antialiasing_size_x4 = x_antialiasing_size.multiply4();
            x_antialiasing_size_x4.Normalize();
            y_antialiasing_size_x4 = y_antialiasing_size.multiply4();
            y_antialiasing_size_x4.Normalize();
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            x_antialiasing_size_x5 = x_antialiasing_size.multiply(5);
            x_antialiasing_size_x5.Normalize();
            y_antialiasing_size_x5 = y_antialiasing_size.multiply(5);
            y_antialiasing_size_x5.Normalize();
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            x_antialiasing_size_x6 = x_antialiasing_size.multiply(6);
            x_antialiasing_size_x6.Normalize();
            y_antialiasing_size_x6 = y_antialiasing_size.multiply(6);
            y_antialiasing_size_x6.Normalize();
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            x_antialiasing_size_x7 = x_antialiasing_size.multiply(7);
            x_antialiasing_size_x7.Normalize();
            y_antialiasing_size_x7 = y_antialiasing_size.multiply(7);
            y_antialiasing_size_x7.Normalize();
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            x_antialiasing_size_x8 = x_antialiasing_size.multiply4().multiply2_mutable();
            x_antialiasing_size_x8.Normalize();
            y_antialiasing_size_x8 = y_antialiasing_size.multiply4().multiply2_mutable();
            y_antialiasing_size_x8.Normalize();
        }

        MantExp[][] data;
        if(!adaptive) {
            MantExp[] temp_x = {x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size, x_antialiasing_size.negate(),
                    x_antialiasing_size.negate(), x_antialiasing_size, MantExp.ZERO, MantExp.ZERO,
                    x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
                    x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
            MantExp[] temp_y = {y_antialiasing_size.negate(), y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size,
                    MantExp.ZERO, MantExp.ZERO, y_antialiasing_size.negate(), y_antialiasing_size,
                    y_antialiasing_size_x2.negate(), MantExp.ZERO, y_antialiasing_size_x2, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size_x2.negate(), MantExp.ZERO, y_antialiasing_size_x2,
                    y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size.negate(), y_antialiasing_size};

            data = new MantExp[][] {temp_x, temp_y};
        }
        else {
            MantExp[] temp_x = {x_antialiasing_size_x2.negate(), x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2.negate(),
                    x_antialiasing_size_x2.negate(), x_antialiasing_size_x2, MantExp.ZERO, MantExp.ZERO,
                    x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size.negate(), x_antialiasing_size,
                    x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size, x_antialiasing_size.negate(), x_antialiasing_size.negate(), x_antialiasing_size, MantExp.ZERO, MantExp.ZERO

            };


            MantExp[] temp_y = {y_antialiasing_size_x2.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size_x2,
                    MantExp.ZERO, MantExp.ZERO, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2,
                    y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size_x2,
                    y_antialiasing_size.negate(), y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size, MantExp.ZERO, MantExp.ZERO, y_antialiasing_size.negate(), y_antialiasing_size


            };

            data = new MantExp[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x3,
                    x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3,
                    x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x3, x_antialiasing_size_x3
            };
            MantExp[] temp_y = {y_antialiasing_size_x3.negate(), MantExp.ZERO, y_antialiasing_size_x3, y_antialiasing_size_x3.negate(), y_antialiasing_size_x3, y_antialiasing_size_x3.negate(), MantExp.ZERO, y_antialiasing_size_x3,
                    y_antialiasing_size_x2.negate(), y_antialiasing_size_x2, y_antialiasing_size_x3.negate(), y_antialiasing_size_x3, y_antialiasing_size_x3.negate(), y_antialiasing_size_x3, y_antialiasing_size_x2.negate(), y_antialiasing_size_x2,
                    y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size_x3.negate(), y_antialiasing_size_x3, y_antialiasing_size_x3.negate(), y_antialiasing_size_x3, y_antialiasing_size.negate(), y_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4,
                    x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x4
            };

            MantExp[] temp_y = {y_antialiasing_size_x4.negate(), y_antialiasing_size_x2.negate(), MantExp.ZERO, y_antialiasing_size_x2, y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x2.negate(), MantExp.ZERO, y_antialiasing_size_x2, y_antialiasing_size_x4,
                    y_antialiasing_size_x3.negate(), y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size_x3, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x4.negate(), y_antialiasing_size_x4, y_antialiasing_size_x3.negate(), y_antialiasing_size.negate(), y_antialiasing_size, y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x5, };
            MantExp[] temp_y = {y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x5, y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x6, };
            MantExp[] temp_y = {y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x6, y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x7, };
            MantExp[] temp_y = {y_antialiasing_size_x7.negate(), y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x7, y_antialiasing_size_x7.negate(), y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            MantExp[] temp_x = {x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x8.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x7.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x6.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x5.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x4.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x3.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size_x2.negate(), x_antialiasing_size.negate(), x_antialiasing_size.negate(), MantExp.ZERO, MantExp.ZERO, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x3, x_antialiasing_size_x3, x_antialiasing_size_x4, x_antialiasing_size_x4, x_antialiasing_size_x5, x_antialiasing_size_x5, x_antialiasing_size_x6, x_antialiasing_size_x6, x_antialiasing_size_x7, x_antialiasing_size_x7, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, x_antialiasing_size_x8, };
            MantExp[] temp_y = {y_antialiasing_size_x8.negate(), y_antialiasing_size_x7.negate(), y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x8, y_antialiasing_size_x8.negate(), y_antialiasing_size_x7.negate(), y_antialiasing_size_x6.negate(), y_antialiasing_size_x5.negate(), y_antialiasing_size_x4.negate(), y_antialiasing_size_x3.negate(), y_antialiasing_size_x2.negate(), y_antialiasing_size.negate(), MantExp.ZERO, y_antialiasing_size, y_antialiasing_size_x2, y_antialiasing_size_x3, y_antialiasing_size_x4, y_antialiasing_size_x5, y_antialiasing_size_x6, y_antialiasing_size_x7, y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    public Apfloat[][] createAntialiasingStepsApfloat(Apfloat ddtemp_size_image_size_x, Apfloat ddtemp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {


        Apfloat ddx_antialiasing_size;

        Apfloat ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            Apfloat oneSixteenth = new MyApfloat(0.0625);
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, oneSixteenth);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, oneSixteenth);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            Apfloat oneFourteenth = MyApfloat.reciprocal(new MyApfloat(14.0));
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, oneFourteenth);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            Apfloat oneTwelveth = MyApfloat.reciprocal(new MyApfloat(12.0));
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, oneTwelveth);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            Apfloat oneTenth = MyApfloat.reciprocal(new MyApfloat(10.0));
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, oneTenth);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            Apfloat point125 = new MyApfloat(0.125);

            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, point125);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, point125);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            Apfloat oneSixth = MyApfloat.reciprocal(new MyApfloat(6.0));
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, oneSixth);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, oneSixth);
        }
        else {
            Apfloat point25 = new MyApfloat(0.25);

            ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, point25);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, point25);
        }


        Apfloat[][] steps = createAntialiasingStepsApfloatGrid(ddx_antialiasing_size,  ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsApfloat(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    private void precalculateJitterToAntialiasingStepsApfloat(Apfloat[][] steps, Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size) {

        Apfloat[] temp_x = steps[0];
        Apfloat[] temp_y = steps[1];

        precalculatedJitterDataApfloat = new Apfloat[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataApfloat[k][0][i] = MyApfloat.fp.add(temp_x[i], MyApfloat.fp.multiply(new MyApfloat(aaJitterKernelX[k][i]), ddx_antialiasing_size));
                precalculatedJitterDataApfloat[k][1][i] = MyApfloat.fp.add(temp_y[i], MyApfloat.fp.multiply(new MyApfloat(aaJitterKernelY[k][i]), ddy_antialiasing_size));
            }
        }
    }

    public Apfloat[][] createAntialiasingStepsApfloatGrid(Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size, boolean adaptive, int max_samples) {

        Apfloat ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        Apfloat ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;


        ddx_antialiasing_size_x2 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.TWO);
        ddy_antialiasing_size_x2 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.TWO);

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.THREE);
            ddy_antialiasing_size_x3 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.THREE);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.FOUR);
            ddy_antialiasing_size_x4 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.FOUR);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.FIVE);
            ddy_antialiasing_size_x5 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.FIVE);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.SIX);
            ddy_antialiasing_size_x6 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.SIX);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.SEVEN);
            ddy_antialiasing_size_x7 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.SEVEN);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.EIGHT);
            ddy_antialiasing_size_x8 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.EIGHT);
        }

        Apfloat zero = MyApfloat.ZERO;

        Apfloat[][] data;

        if (!adaptive) {
            Apfloat[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            Apfloat[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data = new Apfloat[][] {temp_x, temp_y};
        } else {
            Apfloat[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            Apfloat[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new Apfloat[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            Apfloat[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            Apfloat[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            Apfloat[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            Apfloat[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            Apfloat[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            Apfloat[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }


    public MpfrBigNum[][] createAntialiasingStepsMpfrBigNum(MpfrBigNum ddtemp_size_image_size_x, MpfrBigNum ddtemp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        MpfrBigNum ddx_antialiasing_size;

        MpfrBigNum ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size = ddtemp_size_image_size_x.shift2toi(-4);
            ddy_antialiasing_size = ddtemp_size_image_size_y.shift2toi(-4);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            MpfrBigNum oneFourteenth = new MpfrBigNum(14.0);
            oneFourteenth.reciprocal(oneFourteenth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneFourteenth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            MpfrBigNum oneTwelveth = new MpfrBigNum(12.0);
            oneTwelveth.reciprocal(oneTwelveth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneTwelveth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            MpfrBigNum oneTenth = new MpfrBigNum(10.0);
            oneTenth.reciprocal(oneTenth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneTenth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size = ddtemp_size_image_size_x.shift2toi(-3);
            ddy_antialiasing_size = ddtemp_size_image_size_y.shift2toi(-3);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            MpfrBigNum oneSixth = new MpfrBigNum(6.0);
            oneSixth.reciprocal(oneSixth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneSixth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneSixth);
        }
        else {
            ddx_antialiasing_size = ddtemp_size_image_size_x.divide4();
            ddy_antialiasing_size = ddtemp_size_image_size_y.divide4();
        }

        MpfrBigNum[][] steps = createAntialiasingStepsMpfrBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsMpfrBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    private void precalculateJitterToAntialiasingStepsMpfrBigNum(MpfrBigNum[][] steps, MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size) {

        MpfrBigNum[] temp_x = steps[0];
        MpfrBigNum[] temp_y = steps[1];

        precalculatedJitterDataMpfrBigNum = new MpfrBigNum[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {

                MpfrBigNum tempX = ddx_antialiasing_size.mult(aaJitterKernelX[k][i]);
                MpfrBigNum tempY = ddy_antialiasing_size.mult(aaJitterKernelY[k][i]);
                tempX.add(temp_x[i], tempX);
                tempY.add(temp_y[i], tempY);

                precalculatedJitterDataMpfrBigNum[k][0][i] = tempX;
                precalculatedJitterDataMpfrBigNum[k][1][i] = tempY;
            }
        }

    }

    public MpfrBigNum[][] createAntialiasingStepsMpfrBigNumGrid(MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size, boolean adaptive, int max_samples) {

        MpfrBigNum ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        MpfrBigNum ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;


        ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();
        ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = ddx_antialiasing_size.mult(3);
            ddy_antialiasing_size_x3 = ddy_antialiasing_size.mult(3);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = ddx_antialiasing_size.mult4();
            ddy_antialiasing_size_x4 = ddy_antialiasing_size.mult4();
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = ddx_antialiasing_size.mult(5);
            ddy_antialiasing_size_x5 = ddy_antialiasing_size.mult(5);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = ddx_antialiasing_size.mult(6);
            ddy_antialiasing_size_x6 = ddy_antialiasing_size.mult(6);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = ddx_antialiasing_size.mult(7);
            ddy_antialiasing_size_x7 = ddy_antialiasing_size.mult(7);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = ddx_antialiasing_size.shift2toi(3);
            ddy_antialiasing_size_x8 = ddy_antialiasing_size.shift2toi(3);
        }

        MpfrBigNum zero = new MpfrBigNum();

        MpfrBigNum[][] data;
        if (!adaptive) {
            MpfrBigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            MpfrBigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data =  new MpfrBigNum[][] {temp_x, temp_y};
        } else {
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new MpfrBigNum[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            MpfrBigNum[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    private void precalculateJitterToAntialiasingStepsMpirBigNum(MpirBigNum[][] steps, MpirBigNum ddx_antialiasing_size, MpirBigNum ddy_antialiasing_size) {

        MpirBigNum[] temp_x = steps[0];
        MpirBigNum[] temp_y = steps[1];

        precalculatedJitterDataMpirBigNum = new MpirBigNum[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {

                MpirBigNum tempX = ddx_antialiasing_size.mult(new MpirBigNum(aaJitterKernelX[k][i]));
                MpirBigNum tempY = ddy_antialiasing_size.mult(new MpirBigNum(aaJitterKernelY[k][i]));
                tempX.add(temp_x[i], tempX);
                tempY.add(temp_y[i], tempY);

                precalculatedJitterDataMpirBigNum[k][0][i] = tempX;
                precalculatedJitterDataMpirBigNum[k][1][i] = tempY;
            }
        }
    }

    public MpirBigNum[][] createAntialiasingStepsMpirBigNum(MpirBigNum ddtemp_size_image_size_x, MpirBigNum ddtemp_size_image_size_y, boolean adaptive, boolean jitter, int samples) {

        MpirBigNum ddx_antialiasing_size;

        MpirBigNum ddy_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size = ddtemp_size_image_size_x.shift2toi(-4);
            ddy_antialiasing_size = ddtemp_size_image_size_y.shift2toi(-4);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            MpirBigNum oneFourteenth = new MpirBigNum(14.0);
            oneFourteenth.reciprocal(oneFourteenth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneFourteenth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            MpirBigNum oneTwelveth = new MpirBigNum(12.0);
            oneTwelveth.reciprocal(oneTwelveth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneTwelveth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            MpirBigNum oneTenth = new MpirBigNum(10.0);
            oneTenth.reciprocal(oneTenth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneTenth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size = ddtemp_size_image_size_x.shift2toi(-3);
            ddy_antialiasing_size = ddtemp_size_image_size_y.shift2toi(-3);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            MpirBigNum oneSixth = new MpirBigNum(6.0);
            oneSixth.reciprocal(oneSixth);

            ddx_antialiasing_size = ddtemp_size_image_size_x.mult(oneSixth);
            ddy_antialiasing_size = ddtemp_size_image_size_y.mult(oneSixth);
        }
        else {
            ddx_antialiasing_size = ddtemp_size_image_size_x.divide4();
            ddy_antialiasing_size = ddtemp_size_image_size_y.divide4();
        }

        MpirBigNum[][] steps = createAntialiasingStepsMpirBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingStepsMpirBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public MpirBigNum[][] createAntialiasingStepsMpirBigNumGrid(MpirBigNum ddx_antialiasing_size, MpirBigNum ddy_antialiasing_size, boolean adaptive, int max_samples) {

        MpirBigNum ddx_antialiasing_size_x2, ddx_antialiasing_size_x3 = null, ddx_antialiasing_size_x4 = null, ddx_antialiasing_size_x5 = null, ddx_antialiasing_size_x6 = null, ddx_antialiasing_size_x7 = null, ddx_antialiasing_size_x8 = null;

        MpirBigNum ddy_antialiasing_size_x2, ddy_antialiasing_size_x3 = null, ddy_antialiasing_size_x4 = null, ddy_antialiasing_size_x5 = null, ddy_antialiasing_size_x6 = null, ddy_antialiasing_size_x7 = null, ddy_antialiasing_size_x8 = null;

        ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();
        ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            ddx_antialiasing_size_x3 = ddx_antialiasing_size.mult(3);
            ddy_antialiasing_size_x3 = ddy_antialiasing_size.mult(3);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddx_antialiasing_size_x4 = ddx_antialiasing_size.mult4();
            ddy_antialiasing_size_x4 = ddy_antialiasing_size.mult4();
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            ddx_antialiasing_size_x5 = ddx_antialiasing_size.mult(5);
            ddy_antialiasing_size_x5 = ddy_antialiasing_size.mult(5);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            ddx_antialiasing_size_x6 = ddx_antialiasing_size.mult(6);
            ddy_antialiasing_size_x6 = ddy_antialiasing_size.mult(6);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            ddx_antialiasing_size_x7 = ddx_antialiasing_size.mult(7);
            ddy_antialiasing_size_x7 = ddy_antialiasing_size.mult(7);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddx_antialiasing_size_x8 = ddx_antialiasing_size.shift2toi(3);
            ddy_antialiasing_size_x8 = ddy_antialiasing_size.shift2toi(3);
        }

        MpirBigNum zero = new MpirBigNum();

        MpirBigNum[][] data;
        if (!adaptive) {
            MpirBigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            MpirBigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            data = new MpirBigNum[][] {temp_x, temp_y};
        } else {
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            MpirBigNum[] temp_y = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            data = new MpirBigNum[][] {temp_x, temp_y};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), zero, zero, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3,
                    ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3
            };
            MpirBigNum[] temp_y = {ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), zero, ddy_antialiasing_size_x3,
                    ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x3, ddy_antialiasing_size.negate(), ddy_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4,
                    ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4
            };

            MpirBigNum[] temp_y = {ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x4,
                    ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x4, ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, };
            MpirBigNum[] temp_y = {ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x5, ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, };
            MpirBigNum[] temp_y = {ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x6, ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, };
            MpirBigNum[] temp_y = {ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x7, ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            MpirBigNum[] temp_x = {ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x8.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x7.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x6.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x5.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x4.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x3.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), zero, zero, ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x3, ddx_antialiasing_size_x3, ddx_antialiasing_size_x4, ddx_antialiasing_size_x4, ddx_antialiasing_size_x5, ddx_antialiasing_size_x5, ddx_antialiasing_size_x6, ddx_antialiasing_size_x6, ddx_antialiasing_size_x7, ddx_antialiasing_size_x7, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, ddx_antialiasing_size_x8, };
            MpirBigNum[] temp_y = {ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x8, ddy_antialiasing_size_x8.negate(), ddy_antialiasing_size_x7.negate(), ddy_antialiasing_size_x6.negate(), ddy_antialiasing_size_x5.negate(), ddy_antialiasing_size_x4.negate(), ddy_antialiasing_size_x3.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size.negate(), zero, ddy_antialiasing_size, ddy_antialiasing_size_x2, ddy_antialiasing_size_x3, ddy_antialiasing_size_x4, ddy_antialiasing_size_x5, ddy_antialiasing_size_x6, ddy_antialiasing_size_x7, ddy_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y);
        }

        return data;
    }

    private void precalculateJitterToAntialiasingPolarStepsDouble(double[][] steps, double x_antialiasing_size, double y_antialiasing_size) {

        double[] temp_x = steps[0];
        double[] temp_y_sin = steps[1];
        double[] temp_y_cos = steps[2];

        precalculatedJitterDataPolarDouble = new double[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataPolarDouble[k][0][i] = temp_x[i] * Math.exp(aaJitterKernelX[k][i] * x_antialiasing_size);
                double temp = aaJitterKernelY[k][i] * y_antialiasing_size;
                double cosJitter = Math.cos(temp);
                double sinJitter = Math.sin(temp);
                double tempSin = temp_y_sin[i] * cosJitter + temp_y_cos[i] * sinJitter;
                double tempCos = temp_y_cos[i] * cosJitter - temp_y_sin[i] * sinJitter;
                precalculatedJitterDataPolarDouble[k][1][i] = tempSin;
                precalculatedJitterDataPolarDouble[k][2][i] = tempCos;
            }
        }
    }

    public double[][] createAntialiasingPolarStepsDouble(double mulx, double muly, boolean adaptive, boolean jitter, int samples) {
        double y_antialiasing_size;
        double x_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            y_antialiasing_size = muly * 0.0625;
            x_antialiasing_size = mulx * 0.0625;
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            double oneFourteenth = 1.0 / 14;
            y_antialiasing_size = muly * oneFourteenth;
            x_antialiasing_size = mulx * oneFourteenth;
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            double oneTwelveth = 1.0 / 12;
            y_antialiasing_size = muly * oneTwelveth;
            x_antialiasing_size = mulx * oneTwelveth;
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            double oneTenth = 1.0 / 10;
            y_antialiasing_size = muly * oneTenth;
            x_antialiasing_size = mulx * oneTenth;
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            y_antialiasing_size = muly * 0.125;
            x_antialiasing_size = mulx * 0.125;
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            double oneSixth = 1.0 / 6;
            y_antialiasing_size = muly * oneSixth;
            x_antialiasing_size = mulx * oneSixth;
        }
        else {
            y_antialiasing_size = muly * 0.25;
            x_antialiasing_size = mulx * 0.25;
        }

        double[][] steps = createAntialiasingPolarStepsDoubleGrid(x_antialiasing_size, y_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsDouble(steps, x_antialiasing_size, y_antialiasing_size);
        }

        return steps;
    }

    public double[][] createAntialiasingPolarStepsDoubleGrid(double x_antialiasing_size, double y_antialiasing_size, boolean adaptive, int max_samples) {

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double _2cos_y_antialiasing_size_x = 2 * cos_y_antialiasing_size;
        double sin_y_antialiasing_size_x2 = sin_y_antialiasing_size * _2cos_y_antialiasing_size_x;
        double cos_y_antialiasing_size_x2 = _2cos_y_antialiasing_size_x * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        double exp_x_antialiasing_size_x3 = 0, exp_inv_x_antialiasing_size_x3 = 0, exp_x_antialiasing_size_x4 = 0, exp_inv_x_antialiasing_size_x4 = 0, exp_x_antialiasing_size_x5 = 0, exp_inv_x_antialiasing_size_x5 = 0, exp_x_antialiasing_size_x6 = 0, exp_inv_x_antialiasing_size_x6 = 0, exp_x_antialiasing_size_x7 = 0, exp_inv_x_antialiasing_size_x7 = 0, exp_x_antialiasing_size_x8 = 0, exp_inv_x_antialiasing_size_x8 = 0;
        double sin_y_antialiasing_size_x3 = 0, sin_inv_y_antialiasing_size_x3 = 0, sin_y_antialiasing_size_x4 = 0, sin_inv_y_antialiasing_size_x4 = 0, sin_y_antialiasing_size_x5 = 0, sin_inv_y_antialiasing_size_x5 = 0, sin_y_antialiasing_size_x6 = 0, sin_inv_y_antialiasing_size_x6 = 0, sin_y_antialiasing_size_x7 = 0, sin_inv_y_antialiasing_size_x7 = 0, sin_y_antialiasing_size_x8 = 0, sin_inv_y_antialiasing_size_x8 = 0;
        double cos_y_antialiasing_size_x3 = 0, cos_inv_y_antialiasing_size_x3 = 0, cos_y_antialiasing_size_x4 = 0, cos_inv_y_antialiasing_size_x4 = 0, cos_y_antialiasing_size_x5 = 0, cos_inv_y_antialiasing_size_x5 = 0, cos_y_antialiasing_size_x6 = 0, cos_inv_y_antialiasing_size_x6 = 0, cos_y_antialiasing_size_x7 = 0, cos_inv_y_antialiasing_size_x7 = 0, cos_y_antialiasing_size_x8 = 0, cos_inv_y_antialiasing_size_x8 = 0;

        double _2cos_y_antialiasing_size_x2 = 0;
        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            exp_x_antialiasing_size_x3 = exp_x_antialiasing_size_x2 * exp_x_antialiasing_size;
            exp_inv_x_antialiasing_size_x3 = 1 / exp_x_antialiasing_size_x3;

            _2cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size_x2;
            sin_y_antialiasing_size_x3 = (1 + _2cos_y_antialiasing_size_x2) * sin_y_antialiasing_size;
            cos_y_antialiasing_size_x3 = (_2cos_y_antialiasing_size_x2 - 1) * cos_y_antialiasing_size;

            sin_inv_y_antialiasing_size_x3 = -sin_y_antialiasing_size_x3;
            cos_inv_y_antialiasing_size_x3 = cos_y_antialiasing_size_x3;
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            exp_x_antialiasing_size_x4 = exp_x_antialiasing_size_x2 * exp_x_antialiasing_size_x2;
            exp_inv_x_antialiasing_size_x4 = 1 / exp_x_antialiasing_size_x4;

            cos_y_antialiasing_size_x4 = _2cos_y_antialiasing_size_x2 * cos_y_antialiasing_size_x2 - 1;
            sin_y_antialiasing_size_x4 = sin_y_antialiasing_size_x2 * _2cos_y_antialiasing_size_x2;

            sin_inv_y_antialiasing_size_x4 = -sin_y_antialiasing_size_x4;
            cos_inv_y_antialiasing_size_x4 = cos_y_antialiasing_size_x4;
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            exp_x_antialiasing_size_x5 = exp_x_antialiasing_size_x4 * exp_x_antialiasing_size;
            exp_inv_x_antialiasing_size_x5 = 1 / exp_x_antialiasing_size_x5;

            cos_y_antialiasing_size_x5 = cos_y_antialiasing_size * (2 * (cos_y_antialiasing_size_x4 - cos_y_antialiasing_size_x2) + 1);
            sin_y_antialiasing_size_x5 = _2cos_y_antialiasing_size_x * sin_y_antialiasing_size_x4 - sin_y_antialiasing_size_x3;

            sin_inv_y_antialiasing_size_x5 = -sin_y_antialiasing_size_x5;
            cos_inv_y_antialiasing_size_x5 = cos_y_antialiasing_size_x5;
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            exp_x_antialiasing_size_x6 = exp_x_antialiasing_size_x3 * exp_x_antialiasing_size_x3;
            exp_inv_x_antialiasing_size_x6 = 1 / exp_x_antialiasing_size_x6;

            cos_y_antialiasing_size_x6 = 2 * cos_y_antialiasing_size_x3 * cos_y_antialiasing_size_x3 - 1;
            sin_y_antialiasing_size_x6 = 2 * cos_y_antialiasing_size_x3 * sin_y_antialiasing_size_x3;

            sin_inv_y_antialiasing_size_x6 = -sin_y_antialiasing_size_x6;
            cos_inv_y_antialiasing_size_x6 = cos_y_antialiasing_size_x6;
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            exp_x_antialiasing_size_x7 = exp_x_antialiasing_size_x6 * exp_x_antialiasing_size;
            exp_inv_x_antialiasing_size_x7 = 1 / exp_x_antialiasing_size_x7;

            double temp = cos_y_antialiasing_size_x2 + cos_y_antialiasing_size_x6;
            cos_y_antialiasing_size_x7 = cos_y_antialiasing_size * (2 * (temp - cos_y_antialiasing_size_x4) - 1);
            sin_y_antialiasing_size_x7 = sin_y_antialiasing_size * (2 * (temp + cos_y_antialiasing_size_x4) + 1);

            sin_inv_y_antialiasing_size_x7 = -sin_y_antialiasing_size_x7;
            cos_inv_y_antialiasing_size_x7 = cos_y_antialiasing_size_x7;
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            exp_x_antialiasing_size_x8 = exp_x_antialiasing_size_x4 * exp_x_antialiasing_size_x4;
            exp_inv_x_antialiasing_size_x8 = 1 / exp_x_antialiasing_size_x8;

            cos_y_antialiasing_size_x8 = 2 * cos_y_antialiasing_size_x4 * cos_y_antialiasing_size_x4 - 1;
            sin_y_antialiasing_size_x8 = 2 * cos_y_antialiasing_size_x4 * sin_y_antialiasing_size_x4;

            sin_inv_y_antialiasing_size_x8 = -sin_y_antialiasing_size_x8;
            cos_inv_y_antialiasing_size_x8 = cos_y_antialiasing_size_x8;
        }

        double[][] data;

        if(!adaptive) {
            double[] temp_x = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            double[] temp_y_sin = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            double[] temp_y_cos = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            data =  new double[][] {temp_x, temp_y_sin, temp_y_cos};

        }
        else {
            double[] temp_x = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      1,                       1,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1

            };

            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    0, 0, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, 0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };

            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    1, 1, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size, 1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size


            };


            data =  new double[][] {temp_x, temp_y_sin, temp_y_cos};
        }


        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, 1, 1, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3
            };
            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x3, 0, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, 0, sin_y_antialiasing_size_x3,
                    sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size
            };
            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x3, 1, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, 1, cos_y_antialiasing_size_x3,
                    cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4,
                    exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4
            };

            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4,
                    sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3
            };

            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4,
                    cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, 1, 1, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, };

            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, };

            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, 1, 1, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, };

            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, };

            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, 1, 1, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, };
            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, };
            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            double[] temp_x = {exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, 1, 1, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, };
            double[] temp_y_sin = {sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, 0, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, };
            double[] temp_y_cos = {cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, 1, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }


        return data;
    }

    private void precalculateJitterToAntialiasingPolarStepsApfloat(Apfloat[][] steps, Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size) {

        Apfloat[] temp_x = steps[0];
        Apfloat[] temp_y_sin = steps[1];
        Apfloat[] temp_y_cos = steps[2];

        precalculatedJitterDataPolarApfloat = new Apfloat[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataPolarApfloat[k][0][i] = MyApfloat.fp.multiply(temp_x[i], MyApfloat.exp(MyApfloat.fp.multiply(new MyApfloat(aaJitterKernelX[k][i]), ddx_antialiasing_size)));
                Apfloat temp = MyApfloat.fp.multiply(new MyApfloat(aaJitterKernelY[k][i]), ddy_antialiasing_size);
                Apfloat cosJitter = MyApfloat.cos(temp);
                Apfloat sinJitter = MyApfloat.sin(temp);
                Apfloat tempSin = MyApfloat.fp.add(MyApfloat.fp.multiply(temp_y_sin[i], cosJitter), MyApfloat.fp.multiply(temp_y_cos[i], sinJitter));
                Apfloat tempCos = MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp_y_cos[i], cosJitter), MyApfloat.fp.multiply(temp_y_sin[i], sinJitter));
                precalculatedJitterDataPolarApfloat[k][1][i] = tempSin;
                precalculatedJitterDataPolarApfloat[k][2][i] = tempCos;
            }
        }
    }

    public Apfloat[][] createAntialiasingPolarStepsApfloat(Apfloat ddmulx, Apfloat ddmuly, boolean adaptive, boolean jitter, int samples) {

        Apfloat ddy_antialiasing_size;
        Apfloat ddx_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            Apfloat oneSixteenth = new MyApfloat(0.0625);

            ddy_antialiasing_size = ddmuly.multiply(oneSixteenth);
            ddx_antialiasing_size = ddmulx.multiply(oneSixteenth);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            Apfloat oneFourteenth = MyApfloat.reciprocal(new MyApfloat(14.0));

            ddy_antialiasing_size = ddmuly.multiply(oneFourteenth);
            ddx_antialiasing_size = ddmulx.multiply(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            Apfloat oneTwelveth = MyApfloat.reciprocal(new MyApfloat(12.0));

            ddy_antialiasing_size = ddmuly.multiply(oneTwelveth);
            ddx_antialiasing_size = ddmulx.multiply(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            Apfloat oneTenth = MyApfloat.reciprocal(new MyApfloat(10.0));

            ddy_antialiasing_size = ddmuly.multiply(oneTenth);
            ddx_antialiasing_size = ddmulx.multiply(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            Apfloat pointonetwofive = new Apfloat(0.125);

            ddy_antialiasing_size = ddmuly.multiply(pointonetwofive);
            ddx_antialiasing_size = ddmulx.multiply(pointonetwofive);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            Apfloat oneSixth = MyApfloat.reciprocal(new MyApfloat(6.0));
            ddy_antialiasing_size = ddmuly.multiply(oneSixth);
            ddx_antialiasing_size = ddmulx.multiply(oneSixth);
        }
        else {
            Apfloat point25 = new MyApfloat(0.25);
            ddy_antialiasing_size = MyApfloat.fp.multiply(ddmuly, point25);
            ddx_antialiasing_size = MyApfloat.fp.multiply(ddmulx, point25);
        }

        Apfloat[][] steps = createAntialiasingPolarStepsApfloatGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsApfloat(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public Apfloat[][] createAntialiasingPolarStepsApfloatGrid(Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size, boolean adaptive, int max_samples) {

        Apfloat exp_x_antialiasing_size = MyApfloat.exp(ddx_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size = MyApfloat.reciprocal(exp_x_antialiasing_size);

        Apfloat exp_x_antialiasing_size_x2 = MyApfloat.fp.multiply(exp_x_antialiasing_size, exp_x_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size_x2 = MyApfloat.reciprocal(exp_x_antialiasing_size_x2);

        Apfloat one = MyApfloat.ONE;

        Apfloat sin_y_antialiasing_size = MyApfloat.sin(ddy_antialiasing_size);
        Apfloat cos_y_antialiasing_size = MyApfloat.cos(ddy_antialiasing_size);

        Apfloat sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        Apfloat cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        Apfloat _2cos_y_antialiasing_size = MyApfloat.fp.multiply(cos_y_antialiasing_size, MyApfloat.TWO);
        Apfloat sin_y_antialiasing_size_x2 = MyApfloat.fp.multiply(_2cos_y_antialiasing_size, sin_y_antialiasing_size);
        Apfloat cos_y_antialiasing_size_x2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(_2cos_y_antialiasing_size, cos_y_antialiasing_size), one);

        Apfloat sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        Apfloat cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        Apfloat exp_x_antialiasing_size_x3 = null, exp_inv_x_antialiasing_size_x3 = null, exp_x_antialiasing_size_x4 = null, exp_inv_x_antialiasing_size_x4 = null, exp_x_antialiasing_size_x5 = null, exp_inv_x_antialiasing_size_x5 = null, exp_x_antialiasing_size_x6 = null, exp_inv_x_antialiasing_size_x6 = null, exp_x_antialiasing_size_x7 = null, exp_inv_x_antialiasing_size_x7 = null, exp_x_antialiasing_size_x8 = null, exp_inv_x_antialiasing_size_x8 = null;
        Apfloat sin_y_antialiasing_size_x3 = null, sin_inv_y_antialiasing_size_x3 = null, sin_y_antialiasing_size_x4 = null, sin_inv_y_antialiasing_size_x4 = null, sin_y_antialiasing_size_x5 = null, sin_inv_y_antialiasing_size_x5 = null, sin_y_antialiasing_size_x6 = null, sin_inv_y_antialiasing_size_x6 = null, sin_y_antialiasing_size_x7 = null, sin_inv_y_antialiasing_size_x7 = null, sin_y_antialiasing_size_x8 = null, sin_inv_y_antialiasing_size_x8 = null;
        Apfloat cos_y_antialiasing_size_x3 = null, cos_inv_y_antialiasing_size_x3 = null, cos_y_antialiasing_size_x4 = null, cos_inv_y_antialiasing_size_x4 = null, cos_y_antialiasing_size_x5 = null, cos_inv_y_antialiasing_size_x5 = null, cos_y_antialiasing_size_x6 = null, cos_inv_y_antialiasing_size_x6 = null, cos_y_antialiasing_size_x7 = null, cos_inv_y_antialiasing_size_x7 = null, cos_y_antialiasing_size_x8 = null, cos_inv_y_antialiasing_size_x8 = null;


        Apfloat _2cos_y_antialiasing_size_x2 = null;
        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            exp_x_antialiasing_size_x3 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x2, exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x3 = MyApfloat.reciprocal(exp_x_antialiasing_size_x3);

            _2cos_y_antialiasing_size_x2 = MyApfloat.fp.multiply(cos_y_antialiasing_size_x2, MyApfloat.TWO);
            sin_y_antialiasing_size_x3 = MyApfloat.fp.multiply(MyApfloat.fp.add(_2cos_y_antialiasing_size_x2, one), sin_y_antialiasing_size);
            cos_y_antialiasing_size_x3 = MyApfloat.fp.multiply(MyApfloat.fp.subtract(_2cos_y_antialiasing_size_x2, one), cos_y_antialiasing_size);

            sin_inv_y_antialiasing_size_x3 = sin_y_antialiasing_size_x3.negate();
            cos_inv_y_antialiasing_size_x3 = cos_y_antialiasing_size_x3;
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            exp_x_antialiasing_size_x4 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2);
            exp_inv_x_antialiasing_size_x4 = MyApfloat.reciprocal(exp_x_antialiasing_size_x4);

            cos_y_antialiasing_size_x4 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(_2cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2), one);
            sin_y_antialiasing_size_x4 = MyApfloat.fp.multiply(sin_y_antialiasing_size_x2, _2cos_y_antialiasing_size_x2);

            sin_inv_y_antialiasing_size_x4 = sin_y_antialiasing_size_x4.negate();
            cos_inv_y_antialiasing_size_x4 = cos_y_antialiasing_size_x4;
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            exp_x_antialiasing_size_x5 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x4, exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x5 = MyApfloat.reciprocal(exp_x_antialiasing_size_x5);

            cos_y_antialiasing_size_x5 = MyApfloat.fp.multiply(cos_y_antialiasing_size, MyApfloat.fp.add(MyApfloat.fp.multiply(MyApfloat.fp.subtract(cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x2), MyApfloat.TWO), one));
            sin_y_antialiasing_size_x5 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(_2cos_y_antialiasing_size_x2, sin_y_antialiasing_size_x4), sin_y_antialiasing_size_x3);

            sin_inv_y_antialiasing_size_x5 = sin_y_antialiasing_size_x5.negate();
            cos_inv_y_antialiasing_size_x5 = cos_y_antialiasing_size_x5;
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            exp_x_antialiasing_size_x6 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3);
            exp_inv_x_antialiasing_size_x6 = MyApfloat.reciprocal(exp_x_antialiasing_size_x6);

            cos_y_antialiasing_size_x6 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x3), MyApfloat.TWO), one);
            sin_y_antialiasing_size_x6 = MyApfloat.fp.multiply(MyApfloat.fp.multiply(cos_y_antialiasing_size_x3, MyApfloat.TWO), sin_y_antialiasing_size_x3);

            sin_inv_y_antialiasing_size_x6 = sin_y_antialiasing_size_x6.negate();
            cos_inv_y_antialiasing_size_x6 = cos_y_antialiasing_size_x6;
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            exp_x_antialiasing_size_x7 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x6, exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x7 = MyApfloat.reciprocal(exp_x_antialiasing_size_x7);

            Apfloat temp = MyApfloat.fp.add(cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x6);
            cos_y_antialiasing_size_x7 = MyApfloat.fp.multiply(cos_y_antialiasing_size, MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.subtract(temp, cos_y_antialiasing_size_x4), MyApfloat.TWO), one));
            sin_y_antialiasing_size_x7 = MyApfloat.fp.multiply(sin_y_antialiasing_size, MyApfloat.fp.add(MyApfloat.fp.multiply(MyApfloat.fp.add(temp, cos_y_antialiasing_size_x4), MyApfloat.TWO), one));

            sin_inv_y_antialiasing_size_x7 = sin_y_antialiasing_size_x7.negate();
            cos_inv_y_antialiasing_size_x7 = cos_y_antialiasing_size_x7;
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            exp_x_antialiasing_size_x8 = MyApfloat.fp.multiply(exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4);
            exp_inv_x_antialiasing_size_x8 = MyApfloat.reciprocal(exp_x_antialiasing_size_x8);

            cos_y_antialiasing_size_x8 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x4), MyApfloat.TWO), one);
            sin_y_antialiasing_size_x8 = MyApfloat.fp.multiply(MyApfloat.fp.multiply(cos_y_antialiasing_size_x4, MyApfloat.TWO), sin_y_antialiasing_size_x4);

            sin_inv_y_antialiasing_size_x8 = sin_y_antialiasing_size_x8.negate();
            cos_inv_y_antialiasing_size_x8 = cos_y_antialiasing_size_x8;
        }

        Apfloat zero = MyApfloat.ZERO;

        Apfloat[][] data;

        if(!adaptive) {

            Apfloat[] temp_x = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            data = new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
        }
        else {
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      one,                       one,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one

            };

            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    zero, zero, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };


            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    one, one, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size, one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size


            };

            data = new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, one, one, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3
            };
            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3,
                    sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size
            };
            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3,
                    cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4,
                    exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4
            };

            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4,
                    sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3
            };

            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4,
                    cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, };

            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, };

            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, };

            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, };

            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, };
            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, };
            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            Apfloat[] temp_x = {exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, };
            Apfloat[] temp_y_sin = {sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, };
            Apfloat[] temp_y_cos = {cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        return data;

    }

    private void precalculateJitterToAntialiasingPolarStepsDoubleDouble(DoubleDouble[][] steps, DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size) {

        DoubleDouble[] temp_x = steps[0];
        DoubleDouble[] temp_y_sin = steps[1];
        DoubleDouble[] temp_y_cos = steps[2];

        precalculatedJitterDataPolarDoubleDouble = new DoubleDouble[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {
                precalculatedJitterDataPolarDoubleDouble[k][0][i] = temp_x[i].multiply(new DoubleDouble(aaJitterKernelX[k][i]).multiply(ddx_antialiasing_size).exp());
                DoubleDouble temp = new DoubleDouble(aaJitterKernelY[k][i]).multiply(ddy_antialiasing_size);
                DoubleDouble cosJitter = temp.cos();
                DoubleDouble sinJitter = temp.sin();
                DoubleDouble tempSin = temp_y_sin[i].multiply(cosJitter).add(temp_y_cos[i].multiply(sinJitter));
                DoubleDouble tempCos = temp_y_cos[i].multiply(cosJitter).subtract(temp_y_sin[i].multiply(sinJitter));
                precalculatedJitterDataPolarDoubleDouble[k][1][i] = tempSin;
                precalculatedJitterDataPolarDoubleDouble[k][2][i]= tempCos;
            }
        }
    }

    public DoubleDouble[][] createAntialiasingPolarStepsDoubleDouble(DoubleDouble ddmulx, DoubleDouble ddmuly, boolean adaptive, boolean jitter, int samples) {


        DoubleDouble ddy_antialiasing_size;
        DoubleDouble ddx_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            DoubleDouble oneSixteenth = new DoubleDouble(0.0625);

            ddy_antialiasing_size = ddmuly.multiply(oneSixteenth);
            ddx_antialiasing_size = ddmulx.multiply(oneSixteenth);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            DoubleDouble oneFourteenth = new DoubleDouble(1.0).divide(new DoubleDouble(14.0));

            ddy_antialiasing_size = ddmuly.multiply(oneFourteenth);
            ddx_antialiasing_size = ddmulx.multiply(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            DoubleDouble oneTwelveth = new DoubleDouble(1.0).divide(new DoubleDouble(12.0));

            ddy_antialiasing_size = ddmuly.multiply(oneTwelveth);
            ddx_antialiasing_size = ddmulx.multiply(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            DoubleDouble oneTenth = new DoubleDouble(1.0).divide(new DoubleDouble(10.0));

            ddy_antialiasing_size = ddmuly.multiply(oneTenth);
            ddx_antialiasing_size = ddmulx.multiply(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            DoubleDouble pointonetwofive = new DoubleDouble(0.125);

            ddy_antialiasing_size = ddmuly.multiply(pointonetwofive);
            ddx_antialiasing_size = ddmulx.multiply(pointonetwofive);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            DoubleDouble oneSixth = new DoubleDouble(1.0).divide(new DoubleDouble(6.0));
            ddy_antialiasing_size = ddmuly.multiply(oneSixth);
            ddx_antialiasing_size = ddmulx.multiply(oneSixth);
        }
        else {
            DoubleDouble point25 = new DoubleDouble(0.25);

            ddy_antialiasing_size = ddmuly.multiply(point25);
            ddx_antialiasing_size = ddmulx.multiply(point25);
        }

        DoubleDouble[][] steps = createAntialiasingPolarStepsDoubleDoubleGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsDoubleDouble(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public DoubleDouble[][] createAntialiasingPolarStepsDoubleDoubleGrid(DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size, boolean adaptive, int max_samples) {

        DoubleDouble exp_x_antialiasing_size = ddx_antialiasing_size.exp();
        DoubleDouble exp_inv_x_antialiasing_size = exp_x_antialiasing_size.reciprocal();

        DoubleDouble exp_x_antialiasing_size_x2 = exp_x_antialiasing_size.sqr();
        DoubleDouble exp_inv_x_antialiasing_size_x2 = exp_x_antialiasing_size_x2.reciprocal();

        DoubleDouble one = new DoubleDouble(1.0);

        DoubleDouble sin_y_antialiasing_size = ddy_antialiasing_size.sin();
        DoubleDouble cos_y_antialiasing_size = ddy_antialiasing_size.cos();

        DoubleDouble sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        DoubleDouble cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        DoubleDouble _2cos_y_antialiasing_size = cos_y_antialiasing_size.multiply(2);
        DoubleDouble sin_y_antialiasing_size_x2 = sin_y_antialiasing_size.multiply(_2cos_y_antialiasing_size);
        DoubleDouble cos_y_antialiasing_size_x2 = _2cos_y_antialiasing_size.multiply(cos_y_antialiasing_size).subtract(one);

        DoubleDouble sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        DoubleDouble cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        DoubleDouble exp_x_antialiasing_size_x3 = null, exp_inv_x_antialiasing_size_x3 = null, exp_x_antialiasing_size_x4 = null, exp_inv_x_antialiasing_size_x4 = null, exp_x_antialiasing_size_x5 = null, exp_inv_x_antialiasing_size_x5 = null, exp_x_antialiasing_size_x6 = null, exp_inv_x_antialiasing_size_x6 = null, exp_x_antialiasing_size_x7 = null, exp_inv_x_antialiasing_size_x7 = null, exp_x_antialiasing_size_x8 = null, exp_inv_x_antialiasing_size_x8 = null;
        DoubleDouble sin_y_antialiasing_size_x3 = null, sin_inv_y_antialiasing_size_x3 = null, sin_y_antialiasing_size_x4 = null, sin_inv_y_antialiasing_size_x4 = null, sin_y_antialiasing_size_x5 = null, sin_inv_y_antialiasing_size_x5 = null, sin_y_antialiasing_size_x6 = null, sin_inv_y_antialiasing_size_x6 = null, sin_y_antialiasing_size_x7 = null, sin_inv_y_antialiasing_size_x7 = null, sin_y_antialiasing_size_x8 = null, sin_inv_y_antialiasing_size_x8 = null;
        DoubleDouble cos_y_antialiasing_size_x3 = null, cos_inv_y_antialiasing_size_x3 = null, cos_y_antialiasing_size_x4 = null, cos_inv_y_antialiasing_size_x4 = null, cos_y_antialiasing_size_x5 = null, cos_inv_y_antialiasing_size_x5 = null, cos_y_antialiasing_size_x6 = null, cos_inv_y_antialiasing_size_x6 = null, cos_y_antialiasing_size_x7 = null, cos_inv_y_antialiasing_size_x7 = null, cos_y_antialiasing_size_x8 = null, cos_inv_y_antialiasing_size_x8 = null;


        DoubleDouble _2cos_y_antialiasing_size_x2 = null;
        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            exp_x_antialiasing_size_x3 = exp_x_antialiasing_size_x2.multiply(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x3 = exp_x_antialiasing_size_x3.reciprocal();

            _2cos_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2.multiply(2);
            sin_y_antialiasing_size_x3 = _2cos_y_antialiasing_size_x2.add(one).multiply(sin_y_antialiasing_size);
            cos_y_antialiasing_size_x3 = _2cos_y_antialiasing_size_x2.subtract(one).multiply(cos_y_antialiasing_size);

            sin_inv_y_antialiasing_size_x3 = sin_y_antialiasing_size_x3.negate();
            cos_inv_y_antialiasing_size_x3 = cos_y_antialiasing_size_x3;
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            exp_x_antialiasing_size_x4 = exp_x_antialiasing_size_x2.sqr();
            exp_inv_x_antialiasing_size_x4 = exp_x_antialiasing_size_x4.reciprocal();

            cos_y_antialiasing_size_x4 = _2cos_y_antialiasing_size_x2.multiply(cos_y_antialiasing_size_x2).subtract(one);
            sin_y_antialiasing_size_x4 = sin_y_antialiasing_size_x2.multiply(_2cos_y_antialiasing_size_x2);

            sin_inv_y_antialiasing_size_x4 = sin_y_antialiasing_size_x4.negate();
            cos_inv_y_antialiasing_size_x4 = cos_y_antialiasing_size_x4;
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            exp_x_antialiasing_size_x5 = exp_x_antialiasing_size_x4.multiply(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x5 = exp_x_antialiasing_size_x5.reciprocal();

            cos_y_antialiasing_size_x5 = cos_y_antialiasing_size.multiply(cos_y_antialiasing_size_x4.subtract(cos_y_antialiasing_size_x2).multiply(2).add(one));
            sin_y_antialiasing_size_x5 = _2cos_y_antialiasing_size_x2.multiply(sin_y_antialiasing_size_x4).subtract(sin_y_antialiasing_size_x3);

            sin_inv_y_antialiasing_size_x5 = sin_y_antialiasing_size_x5.negate();
            cos_inv_y_antialiasing_size_x5 = cos_y_antialiasing_size_x5;
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            exp_x_antialiasing_size_x6 = exp_x_antialiasing_size_x3.sqr();
            exp_inv_x_antialiasing_size_x6 = exp_x_antialiasing_size_x6.reciprocal();

            cos_y_antialiasing_size_x6 = cos_y_antialiasing_size_x3.sqr().multiply(2).subtract(one);
            sin_y_antialiasing_size_x6 = cos_y_antialiasing_size_x3.multiply(2).multiply(sin_y_antialiasing_size_x3);

            sin_inv_y_antialiasing_size_x6 = sin_y_antialiasing_size_x6.negate();
            cos_inv_y_antialiasing_size_x6 = cos_y_antialiasing_size_x6;
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            exp_x_antialiasing_size_x7 = exp_x_antialiasing_size_x6.multiply(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x7 = exp_x_antialiasing_size_x7.reciprocal();

            DoubleDouble temp = cos_y_antialiasing_size_x2.add(cos_y_antialiasing_size_x6);
            cos_y_antialiasing_size_x7 = cos_y_antialiasing_size.multiply(temp.subtract(cos_y_antialiasing_size_x4).multiply(2).subtract(one));
            sin_y_antialiasing_size_x7 = sin_y_antialiasing_size.multiply(temp.add(cos_y_antialiasing_size_x4).multiply(2).add(one));

            sin_inv_y_antialiasing_size_x7 = sin_y_antialiasing_size_x7.negate();
            cos_inv_y_antialiasing_size_x7 = cos_y_antialiasing_size_x7;
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            exp_x_antialiasing_size_x8 = exp_x_antialiasing_size_x4.sqr();
            exp_inv_x_antialiasing_size_x8 = exp_x_antialiasing_size_x8.reciprocal();

            cos_y_antialiasing_size_x8 = cos_y_antialiasing_size_x4.sqr().multiply(2).subtract(one);
            sin_y_antialiasing_size_x8 = cos_y_antialiasing_size_x4.multiply(2).multiply(sin_y_antialiasing_size_x4);

            sin_inv_y_antialiasing_size_x8 = sin_y_antialiasing_size_x8.negate();
            cos_inv_y_antialiasing_size_x8 = cos_y_antialiasing_size_x8;
        }

        DoubleDouble zero = new DoubleDouble();

        DoubleDouble[][] data;
        if(!adaptive) {

            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            data = new DoubleDouble[][] {temp_x, temp_y_sin, temp_y_cos};
        }
        else {
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      one,                       one,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one

            };

            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    zero, zero, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };


            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    one, one, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size, one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size


            };

            data = new DoubleDouble[][] {temp_x, temp_y_sin, temp_y_cos};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, one, one, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3
            };
            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3,
                    sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size
            };
            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3,
                    cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4,
                    exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4
            };

            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4,
                    sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3
            };

            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4,
                    cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, };

            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, };

            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, };

            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, };

            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, };
            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7,};
            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            DoubleDouble[] temp_x = {exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, };
            DoubleDouble[] temp_y_sin = {sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, };
            DoubleDouble[] temp_y_cos = {cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        return data;
    }

    private void precalculateJitterToAntialiasingPolarStepsMpfrBigNum(MpfrBigNum[][] steps, MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size) {

        MpfrBigNum[] temp_x = steps[0];
        MpfrBigNum[] temp_y_sin = steps[1];
        MpfrBigNum[] temp_y_cos = steps[2];

        precalculatedJitterDataPolarMpfrBigNum = new MpfrBigNum[NUMBER_OF_AA_JITTER_KERNELS][steps.length][temp_x.length];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < temp_x.length; i++) {

                MpfrBigNum tempexp = ddx_antialiasing_size.mult(aaJitterKernelX[k][i]);
                tempexp.exp(tempexp);
                tempexp.mult(temp_x[i], tempexp);
                precalculatedJitterDataPolarMpfrBigNum[k][0][i] = tempexp;

                MpfrBigNum temp = ddy_antialiasing_size.mult(aaJitterKernelY[k][i]);
                MpfrBigNum[] sin_cos = temp.sin_cos();
                MpfrBigNum cosJitter = sin_cos[1];
                MpfrBigNum sinJitter = sin_cos[0];

                MpfrBigNum tempSin = temp_y_sin[i].mult(cosJitter);
                tempSin.add(temp_y_cos[i].mult(sinJitter), tempSin);

                MpfrBigNum tempCos = temp_y_cos[i].mult(cosJitter);
                tempCos.sub(temp_y_sin[i].mult(sinJitter), tempCos);

                precalculatedJitterDataPolarMpfrBigNum[k][1][i] = tempSin;
                precalculatedJitterDataPolarMpfrBigNum[k][2][i] = tempCos;
            }
        }
    }

    public MpfrBigNum[][] createAntialiasingPolarStepsMpfrBigNum(MpfrBigNum ddmulx, MpfrBigNum ddmuly, boolean adaptive, boolean jitter, int samples) {

        MpfrBigNum ddy_antialiasing_size;
        MpfrBigNum ddx_antialiasing_size;

        if(samples > MAX_AA_SAMPLES_224 && !adaptive) {
            ddy_antialiasing_size = ddmuly.shift2toi(-4);
            ddx_antialiasing_size = ddmulx.shift2toi(-4);
        }
        else if(samples > MAX_AA_SAMPLES_168 && !adaptive) {
            MpfrBigNum oneFourteenth = new MpfrBigNum(14.0);
            oneFourteenth.reciprocal(oneFourteenth);
            ddy_antialiasing_size = ddmuly.mult(oneFourteenth);
            ddx_antialiasing_size = ddmulx.mult(oneFourteenth);
        }
        else if(samples > MAX_AA_SAMPLES_120 && !adaptive) {
            MpfrBigNum oneTwelveth = new MpfrBigNum(12.0);
            oneTwelveth.reciprocal(oneTwelveth);
            ddy_antialiasing_size = ddmuly.mult(oneTwelveth);
            ddx_antialiasing_size = ddmulx.mult(oneTwelveth);
        }
        else if(samples > MAX_AA_SAMPLES_80 && !adaptive) {
            MpfrBigNum oneTenth = new MpfrBigNum(10.0);
            oneTenth.reciprocal(oneTenth);
            ddy_antialiasing_size = ddmuly.mult(oneTenth);
            ddx_antialiasing_size = ddmulx.mult(oneTenth);
        }
        else if(samples > MAX_AA_SAMPLES_48 && !adaptive) {
            ddy_antialiasing_size = ddmuly.shift2toi(-3);
            ddx_antialiasing_size = ddmulx.shift2toi(-3);
        }
        else if(samples > MAX_AA_SAMPLES_24 && !adaptive) {
            MpfrBigNum oneSixth = new MpfrBigNum(6.0);
            oneSixth.reciprocal(oneSixth);
            ddy_antialiasing_size = ddmuly.mult(oneSixth);
            ddx_antialiasing_size = ddmulx.mult(oneSixth);
        }
        else {
            ddy_antialiasing_size = ddmuly.divide4();
            ddx_antialiasing_size = ddmulx.divide4();
        }

        MpfrBigNum[][] steps = createAntialiasingPolarStepsMpfrBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive, samples);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsMpfrBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public MpfrBigNum[][] createAntialiasingPolarStepsMpfrBigNumGrid(MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size, boolean adaptive, int max_samples) {

        MpfrBigNum exp_x_antialiasing_size = ddx_antialiasing_size.exp();
        MpfrBigNum exp_inv_x_antialiasing_size = exp_x_antialiasing_size.reciprocal();

        MpfrBigNum exp_x_antialiasing_size_x2 = exp_x_antialiasing_size.square();
        MpfrBigNum exp_inv_x_antialiasing_size_x2 = exp_x_antialiasing_size_x2.reciprocal();

        MpfrBigNum one = new MpfrBigNum(1);

        MpfrBigNum[] res = ddy_antialiasing_size.sin_cos();

        MpfrBigNum sin_y_antialiasing_size = res[0];
        MpfrBigNum cos_y_antialiasing_size = res[1];

        MpfrBigNum sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        MpfrBigNum cos_inv_y_antialiasing_size = new MpfrBigNum(cos_y_antialiasing_size);

        MpfrBigNum _2cos_y_antialiasing_size = cos_y_antialiasing_size.mult2();

        MpfrBigNum sin_y_antialiasing_size_x2 = sin_y_antialiasing_size.mult(_2cos_y_antialiasing_size);

        MpfrBigNum cos_y_antialiasing_size_x2 = _2cos_y_antialiasing_size.mult(cos_y_antialiasing_size);
        cos_y_antialiasing_size_x2.sub(1, cos_y_antialiasing_size_x2);

        MpfrBigNum sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        MpfrBigNum cos_inv_y_antialiasing_size_x2 = new MpfrBigNum(cos_y_antialiasing_size_x2);

        MpfrBigNum exp_x_antialiasing_size_x3 = null, exp_inv_x_antialiasing_size_x3 = null, exp_x_antialiasing_size_x4 = null, exp_inv_x_antialiasing_size_x4 = null, exp_x_antialiasing_size_x5 = null, exp_inv_x_antialiasing_size_x5 = null, exp_x_antialiasing_size_x6 = null, exp_inv_x_antialiasing_size_x6 = null, exp_x_antialiasing_size_x7 = null, exp_inv_x_antialiasing_size_x7 = null, exp_x_antialiasing_size_x8 = null, exp_inv_x_antialiasing_size_x8 = null;
        MpfrBigNum sin_y_antialiasing_size_x3 = null, sin_inv_y_antialiasing_size_x3 = null, sin_y_antialiasing_size_x4 = null, sin_inv_y_antialiasing_size_x4 = null, sin_y_antialiasing_size_x5 = null, sin_inv_y_antialiasing_size_x5 = null, sin_y_antialiasing_size_x6 = null, sin_inv_y_antialiasing_size_x6 = null, sin_y_antialiasing_size_x7 = null, sin_inv_y_antialiasing_size_x7 = null, sin_y_antialiasing_size_x8 = null, sin_inv_y_antialiasing_size_x8 = null;
        MpfrBigNum cos_y_antialiasing_size_x3 = null, cos_inv_y_antialiasing_size_x3 = null, cos_y_antialiasing_size_x4 = null, cos_inv_y_antialiasing_size_x4 = null, cos_y_antialiasing_size_x5 = null, cos_inv_y_antialiasing_size_x5 = null, cos_y_antialiasing_size_x6 = null, cos_inv_y_antialiasing_size_x6 = null, cos_y_antialiasing_size_x7 = null, cos_inv_y_antialiasing_size_x7 = null, cos_y_antialiasing_size_x8 = null, cos_inv_y_antialiasing_size_x8 = null;


        MpfrBigNum _2cos_y_antialiasing_size_x2 = null;
        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            exp_x_antialiasing_size_x3 = exp_x_antialiasing_size_x2.mult(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x3 = exp_x_antialiasing_size_x3.reciprocal();

            _2cos_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2.mult2();
            MpfrBigNum temp = _2cos_y_antialiasing_size_x2.add(1);
            sin_y_antialiasing_size_x3 = temp.mult(sin_y_antialiasing_size, temp);

            temp = _2cos_y_antialiasing_size_x2.sub(1);
            cos_y_antialiasing_size_x3 = temp.mult(cos_y_antialiasing_size, temp);

            sin_inv_y_antialiasing_size_x3 = sin_y_antialiasing_size_x3.negate();
            cos_inv_y_antialiasing_size_x3 = cos_y_antialiasing_size_x3;
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            exp_x_antialiasing_size_x4 = exp_x_antialiasing_size_x2.square();
            exp_inv_x_antialiasing_size_x4 = exp_x_antialiasing_size_x4.reciprocal();

            MpfrBigNum temp = _2cos_y_antialiasing_size_x2.mult(cos_y_antialiasing_size_x2);
            cos_y_antialiasing_size_x4 = temp.sub(1, temp);
            sin_y_antialiasing_size_x4 = sin_y_antialiasing_size_x2.mult(_2cos_y_antialiasing_size_x2);

            sin_inv_y_antialiasing_size_x4 = sin_y_antialiasing_size_x4.negate();
            cos_inv_y_antialiasing_size_x4 = cos_y_antialiasing_size_x4;
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            exp_x_antialiasing_size_x5 = exp_x_antialiasing_size_x4.mult(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x5 = exp_x_antialiasing_size_x5.reciprocal();

            MpfrBigNum temp = cos_y_antialiasing_size_x4.sub(cos_y_antialiasing_size_x2);
            temp.mult2(temp);
            temp.add(1, temp);
            temp.mult(cos_y_antialiasing_size, temp);
            cos_y_antialiasing_size_x5 = temp;

            temp = _2cos_y_antialiasing_size_x2.mult(sin_y_antialiasing_size_x4);
            temp.sub(sin_y_antialiasing_size_x3, temp);
            sin_y_antialiasing_size_x5 = temp;

            sin_inv_y_antialiasing_size_x5 = sin_y_antialiasing_size_x5.negate();
            cos_inv_y_antialiasing_size_x5 = cos_y_antialiasing_size_x5;
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            exp_x_antialiasing_size_x6 = exp_x_antialiasing_size_x3.square();
            exp_inv_x_antialiasing_size_x6 = exp_x_antialiasing_size_x6.reciprocal();

            MpfrBigNum temp = cos_y_antialiasing_size_x3.square();
            temp.mult2(temp);
            temp.sub(one, temp);
            cos_y_antialiasing_size_x6 = temp;

            temp = cos_y_antialiasing_size_x3.mult2();
            temp.mult(sin_y_antialiasing_size_x3, temp);
            sin_y_antialiasing_size_x6 = temp;

            sin_inv_y_antialiasing_size_x6 = sin_y_antialiasing_size_x6.negate();
            cos_inv_y_antialiasing_size_x6 = cos_y_antialiasing_size_x6;
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            exp_x_antialiasing_size_x7 = exp_x_antialiasing_size_x6.mult(exp_x_antialiasing_size);
            exp_inv_x_antialiasing_size_x7 = exp_x_antialiasing_size_x7.reciprocal();

            MpfrBigNum temp = cos_y_antialiasing_size_x2.add(cos_y_antialiasing_size_x6);

            MpfrBigNum temp2 = temp.sub(cos_y_antialiasing_size_x4);
            temp2.mult2(temp2);
            temp2.sub(one, temp2);
            temp2.mult(cos_y_antialiasing_size, temp2);
            cos_y_antialiasing_size_x7 = temp2;

            temp2 = temp.add(cos_y_antialiasing_size_x4);
            temp2.mult2(temp2);
            temp2.add(one, temp2);
            temp2.mult(sin_y_antialiasing_size, temp2);
            sin_y_antialiasing_size_x7 = temp2;

            sin_inv_y_antialiasing_size_x7 = sin_y_antialiasing_size_x7.negate();
            cos_inv_y_antialiasing_size_x7 = cos_y_antialiasing_size_x7;
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            exp_x_antialiasing_size_x8 = exp_x_antialiasing_size_x4.square();
            exp_inv_x_antialiasing_size_x8 = exp_x_antialiasing_size_x8.reciprocal();

            MpfrBigNum temp = cos_y_antialiasing_size_x4.square();
            temp.mult2(temp);
            temp.sub(one, temp);
            cos_y_antialiasing_size_x8 = temp;

            temp = cos_y_antialiasing_size_x4.mult2();
            temp.mult(sin_y_antialiasing_size_x4, temp);
            sin_y_antialiasing_size_x8 = temp;

            sin_inv_y_antialiasing_size_x8 = sin_y_antialiasing_size_x8.negate();
            cos_inv_y_antialiasing_size_x8 = cos_y_antialiasing_size_x8;
        }

        MpfrBigNum zero = new MpfrBigNum();

        MpfrBigNum[][] data;

        if(!adaptive) {

            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            data = new MpfrBigNum[][] {temp_x, temp_y_sin, temp_y_cos};
        }
        else {
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      one,                       one,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one

            };

            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    zero, zero, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };


            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    one, one, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size, one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size


            };

            data = new MpfrBigNum[][] {temp_x, temp_y_sin, temp_y_cos};
        }

        if(max_samples > MAX_AA_SAMPLES_24 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, one, one, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3,
                    exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3
            };
            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, zero, sin_y_antialiasing_size_x3,
                    sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x3, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size
            };
            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, one, cos_y_antialiasing_size_x3,
                    cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x3, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_48 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4,
                    exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4
            };

            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x4,
                    sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x4, sin_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size_x3
            };

            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x4,
                    cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x4, cos_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size_x3
            };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_80 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, };

            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, };

            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_120 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, };

            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, };

            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_168 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, };
            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, };
            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        if(max_samples > MAX_AA_SAMPLES_224 && !adaptive) {
            //Not implemented for adaptive
            MpfrBigNum[] temp_x = {exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x8, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x7, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x6, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x5, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x4, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x3, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, one, one, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x3, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x4, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x5, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x6, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x7, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, exp_x_antialiasing_size_x8, };
            MpfrBigNum[] temp_y_sin = {sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x8, sin_inv_y_antialiasing_size_x7, sin_inv_y_antialiasing_size_x6, sin_inv_y_antialiasing_size_x5, sin_inv_y_antialiasing_size_x4, sin_inv_y_antialiasing_size_x3, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, zero, sin_y_antialiasing_size, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x3, sin_y_antialiasing_size_x4, sin_y_antialiasing_size_x5, sin_y_antialiasing_size_x6, sin_y_antialiasing_size_x7, sin_y_antialiasing_size_x8, };
            MpfrBigNum[] temp_y_cos = {cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x8, cos_inv_y_antialiasing_size_x7, cos_inv_y_antialiasing_size_x6, cos_inv_y_antialiasing_size_x5, cos_inv_y_antialiasing_size_x4, cos_inv_y_antialiasing_size_x3, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, one, cos_y_antialiasing_size, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x3, cos_y_antialiasing_size_x4, cos_y_antialiasing_size_x5, cos_y_antialiasing_size_x6, cos_y_antialiasing_size_x7, cos_y_antialiasing_size_x8, };

            data[0] = merge(data[0], temp_x);
            data[1] = merge(data[1], temp_y_sin);
            data[2] = merge(data[2], temp_y_cos);
        }

        return data;

    }

    // http://www.burtleburtle.net/bob/hash/integer.html

    public static long wang_hash(long a)
    {
        a = (a ^ 61) ^ (a >>> 16);
        a = a & 0xFFFFFFFFL;
        a = a + (a << 3);
        a = a & 0xFFFFFFFFL;
        a = a ^ (a >>> 4);
        a = a & 0xFFFFFFFFL;
        a = a * 0x27d4eb2dL;
        a = a & 0xFFFFFFFFL;
        a = a ^ (a >>> 15);
        return a & 0xFFFFFFFFL;
    }

    public static long burtle_hash(long a)
    {
        a = (a+0x7ed55d16L) + (a<<12);
        a = a & 0xFFFFFFFFL;
        a = (a^0xc761c23cL) ^ (a>>>19);
        a = a & 0xFFFFFFFFL;
        a = (a+0x165667b1L) + (a<<5);
        a = a & 0xFFFFFFFFL;
        a = (a+0xd3a2646cL) ^ (a<<9);
        a = a & 0xFFFFFFFFL;
        a = (a+0xfd7046c5L) + (a<<3);
        a = a & 0xFFFFFFFFL;
        a = (a^0xb55a4f09L) ^ (a>>>16);
        return a & 0xFFFFFFFFL;
    }
    // uniform in [0,1)
    public static double dither(int x, int y, int c)
    {
        return burtle_hash(x + burtle_hash(y + burtle_hash(c))) / (double) (0x100000000L);
    }

    public static long hash( long x) {
        x = ((x >>> 16) ^ x) * 0x45d9f3bL;
        x = x & 0xFFFFFFFFL;
        x = ((x >>> 16) ^ x) * 0x45d9f3bL;
        x = x & 0xFFFFFFFFL;
        x = (x >>> 16) ^ x;
        x = x & 0xFFFFFFFFL;
        return x;
    }

    public static double[] GetPixelOffset(int i, int j, int jitterSeed, int jitterShape, double s) {
        int jitteroffset = jitterSeed << 1;
        double u = dither(i, j, jitteroffset);
        double v = dither(i, j, jitteroffset + 1);
        switch (jitterShape)
        {
            default:
            case 0: // uniform
                return new double[] {s * (u - 0.5), s * (v - 0.5)};//x, y
            case 1: // Gaussian
                // https://en.wikipedia.org/wiki/Box%E2%80%93Muller_transform
                double r = 0 < u && u < 1 ? Math.sqrt(-2 * Math.log(u)) : 0;
                double t = 2 * 3.141592653589793 * v;
                s *= 0.5;
                return new double[] {s * r * Math.cos(t), s * r * Math.sin(t)};//x, y
        }

    }

    /*public static void main(String[] args) {

        Location loc = new Location();

        double dsize = 0.5;
        double x_antialiasing_size = dsize * (1 / 8.0);
        double y_antialiasing_size = dsize * (1 / 8.0);
        double x_antialiasing_size_x2 = x_antialiasing_size * 2;
        double x_antialiasing_size_x3 = x_antialiasing_size * 3;
        double x_antialiasing_size_x4 = x_antialiasing_size * 4;

        double y_antialiasing_size_x2 = y_antialiasing_size * 2;
        double y_antialiasing_size_x3 = y_antialiasing_size * 3;
        double y_antialiasing_size_x4 = y_antialiasing_size * 4;
        double[][] res = loc.createAntialiasingStepsDoubleGrid(x_antialiasing_size, y_antialiasing_size, x_antialiasing_size_x2, y_antialiasing_size_x2, x_antialiasing_size_x3, y_antialiasing_size_x3, x_antialiasing_size_x4, y_antialiasing_size_x4, 80, false);

        int samples = 81;
        int[] X = new int[] {0, -1, 1, 1, -1};
        int[] Y = new int[] {0, -1, -1, 1, 1};

        List<Integer> Xlist = Arrays.stream(X).boxed().collect(Collectors.toList());
        List<Integer> Ylist = Arrays.stream(Y).boxed().collect(Collectors.toList());
        int size = 3;

        if (samples > 5) {
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(0);
            Xlist.add(0);

            Ylist.add(0);
            Ylist.add(0);
            Ylist.add(-1);
            Ylist.add(1);
            size = 3;
        }

        if(samples > 9) {
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(2);

            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            size = 5;
        }

        if(samples > 17) {
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(2);
            Xlist.add(2);

            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-1);
            Ylist.add(1);
            size = 5;
        }

        if(samples > 25) {
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(3);

            Ylist.add(-3);
            Ylist.add(0);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(0);
            Ylist.add(3);
            size = 7;
        }

        if(samples > 33) {
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(3);
            Xlist.add(3);

            Ylist.add(-2);
            Ylist.add(2);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-2);
            Ylist.add(2);
            size = 7;
        }

        if(samples > 41) {
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(3);
            Xlist.add(3);

            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-3);
            Ylist.add(3);
            Ylist.add(-1);
            Ylist.add(1);
            size = 7;
        }


        if(samples > 49) {
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-2);
            Xlist.add(-2);
            Xlist.add(0);
            Xlist.add(0);
            Xlist.add(2);
            Xlist.add(2);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);

            Ylist.add(-4);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(-2);
            Ylist.add(0);
            Ylist.add(2);
            Ylist.add(4);
            size = 9;
        }

        if(samples > 65) {
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-4);
            Xlist.add(-3);
            Xlist.add(-3);
            Xlist.add(-1);
            Xlist.add(-1);
            Xlist.add(1);
            Xlist.add(1);
            Xlist.add(3);
            Xlist.add(3);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);
            Xlist.add(4);

            Ylist.add(-3);
            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(3);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-4);
            Ylist.add(4);
            Ylist.add(-3);
            Ylist.add(-1);
            Ylist.add(1);
            Ylist.add(3);
            size = 9;
        }


        for(int i = 1; i < Xlist.size(); i++) {
            if(res[0][i - 1] != Xlist.get(i) * x_antialiasing_size) {
                System.out.println(i);
            }
        }

        for(int i = 1; i < Ylist.size(); i++) {
            if(res[1][i - 1] != Ylist.get(i) * y_antialiasing_size) {
                System.out.println(i);
            }
        }

    }*/

   /* public static void main(String[] args) {
        Location loc = new Location();

        MyApfloat.setPrecision(1200);

        int samples = 288;
        double dsize = 0.5;
        double x_antialiasing_size = dsize * (1 / 14.0);
        double y_antialiasing_size = dsize * (1 / 14.0);
        double[][] res = loc.createAntialiasingStepsDoubleGrid(x_antialiasing_size, y_antialiasing_size,false, samples);

        MpirBigNum ddx_antialiasing_size = new MpirBigNum(x_antialiasing_size);
        MpirBigNum ddy_antialiasing_size = new MpirBigNum(y_antialiasing_size);
        MpirBigNum[][] res2 = loc.createAntialiasingStepsMpirBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, false, samples);
        int len = 17;
        int[][] stencil = new int[len][len];

        for(int i = 0; i < res[0].length; i++) {
            if(Math.abs(res[0][i] - res2[0][i].doubleValue()) > 1e-10) {
                System.out.println(i);
            }
            if(Math.abs(res[1][i] - res2[1][i].doubleValue()) > 1e-10) {
                System.out.println(i);
            }
        }

        for(int i = 0; i < res[0].length; i++) {
            double x = res[0][i] / x_antialiasing_size;
            double y = res[1][i] / y_antialiasing_size;

            stencil[((int)x) + len/2][((int)y) + len/2] = i + 1;
        }

        for(int i = 0; i < stencil.length; i++) {
            for(int j = 0; j < stencil[i].length; j++) {
                System.out.print(String.format("%3d", stencil[i][j]) + " | ");
            }
            System.out.println();
        }

        double[][] res3 = loc.createAntialiasingPolarStepsDoubleGrid(x_antialiasing_size, y_antialiasing_size, false, samples);

        for(int i = 0; i < res[0].length; i++) {
            double expX = Math.exp(res[0][i]);
            double cosY = Math.cos(res[1][i]);
            double sinY = Math.sin(res[1][i]);

            if(Math.abs(expX - res3[0][i]) > 1e-10) {
                System.out.println(i);
            }

            if(Math.abs(sinY - res3[1][i]) > 1e-10) {
                System.out.println(i);
            }

            if(Math.abs(cosY - res3[2][i]) > 1e-10) {
                System.out.println(i);
            }
        }

    }*/

    /*public static void main(String[] args) {

        //x_antialiasing_size,  y_antialiasing_size, x_antialiasing_size_x2, y_antialiasing_size_x2
        String[] xs = new String[] {"zero", "ddx_antialiasing_size", "ddx_antialiasing_size_x2", "ddx_antialiasing_size_x3", "ddx_antialiasing_size_x4", "ddx_antialiasing_size_x5", "ddx_antialiasing_size_x6", "ddx_antialiasing_size_x7", "ddx_antialiasing_size_x8"};
        String[] ys = new String[] {"zero", "ddy_antialiasing_size", "ddy_antialiasing_size_x2", "ddy_antialiasing_size_x3", "ddy_antialiasing_size_x4", "ddy_antialiasing_size_x5", "ddy_antialiasing_size_x6", "ddy_antialiasing_size_x7", "ddy_antialiasing_size_x8"};

        int size = 7;
        System.out.print("{");
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    if(x < 0) {
                        //System.out.print("-" + xs[Math.abs(x)] + ", ");
                        System.out.print(xs[Math.abs(x)] + ".negate(), ");
                    }
                    else {
                        System.out.print(xs[Math.abs(x)] + ", ");
                    }

                }
            }
        }
        System.out.print("}");
        System.out.println();
        System.out.print("{");
        int count = 0;
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    if(y < 0) {
                        System.out.print(ys[Math.abs(y)] + ".negate(), ");
                        //System.out.print("-"+ys[Math.abs(y)] + ", ");
                    }
                    else {
                        System.out.print(ys[Math.abs(y)] + ", ");
                    }
                    count++;
                }
            }
        }
        System.out.print("}");

        System.out.println();
        System.out.println(count);
    }*/

    /*public static void main(String[] args) {

        int size = 8;
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    System.out.println("Xlist.add(" + x + ");");

                }
            }
        }


        System.out.println();
        System.out.println();

        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    System.out.println("Ylist.add(" + y + ");");
                }
            }
        }

        System.out.println();

    }*/

    /*public static void main(String[] args) {


        String[] xs = new String[] {"one", "exp_x_antialiasing_size", "exp_x_antialiasing_size_x2", "exp_x_antialiasing_size_x3", "exp_x_antialiasing_size_x4", "exp_x_antialiasing_size_x5", "exp_x_antialiasing_size_x6", "exp_x_antialiasing_size_x7", "exp_x_antialiasing_size_x8"};
        String[] sin_ys = new String[] {"zero", "sin_y_antialiasing_size", "sin_y_antialiasing_size_x2", "sin_y_antialiasing_size_x3", "sin_y_antialiasing_size_x4", "sin_y_antialiasing_size_x5", "sin_y_antialiasing_size_x6", "sin_y_antialiasing_size_x7", "sin_y_antialiasing_size_x8"};
        String[] cos_ys = new String[] {"one", "cos_y_antialiasing_size", "cos_y_antialiasing_size_x2", "cos_y_antialiasing_size_x3", "cos_y_antialiasing_size_x4", "cos_y_antialiasing_size_x5", "cos_y_antialiasing_size_x6", "cos_y_antialiasing_size_x7", "cos_y_antialiasing_size_x8"};

        int size = 8;
        System.out.print("{");
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    if(x < 0) {
                        String temp = xs[Math.abs(x)];
                        temp = temp.replace("exp", "exp_inv");
                        System.out.print(temp + ", ");
                    }
                    else {
                        System.out.print(xs[Math.abs(x)] + ", ");
                    }

                }
            }
        }
        System.out.print("}");
        System.out.println();
        System.out.print("{");
        int count = 0;
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    if(y < 0) {
                        String temp = sin_ys[Math.abs(y)];
                        temp = temp.replace("sin_y", "sin_inv_y");
                        System.out.print(temp + ", ");
                    }
                    else {
                        System.out.print(sin_ys[Math.abs(y)] + ", ");
                    }
                    count++;
                }
            }
        }
        System.out.print("}");


        System.out.println();
        System.out.print("{");
        for(int x = -size; x <= size; x++) {
            for(int y = -size; y <= size; y++) {
                if(Math.max(Math.abs(x), Math.abs(y)) == size) {
                    if(y < 0) {
                        String temp = cos_ys[Math.abs(y)];
                        temp = temp.replace("cos_y", "cos_inv_y");
                        System.out.print(temp + ", ");
                    }
                    else {
                        System.out.print(cos_ys[Math.abs(y)] + ", ");
                    }
                }
            }
        }
        System.out.print("}");

        System.out.println();
        System.out.println(count);
    }*/
}
