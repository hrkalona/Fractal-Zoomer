package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

import java.util.HashMap;

public class Location {

    protected Apfloat ddxcenter;
    protected Apfloat ddycenter;
    protected double xcenter;
    protected double ycenter;

    protected double[] antialiasing_x;

    protected Apfloat[] ddantialiasing_x;

    protected boolean highPresicion;

    protected Fractal fractal;

    protected GenericComplex reference;

    public boolean isPolar() {return false;}


    public BigPoint getPoint(int x, int y) {return  null;}
    public GenericComplex getComplex(int x, int y) {return  null;}
    public void precalculateY(int y) {}
    public GenericComplex getComplexWithX(int x) {return  null;}
    public void precalculateX(int x) {}
    public GenericComplex getComplexWithY(int y) {return  null;}
    public void createAntialiasingSteps(boolean adaptive) {}
    public GenericComplex getAntialiasingComplex(int sample) {return  null;}
    public Complex getComplexOrbit(int x, int y) {return  null;}

    public void setReference(GenericComplex ref) {reference = ref instanceof BigComplex ? ref : new BigNumComplex((BigNumComplex)ref); }

    public static Location getInstanceForDrawing(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, boolean polar, boolean highPresicion) {

        /*if(polar) {
            return new PolarLocationArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal);
        }
        else {
            if(fractal.supportsBignum() && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                return new CartesianLocationBigNumArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
            }
            else {
                return new CartesianLocationArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
            }
        }*/

        if(polar) {
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                return new PolarLocationDeep(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal);
            }
            return new PolarLocation(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, highPresicion);
        }
        else {
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                if(fractal.supportsBignum() && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
                    return new CartesianLocationDeepBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
                }
                else {
                    return new CartesianLocationDeep(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
                }
            }

           if(fractal.supportsBignum() && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && ThreadDraw.USE_BIGNUM_FOR_PIXELS_IF_POSSIBLE) {
               return new CartesianLocationBigNum(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, highPresicion);
           } else {
                return new CartesianLocation(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, highPresicion);
            }
        }

    }

    public static Location getCopy(Location loc) {
        if(loc instanceof CartesianLocation) {
            return new CartesianLocation((CartesianLocation)loc);
        }
        else if(loc instanceof PolarLocation) {
            return new PolarLocation((PolarLocation)loc);
        }
        else if(loc instanceof CartesianLocationDeep) {
            return new CartesianLocationDeep((CartesianLocationDeep)loc);
        }
        else if(loc instanceof PolarLocationDeep) {
            return new PolarLocationDeep((PolarLocationDeep)loc);
        }
        else if(loc instanceof CartesianLocationArbitrary) {
            return new CartesianLocationArbitrary((CartesianLocationArbitrary)loc);
        }
        else if(loc instanceof PolarLocationArbitrary) {
            return new PolarLocationArbitrary((PolarLocationArbitrary)loc);
        }
        else if(loc instanceof CartesianLocationBigNum) {
            return new CartesianLocationBigNum((CartesianLocationBigNum)loc);
        }
        else if(loc instanceof CartesianLocationDeepBigNum) {
            return new CartesianLocationDeepBigNum((CartesianLocationDeepBigNum)loc);
        }
        else if(loc instanceof CartesianLocationBigNumArbitrary) {
            return new CartesianLocationBigNumArbitrary((CartesianLocationBigNumArbitrary)loc);
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
            return new MantExpComplex(cn.getRe().getMantExp(), cn.getIm().getMantExp());
        }
        else {
            BigComplex cn = (BigComplex)c;
            return new MantExpComplex(getMantExp(cn.getRe()), getMantExp(cn.getIm()));
        }
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

            BigNum temp_x[] = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            BigNum temp_y[] = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new BigNum[][] {temp_x, temp_y};
        }
        else {

            BigNum temp_x[] = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            BigNum temp_y[] = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            return new BigNum[][] {temp_x, temp_y};
        }
    }
    public double[][] createAntialiasingStepsDouble(double temp_size_image_size_x, double temp_size_image_size_y, boolean adaptive) {
        double x_antialiasing_size = temp_size_image_size_x * 0.25;
        double x_antialiasing_size_x2 = 2 * x_antialiasing_size;

        double y_antialiasing_size = temp_size_image_size_y * 0.25;
        double y_antialiasing_size_x2 = 2 * y_antialiasing_size;

        if(!adaptive) {
            double temp_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
            double temp_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
                    0, 0, -y_antialiasing_size, y_antialiasing_size,
                    -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
                    -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

            return new double[][] {temp_x, temp_y};
        }
        else {
            double temp_x[] = {-x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2, -x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, x_antialiasing_size_x2, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2, -x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, 0, 0

            };


            double temp_y[] = {-y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, y_antialiasing_size_x2,
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
            Apfloat temp_x[] = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            Apfloat temp_y[] = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            return new Apfloat[][] {temp_x, temp_y};
        } else {
            Apfloat temp_x[] = {ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2.negate(),
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size,
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero

            };


            Apfloat temp_y[] = {ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    zero, zero, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size, zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size


            };

            return new Apfloat[][] {temp_x, temp_y};
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
            double temp_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            double temp_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            double temp_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            return new double[][] {temp_x, temp_y_sin, temp_y_cos};

        }
        else {
            double temp_x[] = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      1,                       1,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1

            };

            double temp_y_sin[] = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    0, 0, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, 0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };

            double temp_y_cos[] = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
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
        Apfloat cos_y_antialiasing_size_x2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(MyApfloat.TWO, cos_y_antialiasing_size), cos_y_antialiasing_size), one);

        Apfloat sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        Apfloat cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        Apfloat zero = MyApfloat.ZERO;

        if(!adaptive) {

            Apfloat temp_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};


            Apfloat temp_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                    zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                    sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

            Apfloat temp_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                    one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                    cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


            return new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
        }
        else {
            Apfloat temp_x[] = {exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2,
                    exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,                      one,                       one,
                    exp_inv_x_antialiasing_size_x2,exp_inv_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,  exp_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size,
                    exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one

            };

            Apfloat temp_y_sin[] = {sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    zero, zero, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_y_antialiasing_size_x2,
                    sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size, zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size

            };


            Apfloat temp_y_cos[] = {cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    one, one, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_y_antialiasing_size_x2,
                    cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size, one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size


            };

            return new Apfloat[][] {temp_x, temp_y_sin, temp_y_cos};
        }

    }
}
