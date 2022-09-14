package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class CartesianLocation extends Location {
    private double temp_size_image_size_x;
    private double temp_size_image_size_y;
    private double temp_xcenter_size;
    private double temp_ycenter_size;

    private Apfloat ddtemp_size_image_size_x;
    private Apfloat ddtemp_size_image_size_y;
    private Apfloat ddtemp_xcenter_size;
    private Apfloat ddtemp_ycenter_size;

    private BigComplex rotation;
    private BigComplex rot_center;

    private double[] antialiasing_y;
    private Apfloat[] ddantialiasing_y;

    //Dont copy those
    private double tempY;
    private double tempX;
    private Apfloat ddtempX;
    private Apfloat ddtempY;

    private Apfloat ddsize;
    private double size;

    private double height_ratio;

    public CartesianLocation(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, boolean highPresicion) {

        super();

        this.highPresicion = highPresicion;
        this.fractal = fractal;

        this.height_ratio = height_ratio;

        if(highPresicion) {
            ddxcenter = xCenter;
            ddycenter = yCenter;

            Apfloat point5 = new MyApfloat(0.5);
            Apfloat size_2_x = MyApfloat.fp.multiply(size, point5);
            Apfloat ddimage_size = new MyApfloat(image_size);
            Apfloat ddheight_ratio = new MyApfloat(height_ratio);

            ddsize = size;

            Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, ddheight_ratio), point5);
            ddtemp_size_image_size_x = MyApfloat.fp.divide(size, ddimage_size);
            ddtemp_size_image_size_y = MyApfloat.fp.divide(MyApfloat.fp.multiply(size, ddheight_ratio), ddimage_size);

            ddtemp_xcenter_size = MyApfloat.fp.subtract(xCenter, size_2_x);
            ddtemp_ycenter_size =  MyApfloat.fp.add(yCenter, size_2_y);

            rotation = new BigComplex(rotation_vals[0], rotation_vals[1]);
            rot_center = new BigComplex(rotation_center[0], rotation_center[1]);
        }
        else {
            xcenter = xCenter.doubleValue();
            ycenter = yCenter.doubleValue();


            double dsize = size.doubleValue();

            this.size = dsize;

            double size_2_x = dsize * 0.5;
            double size_2_y = (dsize * height_ratio) * 0.5;
            temp_size_image_size_x = dsize / image_size;
            temp_size_image_size_y = (dsize * height_ratio) / image_size;

            temp_xcenter_size = xcenter - size_2_x;
            temp_ycenter_size = ycenter + size_2_y;
        }

    }

    public CartesianLocation(CartesianLocation other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        xcenter = other.xcenter;
        ycenter = other.ycenter;
        temp_size_image_size_x = other.temp_size_image_size_x;
        temp_size_image_size_y = other.temp_size_image_size_y;
        temp_xcenter_size = other.temp_xcenter_size;
        temp_ycenter_size = other.temp_ycenter_size;

        ddsize = other.ddsize;
        size = other.size;
        height_ratio = other.height_ratio;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        ddtemp_size_image_size_x = other.ddtemp_size_image_size_x;
        ddtemp_size_image_size_y = other.ddtemp_size_image_size_y;
        ddtemp_xcenter_size = other.ddtemp_xcenter_size;
        ddtemp_ycenter_size = other.ddtemp_ycenter_size;

        rotation = other.rotation;
        rot_center = other.rot_center;

        antialiasing_y = other.antialiasing_y;
        ddantialiasing_y = other.ddantialiasing_y;
        antialiasing_x = other.antialiasing_x;
        ddantialiasing_x = other.ddantialiasing_x;

        reference = other.reference;
    }

    public CartesianLocation(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size) {

        super();

        this.highPresicion = true;

        this.height_ratio = height_ratio;

        ddsize = size;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        Apfloat point5 = new MyApfloat(0.5);
        Apfloat size_2_x = MyApfloat.fp.multiply(size, point5);
        Apfloat ddimage_size = new MyApfloat(image_size);
        Apfloat ddiheight_ratio = new MyApfloat(height_ratio);

        Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, ddiheight_ratio), point5);
        ddtemp_size_image_size_x = MyApfloat.fp.divide(size, ddimage_size);
        ddtemp_size_image_size_y = MyApfloat.fp.divide(MyApfloat.fp.multiply(size, ddiheight_ratio), ddimage_size);

        ddtemp_xcenter_size = MyApfloat.fp.subtract(xCenter, size_2_x);
        ddtemp_ycenter_size =  MyApfloat.fp.add(yCenter, size_2_y);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        if(highPresicion) {

            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));

            BigComplex temp = new BigComplex(ddtempX, ddtempY);

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public void precalculateY(int y) {

        if(highPresicion) {
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
        }

    }

    @Override
    public void precalculateX(int x) {

        if(highPresicion) {
            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
        }

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        if(highPresicion) {
            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
            BigComplex temp = new BigComplex(ddtempX, ddtempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        if(highPresicion) {
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
            BigComplex temp = new BigComplex(ddtempX, ddtempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public BigPoint getPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }


    public BigPoint getDragPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.subtract(ddxcenter, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        if(highPresicion) {
            Apfloat[][] steps = createAntialiasingStepsApfloat(ddtemp_size_image_size_x, ddtemp_size_image_size_y, adaptive);
            ddantialiasing_x = steps[0];
            ddantialiasing_y = steps[1];
        }
        else {
            double[][] steps = createAntialiasingStepsDouble(temp_size_image_size_x, temp_size_image_size_y, adaptive);
            antialiasing_x = steps[0];
            antialiasing_y = steps[1];
        }
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        if(highPresicion) {
            BigComplex temp = new BigComplex(MyApfloat.fp.add(ddtempX, ddantialiasing_x[sample]), MyApfloat.fp.add(ddtempY, ddantialiasing_y[sample]));

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            return new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
        }
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        if(highPresicion) {

            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));

            BigComplex temp = new BigComplex(ddtempX, ddtempY);

            return temp.toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(highPresicion) {
            if(height_ratio == 1) {
                return new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5)), MyApfloat.SQRT_TWO));
            }
            else {
                Apfloat temp = MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5));
                Apfloat temp2 = MyApfloat.fp.multiply(temp, new MyApfloat(height_ratio));
                return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2))));
            }
        }
        else {
            if(height_ratio == 1) { // ((size * 0.5) / image_size) * sqrt(image_size^2 + image_size^2) = ((size * 0.5) / image_size) * sqrt(2) * image_size
                double sqrt2 = Math.sqrt(2);
                return new MantExp(sqrt2 * size * 0.5);
            }
            else {
                double temp = size * 0.5;
                double temp2 = temp * height_ratio;
                return new MantExp(Math.sqrt(temp * temp + temp2 * temp2));
            }
        }
    }

}
