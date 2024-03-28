package fractalzoomer.core.location.normal;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalApfloatArbitrary extends Location {

    protected Apfloat ddxcenter;
    protected Apfloat ddycenter;
    private Apfloat ddtemp_size_image_size_x;
    private Apfloat ddtemp_size_image_size_y;
    protected Apfloat ddtemp_xcenter_size;
    protected Apfloat ddtemp_ycenter_size;

    private Apfloat[] ddantialiasing_x;
    private Apfloat[] ddantialiasing_y;

    //Dont copy those
    private Apfloat ddtempX;
    private Apfloat ddtempY;

    protected Apfloat ddsize;

    protected Apfloat height_ratio;


    protected Rotation rotation;

    private JitterSettings js;

    private Apfloat coefxdd;
    private Apfloat coefydd;

    protected int width;
    protected int height;

    public CartesianLocationNormalApfloatArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        ddxcenter = xCenter;
        ddycenter = yCenter;

        ddsize = size;

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        coefxdd = new MyApfloat(coefx);
        coefydd = new Apfloat(coefy);


        Apfloat size_2_x = MyApfloat.fp.multiply(size, coefxdd);
        Apfloat ddimage_size = new MyApfloat(image_size);
        this.height_ratio = new MyApfloat(height_ratio);

        Apfloat temp = MyApfloat.fp.multiply(size, this.height_ratio);
        Apfloat size_2_y = MyApfloat.fp.multiply(temp, coefydd);
        ddtemp_size_image_size_x = MyApfloat.fp.divide(size, ddimage_size);
        ddtemp_size_image_size_y = MyApfloat.fp.divide(temp, ddimage_size);

        ddtemp_xcenter_size = MyApfloat.fp.subtract(xCenter, size_2_x);
        ddtemp_ycenter_size =  MyApfloat.fp.add(yCenter, size_2_y);

        rotation = new Rotation(new BigComplex(rotation_vals[0], rotation_vals[1]), new BigComplex(rotation_center[0], rotation_center[1]));
        this.js = js;
        this.width = width;
        this.height = height;
    }

    public CartesianLocationNormalApfloatArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height) {
        super();
        this.height_ratio = new MyApfloat(height_ratio);
        ddsize = size;
        ddxcenter = xCenter;
        ddycenter = yCenter;

        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        coefxdd = new MyApfloat(coefx);
        coefydd = new MyApfloat(coefy);

        Apfloat size_2_x = MyApfloat.fp.multiply(size, coefxdd);
        Apfloat ddimage_size = new MyApfloat(image_size);
        Apfloat ddiheight_ratio = new MyApfloat(height_ratio);
        Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, ddiheight_ratio), coefydd);
        ddtemp_size_image_size_x = MyApfloat.fp.divide(size, ddimage_size);
        ddtemp_size_image_size_y = MyApfloat.fp.divide(MyApfloat.fp.multiply(size, ddiheight_ratio), ddimage_size);
        ddtemp_xcenter_size = MyApfloat.fp.subtract(xCenter, size_2_x);
        ddtemp_ycenter_size =  MyApfloat.fp.add(yCenter, size_2_y);

        this.width = width;
        this.height = height;
    }

    public CartesianLocationNormalApfloatArbitrary(CartesianLocationNormalApfloatArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        ddsize = other.ddsize;

        height_ratio = other.height_ratio;

        ddtemp_size_image_size_x = other.ddtemp_size_image_size_x;
        ddtemp_size_image_size_y = other.ddtemp_size_image_size_y;
        ddtemp_xcenter_size = other.ddtemp_xcenter_size;
        ddtemp_ycenter_size = other.ddtemp_ycenter_size;

        rotation = other.rotation;

        ddantialiasing_y = other.ddantialiasing_y;
        ddantialiasing_x = other.ddantialiasing_x;

        coefxdd = other.coefxdd;
        coefydd = other.coefydd;

        js = other.js;
        width = other.width;
        height = other.height;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {

       return getComplexBase(offset.getX(x), offset.getY(y));

    }

    protected BigComplex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x + res[1])));
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y + res[0])));
        }
        else {
            if (x == indexX + 1) {
                ddtempX = MyApfloat.fp.add(ddtempX, ddtemp_size_image_size_x);
            } else if (x == indexX - 1) {
                ddtempX = MyApfloat.fp.subtract(ddtempX, ddtemp_size_image_size_x);
            } else if (x != indexX) {
                ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
            }

            if (y == indexY + 1) {
                ddtempY = MyApfloat.fp.subtract(ddtempY, ddtemp_size_image_size_y);
            } else if (y == indexY - 1) {
                ddtempY = MyApfloat.fp.add(ddtempY, ddtemp_size_image_size_y);
            } else if (y != indexY) {
                ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
            }
        }

        indexX = x;
        indexY = y;

        BigComplex temp = new BigComplex(ddtempX, ddtempY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            if (y == indexY + 1) {
                ddtempY = MyApfloat.fp.subtract(ddtempY, ddtemp_size_image_size_y);
            } else if (y == indexY - 1) {
                ddtempY = MyApfloat.fp.add(ddtempY, ddtemp_size_image_size_y);
            } else if (y != indexY) {
                ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
            }
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                ddtempX = MyApfloat.fp.add(ddtempX, ddtemp_size_image_size_x);
            } else if (x == indexX - 1) {
                ddtempX = MyApfloat.fp.subtract(ddtempX, ddtemp_size_image_size_x);
            } else if (x != indexX) {
                ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
            }
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {

        return getComplexWithXBase(offset.getX(x));

    }

    protected BigComplex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            ddtempX = MyApfloat.fp.add(ddtempX, ddtemp_size_image_size_x);
        }
        else if(x == indexX - 1) {
            ddtempX = MyApfloat.fp.subtract(ddtempX, ddtemp_size_image_size_x);
        }
        else if (x != indexX) {
            ddtempX = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        }

        indexX = x;

        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {

        return getComplexWithYBase(offset.getY(y));

    }

    protected BigComplex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            ddtempY = MyApfloat.fp.subtract(ddtempY, ddtemp_size_image_size_y);
        }
        else if(y == indexY - 1) {
            ddtempY = MyApfloat.fp.add(ddtempY, ddtemp_size_image_size_y);
        }
        else if (y != indexY) {
            ddtempY = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
        }

        indexY = y;

        BigComplex temp = new BigComplex(ddtempX, ddtempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        Apfloat[][] steps = createAntialiasingStepsApfloat(ddtemp_size_image_size_x, ddtemp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        ddantialiasing_x = steps[0];
        ddantialiasing_y = steps[1];
    }

    protected BigComplex getAntialiasingComplexBase(int sample, int loc) {

        BigComplex temp;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            Apfloat[] ddantialiasing_x = precalculatedJitterDataApfloat[r][0];
            Apfloat[] ddantialiasing_y = precalculatedJitterDataApfloat[r][1];
            temp = new BigComplex(MyApfloat.fp.add(ddtempX, ddantialiasing_x[sample]), MyApfloat.fp.add(ddtempY, ddantialiasing_y[sample]));
        }
        else {
            temp = new BigComplex(MyApfloat.fp.add(ddtempX, ddantialiasing_x[sample]), MyApfloat.fp.add(ddtempY, ddantialiasing_y[sample]));
        }

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }

    public BigPoint getPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }

    public Apfloat getNRradius() {
        int image_size = Math.min(width, height);
        Apfloat coef = new MyApfloat(image_size * 0.1);
        Apfloat x = MyApfloat.fp.multiply(coef, ddtemp_size_image_size_x);
        Apfloat y = MyApfloat.fp.multiply(coef, ddtemp_size_image_size_y);
        x = MyApfloat.fp.multiply(x, x);
        y = MyApfloat.fp.multiply(y, y);
        return MyApfloat.fp.sqrt(MyApfloat.fp.add(x, y));
    }

    public BigPoint getPointRelativeToPoint(int x, int y, BigPoint refPoint) {
        Apfloat size05x = MyApfloat.fp.multiply(ddsize, coefxdd);
        Apfloat temp = MyApfloat.fp.multiply(ddsize, this.height_ratio);
        Apfloat size05y = MyApfloat.fp.multiply(temp, coefydd);
        Apfloat newX = MyApfloat.fp.add(MyApfloat.fp.subtract(size05x, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), refPoint.x);
        Apfloat newY = MyApfloat.fp.add(MyApfloat.fp.add(size05y.negate(), MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))), refPoint.y);
        return new BigPoint(newX, newY);
    }

    public BigPoint getDragPoint(int x, int y) {
        return new BigPoint(MyApfloat.fp.subtract(ddxcenter, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y))));
    }

    public Complex getComplexOrbit(int x, int y) {
        Apfloat ddtempXO = MyApfloat.fp.add(ddtemp_xcenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_x, new MyApfloat(x)));
        Apfloat ddtempYO = MyApfloat.fp.subtract(ddtemp_ycenter_size, MyApfloat.fp.multiply(ddtemp_size_image_size_y, new MyApfloat(y)));
        BigComplex temp = new BigComplex(ddtempXO, ddtempYO);
        return temp.toComplex();
    }

}
