package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.core.antialiasing.GaussianAntialiasingAlgorithm;
import fractalzoomer.core.location.delta.*;
import fractalzoomer.core.location.normal.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
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

    public static int NUMBER_OF_AA_JITTER_KERNELS = 30;
    private static double[][] aaJitterKernelX;
    private static double[][] aaJitterKernelY;

    protected double[][][] precalculatedJitterDataDouble;
    protected double[][][] precalculatedJitterDataPolarDouble;
    protected Apfloat[][][] precalculatedJitterDataApfloat;

    protected Apfloat[][][] precalculatedJitterDataPolarApfloat;
    protected MpfrBigNum[][][] precalculatedJitterDataMpfrBigNum;
    protected MpfrBigNum[][][] precalculatedJitterDataPolarMpfrBigNum;
    protected MpirBigNum[][][] precalculatedJitterDataMpirBigNum;
    protected DoubleDouble[][][] precalculatedJitterDataDoubleDouble;
    protected DoubleDouble[][][] precalculatedJitterDataPolarDoubleDouble;

    protected BigNum[][][] precalculatedJitterDataBigNum;

    private static final int MAX_AA_SAMPLES = 24;
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

        aaJitterKernelX = new double[NUMBER_OF_AA_JITTER_KERNELS][MAX_AA_SAMPLES];
        aaJitterKernelY = new double[NUMBER_OF_AA_JITTER_KERNELS][MAX_AA_SAMPLES];

        for(int k = 0; k < NUMBER_OF_AA_JITTER_KERNELS; k++) {
            for (int i = 0; i < MAX_AA_SAMPLES; i++) {
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
        precalculatedJitterDataPolarDouble = other.precalculatedJitterDataPolarDouble;
        precalculatedJitterDataPolarApfloat = other.precalculatedJitterDataPolarApfloat;
        precalculatedJitterDataPolarDoubleDouble = other.precalculatedJitterDataPolarDoubleDouble;
        precalculatedJitterDataPolarMpfrBigNum = other.precalculatedJitterDataPolarMpfrBigNum;
    }

    public boolean isPolar() {return false;}


    //public BigPoint getPoint(int x, int y) {return  null;}
    public GenericComplex getComplex(int x, int y) {return  null;}
    public void precalculateY(int y) {}
    public GenericComplex getComplexWithX(int x) {return  null;}
    public void precalculateX(int x) {}
    public GenericComplex getComplexWithY(int y) {return  null;}
    public void createAntialiasingSteps(boolean adaptive, boolean jitter) {
        this.aaJitter = jitter;
    }
    public GenericComplex getAntialiasingComplex(int sample, int loc) {return  null;}
    public void setReference(GenericComplex ref) {reference = ref; }

    public static Location getInstanceForDrawing(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js, boolean polar, boolean highPresicion) {


        if(highPresicion && ThreadDraw.HIGH_PRECISION_CALCULATION) {
            int lib = ThreadDraw.getHighPrecisionLibrary(size, fractal);
            if(polar) {
                if(lib == Constants.ARBITRARY_MPFR || lib == Constants.ARBITRARY_MPIR) {
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
                else if(lib == Constants.ARBITRARY_DOUBLEDOUBLE) {
                    return new CartesianLocationNormalDoubleDoubleArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
            }
        }

        int bignumLib = ThreadDraw.getBignumLibrary(size, fractal);
        boolean isDeep = ThreadDraw.useExtendedRange(size, fractal);

        if(polar) {
            if(highPresicion && isDeep) {
                if((bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_MPIR)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    return new PolarLocationDeltaDeepMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new PolarLocationDeltaDeepApfloat(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
            }
            else if(highPresicion) {

                if((bignumLib == Constants.BIGNUM_MPIR || bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_DOUBLE || bignumLib == Constants.BIGNUM_DOUBLEDOUBLE)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if (bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_MPIR) {
                        return new PolarLocationDeltaMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                    }
                    else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                        return new PolarLocationDeltaDoubleDouble(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                    }
                    else {
                        return new PolarLocationDeltaDouble(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                    }
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
                if((bignumLib == Constants.BIGNUM_BUILT_IN || bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_MPIR)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                        return new CartesianLocationDeltaDeepBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else if(bignumLib == Constants.BIGNUM_MPIR) {
                        return new CartesianLocationDeltaDeepMpirBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else {
                        return new CartesianLocationDeltaDeepMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                }
                else {
                    return new CartesianLocationDeltaDeepApfloat(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                }
            }
            else if(highPresicion) {
                if((bignumLib == Constants.BIGNUM_BUILT_IN || bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_MPIR || bignumLib == Constants.BIGNUM_DOUBLE || bignumLib == Constants.BIGNUM_DOUBLEDOUBLE)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                        return new CartesianLocationDeltaBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else if (bignumLib == Constants.BIGNUM_MPFR){
                        return new CartesianLocationDeltaMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else if (bignumLib == Constants.BIGNUM_MPIR){
                        return new CartesianLocationDeltaMpirBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE){
                        return new CartesianLocationDeltaDoubleDouble(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else {
                        return new CartesianLocationDeltaDouble(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                } else {
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
            mantissa = 0.0;
            exponent = MantExp.MIN_BIG_EXPONENT;
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

        return new MantExp(mantissa, exponent);
    }

    public MantExpComplex getMantExpComplex(BigComplex c) {
        return new MantExpComplex(getMantExp(c.getRe()), getMantExp(c.getIm()));
    }

    public MantExpComplex getMantExpComplex(GenericComplex c) {

        if(c instanceof MpirBigNumComplex) {
            MpirBigNumComplex cn = (MpirBigNumComplex)c;
            return new MantExpComplex(cn);
        }
        else if(c instanceof MpfrBigNumComplex) {
            MpfrBigNumComplex cn = (MpfrBigNumComplex)c;
            return new MantExpComplex(cn);
        }
        else if(c instanceof BigNumComplex) {
            BigNumComplex cn = (BigNumComplex)c;
            return new MantExpComplex(cn);
        }
        else if (c instanceof BigComplex){
            BigComplex cn = (BigComplex)c;
            return new MantExpComplex(getMantExp(cn.getRe()), getMantExp(cn.getIm()));
        }
        else if (c instanceof DDComplex){
            DDComplex cn = (DDComplex)c;
            return new MantExpComplex(cn);
        }
        else {
            return new MantExpComplex((Complex) c);
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
                precalculatedJitterDataBigNum[k][0][i] = temp_x[i].add(ddx_antialiasing_size.mult(new BigNum(aaJitterKernelX[k][i])));
                precalculatedJitterDataBigNum[k][1][i] = temp_y[i].add(ddy_antialiasing_size.mult(new BigNum(aaJitterKernelY[k][i])));
            }
        }

    }

    public BigNum[][] createAntialiasingStepsBigNum(BigNum bntemp_size_image_size_x, BigNum bntemp_size_image_size_y, boolean adaptive, boolean jitter) {
        BigNum point25 = new BigNum(0.25);

        BigNum ddx_antialiasing_size = bntemp_size_image_size_x.mult(point25);
        BigNum ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();

        BigNum ddy_antialiasing_size = bntemp_size_image_size_y.mult(point25);
        BigNum ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        BigNum[][] steps = createAntialiasingStepsBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, ddx_antialiasing_size_x2, ddy_antialiasing_size_x2, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingStepsBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public BigNum[][] createAntialiasingStepsBigNumGrid(BigNum ddx_antialiasing_size, BigNum ddy_antialiasing_size, BigNum ddx_antialiasing_size_x2, BigNum ddy_antialiasing_size_x2, boolean adaptive) {

        BigNum zero = new BigNum();

        if(!adaptive) {

            BigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            BigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new BigNum[][] {temp_x, temp_y};
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

            return new BigNum[][] {temp_x, temp_y};
        }
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

    public DoubleDouble[][] createAntialiasingStepsDoubleDouble(DoubleDouble bntemp_size_image_size_x, DoubleDouble bntemp_size_image_size_y, boolean adaptive, boolean jitter) {
        DoubleDouble point25 = new DoubleDouble(0.25);

        DoubleDouble ddx_antialiasing_size = bntemp_size_image_size_x.multiply(point25);
        DoubleDouble ddx_antialiasing_size_x2 = ddx_antialiasing_size.multiply(2);

        DoubleDouble ddy_antialiasing_size = bntemp_size_image_size_y.multiply(point25);
        DoubleDouble ddy_antialiasing_size_x2 = ddy_antialiasing_size.multiply(2);

        DoubleDouble[][] steps = createAntialiasingStepsDoubleDoubleGrid(ddx_antialiasing_size, ddy_antialiasing_size, ddx_antialiasing_size_x2, ddy_antialiasing_size_x2, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingStepsDoubleDouble(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public DoubleDouble[][] createAntialiasingStepsDoubleDoubleGrid(DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size, DoubleDouble ddx_antialiasing_size_x2, DoubleDouble ddy_antialiasing_size_x2, boolean adaptive) {

        DoubleDouble zero = new DoubleDouble();

        if(!adaptive) {

            DoubleDouble[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            DoubleDouble[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new DoubleDouble[][] {temp_x, temp_y};
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

            return new DoubleDouble[][] {temp_x, temp_y};
        }
    }

    public double[] getGaussianCoefficients(int samples) {

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

        X = Xlist.stream().mapToInt(i -> i).toArray();
        Y = Ylist.stream().mapToInt(i -> i).toArray();

        return GaussianAntialiasingAlgorithm.createGaussianKernel(size, X, Y);
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

    public double[][] createAntialiasingStepsDouble(double temp_size_image_size_x, double temp_size_image_size_y, boolean adaptive, boolean jitter) {
        double x_antialiasing_size = temp_size_image_size_x * 0.25;
        double x_antialiasing_size_x2 = 2 * x_antialiasing_size;

        double y_antialiasing_size = temp_size_image_size_y * 0.25;
        double y_antialiasing_size_x2 = 2 * y_antialiasing_size;

        double[][] steps = createAntialiasingStepsDoubleGrid(x_antialiasing_size,  y_antialiasing_size, x_antialiasing_size_x2, y_antialiasing_size_x2, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingStepsDouble(steps, x_antialiasing_size, y_antialiasing_size);
        }

        return steps;
    }

    public double[][] createAntialiasingStepsDoubleGrid(double x_antialiasing_size, double y_antialiasing_size, double x_antialiasing_size_x2, double y_antialiasing_size_x2, boolean adaptive) {

        if(!adaptive) {
            double[] temp_x = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
            double[] temp_y = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
                    0, 0, -y_antialiasing_size, y_antialiasing_size,
                    -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
                    -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

            return new double[][] {temp_x, temp_y};
        }
        else {
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

            return new double[][] {temp_x, temp_y};
        }
    }

    public Apfloat[][] createAntialiasingStepsApfloat(Apfloat ddtemp_size_image_size_x, Apfloat ddtemp_size_image_size_y, boolean adaptive, boolean jitter) {
        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, point25);
        Apfloat ddx_antialiasing_size_x2 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.TWO);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, point25);
        Apfloat ddy_antialiasing_size_x2 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.TWO);

        Apfloat[][] steps = createAntialiasingStepsApfloatGrid(ddx_antialiasing_size,  ddy_antialiasing_size, ddx_antialiasing_size_x2, ddy_antialiasing_size_x2, adaptive);

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

    public Apfloat[][] createAntialiasingStepsApfloatGrid(Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size, Apfloat ddx_antialiasing_size_x2, Apfloat ddy_antialiasing_size_x2, boolean adaptive) {

        Apfloat zero = MyApfloat.ZERO;

        if (!adaptive) {
            Apfloat[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            Apfloat[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new Apfloat[][] {temp_x, temp_y};
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

            return new Apfloat[][] {temp_x, temp_y};
        }
    }


    public MpfrBigNum[][] createAntialiasingStepsMpfrBigNum(MpfrBigNum ddtemp_size_image_size_x, MpfrBigNum ddtemp_size_image_size_y, boolean adaptive, boolean jitter) {

        MpfrBigNum ddx_antialiasing_size = ddtemp_size_image_size_x.divide4();
        MpfrBigNum ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();

        MpfrBigNum ddy_antialiasing_size = ddtemp_size_image_size_y.divide4();
        MpfrBigNum ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        MpfrBigNum[][] steps = createAntialiasingStepsMpfrBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, ddx_antialiasing_size_x2, ddy_antialiasing_size_x2, adaptive);

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

    public MpfrBigNum[][] createAntialiasingStepsMpfrBigNumGrid(MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size, MpfrBigNum ddx_antialiasing_size_x2, MpfrBigNum ddy_antialiasing_size_x2, boolean adaptive) {

        MpfrBigNum zero = new MpfrBigNum();

        if (!adaptive) {
            MpfrBigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            MpfrBigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new MpfrBigNum[][] {temp_x, temp_y};
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

            return new MpfrBigNum[][] {temp_x, temp_y};
        }
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

    public MpirBigNum[][] createAntialiasingStepsMpirBigNum(MpirBigNum ddtemp_size_image_size_x, MpirBigNum ddtemp_size_image_size_y, boolean adaptive, boolean jitter) {

        MpirBigNum ddx_antialiasing_size = ddtemp_size_image_size_x.divide4();
        MpirBigNum ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();

        MpirBigNum ddy_antialiasing_size = ddtemp_size_image_size_y.divide4();
        MpirBigNum ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

        MpirBigNum[][] steps = createAntialiasingStepsMpirBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, ddx_antialiasing_size_x2, ddy_antialiasing_size_x2, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingStepsMpirBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public MpirBigNum[][] createAntialiasingStepsMpirBigNumGrid(MpirBigNum ddx_antialiasing_size, MpirBigNum ddy_antialiasing_size, MpirBigNum ddx_antialiasing_size_x2, MpirBigNum ddy_antialiasing_size_x2, boolean adaptive) {


        MpirBigNum zero = new MpirBigNum();

        if (!adaptive) {
            MpirBigNum[] temp_x = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            MpirBigNum[] temp_y = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new MpirBigNum[][] {temp_x, temp_y};
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

            return new MpirBigNum[][] {temp_x, temp_y};
        }
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

    public double[][] createAntialiasingPolarStepsDouble(double mulx, double muly, boolean adaptive, boolean jitter) {
        double y_antialiasing_size = muly * 0.25;
        double x_antialiasing_size = mulx * 0.25;

        double[][] steps = createAntialiasingPolarStepsDoubleGrid(x_antialiasing_size, y_antialiasing_size, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsDouble(steps, x_antialiasing_size, y_antialiasing_size);
        }

        return steps;
    }

    public double[][] createAntialiasingPolarStepsDoubleGrid(double x_antialiasing_size, double y_antialiasing_size, boolean adaptive) {

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

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


            return new double[][] {temp_x, temp_y_sin, temp_y_cos};

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


            return new double[][] {temp_x, temp_y_sin, temp_y_cos};
        }
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

    public Apfloat[][] createAntialiasingPolarStepsApfloat(Apfloat ddmulx, Apfloat ddmuly, boolean adaptive, boolean jitter) {

        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddmuly, point25);
        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddmulx, point25);

        Apfloat[][] steps = createAntialiasingPolarStepsApfloatGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsApfloat(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public Apfloat[][] createAntialiasingPolarStepsApfloatGrid(Apfloat ddx_antialiasing_size, Apfloat ddy_antialiasing_size, boolean adaptive) {

        Apfloat exp_x_antialiasing_size = MyApfloat.exp(ddx_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size = MyApfloat.reciprocal(exp_x_antialiasing_size);

        Apfloat exp_x_antialiasing_size_x2 = MyApfloat.fp.multiply(exp_x_antialiasing_size, exp_x_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size_x2 = MyApfloat.reciprocal(exp_x_antialiasing_size_x2);

        Apfloat one = MyApfloat.ONE;

        Apfloat sin_y_antialiasing_size = MyApfloat.sin(ddy_antialiasing_size);
        Apfloat cos_y_antialiasing_size = MyApfloat.cos(ddy_antialiasing_size);

        Apfloat sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        Apfloat cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        Apfloat sin_y_antialiasing_size_x2 = MyApfloat.fp.multiply(MyApfloat.fp.multiply(MyApfloat.TWO, sin_y_antialiasing_size), cos_y_antialiasing_size);
        Apfloat cos_y_antialiasing_size_x2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(cos_y_antialiasing_size, cos_y_antialiasing_size), MyApfloat.TWO), one);

        Apfloat sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        Apfloat cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        Apfloat zero = MyApfloat.ZERO;

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


            return new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
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

            return new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
        }

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

    public DoubleDouble[][] createAntialiasingPolarStepsDoubleDouble(DoubleDouble ddmulx, DoubleDouble ddmuly, boolean adaptive, boolean jitter) {

        DoubleDouble point25 = new DoubleDouble(0.25);

        DoubleDouble ddy_antialiasing_size = ddmuly.multiply(point25);
        DoubleDouble ddx_antialiasing_size = ddmulx.multiply(point25);

        DoubleDouble[][] steps = createAntialiasingPolarStepsDoubleDoubleGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsDoubleDouble(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public DoubleDouble[][] createAntialiasingPolarStepsDoubleDoubleGrid(DoubleDouble ddx_antialiasing_size, DoubleDouble ddy_antialiasing_size, boolean adaptive) {

        DoubleDouble exp_x_antialiasing_size = ddx_antialiasing_size.exp();
        DoubleDouble exp_inv_x_antialiasing_size = exp_x_antialiasing_size.reciprocal();

        DoubleDouble exp_x_antialiasing_size_x2 = exp_x_antialiasing_size.sqr();
        DoubleDouble exp_inv_x_antialiasing_size_x2 = exp_x_antialiasing_size_x2.reciprocal();

        DoubleDouble one = new DoubleDouble(1.0);

        DoubleDouble sin_y_antialiasing_size = ddy_antialiasing_size.sin();
        DoubleDouble cos_y_antialiasing_size = ddy_antialiasing_size.cos();

        DoubleDouble sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        DoubleDouble cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        DoubleDouble sin_y_antialiasing_size_x2 = sin_y_antialiasing_size.multiply(cos_y_antialiasing_size).multiply(2);
        DoubleDouble cos_y_antialiasing_size_x2 = cos_y_antialiasing_size.sqr().multiply(2).subtract(one);

        DoubleDouble sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        DoubleDouble cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        DoubleDouble zero = new DoubleDouble();

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


            return new DoubleDouble[][] {temp_x, temp_y_sin, temp_y_cos};
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

            return new DoubleDouble[][] {temp_x, temp_y_sin, temp_y_cos};
        }

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

    public MpfrBigNum[][] createAntialiasingPolarStepsMpfrBigNum(MpfrBigNum ddmulx, MpfrBigNum ddmuly, boolean adaptive, boolean jitter) {

        MpfrBigNum ddy_antialiasing_size = ddmuly.divide4();
        MpfrBigNum ddx_antialiasing_size = ddmulx.divide4();

        MpfrBigNum[][] steps = createAntialiasingPolarStepsMpfrBigNumGrid(ddx_antialiasing_size, ddy_antialiasing_size, adaptive);

        if(jitter) {
            precalculateJitterToAntialiasingPolarStepsMpfrBigNum(steps, ddx_antialiasing_size, ddy_antialiasing_size);
        }

        return steps;
    }

    public MpfrBigNum[][] createAntialiasingPolarStepsMpfrBigNumGrid(MpfrBigNum ddx_antialiasing_size, MpfrBigNum ddy_antialiasing_size, boolean adaptive) {

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

        MpfrBigNum sin_y_antialiasing_size_x2 = sin_y_antialiasing_size.mult2();
        sin_y_antialiasing_size_x2.mult(cos_y_antialiasing_size, sin_y_antialiasing_size_x2);

        MpfrBigNum cos_y_antialiasing_size_x2 = cos_y_antialiasing_size.square();
        cos_y_antialiasing_size_x2.mult2(cos_y_antialiasing_size_x2);
        cos_y_antialiasing_size_x2.sub(1, cos_y_antialiasing_size_x2);


        MpfrBigNum sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        MpfrBigNum cos_inv_y_antialiasing_size_x2 = new MpfrBigNum(cos_y_antialiasing_size_x2);

        MpfrBigNum zero = new MpfrBigNum();

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


            return new MpfrBigNum[][] {temp_x, temp_y_sin, temp_y_cos};
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

            return new MpfrBigNum[][] {temp_x, temp_y_sin, temp_y_cos};
        }

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

    protected double[] GetPixelOffset(int i, int j, int jitterSeed, int jitterShape, double s) {
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
}
