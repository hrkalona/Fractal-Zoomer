package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDeep extends Location {
    private MantExp mheight_ratio;
    private double height_ratio;
    private MantExp size;
    private MantExp temp_size_image_size_x;
    private MantExp temp_size_image_size_y;
    private MantExp temp_x_corner;
    private MantExp temp_y_corner;
    private JitterSettings js;

    private MantExp tempY;
    private MantExp tempX;

    private Apfloat ddxcenter;
    private Apfloat ddycenter;

    private double image_size_2x;
    private double image_size_2y;

    private int bignumLib;

    private MantExp[] antialiasing_x;

    private MantExp[] antialiasing_y;

    private int width;
    private int height;

    public CartesianLocationDeltaDeep(Apfloat xCenter, Apfloat yCenter, Apfloat ddsize, double height_ratio, int width, int height, JitterSettings js, Fractal fractal, int bignumLib) {

        super();

        this.fractal = fractal;
        this.mheight_ratio = new MantExp(height_ratio);
        ddxcenter = xCenter;
        ddycenter = yCenter;
        //Apfloat ddheight_ratio = new MyApfloat(height_ratio);
        this.bignumLib = bignumLib;

        this.js = js;
        //this.ddsize = ddsize;
        size = new MantExp(ddsize);

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        this.width = width;
        this.height = height;

        image_size_2x = width * 0.5;
        image_size_2y = height * 0.5;

        MantExp size_2_x = size.multiply(coefx);
        MantExp temp = size.multiply(mheight_ratio);
        MantExp size_2_y = temp.multiply(coefy);
        size_2_x.Normalize();
        size_2_y.Normalize();

        temp_size_image_size_x = size.divide(image_size);
        temp_size_image_size_y = temp.divide(image_size);
        temp_size_image_size_x.Normalize();
        temp_size_image_size_y.Normalize();

        temp_x_corner = size_2_x.negate();
        temp_y_corner = size_2_y;

    }

    public CartesianLocationDeltaDeep(CartesianLocationDeltaDeep other) {
        super(other);
        fractal = other.fractal;
        reference = other.reference;
        temp_x_corner = other.temp_x_corner;
        temp_y_corner = other.temp_y_corner;
        temp_size_image_size_x = other.temp_size_image_size_x;
        temp_size_image_size_y = other.temp_size_image_size_y;
        js = other.js;
        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        bignumLib = other.bignumLib;
        //ddsize = other.ddsize;
        antialiasing_x = other.antialiasing_x;
        antialiasing_y = other.antialiasing_y;
        size = other.size;
        height_ratio = other.height_ratio;
        image_size_2x = other.image_size_2x;
        image_size_2y = other.image_size_2y;
        width = other.width;
        height = other.height;
    }

    private MantExpComplex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);

//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x + res[1]));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y + res[0]));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply((x + res[1]) - image_size_2x);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - (y + res[0]));
            tempY.Normalize();
        }
        else {
//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply(x - image_size_2x);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - y);
            tempY.Normalize();
        }

        indexX = x;
        indexY = y;

        return MantExpComplex.create(tempX, tempY);
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    private MantExpComplex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

//        tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//        tempX.Normalize();

        tempX = temp_size_image_size_x.multiply(x - image_size_2x);
        tempX.Normalize();

        indexX = x;

        return MantExpComplex.create(tempX, tempY);
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
       return getComplexWithXBase(offset.getX(x));
    }

    private MantExpComplex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

//        tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//        tempY.Normalize();
        tempY = temp_size_image_size_y.multiply(image_size_2y - y);
        tempY.Normalize();

        indexY = y;

        return MantExpComplex.create(tempX, tempY);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//            tempY.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2y - y);
            tempY.Normalize();
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//            tempX.Normalize();
            tempX = temp_size_image_size_x.multiply(x - image_size_2x);
            tempX.Normalize();
        }

        indexX = x;

    }

    @Override
    public GenericComplex getReferencePoint() {

        if(bignumLib == Constants.BIGNUM_BUILT_IN) {
            return new BigNumComplex(ddxcenter, ddycenter);
        }
        else if(bignumLib == Constants.BIGNUM_BIGINT) {
            return new BigIntNumComplex(ddxcenter, ddycenter);
        }
        else if (bignumLib == Constants.BIGNUM_MPFR) {
            return new MpfrBigNumComplex(ddxcenter, ddycenter);
        }
        else if (bignumLib == Constants.BIGNUM_MPIR) {
            return new MpirBigNumComplex(ddxcenter, ddycenter);
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            return new DDComplex(ddxcenter, ddycenter);
        }
        else if (bignumLib == Constants.BIGNUM_DOUBLE) {
            return new Complex(ddxcenter, ddycenter);
        }
        else {
            return new BigComplex(ddxcenter, ddycenter);
        }
    }

    @Override
    public MantExp getMaxSizeInImage() {

        MantExp temp = temp_size_image_size_x.multiply(width * 0.5);
        temp = temp.square();
        temp.Normalize();
        MantExp temp2 = temp_size_image_size_y.multiply(height * 0.5);
        temp2 = temp2.square();
        temp2.Normalize();
        MantExp temp3 = temp.add(temp2).sqrt();
        temp3.Normalize();
        return temp3;

    }

    @Override
    public MantExp getSize() {
        return new MantExp(size);
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {

        MantExpComplex temp;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            MantExp[] antialiasing_x = precalculatedJitterDataMantexp[r][0];
            MantExp[] antialiasing_y = precalculatedJitterDataMantexp[r][1];
            MantExp x = tempX.add(antialiasing_x[sample]);
            x.Normalize();
            MantExp y = tempY.add(antialiasing_y[sample]);
            y.Normalize();
            temp = MantExpComplex.create(x, y);
        }
        else {
            MantExp x = tempX.add(antialiasing_x[sample]);
            x.Normalize();
            MantExp y = tempY.add(antialiasing_y[sample]);
            y.Normalize();
            temp = MantExpComplex.create(x, y);
        }

        return temp;

    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        MantExp[][] steps = createAntialiasingStepsMantexp(temp_size_image_size_x, temp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        antialiasing_x = steps[0];
        antialiasing_y = steps[1];
    }
}
