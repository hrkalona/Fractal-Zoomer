package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDelta extends Location {
    private double height_ratio;
    private double dsize;
    private double temp_size_image_size_x;
    private double temp_size_image_size_y;
    private double temp_x_corner;
    private double temp_y_corner;

    private double image_size_2x;
    private double image_size_2y;
    private JitterSettings js;

    private double tempY;
    private double tempX;

    private Apfloat ddxcenter;
    private Apfloat ddycenter;

    private int bignumLib;

    private double[] antialiasing_x;

    private double[] antialiasing_y;

    private int width;
    private int height;

    public CartesianLocationDelta(Apfloat xCenter, Apfloat yCenter, Apfloat ddsize, double height_ratio, int width, int height, JitterSettings js, Fractal fractal, int bignumLib) {

        super();

        this.fractal = fractal;
        this.height_ratio = height_ratio;
        ddxcenter = xCenter;
        ddycenter = yCenter;
        //Apfloat ddheight_ratio = new MyApfloat(height_ratio);
        this.bignumLib = bignumLib;

        this.js = js;
        //this.ddsize = ddsize;
        dsize = ddsize.doubleValue();

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        this.width = width;
        this.height = height;

        image_size_2x = width * 0.5;
        image_size_2y = height * 0.5;

        double size_2_x = dsize * coefx;
        double temp = dsize * height_ratio;
        double size_2_y = temp * coefy;
        temp_size_image_size_x = dsize / image_size;
        temp_size_image_size_y = temp / image_size;

        temp_x_corner = - size_2_x;
        temp_y_corner = size_2_y;

    }

    public CartesianLocationDelta(CartesianLocationDelta other) {
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
        dsize = other.dsize;
        height_ratio = other.height_ratio;
        image_size_2x = other.image_size_2x;
        image_size_2y = other.image_size_2y;
        width = other.width;
        height = other.height;
    }

    private Complex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
//            tempX = temp_x_corner + (x + res[1]) * temp_size_image_size_x;
//            tempY = temp_y_corner - (y + res[0]) * temp_size_image_size_y;
            tempX = ((x + res[1]) - image_size_2x) * temp_size_image_size_x;
            tempY = (image_size_2y - (y + res[0])) * temp_size_image_size_y;
        }
        else {
            //tempX = temp_x_corner + x * temp_size_image_size_x;
            //tempY = temp_y_corner - y * temp_size_image_size_y;
            tempX = (x - image_size_2x) * temp_size_image_size_x;
            tempY = (image_size_2y - y ) * temp_size_image_size_y;
        }

        indexX = x;
        indexY = y;

        return new Complex(tempX, tempY);
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    private Complex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        //tempX = temp_x_corner + x * temp_size_image_size_x;
        tempX = (x - image_size_2x) * temp_size_image_size_x;

        indexX = x;

        return new Complex(tempX, tempY);
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
       return getComplexWithXBase(offset.getX(x));
    }

    private Complex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        //tempY = temp_y_corner - y * temp_size_image_size_y;
        tempY = (image_size_2y - y ) * temp_size_image_size_y;

        indexY = y;

        return new Complex(tempX, tempY);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            //tempY = temp_y_corner - y * temp_size_image_size_y;
            tempY = (image_size_2y - y ) * temp_size_image_size_y;
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            //tempX = temp_x_corner + x * temp_size_image_size_x;
            tempX = (x - image_size_2x) * temp_size_image_size_x;
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

        return new MantExp(Math.hypot(temp_size_image_size_x * width * 0.5, temp_size_image_size_y * height * 0.5));

    }

    @Override
    public MantExp getSize() {
        return new MantExp(dsize);
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {

        Complex temp;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataDouble[r][0];
            double[] antialiasing_y = precalculatedJitterDataDouble[r][1];
            temp = new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
        }
        else {
            temp = new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
        }

        return temp;

    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        double[][] steps = createAntialiasingStepsDouble(temp_size_image_size_x, temp_size_image_size_y, adaptive, jitter, numberOfExtraSamples);
        antialiasing_x = steps[0];
        antialiasing_y = steps[1];
    }
}
