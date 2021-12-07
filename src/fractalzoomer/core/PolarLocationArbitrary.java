package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class PolarLocationArbitrary extends Location {

    private BigComplex rotation;
    private BigComplex rot_center;

    private int image_size;

    private Apfloat ddmuly;
    private Apfloat ddmulx;
    private Apfloat ddstart;
    private Apfloat ddcenter;

    private Apfloat[] ddantialiasing_y_sin;
    private Apfloat[] ddantialiasing_y_cos;


    //Dont copy those

    private Apfloat temp_ddsf;
    private Apfloat temp_ddcf;
    private Apfloat temp_ddr;

    private static int expIterations = 8;

    public PolarLocationArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal) {

        super();

        this.highPresicion = true;
        this.fractal = fractal;
        this.image_size = image_size;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        ddcenter = MyApfloat.log(size);

        Apfloat ddimage_size = new MyApfloat(image_size);

        ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

        ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(height_ratio));

        ddstart = MyApfloat.fp.subtract(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, ddimage_size), new MyApfloat(0.5)));

        rotation = new BigComplex(rotation_vals[0], rotation_vals[1]);
        rot_center = new BigComplex(rotation_center[0], rotation_center[1]);

    }

    public PolarLocationArbitrary(PolarLocationArbitrary other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        xcenter = other.xcenter;
        ycenter = other.ycenter;

        ddcenter = other.ddcenter;

        ddmulx = other.ddmulx;
        ddmuly = other.ddmuly;
        ddstart = other.ddstart;

        image_size = other.image_size;

        rotation = other.rotation;
        rot_center = other.rot_center;

        ddantialiasing_y_cos = other.ddantialiasing_y_cos;
        ddantialiasing_y_sin = other.ddantialiasing_y_sin;
        ddantialiasing_x = other.ddantialiasing_x;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        temp_ddr = MyApfloat.exp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart), expIterations);

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

    }

    @Override
    public void precalculateX(int x) {

        temp_ddr = MyApfloat.exp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart), expIterations);

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        temp_ddr = MyApfloat.exp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart), expIterations);

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public BigPoint getPoint(int x, int y) {
        Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
        Apfloat sf = MyApfloat.sin(f);
        Apfloat cf = MyApfloat.cos(f);

        Apfloat r = MyApfloat.exp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));

        return new BigPoint(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(r, cf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(r, sf)));
    }

    @Override
    public void createAntialiasingSteps() {

        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddmuly, point25);
        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddmulx, point25);

        Apfloat exp_x_antialiasing_size = MyApfloat.exp(ddx_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size = MyApfloat.reciprocal(exp_x_antialiasing_size);

        Apfloat exp_x_antialiasing_size_x2 = MyApfloat.fp.multiply(exp_x_antialiasing_size, exp_x_antialiasing_size);
        Apfloat exp_inv_x_antialiasing_size_x2 = MyApfloat.reciprocal(exp_x_antialiasing_size_x2);

        Apfloat one = MyApfloat.ONE;

        Apfloat temp_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
                exp_inv_x_antialiasing_size, exp_x_antialiasing_size, one, one,
                exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, one, one, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
                exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};

        ddantialiasing_x = temp_x;

        Apfloat sin_y_antialiasing_size = MyApfloat.sin(ddy_antialiasing_size);
        Apfloat cos_y_antialiasing_size = MyApfloat.cos(ddy_antialiasing_size);

        Apfloat sin_inv_y_antialiasing_size = sin_y_antialiasing_size.negate();
        Apfloat cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        Apfloat sin_y_antialiasing_size_x2 = MyApfloat.fp.multiply(MyApfloat.fp.multiply(MyApfloat.TWO, sin_y_antialiasing_size), cos_y_antialiasing_size);
        Apfloat cos_y_antialiasing_size_x2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(MyApfloat.TWO, cos_y_antialiasing_size), cos_y_antialiasing_size), one);

        Apfloat sin_inv_y_antialiasing_size_x2 = sin_y_antialiasing_size_x2.negate();
        Apfloat cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        Apfloat zero = MyApfloat.ZERO;

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
        Apfloat sf2 = MyApfloat.fp.add(MyApfloat.fp.multiply(temp_ddsf, ddantialiasing_y_cos[sample]), MyApfloat.fp.multiply(temp_ddcf, ddantialiasing_y_sin[sample]));
        Apfloat cf2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp_ddcf, ddantialiasing_y_cos[sample]), MyApfloat.fp.multiply(temp_ddsf, ddantialiasing_y_sin[sample]));

        Apfloat r2 = MyApfloat.fp.multiply(temp_ddr, ddantialiasing_x[sample]);

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(r2, cf2)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(r2, sf2)));

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
        temp_ddsf = MyApfloat.fastSin(f);
        temp_ddcf = MyApfloat.fastCos(f);

        temp_ddr = MyApfloat.exp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));

        return temp.toComplex();
    }

    @Override
    public MantExp getSeriesApproxSize() {
        Apfloat end = MyApfloat.fp.add(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, new MyApfloat(image_size)), new MyApfloat(0.55))); // add 10% extra
        return new MantExp(MyApfloat.exp(end, expIterations));
    }
}
