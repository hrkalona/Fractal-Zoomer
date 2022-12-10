package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.core.location.delta.*;
import fractalzoomer.core.location.normal.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import fractalzoomer.utils.PixelOffset;
import org.apfloat.Apfloat;

import java.util.HashMap;

public class Location {

    protected Fractal fractal;

    protected GenericComplex reference;

    protected int indexX;
    protected int indexY;

    public static PixelOffset offset;

    static {
        offset = new PixelOffset();
    }

    public Location() {
        indexX = Integer.MIN_VALUE;
        indexY = Integer.MIN_VALUE;
    }

    public boolean isPolar() {return false;}


    //public BigPoint getPoint(int x, int y) {return  null;}
    public GenericComplex getComplex(int x, int y) {return  null;}
    public void precalculateY(int y) {}
    public GenericComplex getComplexWithX(int x) {return  null;}
    public void precalculateX(int x) {}
    public GenericComplex getComplexWithY(int y) {return  null;}
    public void createAntialiasingSteps(boolean adaptive) {}
    public GenericComplex getAntialiasingComplex(int sample) {return  null;}
    public void setReference(GenericComplex ref) {reference = ref; }

    public static Location getInstanceForDrawing(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js, boolean polar, boolean highPresicion) {

        /*if(polar) {
            return new PolarLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal);
        }
        else {
            if(fractal.supportsBignum() && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                return new CartesianLocationNormalBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
            }
            else {
                return new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
            }
        }*/

        int bignumLib = ThreadDraw.getBignumLibrary(size, fractal);

        if(polar) {
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                if(bignumLib == Constants.BIGNUM_MPFR
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    return new PolarLocationDeltaDeepMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
                else {
                    return new PolarLocationDeltaDeepApfloat(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);
                }
            }
            else if(highPresicion) {

                if((bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_DOUBLE || bignumLib == Constants.BIGNUM_DOUBLEDOUBLE)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if (bignumLib == Constants.BIGNUM_MPFR) {
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
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                if((bignumLib == Constants.BIGNUM_BUILT_IN || bignumLib == Constants.BIGNUM_MPFR)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                        return new CartesianLocationDeltaDeepBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
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
                if((bignumLib == Constants.BIGNUM_BUILT_IN || bignumLib == Constants.BIGNUM_MPFR || bignumLib == Constants.BIGNUM_DOUBLE || bignumLib == Constants.BIGNUM_DOUBLEDOUBLE)
                        && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    if(bignumLib == Constants.BIGNUM_BUILT_IN) {
                        return new CartesianLocationDeltaBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
                    }
                    else if (bignumLib == Constants.BIGNUM_MPFR){
                        return new CartesianLocationDeltaMpfrBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
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
        if(c instanceof BigNumComplex) {
            BigNumComplex cn = (BigNumComplex)c;
            return new MantExpComplex(cn);
        }
        else if(c instanceof MpfrBigNumComplex) {
            MpfrBigNumComplex cn = (MpfrBigNumComplex)c;
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


    public BigNum[][] createAntialiasingStepsBigNum(BigNum bntemp_size_image_size_x, BigNum bntemp_size_image_size_y, boolean adaptive) {
        BigNum point25 = new BigNum(0.25);

        BigNum ddx_antialiasing_size = bntemp_size_image_size_x.mult(point25);
        BigNum ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();

        BigNum ddy_antialiasing_size = bntemp_size_image_size_y.mult(point25);
        BigNum ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

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

    public DoubleDouble[][] createAntialiasingStepsDoubleDouble(DoubleDouble bntemp_size_image_size_x, DoubleDouble bntemp_size_image_size_y, boolean adaptive) {
        DoubleDouble point25 = new DoubleDouble(0.25);

        DoubleDouble ddx_antialiasing_size = bntemp_size_image_size_x.multiply(point25);
        DoubleDouble ddx_antialiasing_size_x2 = ddx_antialiasing_size.multiply(2);

        DoubleDouble ddy_antialiasing_size = bntemp_size_image_size_y.multiply(point25);
        DoubleDouble ddy_antialiasing_size_x2 = ddy_antialiasing_size.multiply(2);

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

    public double[][] createAntialiasingStepsDouble(double temp_size_image_size_x, double temp_size_image_size_y, boolean adaptive) {
        double x_antialiasing_size = temp_size_image_size_x * 0.25;
        double x_antialiasing_size_x2 = 2 * x_antialiasing_size;

        double y_antialiasing_size = temp_size_image_size_y * 0.25;
        double y_antialiasing_size_x2 = 2 * y_antialiasing_size;

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

    public Apfloat[][] createAntialiasingStepsApfloat(Apfloat ddtemp_size_image_size_x, Apfloat ddtemp_size_image_size_y, boolean adaptive) {
        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, point25);
        Apfloat ddx_antialiasing_size_x2 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.TWO);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, point25);
        Apfloat ddy_antialiasing_size_x2 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.TWO);

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

    public MpfrBigNum[][] createAntialiasingStepsMpfrBigNum(MpfrBigNum ddtemp_size_image_size_x, MpfrBigNum ddtemp_size_image_size_y, boolean adaptive) {

        MpfrBigNum ddx_antialiasing_size = ddtemp_size_image_size_x.divide4();
        MpfrBigNum ddx_antialiasing_size_x2 = ddx_antialiasing_size.mult2();

        MpfrBigNum ddy_antialiasing_size = ddtemp_size_image_size_y.divide4();
        MpfrBigNum ddy_antialiasing_size_x2 = ddy_antialiasing_size.mult2();

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

    public double[][] createAntialiasingPolarStepsDouble(double mulx, double muly, boolean adaptive) {

        double y_antialiasing_size = muly * 0.25;
        double x_antialiasing_size = mulx * 0.25;

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

    public Apfloat[][] createAntialiasingPolarStepsApfloat(Apfloat ddmulx, Apfloat ddmuly, boolean adaptive) {

        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddmuly, point25);
        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddmulx, point25);

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

    public DoubleDouble[][] createAntialiasingPolarStepsDoubleDouble(DoubleDouble ddmulx, DoubleDouble ddmuly, boolean adaptive) {

        DoubleDouble point25 = new DoubleDouble(0.25);

        DoubleDouble ddy_antialiasing_size = ddmuly.multiply(point25);
        DoubleDouble ddx_antialiasing_size = ddmulx.multiply(point25);

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

    public MpfrBigNum[][] createAntialiasingPolarStepsMpfrBigNum(MpfrBigNum ddmulx, MpfrBigNum ddmuly, boolean adaptive) {

        MpfrBigNum ddy_antialiasing_size = ddmuly.divide4();
        MpfrBigNum ddx_antialiasing_size = ddmulx.divide4();

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

    public static int wang_hash(int a)
    {
        a = (a ^ 61) ^ (a >>> 16);
        a = a + (a << 3);
        a = a ^ (a >>> 4);
        a = a * 0x27d4eb2d;
        a = a ^ (a >>> 15);
        return a;
    }

    public static int burtle_hash(int a)
    {
        a = (a+0x7ed55d16) + (a<<12);
        a = (a^0xc761c23c) ^ (a>>>19);
        a = (a+0x165667b1) + (a<<5);
        a = (a+0xd3a2646c) ^ (a<<9);
        a = (a+0xfd7046c5) + (a<<3);
        a = (a^0xb55a4f09) ^ (a>>>16);
        return a;
    }
    // uniform in [0,1)
    public static double dither(int x, int y, int c)
    {
        return burtle_hash(x + burtle_hash(y + burtle_hash(c))) / (double) (0x100000000L);
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
