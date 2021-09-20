package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class PolarLocationDeep extends Location {
    private BigComplex rotation;
    private BigComplex rot_center;

    private Apfloat ddmuly;
    private Apfloat ddmulx;
    private Apfloat ddstart;
    private Apfloat ddcenter;
    private int image_size;

    private Apfloat[] ddantialiasing_y_sin;
    private Apfloat[] ddantialiasing_y_cos;


    //Dont copy those

    private Apfloat temp_ddsf;
    private Apfloat temp_ddcf;
    private Apfloat temp_ddr;
    private static int expIterations = 8;

    public PolarLocationDeep(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal) {

        super();

        this.highPresicion = true;
        this.fractal = fractal;
        this.image_size = image_size;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        ddcenter = MyApfloat.log(size);

        Apfloat ddimage_size = new MyApfloat(image_size);

        ddmuly = new MyApfloat(circle_period).multiply(new MyApfloat(2.0)).multiply(MyApfloat.getPi()).divide(ddimage_size);

        ddmulx = ddmuly.multiply(new MyApfloat(height_ratio));

        ddstart = ddcenter.subtract(ddmulx.multiply(ddimage_size).multiply(new MyApfloat(0.5)));

        rotation = new BigComplex(rotation_vals[0], rotation_vals[1]);
        rot_center = new BigComplex(rotation_center[0], rotation_center[1]);

    }

    public PolarLocationDeep(PolarLocationDeep other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        ddmulx = other.ddmulx;
        ddmuly = other.ddmuly;
        ddstart = other.ddstart;
        ddcenter = other.ddcenter;
        image_size = other.image_size;

        rotation = other.rotation;
        rot_center = other.rot_center;

        ddantialiasing_y_cos = other.ddantialiasing_y_cos;
        ddantialiasing_y_sin = other.ddantialiasing_y_sin;
        ddantialiasing_x = other.ddantialiasing_x;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        Apfloat f = ddmuly.multiply(new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        temp_ddr = MyApfloat.exp(ddmulx.multiply(new MyApfloat(x)).add(ddstart), expIterations);

        BigComplex temp = new BigComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        temp =  temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public void precalculateY(int y) {

        Apfloat f = ddmuly.multiply(new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

    }

    @Override
    public void precalculateX(int x) {

        temp_ddr = MyApfloat.exp(ddmulx.multiply(new MyApfloat(x)).add(ddstart), expIterations);

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        temp_ddr = MyApfloat.exp(ddmulx.multiply(new MyApfloat(x)).add(ddstart), expIterations);

        BigComplex temp = new BigComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        Apfloat f = ddmuly.multiply(new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        BigComplex temp = new BigComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public BigPoint getPoint(int x, int y) {
        Apfloat f = ddmuly.multiply(new MyApfloat(y));
        Apfloat sf = MyApfloat.sin(f);
        Apfloat cf = MyApfloat.cos(f);

        Apfloat r = MyApfloat.exp(ddmulx.multiply(new MyApfloat(x)).add(ddstart));

        return new BigPoint(ddxcenter.add(r.multiply(cf)), ddycenter.add(r.multiply(sf)));
    }

    @Override
    public void createAntialiasingSteps() {
        Apfloat point25 = new MyApfloat(0.25);
        Apfloat two = new MyApfloat(2.0);

        Apfloat ddy_antialiasing_size = ddmuly.multiply(point25);
        Apfloat ddx_antialiasing_size = ddmulx.multiply(point25);

        Apfloat exp_x_antialiasing_size = MyApfloat.exp(ddx_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size = MyApfloat.reciprocal(exp_x_antialiasing_size);

        Apfloat exp_x_antialiasing_size_x2 = exp_x_antialiasing_size.multiply(exp_x_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size_x2 = MyApfloat.reciprocal(exp_x_antialiasing_size_x2);

        Apfloat one = new MyApfloat(1.0);

        Apfloat temp_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};

        ddantialiasing_x = temp_x;

        Apfloat sin_y_antialiasing_size = MyApfloat.sin(ddy_antialiasing_size);
        Apfloat cos_y_antialiasing_size = MyApfloat.cos(ddy_antialiasing_size);

        Apfloat sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        Apfloat cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        Apfloat sin_y_antialiasing_size_x2 = two.multiply(sin_y_antialiasing_size).multiply(cos_y_antialiasing_size);
        Apfloat cos_y_antialiasing_size_x2 = two.multiply(cos_y_antialiasing_size).multiply(cos_y_antialiasing_size).subtract(one);

        Apfloat sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        Apfloat cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        Apfloat zero = new MyApfloat(0.0);

        Apfloat temp_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
                zero, zero, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
                sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, zero, sin_y_antialiasing_size_x2,
                sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

        Apfloat temp_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
                one, one, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
                cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, one, cos_y_antialiasing_size_x2,
                cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};


        ddantialiasing_y_sin = temp_y_sin;
        ddantialiasing_y_cos = temp_y_cos;
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        Apfloat sf2 = temp_ddsf.multiply(ddantialiasing_y_cos[sample]).add(temp_ddcf.multiply(ddantialiasing_y_sin[sample]));
        Apfloat cf2 = temp_ddcf.multiply(ddantialiasing_y_cos[sample]).subtract(temp_ddsf.multiply(ddantialiasing_y_sin[sample]));

        Apfloat r2 = temp_ddr.multiply(ddantialiasing_x[sample]);

        BigComplex temp = new BigComplex(ddxcenter.add(r2.multiply(cf2)), ddycenter.add(r2.multiply(sf2)));

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);

        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        Apfloat f = ddmuly.multiply(new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        temp_ddr = MyApfloat.exp(ddmulx.multiply(new MyApfloat(x)).add(ddstart));

        BigComplex temp = new BigComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));

        return temp.toComplex();
    }

    @Override
    public MantExp getSeriesApproxSize() {
        Apfloat end = ddcenter.add(ddmulx.multiply(new MyApfloat(image_size)).multiply(new MyApfloat(0.55))); // add 10% extra
        return new MantExp(MyApfloat.exp(end, expIterations));
    }
}
