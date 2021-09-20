package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

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
            Apfloat size_2_x = size.multiply(point5);
            Apfloat ddimage_size = new MyApfloat(image_size);
            Apfloat ddheight_ratio = new MyApfloat(height_ratio);

            ddsize = size;

            Apfloat size_2_y = size.multiply(ddheight_ratio).multiply(point5);
            ddtemp_size_image_size_x = size.divide(ddimage_size);
            ddtemp_size_image_size_y = size.multiply(ddheight_ratio).divide(ddimage_size);

            ddtemp_xcenter_size = xCenter.subtract(size_2_x);
            ddtemp_ycenter_size =  yCenter.add(size_2_y);

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
    }

    public CartesianLocation(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size) {

        super();

        this.highPresicion = true;

        this.height_ratio = height_ratio;

        ddsize = size;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        Apfloat point5 = new MyApfloat(0.5);
        Apfloat size_2_x = size.multiply(point5);
        Apfloat ddimage_size = new MyApfloat(image_size);
        Apfloat ddiheight_ratio = new MyApfloat(height_ratio);

        Apfloat size_2_y = size.multiply(ddiheight_ratio).multiply(point5);
        ddtemp_size_image_size_x = size.divide(ddimage_size);
        ddtemp_size_image_size_y = size.multiply(ddiheight_ratio).divide(ddimage_size);

        ddtemp_xcenter_size = xCenter.subtract(size_2_x);
        ddtemp_ycenter_size =  yCenter.add(size_2_y);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        if(highPresicion) {

            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));

            BigComplex temp = new BigComplex(ddtempX, ddtempY);

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(Fractal.refPoint).toComplex();
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
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
        }

    }

    @Override
    public void precalculateX(int x) {

        if(highPresicion) {
            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
        }

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        if(highPresicion) {
            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
            BigComplex temp = new BigComplex(ddtempX, ddtempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(Fractal.refPoint).toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        if(highPresicion) {
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));
            BigComplex temp = new BigComplex(ddtempX, ddtempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(Fractal.refPoint).toComplex();
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public BigPoint getPoint(int x, int y) {
        return new BigPoint(ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x))), ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y))));
    }


    public BigPoint getDragPoint(int x, int y) {
        return new BigPoint(ddxcenter.subtract(ddtemp_size_image_size_x.multiply(new MyApfloat(x))), ddycenter.add(ddtemp_size_image_size_y.multiply(new MyApfloat(y))));
    }

    @Override
    public void createAntialiasingSteps() {
        if(highPresicion) {
            Apfloat point25 = new MyApfloat(0.25);
            Apfloat two = new MyApfloat(2.0);

            Apfloat ddx_antialiasing_size = ddtemp_size_image_size_x.multiply(point25);
            Apfloat ddx_antialiasing_size_x2 = ddx_antialiasing_size.multiply(two);

            Apfloat ddy_antialiasing_size = ddtemp_size_image_size_y.multiply(point25);
            Apfloat ddy_antialiasing_size_x2 = ddy_antialiasing_size.multiply(two);

            Apfloat zero = new MyApfloat(0.0);

            Apfloat temp_x[] = {ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size.negate(),
                    ddx_antialiasing_size.negate(), ddx_antialiasing_size, zero, zero,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), zero, zero, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2,
                    ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size_x2.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size.negate(), ddx_antialiasing_size, ddx_antialiasing_size, ddx_antialiasing_size_x2, ddx_antialiasing_size_x2};
            Apfloat temp_y[] = {ddy_antialiasing_size.negate(), ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size,
                    zero, zero, ddy_antialiasing_size.negate(), ddy_antialiasing_size,
                    ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), zero, ddy_antialiasing_size_x2,
                    ddy_antialiasing_size.negate(), ddy_antialiasing_size, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size_x2.negate(), ddy_antialiasing_size_x2, ddy_antialiasing_size.negate(), ddy_antialiasing_size};

            ddantialiasing_x = temp_x;
            ddantialiasing_y = temp_y;

        }
        else {

            double x_antialiasing_size = temp_size_image_size_x * 0.25;
            double x_antialiasing_size_x2 = 2 * x_antialiasing_size;

            double y_antialiasing_size = temp_size_image_size_y * 0.25;
            double y_antialiasing_size_x2 = 2 * y_antialiasing_size;

            double temp_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
                    -x_antialiasing_size, x_antialiasing_size, 0, 0,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
                    -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
            double temp_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
                    0, 0, -y_antialiasing_size, y_antialiasing_size,
                    -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
                    -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

            antialiasing_x = temp_x;
            antialiasing_y = temp_y;
        }
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        if(highPresicion) {
            BigComplex temp = new BigComplex(ddtempX.add(ddantialiasing_x[sample]), ddtempY.add(ddantialiasing_y[sample]));

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(Fractal.refPoint).toComplex();
        }
        else {
            return new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
        }
    }

    public Complex getComplexOrbit(int x, int y) {
        if(highPresicion) {

            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));

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
    public MantExp getSeriesApproxSize() {
        if(highPresicion) {
            if(height_ratio == 1) {
                return new MantExp(ddsize.multiply(new MyApfloat(0.525)).multiply(MyApfloat.SQRT_TWO));
            }
            else {
                Apfloat temp = ddsize.multiply(new MyApfloat(0.525));
                Apfloat temp2 = temp.multiply(new MyApfloat(height_ratio));
                return new MantExp(ApfloatMath.sqrt(temp.multiply(temp).add(temp2.multiply(temp2))));
            }
        }
        else {
            if(height_ratio == 1) {
                double sqrt2 = Math.sqrt(2);
                return new MantExp(sqrt2 * size * 0.525); //0.5 is ok but lets add 5%
            }
            else {
                double temp = size * 0.525; //0.5 is ok but lets add 5%
                double temp2 = temp * height_ratio;
                return new MantExp(Math.sqrt(temp * temp + temp2 * temp2));
            }
        }
    }

}
