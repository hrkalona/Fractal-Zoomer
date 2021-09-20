package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

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
        Apfloat size_2_x = size.multiply(point5);
        Apfloat ddimage_size = new MyApfloat(image_size);
        this.height_ratio = new MyApfloat(height_ratio);

        Apfloat size_2_y = size.multiply(this.height_ratio).multiply(point5);
        ddtemp_size_image_size_x = size.divide(ddimage_size);
        ddtemp_size_image_size_y = size.multiply(this.height_ratio).divide(ddimage_size);

        ddtemp_xcenter_size = xCenter.subtract(size_2_x);
        ddtemp_ycenter_size =  yCenter.add(size_2_y);

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

        ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
        ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));

        BigComplex temp = new BigComplex(ddtempX, ddtempY);

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public void precalculateY(int y) {

        ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));

    }

    @Override
    public void precalculateX(int x) {

        ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));
        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        temp = temp.sub(Fractal.refPoint);
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
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

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        BigComplex temp = new BigComplex(ddtempX.add(ddantialiasing_x[sample]), ddtempY.add(ddantialiasing_y[sample]));

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        temp = temp.sub(Fractal.refPoint);

        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    public Complex getComplexOrbit(int x, int y) {
        ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new MyApfloat(x)));
        ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new MyApfloat(y)));

        BigComplex temp = new BigComplex(ddtempX, ddtempY);

        return temp.toComplex();
    }

    @Override
    public MantExp getSeriesApproxSize() {
        if(height_ratio.compareTo(Apfloat.ONE) == 0) {
            return new MantExp(ddsize.multiply(new MyApfloat(0.525)).multiply(MyApfloat.SQRT_TWO)); //0.5 is ok but lets add 5%
        }
        else {
            Apfloat temp = ddsize.multiply(new MyApfloat(0.525)); //0.5 is ok but lets add 5%
            Apfloat temp2 = temp.multiply(height_ratio);
            return new MantExp(ApfloatMath.sqrt(temp.multiply(temp).add(temp2.multiply(temp2))));
        }
    }

}
