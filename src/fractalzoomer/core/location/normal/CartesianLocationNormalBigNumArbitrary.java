package fractalzoomer.core.location.normal;

import fractalzoomer.core.BigNum;
import fractalzoomer.core.BigNumComplex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalBigNumArbitrary extends Location {
    protected BigNum ddxcenter;
    protected BigNum ddycenter;
    private BigNum bntemp_size_image_size_x;
    private BigNum bntemp_size_image_size_y;
    private BigNum bntemp_xcenter_size;
    private BigNum bntemp_ycenter_size;

    protected Rotation rotation;

    private BigNum[] bnantialiasing_y;
    private BigNum[] bnantialiasing_x;

    //Dont copy those
    private BigNum bntempX;
    private BigNum bntempY;

    protected BigNum bnsize;
    protected Apfloat ddsize;

    protected Apfloat height_ratio;

    protected Apfloat point5;

    private JitterSettings js;

    public CartesianLocationNormalBigNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size_in, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        this.height_ratio = new MyApfloat(height_ratio);

        int image_size = offset.getImageSize(image_size_in);

        point5 = new MyApfloat(0.5);

        bnsize = BigNum.create(size);
        
        BigNum size_2_x = bnsize.divide2();
        Apfloat ddimage_size = new MyApfloat(image_size);

        ddsize = size;

        ddxcenter = BigNum.create(xCenter);
        ddycenter = BigNum.create(yCenter);

        Apfloat temp = MyApfloat.fp.multiply(size, this.height_ratio);
        BigNum size_2_y = BigNum.create(temp).divide2();
        bntemp_size_image_size_x = BigNum.create(MyApfloat.fp.divide(size, ddimage_size));
        bntemp_size_image_size_y = BigNum.create(MyApfloat.fp.divide(temp, ddimage_size));

        bntemp_xcenter_size = ddxcenter.sub(size_2_x);
        bntemp_ycenter_size = ddycenter.add(size_2_y);

        rotation = new Rotation(new BigNumComplex(rotation_vals[0], rotation_vals[1]), new BigNumComplex(rotation_center[0], rotation_center[1]));
        this.js = js;

    }

    public CartesianLocationNormalBigNumArbitrary(CartesianLocationNormalBigNumArbitrary other) {

        super(other);

        fractal = other.fractal;

        bnsize = other.bnsize;
        ddsize = other.ddsize;
        height_ratio = other.height_ratio;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        bntemp_size_image_size_x = other.bntemp_size_image_size_x;
        bntemp_size_image_size_y = other.bntemp_size_image_size_y;
        bntemp_xcenter_size = other.bntemp_xcenter_size;
        bntemp_ycenter_size = other.bntemp_ycenter_size;
        rotation = other.rotation;

        bnantialiasing_x = other.bnantialiasing_x;
        bnantialiasing_y = other.bnantialiasing_y;

        point5 = other.point5;
        js = other.js;

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected BigNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(BigNum.create(x + res[1])));
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(BigNum.create(y + res[0])));
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

        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);

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

    protected BigNumComplex getComplexWithXBase(int x) {

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

        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected BigNumComplex getComplexWithYBase(int y) {

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

        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        BigNum[][] steps = createAntialiasingStepsBigNum(bntemp_size_image_size_x, bntemp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        bnantialiasing_x = steps[0];
        bnantialiasing_y = steps[1];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }

    protected BigNumComplex getAntialiasingComplexBase(int sample, int loc) {
        BigNumComplex temp;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            BigNum[] bnantialiasing_x = precalculatedJitterDataBigNum[r][0];
            BigNum[] bnantialiasing_y = precalculatedJitterDataBigNum[r][1];
            temp = new BigNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));
        }
        else {
            temp = new BigNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));
        }

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
