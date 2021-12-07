package fractalzoomer.core;

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


    public BigPoint getPoint(int x, int y) {return  null;}
    public GenericComplex getComplex(int x, int y) {return  null;}
    public void precalculateY(int y) {}
    public GenericComplex getComplexWithX(int x) {return  null;}
    public void precalculateX(int x) {}
    public GenericComplex getComplexWithY(int y) {return  null;}
    public void createAntialiasingSteps() {}
    public GenericComplex getAntialiasingComplex(int sample) {return  null;}
    public Complex getComplexOrbit(int x, int y) {return  null;}

    public static Location getInstanceForDrawing(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, boolean polar, boolean highPresicion) {

        /*if(polar) {
            return new PolarLocationArbitrary(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal);
        }
        else {
            return new CartesianLocationArbitrary(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
        }*/

        if(polar) {
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                return new PolarLocationDeep(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal);
            }
            return new PolarLocation(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, highPresicion);
        }
        else {
            if(highPresicion && size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0) {
                return new CartesianLocationDeep(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal);
            }
            return new CartesianLocation(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, highPresicion);
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
        return null;
    }

    private HashMap<Long, Apfloat> twoToExpReciprocals = new HashMap<>();

    protected MantExp getMantExp(Apfloat c) {
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

    public MantExp getSeriesApproxSize() {
        return null;
    }

}
