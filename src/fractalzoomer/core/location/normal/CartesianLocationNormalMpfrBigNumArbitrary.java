package fractalzoomer.core.location.normal;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalMpfrBigNumArbitrary extends Location {

    protected MpfrBigNum ddxcenter;
    protected MpfrBigNum ddycenter;

    private MpfrBigNum ddtemp_size_image_size_x;
    private MpfrBigNum ddtemp_size_image_size_y;
    private MpfrBigNum ddtemp_xcenter_size;
    private MpfrBigNum ddtemp_ycenter_size;

    private MpfrBigNum[] ddantialiasing_x;

    protected Rotation rotation;
    private MpfrBigNum[] ddantialiasing_y;

    //Dont copy those
    private MpfrBigNum ddtempX;
    private MpfrBigNum ddtempY;

    protected MpfrBigNum ddsize;

    protected double height_ratio;
    private MpfrBigNum tempResultX;
    private MpfrBigNum tempResultY;

    private JitterSettings js;

    public CartesianLocationNormalMpfrBigNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size_in, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        this.height_ratio = height_ratio;

        ddsize = new MpfrBigNum(size);

        int image_size = offset.getImageSize(image_size_in);

        MpfrBigNum size_2_x = ddsize.divide2();

        MpfrBigNum size_2_y = ddsize.mult(height_ratio);
        size_2_y = size_2_y.divide2(size_2_y);

        ddtemp_size_image_size_x = ddsize.divide(image_size);
        ddtemp_size_image_size_y = ddsize.mult(height_ratio);
        ddtemp_size_image_size_y = ddtemp_size_image_size_y.divide(image_size, ddtemp_size_image_size_y);

        ddxcenter = new MpfrBigNum(xCenter);
        ddycenter = new MpfrBigNum(yCenter);
        ddtemp_xcenter_size = ddxcenter.sub(size_2_x);
        ddtemp_ycenter_size = ddycenter.add(size_2_y);

        rotation = new Rotation(new MpfrBigNumComplex(rotation_vals[0], rotation_vals[1]), new MpfrBigNumComplex(rotation_center[0], rotation_center[1]));

        ddtempX = new MpfrBigNum();
        ddtempY = new MpfrBigNum();
        tempResultX = new MpfrBigNum();
        tempResultY = new MpfrBigNum();
        this.js = js;

    }

    public CartesianLocationNormalMpfrBigNumArbitrary(CartesianLocationNormalMpfrBigNumArbitrary other) {

        super();

        fractal = other.fractal;

        ddsize = other.ddsize;
        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        height_ratio = other.height_ratio;

        ddtemp_size_image_size_x = other.ddtemp_size_image_size_x;
        ddtemp_size_image_size_y = other.ddtemp_size_image_size_y;
        ddtemp_xcenter_size = other.ddtemp_xcenter_size;
        ddtemp_ycenter_size = other.ddtemp_ycenter_size;

        rotation = other.rotation;

        ddantialiasing_y = other.ddantialiasing_y;
        ddantialiasing_x = other.ddantialiasing_x;

        ddtempX = new MpfrBigNum();
        ddtempY = new MpfrBigNum();
        tempResultX = other.tempResultX;
        tempResultY = other.tempResultY;
        js = other.js;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected MpfrBigNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
//            ddtemp_size_image_size_x.mult(x + res[1], ddtempX);
//            ddtemp_xcenter_size.add(ddtempX, ddtempX);
//            ddtemp_size_image_size_y.mult(y + res[0], ddtempY);
//            ddtemp_ycenter_size.sub(ddtempY, ddtempY);
            MpfrBigNum.ApBmC_DsEmG(ddtempX, ddtempY, ddtemp_xcenter_size, ddtemp_size_image_size_x, x + res[1], ddtemp_ycenter_size, ddtemp_size_image_size_y, y + res[0]);
        }
        else {
            if (x == indexX + 1) {
                ddtempX.add(ddtemp_size_image_size_x, ddtempX);
            } else if (x == indexX - 1) {
                ddtempX.sub(ddtemp_size_image_size_x, ddtempX);
            } else {
                //ddtemp_size_image_size_x.mult(x, ddtempX);
                //ddtemp_xcenter_size.add(ddtempX, ddtempX);
                MpfrBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
            }

            if (y == indexY + 1) {
                ddtempY.sub(ddtemp_size_image_size_y, ddtempY);
            } else if (y == indexY - 1) {
                ddtempY.add(ddtemp_size_image_size_y, ddtempY);
            } else {
                //ddtemp_size_image_size_y.mult(y, ddtempY);
                //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
                MpfrBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
            }
        }

        indexX = x;
        indexY = y;

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpfrBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            if (y == indexY + 1) {
                ddtempY.sub(ddtemp_size_image_size_y, ddtempY);
            } else if (y == indexY - 1) {
                ddtempY.add(ddtemp_size_image_size_y, ddtempY);
            } else {
                //ddtemp_size_image_size_y.mult(y, ddtempY);
                //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
                MpfrBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
            }
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                ddtempX.add(ddtemp_size_image_size_x, ddtempX);
            } else if (x == indexX - 1) {
                ddtempX.sub(ddtemp_size_image_size_x, ddtempX);
            } else {
                //ddtemp_size_image_size_x.mult(x, ddtempX);
                //ddtemp_xcenter_size.add(ddtempX, ddtempX);
                MpfrBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
            }
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXBase(offset.getX(x));
    }

    protected MpfrBigNumComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            ddtempX.add(ddtemp_size_image_size_x, ddtempX);
        }
        else if(x == indexX - 1) {
            ddtempX.sub(ddtemp_size_image_size_x, ddtempX);
        }
        else {
            //ddtemp_size_image_size_x.mult(x, ddtempX);
            //ddtemp_xcenter_size.add(ddtempX, ddtempX);
            MpfrBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
        }

        indexX = x;


        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpfrBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected MpfrBigNumComplex getComplexWithYBase(int y) {

        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            ddtempY.sub(ddtemp_size_image_size_y, ddtempY);
        }
        else if(y == indexY - 1) {
            ddtempY.add(ddtemp_size_image_size_y, ddtempY);
        }
        else {
            //ddtemp_size_image_size_y.mult(y, ddtempY);
            //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
            MpfrBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
        }

        indexY = y;

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpfrBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        MpfrBigNum[][] steps = createAntialiasingStepsMpfrBigNum(ddtemp_size_image_size_x, ddtemp_size_image_size_y, adaptive);
        ddantialiasing_x = steps[0];
        ddantialiasing_y = steps[1];
    }

    protected MpfrBigNumComplex getAntialiasingComplexBase(int sample) {

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpfrBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpfrBigNum.self_add(tempResultX, tempResultY, ddantialiasing_x[sample], ddantialiasing_y[sample]);

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);
        //MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX.add(ddantialiasing_x[sample], tempResultX), tempResultY.add(ddantialiasing_y[sample], tempResultY));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;

    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        return getAntialiasingComplexBase(sample);
    }
}
