package fractalzoomer.core.location;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

public class CartesianLocationBigNumArbitrary extends Location {
    private BigNum bntemp_size_image_size_x;
    private BigNum bntemp_size_image_size_y;
    private BigNum bntemp_xcenter_size;
    private BigNum bntemp_ycenter_size;

    private BigNumComplex rotation;
    private BigNumComplex rot_center;

    private BigNum[] bnantialiasing_y;
    private BigNum[] bnantialiasing_x;

    //Dont copy those
    private BigNum bntempX;
    private BigNum bntempY;

    private BigNum bnsize;
    private double size;
    private Apfloat ddsize;

    private double height_ratio;

    public CartesianLocationBigNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal) {

        super();

        this.highPresicion = true;
        this.fractal = fractal;

        this.height_ratio = height_ratio;

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

    public CartesianLocationBigNumArbitrary(CartesianLocationBigNumArbitrary other) {
        highPresicion = other.highPresicion;
        fractal = other.fractal;

        bnsize = new BigNum(other.bnsize);
        ddsize = other.ddsize;
        height_ratio = other.height_ratio;

        bntemp_size_image_size_x = new BigNum(other.bntemp_size_image_size_x);
        bntemp_size_image_size_y = new BigNum(other.bntemp_size_image_size_y);
        bntemp_xcenter_size = new BigNum(other.bntemp_xcenter_size);
        bntemp_ycenter_size = new BigNum(other.bntemp_ycenter_size);

        rotation = new BigNumComplex(other.rotation);
        rot_center = new BigNumComplex(other.rot_center);

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

    @Override
    public GenericComplex getComplex(int x, int y) {
        bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
        bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));

        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void precalculateY(int y) {

        bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));

    }

    @Override
    public void precalculateX(int x) {

        bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));
        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        BigNum[][] steps = createAntialiasingStepsBigNum(bntemp_size_image_size_x, bntemp_size_image_size_y, adaptive);
        bnantialiasing_x = steps[0];
        bnantialiasing_y = steps[1];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        BigNumComplex temp = new BigNumComplex(bntempX.add(bnantialiasing_x[sample]), bntempY.add(bnantialiasing_y[sample]));

        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public Complex getComplexOrbit(int x, int y) {
        bntempX = bntemp_xcenter_size.add(bntemp_size_image_size_x.mult(new BigNum(x)));
        bntempY = bntemp_ycenter_size.sub(bntemp_size_image_size_y.mult(new BigNum(y)));

        BigNumComplex temp = new BigNumComplex(bntempX, bntempY);

        return temp.toComplex();
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(height_ratio == 1) {
            return new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5)), MyApfloat.SQRT_TWO));
        }
        else {
            Apfloat temp = MyApfloat.fp.multiply(ddsize, new MyApfloat(0.5));
            Apfloat temp2 = MyApfloat.fp.multiply(temp, new MyApfloat(height_ratio));
            return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2))));
        }
    }

}
