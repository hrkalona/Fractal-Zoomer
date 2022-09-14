package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class PolarLocation extends Location {
    private double muly;
    private double mulx;
    private double start;

    private double xcenter;
    private double ycenter;
    private BigComplex rotation;
    private BigComplex rot_center;

    private int image_size;

    private Apfloat ddmuly;
    private Apfloat ddmulx;
    private Apfloat ddstart;
    private Apfloat ddcenter;

    private double center;

    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;
    private Apfloat[] ddantialiasing_y_sin;
    private Apfloat[] ddantialiasing_y_cos;


    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private double temp_r;

    private Apfloat temp_ddsf;
    private Apfloat temp_ddcf;
    private Apfloat temp_ddr;

    public PolarLocation(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, boolean highPresicion) {

        super();

        this.highPresicion = highPresicion;
        this.fractal = fractal;
        this.image_size = image_size;

        if(highPresicion) {
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
        else {
            xcenter = xCenter.doubleValue();
            ycenter = yCenter.doubleValue();
            double dsize = size.doubleValue();

            center = Math.log(dsize);

            muly = (2 * circle_period * Math.PI) / image_size;

            mulx = muly * height_ratio;

            start = center - mulx * image_size * 0.5;
        }

    }

    public PolarLocation(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period) {

        super();

        this.highPresicion = true;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        this.image_size = image_size;

        ddcenter = MyApfloat.fp.log(size);

        Apfloat ddimage_size = new MyApfloat(image_size);

        ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

        ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(height_ratio));

        ddstart = MyApfloat.fp.subtract(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, ddimage_size), new MyApfloat(0.5)));

    }

    public PolarLocation(PolarLocation other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        xcenter = other.xcenter;
        ycenter = other.ycenter;

        mulx = other.mulx;
        muly = other.muly;
        start = other.start;

        ddcenter = other.ddcenter;
        center = other.center;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        ddmulx = other.ddmulx;
        ddmuly = other.ddmuly;
        ddstart = other.ddstart;

        image_size = other.image_size;

        rotation = other.rotation;
        rot_center = other.rot_center;

        antialiasing_y_cos = other.antialiasing_y_cos;
        antialiasing_y_sin = other.antialiasing_y_sin;
        ddantialiasing_y_cos = other.ddantialiasing_y_cos;
        ddantialiasing_y_sin = other.ddantialiasing_y_sin;
        antialiasing_x = other.antialiasing_x;
        ddantialiasing_x = other.ddantialiasing_x;

        reference = other.reference;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        if(highPresicion) {
            Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
            temp_ddsf = MyApfloat.fastSin(f);
            temp_ddcf = MyApfloat.fastCos(f);

            temp_ddr = MyApfloat.fastExp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));

            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            double f = y * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);

            temp_r = Math.exp(x * mulx + start);

            return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
        }
    }

    @Override
    public void precalculateY(int y) {

        if(highPresicion) {
            Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
            temp_ddsf = MyApfloat.fastSin(f);
            temp_ddcf = MyApfloat.fastCos(f);
        }
        else {
            double f = y * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }

    }

    @Override
    public void precalculateX(int x) {

        if(highPresicion) {
            temp_ddr = MyApfloat.fastExp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));
        }
        else {
            temp_r = Math.exp(x * mulx + start);
        }

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        if(highPresicion) {
            temp_ddr = MyApfloat.fastExp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));

            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            temp_r = Math.exp(x * mulx + start);
            return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
        }
    }

    @Override
    public boolean isPolar() {return true;}

    @Override
    public GenericComplex getComplexWithY(int y) {
        if(highPresicion) {
            Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
            temp_ddsf = MyApfloat.fastSin(f);
            temp_ddcf = MyApfloat.fastCos(f);

            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            double f = y * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);

            return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
        }
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
        if(highPresicion) {
            Apfloat[][] steps = createAntialiasingPolarStepsApfloat(ddmulx, ddmuly, adaptive);
            ddantialiasing_x = steps[0];
            ddantialiasing_y_sin = steps[1];
            ddantialiasing_y_cos = steps[2];
        }
        else {
            double[][] steps = createAntialiasingPolarStepsDouble(mulx, muly, adaptive);
            antialiasing_x = steps[0];
            antialiasing_y_sin = steps[1];
            antialiasing_y_cos = steps[2];
        }
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        if(highPresicion) {
            Apfloat sf2 = MyApfloat.fp.add(MyApfloat.fp.multiply(temp_ddsf, ddantialiasing_y_cos[sample]), MyApfloat.fp.multiply(temp_ddcf, ddantialiasing_y_sin[sample]));
            Apfloat cf2 = MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp_ddcf, ddantialiasing_y_cos[sample]), MyApfloat.fp.multiply(temp_ddsf, ddantialiasing_y_sin[sample]));

            Apfloat r2 = MyApfloat.fp.multiply(temp_ddr, ddantialiasing_x[sample]);

            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(r2, cf2)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(r2, sf2)));

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            double sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            double cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            double r2 = temp_r * antialiasing_x[sample];
            return new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2);
        }
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        if(highPresicion) {
            Apfloat f = MyApfloat.fp.multiply(ddmuly, new MyApfloat(y));
            temp_ddsf = MyApfloat.fastSin(f);
            temp_ddcf = MyApfloat.fastCos(f);

            temp_ddr = MyApfloat.fastExp(MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, new MyApfloat(x)), ddstart));

            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));

            return temp.toComplex();
        }
        else {
            double f = y * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);

            temp_r = Math.exp(x * mulx + start);

            return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
        }
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(highPresicion) {
            Apfloat end = MyApfloat.fp.add(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, new MyApfloat(image_size)), new MyApfloat(0.5)));
            return new MantExp(MyApfloat.fastExp(end));
        }
        else {
            double end = center + mulx * image_size * 0.5;
            return new MantExp(Math.exp(end));
        }
    }
}
