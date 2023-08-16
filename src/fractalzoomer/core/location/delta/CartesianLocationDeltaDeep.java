package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
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

    private double image_size_2;

    private int bignumLib;

    private MantExp[] antialiasing_x;

    private MantExp[] antialiasing_y;

    public CartesianLocationDeltaDeep(Apfloat xCenter, Apfloat yCenter, Apfloat ddsize, double height_ratio, int image_size_in, JitterSettings js, int bignumLib) {

        super();

        this.mheight_ratio = new MantExp(height_ratio);
        ddxcenter = xCenter;
        ddycenter = yCenter;
        //Apfloat ddheight_ratio = new MyApfloat(height_ratio);
        this.bignumLib = bignumLib;

        this.js = js;
        //this.ddsize = ddsize;
        size = new MantExp(ddsize);

        int image_size = offset.getImageSize(image_size_in);
        image_size_2 = image_size * 0.5;

//        Apfloat point5 = new MyApfloat(0.5);
//        Apfloat size_2_x = MyApfloat.fp.multiply(ddsize, point5);
//        Apfloat ddimage_size = new MyApfloat(image_size);
//        Apfloat temp = MyApfloat.fp.multiply(ddsize, ddheight_ratio);
//        Apfloat size_2_y = MyApfloat.fp.multiply(temp, point5);
//        Apfloat ddtemp_size_image_size_x = MyApfloat.fp.divide(ddsize, ddimage_size);
//        Apfloat ddtemp_size_image_size_y = MyApfloat.fp.divide(temp, ddimage_size);
//        temp_size_image_size_x = ddtemp_size_image_size_x.doubleValue();
//        temp_size_image_size_y = ddtemp_size_image_size_y.doubleValue();
//
//
//        temp_x_corner = size_2_x.negate().doubleValue();
//        temp_y_corner =  size_2_y.doubleValue();

        MantExp size_2_x = size.divide2();
        MantExp temp = size.multiply(mheight_ratio);
        MantExp size_2_y = temp.divide2();
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
        image_size_2 = other.image_size_2;
    }

    private MantExpComplex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);

//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x + res[1]));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y + res[0]));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply((x + res[1]) - image_size_2);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2 - (y + res[0]));
            tempY.Normalize();
        }
        else {
//            tempX = temp_x_corner.add(temp_size_image_size_x.multiply(x));
//            tempX.Normalize();
//            tempY = temp_y_corner.subtract(temp_size_image_size_y.multiply(y));
//            tempY.Normalize();

            tempX = temp_size_image_size_x.multiply(x - image_size_2);
            tempX.Normalize();
            tempY = temp_size_image_size_y.multiply(image_size_2 - y);
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

        tempX = temp_size_image_size_x.multiply(x - image_size_2);
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
        tempY = temp_size_image_size_y.multiply(image_size_2 - y);
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
            tempY = temp_size_image_size_y.multiply(image_size_2 - y);
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
            tempX = temp_size_image_size_x.multiply(x - image_size_2);
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
        if(height_ratio == 1) { // ((size * 0.5) / image_size) * sqrt(image_size^2 + image_size^2) = ((size * 0.5) / image_size) * sqrt(2) * image_size
            MantExp sqrt2 = MantExp.TWO.sqrt();
            MantExp temp = sqrt2.multiply(size).divide2();
            temp.Normalize();
            return temp;
        }
        else {
            MantExp temp = size.divide2();
            MantExp temp2 = temp.multiply(mheight_ratio);
            MantExp temp3 = temp.square().add(temp2.square()).sqrt();
            temp3.Normalize();
            return temp3;
        }
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
