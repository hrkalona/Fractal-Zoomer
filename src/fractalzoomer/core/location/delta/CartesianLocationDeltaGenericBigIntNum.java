package fractalzoomer.core.location.delta;

import fractalzoomer.core.*;
import fractalzoomer.core.location.normal.CartesianLocationNormalBigIntNumArbitrary;
import fractalzoomer.core.location.normal.CartesianLocationNormalBigNumArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public abstract class CartesianLocationDeltaGenericBigIntNum extends CartesianLocationNormalBigIntNumArbitrary {
    protected CartesianLocationDeltaGenericBigIntNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, rotation_center, rotation_vals, fractal, js);

    }

    protected CartesianLocationDeltaGenericBigIntNum(CartesianLocationDeltaGenericBigIntNum other) {

        super(other);
        reference = other.reference;

    }

    protected BigIntNumComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub(reference);
    }

    protected BigIntNumComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub(reference);
    }

    protected BigIntNumComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub(reference);
    }

    protected BigIntNumComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub(reference);
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigIntNumComplex tempbn = new BigIntNumComplex(ddxcenter, ddycenter);

        if(rotation.shouldRotate(ddxcenter, ddycenter)) {
            tempbn = rotation.rotate(tempbn);
        }

        tempbn = fractal.getPlaneTransformedPixel(tempbn);
        return tempbn;
    }

    @Override
    public MantExp getMaxSizeInImage() {

        BigIntNum temp = bntemp_size_image_size_x.mult(width * 0.5);
        BigIntNum temp2 = bntemp_size_image_size_y.mult(height * 0.5);

        temp = temp.square();
        temp2 = temp2.square();

        return temp.add(temp2).sqrt().getMantExp();
    }

    @Override
    public MantExp getSize() {
        return bnsize.getMantExp();
    }
}
