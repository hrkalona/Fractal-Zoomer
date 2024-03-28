package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.normal.CartesianLocationNormalBigNumArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public abstract class CartesianLocationDeltaGenericBigNum extends CartesianLocationNormalBigNumArbitrary {
    protected CartesianLocationDeltaGenericBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, rotation_center, rotation_vals, fractal, js);

    }

    protected CartesianLocationDeltaGenericBigNum(CartesianLocationDeltaGenericBigNum other) {

        super(other);
        reference = other.reference;

    }

    protected BigNumComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub(reference);
    }

    protected BigNumComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub(reference);
    }

    protected BigNumComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub(reference);
    }

    protected BigNumComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub(reference);
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigNumComplex tempbn = new BigNumComplex(ddxcenter, ddycenter);

        if(rotation.shouldRotate(ddxcenter, ddycenter)) {
            tempbn = rotation.rotate(tempbn);
        }

        tempbn = fractal.getPlaneTransformedPixel(tempbn);
        return tempbn;
    }

    @Override
    public MantExp getMaxSizeInImage() {
        BigNum temp = bntemp_size_image_size_x.mult(BigNum.create(width * 0.5));
        BigNum temp2 = bntemp_size_image_size_y.mult(BigNum.create(height * 0.5));

        temp = temp.square();
        temp2 = temp2.square();

        return temp.add(temp2).sqrt().getMantExp();
    }

    @Override
    public MantExp getSize() {
        return bnsize.getMantExp();
    }
}
