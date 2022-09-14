package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class CartesianLocationBigNum extends Location {
    private double temp_size_image_size_x;
    private double temp_size_image_size_y;
    private double temp_xcenter_size;
    private double temp_ycenter_size;

    private BigNum bntemp_size_image_size_x;
    private BigNum bntemp_size_image_size_y;
    private BigNum bntemp_xcenter_size;
    private BigNum bntemp_ycenter_size;

    private BigNumComplex rotation;
    private BigNumComplex rot_center;

    private double[] antialiasing_y;
    private BigNum[] bnantialiasing_y;
    private BigNum[] bnantialiasing_x;

    //Dont copy those
    private double tempY;
    private double tempX;
    private BigNum bntempX;
    private BigNum bntempY;

    private BigNum bnsize;
    private double size;
    private Apfloat ddsize;

    private double height_ratio;

    public CartesianLocationBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, boolean highPresicion) {

        super();

        this.highPresicion = highPresicion;
        this.fractal = fractal;

        this.height_ratio = height_ratio;

        if(highPresicion) {
            Apfloat point5 = new MyApfloat(0.5);
            Apfloat size_2_x = MyApfloat.fp.multiply(size, point5);
            Apfloat ddimage_size = new MyApfloat(image_size);
            Apfloat ddheight_ratio = new MyApfloat(height_ratio);

            bnsize = new BigNum(size);
            ddsize = size;

            Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, ddheight_ratio), point5);
            bntemp_size_image_size_x = new BigNum(MyApfloat.fp.divide(size, ddimage_size));
            bntemp_size_image_size_y = new BigNum(MyApfloat.fp.divide(MyApfloat.fp.multiply(size, ddheight_ratio), ddimage_size));

            bntemp_xcenter_size = new BigNum(MyApfloat.fp.subtract(xCenter, size_2_x));
            bntemp_ycenter_size =  new BigNum(MyApfloat.fp.add(yCenter, size_2_y));

            rotation = new BigNumComplex(rotation_vals[0], rotation_vals[1]);
            rot_center = new BigNumComplex(rotation_center[0], rotation_center[1]);
        }
        else {
            xcenter = xCenter.doubleValue();
            ycenter = yCenter.doubleValue();


            double dsize = size.doubleValue();

            this.size = dsize;

            double size_2_x = dsize * 0.5;
            double size_2_y = (dsize * height_ratio) * 0.5;
            temp_size_image_size_x = dsize / image_size;
            temp_size_image_size_y = (dsize * height_ratio) / image_size;

            temp_xcenter_size = xcenter - size_2_x;
            temp_ycenter_size = ycenter + size_2_y;
        }

    }

    public CartesianLocationBigNum(CartesianLocationBigNum other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        xcenter = other.xcenter;
        ycenter = other.ycenter;
        temp_size_image_size_x = other.temp_size_image_size_x;
        temp_size_image_size_y = other.temp_size_image_size_y;
        temp_xcenter_size = other.temp_xcenter_size;
        temp_ycenter_size = other.temp_ycenter_size;

        if(other.bnsize != null) {
            bnsize = new BigNum(other.bnsize);
        }
        size = other.size;
        ddsize = other.ddsize;
        height_ratio = other.height_ratio;

        if(other.bntemp_size_image_size_x != null) {
            bntemp_size_image_size_x = new BigNum(other.bntemp_size_image_size_x);
            bntemp_size_image_size_y = new BigNum(other.bntemp_size_image_size_y);
            bntemp_xcenter_size = new BigNum(other.bntemp_xcenter_size);
            bntemp_ycenter_size = new BigNum(other.bntemp_ycenter_size);
            rotation = new BigNumComplex(other.rotation);
            rot_center = new BigNumComplex(other.rot_center);
        }

        antialiasing_y = other.antialiasing_y;
        antialiasing_x = other.antialiasing_x;

        if(other.bnantialiasing_x != null && other.bnantialiasing_y != null) {
            bnantialiasing_x = new BigNum[other.bnantialiasing_x.length];
            bnantialiasing_y = new BigNum[other.bnantialiasing_y.length];
            for (int i = 0; i < bnantialiasing_x.length; i++) {
                bnantialiasing_x[i] = new BigNum(other.bnantialiasing_x[i]);
                bnantialiasing_y[i] = new BigNum(other.bnantialiasing_y[i]);
            }
        }

        if(other.reference != null) {
            reference = new BigNumComplex((BigNumComplex) other.reference);
        }

    }

    public CartesianLocationBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size) {

        super();

        this.highPresicion = true;

        this.height_ratio = height_ratio;

        bnsize = new BigNum(size);
        ddsize = size;

        Apfloat point5 = new MyApfloat(0.5);
        Apfloat size_2_x = MyApfloat.fp.multiply(size, point5);
        Apfloat ddimage_size = new MyApfloat(image_size);
        Apfloat ddiheight_ratio = new MyApfloat(height_ratio);

        Apfloat size_2_y = MyApfloat.fp.multiply(MyApfloat.fp.multiply(size, ddiheight_ratio), point5);
        bntemp_size_image_size_x = new BigNum(MyApfloat.fp.divide(size, ddimage_size));
        bntemp_size_image_size_y = new BigNum(MyApfloat.fp.divide(MyApfloat.fp.multiply(size, ddiheight_ratio), ddimage_size));

        bntemp_xcenter_size = new BigNum(MyApfloat.fp.subtract(xCenter, size_2_x));
        bntemp_ycenter_size =  new BigNum(MyApfloat.fp.add(yCenter, size_2_y));

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        if(highPresicion) {

            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));

            BigNumComplex temp = new BigNumComplex(bntempX, bntempY);

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public void precalculateY(int y) {

        if(highPresicion) {
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
        }

    }

    @Override
    public void precalculateX(int x) {

        if(highPresicion) {
            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
        }

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        if(highPresicion) {
            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
            BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        if(highPresicion) {
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));
            BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);
            temp = fractal.getPlaneTransformedPixel(temp);
            return temp.sub(reference).toComplex();
        }
        else {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        if(highPresicion) {
            BigNum[][] steps = createAntialiasingStepsBigNum(bntemp_size_image_size_x, bntemp_size_image_size_y, adaptive);
            bnantialiasing_x = steps[0];
            bnantialiasing_y = steps[1];
        }
        else {
            double[][] steps = createAntialiasingStepsDouble(temp_size_image_size_x, temp_size_image_size_y, adaptive);
            antialiasing_x = steps[0];
            antialiasing_y = steps[1];
        }
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        if(highPresicion) {
            BigNumComplex temp = new BigNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));

            temp = temp.sub(rot_center);
            temp = temp.times(rotation).plus(rot_center);

            temp = fractal.getPlaneTransformedPixel(temp);

            return temp.sub(reference).toComplex();
        }
        else {
            return new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
        }
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        if(highPresicion) {

            bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
            bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));

            BigNumComplex temp = new BigNumComplex(bntempX, bntempY);

            return temp.toComplex();
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
            return new Complex(tempX, tempY);
        }
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(highPresicion) {
            if(height_ratio == 1) {
                return new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5)), MyApfloat.SQRT_TWO));
            }
            else {
                Apfloat temp = MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5));
                Apfloat temp2 = MyApfloat.fp.multiply(temp, new MyApfloat(height_ratio));
                return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2))));
            }
        }
        else {
            if(height_ratio == 1) {
                double sqrt2 = Math.sqrt(2);
                return new MantExp(sqrt2 * size * 0.5);
            }
            else {
                double temp = size * 0.5;
                double temp2 = temp * height_ratio;
                return new MantExp(Math.sqrt(temp * temp + temp2 * temp2));
            }
        }
    }

}
