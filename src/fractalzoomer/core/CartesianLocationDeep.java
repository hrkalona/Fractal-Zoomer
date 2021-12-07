package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class CartesianLocationDeep extends Location {

    private Apfloat ddtemp_size_image_size_x;
    private Apfloat ddtemp_size_image_size_y;
    private Apfloat ddtemp_xcenter_size;
    private Apfloat ddtemp_ycenter_size;

    private BigComplex rotation;
    private BigComplex rot_center;

    private Apfloat[] ddantialiasing_y;

    //Dont copy those
    private Apfloat ddtempX;
    private Apfloat ddtempY;

    private Apfloat ddsize;

    private Apfloat height_ratio;

    public CartesianLocationDeep(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal) {

        super();

        this.highPresicion = true;

        this.fractal = fractal;

        ddsize = size;

        ddxcenter = xCenter;
        ddycenter = yCenter;


        Apfloat point5 = new MyApfloat(0.5);
        Apfloat size_2_x = MyApfloat.fp.multiply(size, point5);
        Apfloat ddimage_size = new MyApfloat(image_size);
        this.height_ratio = new MyApfloat(height_ratio);

        Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, this.height_ratio), point5);
        ddtemp_size_image_size_x = MyApfloat.fp.divide(size, ddimage_size);
        ddtemp_size_image_size_y = MyApfloat.fp.divide(MyApfloat.fp.multiply(size, this.height_ratio), ddimage_size);

        ddtemp_xcenter_size = MyApfloat.fp.subtract(xCenter, size_2_x);
        ddtemp_ycenter_size =  MyApfloat.fp.add(yCenter, size_2_y);

        rotation = new BigComplex(rotation_vals[0], rotation_vals[1]);
        rot_center = new BigComplex(rotation_center[0], rotation_center[1]);


    }

    public CartesianLocationDeep(CartesianLocationDeep other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        ddsize = other.ddsize;

        height_ratio = other.height_ratio;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        ddtemp_size_image_size_x = other.ddtemp_size_image_size_x;
        ddtemp_size_image_size_y = other.ddtemp_size_image_size_y;
        ddtemp_xcenter_size = other.ddtemp_xcenter_size;
        ddtemp_ycenter_size = other.ddtemp_ycenter_size;

        rotation = other.rotation;
        rot_center = other.rot_center;

        ddantialiasing_y = other.ddantialiasing_y;
        ddantialiasing_x = other.ddantialiasing_x;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {

        ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));

        BigComplex temp = new BigComplex(ddtempX, ddtempY);

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public void precalculateY(int y) {

        ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));

    }

    @Override
    public void precalculateX(int x) {

        ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public BigPoint getPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }


    public BigPoint getDragPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.subtract(ddxcenter, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }

    @Override
    public void createAntialiasingSteps() {
        Apfloat point25 = new MyApfloat(0.25);

        Apfloat ddx_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_x, point25);
        Apfloat ddx_antialiasing_size_x2 = MyApfloat.fp.multiply(ddx_antialiasing_size, MyApfloat.TWO);

        Apfloat ddy_antialiasing_size = MyApfloat.fp.multiply(ddtemp_size_image_size_y, point25);
        Apfloat ddy_antialiasing_size_x2 = MyApfloat.fp.multiply(ddy_antialiasing_size, MyApfloat.TWO);

        Apfloat zero = MyApfloat.ZERO;

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

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddtempX, ddantialiasing_x[sample]), MyApfloat.fp.add(ddtempY, ddantialiasing_y[sample]));

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    public Complex getComplexOrbit(int x, int y) {
        ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));

        BigComplex temp = new BigComplex(ddtempX, ddtempY);

        return temp.toComplex();
    }

    @Override
    public MantExp getSeriesApproxSize() {
        if(height_ratio.compareTo(Apfloat.ONE) == 0) {
            return new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddsize, new MyApfloat(0.525)), MyApfloat.SQRT_TWO)); //0.5 is ok but lets add 5%
        }
        else {
            Apfloat temp = MyApfloat.fp.multiply(ddsize, new MyApfloat(0.525)); //0.5 is ok but lets add 5%
            Apfloat temp2 = MyApfloat.fp.multiply(temp, height_ratio);
            return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2))));
        }
    }

}
