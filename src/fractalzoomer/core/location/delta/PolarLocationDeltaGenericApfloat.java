package fractalzoomer.core.location.delta;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.location.normal.PolarLocationNormalApfloatArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericApfloat extends PolarLocationNormalApfloatArbitrary {

    public PolarLocationDeltaGenericApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaGenericApfloat(PolarLocationDeltaGenericApfloat other) {

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
    public MantExp getMaxSizeInImage() {
        Apfloat end = MyApfloat.fp.add(ddcenter, MyApfloat.fp.multiply(MyApfloat.fp.multiply(ddmulx, new MyApfloat(image_size)), point5));
        return new MantExp(expFunction(end));
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigComplex tempbc = new BigComplex(ddxcenter, ddycenter);
        tempbc = rotation.rotate(tempbc);
        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }
}
