package fractalzoomer.core.location.normal;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalBigIntNumArbitrary extends Location {
    protected BigIntNum ddxcenter;
    protected BigIntNum ddycenter;
    protected BigIntNum bntemp_size_image_size_x;
    protected BigIntNum bntemp_size_image_size_y;
    private BigIntNum bntemp_xcenter_size;
    private BigIntNum bntemp_ycenter_size;

    protected Rotation rotation;

    private BigIntNum[] bnantialiasing_y;
    private BigIntNum[] bnantialiasing_x;

    //Dont copy those
    private BigIntNum bntempX;
    private BigIntNum bntempY;

    protected BigIntNum bnsize;

    private JitterSettings js;

    private BigIntNum coefxdd;
    private BigIntNum coefydd;

    protected int width;
    protected int height;

    public CartesianLocationNormalBigIntNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        coefxdd = new BigIntNum(coefx);
        coefydd = new BigIntNum(coefy);

        BigIntNum biSize = new BigIntNum(size);

        BigIntNum size_2_x = biSize.mult(coefxdd);
        BigIntNum ddimage_size = new BigIntNum(image_size);

        bnsize = new BigIntNum(size);

        ddxcenter = new BigIntNum(xCenter);
        ddycenter = new BigIntNum(yCenter);

        BigIntNum temp = biSize.mult(new BigIntNum(height_ratio));
        BigIntNum size_2_y = temp.mult(coefydd);
        bntemp_size_image_size_x = biSize.divide(ddimage_size);
        bntemp_size_image_size_y = temp.divide(ddimage_size);

        bntemp_xcenter_size = ddxcenter.sub(size_2_x);
        bntemp_ycenter_size =  ddycenter.add(size_2_y);

        rotation = new Rotation(new BigIntNumComplex(rotation_vals[0], rotation_vals[1]), new BigIntNumComplex(rotation_center[0], rotation_center[1]));
        this.js = js;
        this.width = width;
        this.height = height;

    }

    public CartesianLocationNormalBigIntNumArbitrary(CartesianLocationNormalBigIntNumArbitrary other) {

        super(other);

        fractal = other.fractal;

        bnsize = other.bnsize;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        bntemp_size_image_size_x = other.bntemp_size_image_size_x;
        bntemp_size_image_size_y = other.bntemp_size_image_size_y;
        bntemp_xcenter_size = other.bntemp_xcenter_size;
        bntemp_ycenter_size = other.bntemp_ycenter_size;
        rotation = other.rotation;

        bnantialiasing_x = other.bnantialiasing_x;
        bnantialiasing_y = other.bnantialiasing_y;

        js = other.js;
        coefxdd = other.coefxdd;
        coefydd = other.coefydd;
        width = other.width;
        height = other.height;

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected BigIntNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigIntNum(x + res[1])));
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigIntNum(y + res[0])));
        }
        else {
            if (x == indexX + 1) {
                bntempX = bntempX.add(bntemp_size_image_size_x);
            } else if (x == indexX - 1) {
                bntempX = bntempX.sub(bntemp_size_image_size_x);
            } else if (x != indexX) {
                bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(x));
            }

            if (y == indexY + 1) {
                bntempY = bntempY.sub(bntemp_size_image_size_y);
            } else if (y == indexY - 1) {
                bntempY = bntempY.add(bntemp_size_image_size_y);
            } else if (y != indexY) {
                bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(y));
            }
        }

        indexX = x;
        indexY = y;

        BigIntNumComplex temp = new BigIntNumComplex(bntempX, bntempY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            if (y == indexY + 1) {
                bntempY = bntempY.sub(bntemp_size_image_size_y);
            } else if (y == indexY - 1) {
                bntempY = bntempY.add(bntemp_size_image_size_y);
            } else if (y != indexY) {
                bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(y));
            }
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                bntempX = bntempX.add(bntemp_size_image_size_x);
            } else if (x == indexX - 1) {
                bntempX = bntempX.sub(bntemp_size_image_size_x);
            } else if (x != indexX) {
                bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(x));
            }
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXBase(offset.getX(x));
    }

    protected BigIntNumComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            bntempX = bntempX.add(bntemp_size_image_size_x);
        }
        else if(x == indexX - 1) {
            bntempX = bntempX.sub(bntemp_size_image_size_x);
        }
        else if (x != indexX) {
            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(x));
        }

        indexX = x;

        BigIntNumComplex temp = new BigIntNumComplex(bntempX, bntempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected BigIntNumComplex getComplexWithYBase(int y) {

        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            bntempY = bntempY.sub(bntemp_size_image_size_y);
        }
        else if(y == indexY - 1) {
            bntempY = bntempY.add(bntemp_size_image_size_y);
        }
        else if (y != indexY) {
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(y));
        }

        indexY = y;

        BigIntNumComplex temp = new BigIntNumComplex(bntempX, bntempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        BigIntNum[][] steps = createAntialiasingStepsBigIntNum(bntemp_size_image_size_x, bntemp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        bnantialiasing_x = steps[0];
        bnantialiasing_y = steps[1];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }

    protected BigIntNumComplex getAntialiasingComplexBase(int sample, int loc) {
        BigIntNumComplex temp;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            BigIntNum[] bnantialiasing_x = precalculatedJitterDataBigIntNum[r][0];
            BigIntNum[] bnantialiasing_y = precalculatedJitterDataBigIntNum[r][1];
            temp = new BigIntNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));
        }
        else {
            temp = new BigIntNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));
        }

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
