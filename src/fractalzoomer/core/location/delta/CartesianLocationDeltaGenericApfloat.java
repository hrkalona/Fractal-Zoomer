package fractalzoomer.core.location.delta;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.location.normal.CartesianLocationNormalApfloatArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public abstract class CartesianLocationDeltaGenericApfloat extends CartesianLocationNormalApfloatArbitrary {

    protected CartesianLocationDeltaGenericApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {
        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);
    }

    protected CartesianLocationDeltaGenericApfloat(CartesianLocationDeltaGenericApfloat other) {

        super(other);
        reference = other.reference;
    }


    protected BigComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub(reference);
    }

    protected BigComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub(reference);
    }

    protected BigComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub(reference);
    }

    protected BigComplex getAntialiasingComplexInternal(int sample) {
        return getAntialiasingComplexBase(sample).sub(reference);
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigComplex tempbc = new BigComplex(ddxcenter, ddycenter);
        tempbc = rotation.rotate(tempbc);
        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(height_ratio.compareTo(Apfloat.ONE) == 0) {
            return new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddsize, point5), MyApfloat.SQRT_TWO));
        }
        else {
            Apfloat temp = MyApfloat.fp.multiply(ddsize, point5);
            Apfloat temp2 = MyApfloat.fp.multiply(temp, height_ratio);
            return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(MyApfloat.fp.multiply(temp, temp), MyApfloat.fp.multiply(temp2, temp2))));
        }
    }
}
