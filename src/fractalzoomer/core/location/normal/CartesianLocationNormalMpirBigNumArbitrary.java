package fractalzoomer.core.location.normal;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.MpirBigNumComplex;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalMpirBigNumArbitrary extends Location {

    protected MpirBigNum ddxcenter;
    protected MpirBigNum ddycenter;

    private MpirBigNum ddtemp_size_image_size_x;
    private MpirBigNum ddtemp_size_image_size_y;
    private MpirBigNum ddtemp_xcenter_size;
    private MpirBigNum ddtemp_ycenter_size;

    private MpirBigNum[] ddantialiasing_x;

    protected Rotation rotation;
    private MpirBigNum[] ddantialiasing_y;

    //Dont copy those
    private MpirBigNum ddtempX;
    private MpirBigNum ddtempY;

    protected MpirBigNum ddsize;

    protected double height_ratio;
    private MpirBigNum tempResultX;
    private MpirBigNum tempResultY;

    protected MpirBigNum heightRatio;

    private JitterSettings js;

    public CartesianLocationNormalMpirBigNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size_in, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        this.height_ratio = height_ratio;

        if(!LibMpfr.hasError()) {
            try {
                ddsize = new MpirBigNum(new MpfrBigNum(size));
            }
            catch (Error e) {
                ddsize = new MpirBigNum(size);
            }
        }
        else {
            //Bug here near 0, -1 very deep
            ddsize = new MpirBigNum(size);
        }

        int image_size = offset.getImageSize(image_size_in);

        MpirBigNum size_2_x = ddsize.divide2();

        heightRatio = new MpirBigNum(height_ratio);

        MpirBigNum size_2_y = ddsize.mult(heightRatio);
        size_2_y = size_2_y.divide2(size_2_y);

        ddtemp_size_image_size_x = ddsize.divide(image_size);
        ddtemp_size_image_size_y = ddsize.mult(heightRatio);
        ddtemp_size_image_size_y = ddtemp_size_image_size_y.divide(image_size, ddtemp_size_image_size_y);

        if(!LibMpfr.hasError()) {
            try {
                ddxcenter = new MpirBigNum(new MpfrBigNum(xCenter));
                ddycenter = new MpirBigNum(new MpfrBigNum(yCenter));
            }
            catch (Error e) {
                ddxcenter = new MpirBigNum(xCenter);
                ddycenter = new MpirBigNum(yCenter);
            }
        }
        else {
            //Bug here near 0, -1 very deep
            ddxcenter = new MpirBigNum(xCenter);
            ddycenter = new MpirBigNum(yCenter);
        }

        ddtemp_xcenter_size = ddxcenter.sub(size_2_x);
        ddtemp_ycenter_size = ddycenter.add(size_2_y);

        MpirBigNumComplex rotCenter;

        if(!LibMpfr.hasError()) {
            try {
                rotCenter = new MpirBigNumComplex(new MpfrBigNumComplex(rotation_center[0], rotation_center[1]));
            }
            catch (Error e) {
                rotCenter = new MpirBigNumComplex(rotation_center[0], rotation_center[1]);
            }
        }
        else {
            rotCenter = new MpirBigNumComplex(rotation_center[0], rotation_center[1]);
        }

        rotation = new Rotation(new MpirBigNumComplex(rotation_vals[0], rotation_vals[1]), rotCenter);

        ddtempX = new MpirBigNum();
        ddtempY = new MpirBigNum();
        tempResultX = new MpirBigNum();
        tempResultY = new MpirBigNum();
        this.js = js;

    }

    public CartesianLocationNormalMpirBigNumArbitrary(CartesianLocationNormalMpirBigNumArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddsize = other.ddsize;
        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        height_ratio = other.height_ratio;

        heightRatio = other.heightRatio;

        ddtemp_size_image_size_x = other.ddtemp_size_image_size_x;
        ddtemp_size_image_size_y = other.ddtemp_size_image_size_y;
        ddtemp_xcenter_size = other.ddtemp_xcenter_size;
        ddtemp_ycenter_size = other.ddtemp_ycenter_size;

        rotation = other.rotation;

        ddantialiasing_y = other.ddantialiasing_y;
        ddantialiasing_x = other.ddantialiasing_x;

        ddtempX = new MpirBigNum();
        ddtempY = new MpirBigNum();
        tempResultX = other.tempResultX;
        tempResultY = other.tempResultY;
        js = other.js;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected MpirBigNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            tempResultX.set(res[1]);
            tempResultX.add(x, tempResultX);
            //ddtemp_size_image_size_x.mult(tempResultX, ddtempX);
            //ddtemp_xcenter_size.add(ddtempX, ddtempX);
            tempResultY.set(res[0]);
            tempResultY.add(y, tempResultY);
            //ddtemp_size_image_size_y.mult(tempResultY, ddtempY);
            //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
            MpirBigNum.ApBmC_DsEmG(ddtempX, ddtempY, ddtemp_xcenter_size, ddtemp_size_image_size_x, tempResultX, ddtemp_ycenter_size, ddtemp_size_image_size_y, tempResultY);
        }
        else {
            if (x == indexX + 1) {
                ddtempX.add(ddtemp_size_image_size_x, ddtempX);
            } else if (x == indexX - 1) {
                ddtempX.sub(ddtemp_size_image_size_x, ddtempX);
            } else if (x != indexX) {
                //ddtemp_size_image_size_x.mult(x, ddtempX);
                //ddtemp_xcenter_size.add(ddtempX, ddtempX);

                if(x < 0) {
                    MpirBigNum.AsBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, -x);
                }
                else {
                    MpirBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
                }
            }

            if (y == indexY + 1) {
                ddtempY.sub(ddtemp_size_image_size_y, ddtempY);
            } else if (y == indexY - 1) {
                ddtempY.add(ddtemp_size_image_size_y, ddtempY);
            } else if (y != indexY) {
                //ddtemp_size_image_size_y.mult(y, ddtempY);
                //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
                if(y < 0) {
                    MpirBigNum.ApBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, -y);
                }
                else {
                    MpirBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
                }
            }
        }

        indexX = x;
        indexY = y;

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpirBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpirBigNumComplex temp = new MpirBigNumComplex(tempResultX, tempResultY);

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
            } else if (y != indexY) {
                //ddtemp_size_image_size_y.mult(y, ddtempY);
                //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
                if(y < 0) {
                    MpirBigNum.ApBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, -y);
                }
                else {
                    MpirBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
                }
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
            } else if (x != indexX) {
                //ddtemp_size_image_size_x.mult(x, ddtempX);
                //ddtemp_xcenter_size.add(ddtempX, ddtempX);
                if(x < 0) {
                    MpirBigNum.AsBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, -x);
                }
                else {
                    MpirBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
                }
            }
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXBase(offset.getX(x));
    }

    protected MpirBigNumComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            ddtempX.add(ddtemp_size_image_size_x, ddtempX);
        }
        else if(x == indexX - 1) {
            ddtempX.sub(ddtemp_size_image_size_x, ddtempX);
        }
        else if (x != indexX) {
            //ddtemp_size_image_size_x.mult(x, ddtempX);
            //ddtemp_xcenter_size.add(ddtempX, ddtempX);
            if(x < 0) {
                MpirBigNum.AsBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, -x);
            }
            else {
                MpirBigNum.ApBmC(ddtempX, ddtemp_xcenter_size, ddtemp_size_image_size_x, x);
            }
        }

        indexX = x;


        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpirBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpirBigNumComplex temp = new MpirBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected MpirBigNumComplex getComplexWithYBase(int y) {

        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            ddtempY.sub(ddtemp_size_image_size_y, ddtempY);
        }
        else if(y == indexY - 1) {
            ddtempY.add(ddtemp_size_image_size_y, ddtempY);
        }
        else if (y != indexY) {
            //ddtemp_size_image_size_y.mult(y, ddtempY);
            //ddtemp_ycenter_size.sub(ddtempY, ddtempY);
            if(y < 0) {
                MpirBigNum.ApBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, -y);
            }
            else {
                MpirBigNum.AsBmC(ddtempY, ddtemp_ycenter_size, ddtemp_size_image_size_y, y);
            }
        }

        indexY = y;

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpirBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        MpirBigNumComplex temp = new MpirBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        MpirBigNum[][] steps = createAntialiasingStepsMpirBigNum(ddtemp_size_image_size_x, ddtemp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        ddantialiasing_x = steps[0];
        ddantialiasing_y = steps[1];
    }

    protected MpirBigNumComplex getAntialiasingComplexBase(int sample, int loc) {

        //tempResultX.set(ddtempX);
        //tempResultY.set(ddtempY);
        MpirBigNum.set(tempResultX, tempResultY, ddtempX, ddtempY);

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            MpirBigNum[] ddantialiasing_x = precalculatedJitterDataMpirBigNum[r][0];
            MpirBigNum[] ddantialiasing_y = precalculatedJitterDataMpirBigNum[r][1];
            MpirBigNum.self_add(tempResultX, tempResultY, ddantialiasing_x[sample], ddantialiasing_y[sample]);
        }
        else {
            MpirBigNum.self_add(tempResultX, tempResultY, ddantialiasing_x[sample], ddantialiasing_y[sample]);
        }

        MpirBigNumComplex temp = new MpirBigNumComplex(tempResultX, tempResultY);
        //MpirBigNumComplex temp = new MpirBigNumComplex(tempResultX.add(ddantialiasing_x[sample], tempResultX), tempResultY.add(ddantialiasing_y[sample], tempResultY));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;

    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }
}
