package fractalzoomer.core.location.normal;

import fractalzoomer.core.DDComplex;
import fractalzoomer.core.DoubleDouble;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalDoubleDoubleArbitrary extends Location {
    protected DoubleDouble ddxcenter;
    protected DoubleDouble ddycenter;
    private DoubleDouble ddtemp_size_image_size_x;
    private DoubleDouble ddtemp_size_image_size_y;
    private DoubleDouble ddtemp_xcenter_size;
    private DoubleDouble ddtemp_ycenter_size;

    protected Rotation rotation;

    private DoubleDouble[] ddantialiasing_y;
    private DoubleDouble[] ddantialiasing_x;

    //Dont copy those
    private DoubleDouble ddtempX;
    private DoubleDouble ddtempY;

    protected DoubleDouble ddsize;

    protected double height_ratio;

    private JitterSettings js;

    public CartesianLocationNormalDoubleDoubleArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size_in, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        this.height_ratio = height_ratio;

        ddsize = new DoubleDouble(size);

        int image_size = offset.getImageSize(image_size_in);

        DoubleDouble size_2_x = ddsize.multiply(0.5);
        DoubleDouble ddimage_size = new DoubleDouble(image_size);

        ddxcenter = new DoubleDouble(xCenter);
        ddycenter = new DoubleDouble(yCenter);

        DoubleDouble temp = ddsize.multiply(new DoubleDouble(height_ratio));
        DoubleDouble size_2_y = temp.multiply(0.5);
        ddtemp_size_image_size_x = ddsize.divide(ddimage_size);
        ddtemp_size_image_size_y = temp.divide(ddimage_size);

        ddtemp_xcenter_size = ddxcenter.subtract(size_2_x);
        ddtemp_ycenter_size =  ddycenter.add(size_2_y);

        rotation = new Rotation(new DDComplex(rotation_vals[0], rotation_vals[1]), new DDComplex(rotation_center[0], rotation_center[1]));
        this.js = js;

    }

    public CartesianLocationNormalDoubleDoubleArbitrary(CartesianLocationNormalDoubleDoubleArbitrary other) {

        super();

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

        ddantialiasing_x = other.ddantialiasing_x;
        ddantialiasing_y = other.ddantialiasing_y;

        js = other.js;

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected DDComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(new DoubleDouble(x + res[1])));
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(new DoubleDouble(y + res[0])));
        }
        else {
            if (x == indexX + 1) {
                ddtempX = ddtempX.add(ddtemp_size_image_size_x);
            } else if (x == indexX - 1) {
                ddtempX = ddtempX.subtract(ddtemp_size_image_size_x);
            } else {
                ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(x));
            }

            if (y == indexY + 1) {
                ddtempY = ddtempY.subtract(ddtemp_size_image_size_y);
            } else if (y == indexY - 1) {
                ddtempY = ddtempY.add(ddtemp_size_image_size_y);
            } else {
                ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(y));
            }
        }

        indexX = x;
        indexY = y;

        DDComplex temp = new DDComplex(ddtempX, ddtempY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            if (y == indexY + 1) {
                ddtempY = ddtempY.subtract(ddtemp_size_image_size_y);
            } else if (y == indexY - 1) {
                ddtempY = ddtempY.add(ddtemp_size_image_size_y);
            } else {
                ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(y));
            }
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                ddtempX = ddtempX.add(ddtemp_size_image_size_x);
            } else if (x == indexX - 1) {
                ddtempX = ddtempX.subtract(ddtemp_size_image_size_x);
            } else {
                ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(x));
            }
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXBase(offset.getX(x));
    }

    protected DDComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            ddtempX = ddtempX.add(ddtemp_size_image_size_x);
        }
        else if(x == indexX - 1) {
            ddtempX = ddtempX.subtract(ddtemp_size_image_size_x);
        }
        else {
            ddtempX = ddtemp_xcenter_size.add(ddtemp_size_image_size_x.multiply(x));
        }

        indexX = x;

        DDComplex temp = new DDComplex(ddtempX, ddtempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected DDComplex getComplexWithYBase(int y) {

        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            ddtempY = ddtempY.subtract(ddtemp_size_image_size_y);
        }
        else if(y == indexY - 1) {
            ddtempY = ddtempY.add(ddtemp_size_image_size_y);
        }
        else {
            ddtempY = ddtemp_ycenter_size.subtract(ddtemp_size_image_size_y.multiply(y));
        }

        indexY = y;

        DDComplex temp = new DDComplex(ddtempX, ddtempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        DoubleDouble[][] steps = createAntialiasingStepsDoubleDouble(ddtemp_size_image_size_x, ddtemp_size_image_size_y, adaptive);
        ddantialiasing_x = steps[0];
        ddantialiasing_y = steps[1];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        return getAntialiasingComplexBase(sample);
    }

    protected DDComplex getAntialiasingComplexBase(int sample) {
        DDComplex temp = new DDComplex(ddtempX.add(ddantialiasing_x[sample]), ddtempY.add(ddantialiasing_y[sample]));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
