package fractalzoomer.core.location;

import fractalzoomer.core.*;
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

        ddcenter = MyApfloat.fp.log(size);

        Apfloat ddimage_size = new MyApfloat(image_size);

        ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

        ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(height_ratio));

        ddstart = MyApfloat.fp.subtract(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, ddimage_size), new MyApfloat(0.5)));

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

        reference = other.reference;
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

        temp =  temp.sub(reference);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
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
        temp = temp.sub(reference);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
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
        temp = temp.sub(reference);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
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
    public void createAntialiasingSteps(boolean adaptive) {
        Apfloat[][] steps = createAntialiasingPolarStepsApfloat(ddmulx, ddmuly, adaptive);
        ddantialiasing_x = steps[0];
        ddantialiasing_y_sin = steps[1];
        ddantialiasing_y_cos = steps[2];
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

        temp = temp.sub(reference);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public boolean isPolar() {return true;}

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
    public MantExp getMaxSizeInImage() {
        Apfloat end = MyApfloat.fp.add(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, new MyApfloat(image_size)), new MyApfloat(0.5)));
        return new MantExp(MyApfloat.exp(end, expIterations));
    }
}
