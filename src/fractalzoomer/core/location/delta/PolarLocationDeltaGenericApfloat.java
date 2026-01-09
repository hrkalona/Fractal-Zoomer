package fractalzoomer.core.location.delta;

import fractalzoomer.core.location.normal.PolarLocationNormalApfloatArbitrary;
import fractalzoomer.core.numerics.BigComplex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericApfloat extends PolarLocationNormalApfloatArbitrary {

    public PolarLocationDeltaGenericApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int widht, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, widht, height, circle_period, rotation_center, rotation_vals, fractal, js);

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

    protected BigComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub(reference);
    }


    @Override
    public MantExp getMaxSizeInImage() {
        return new MantExp(MyApfloat.fp.multiply(size, new MyApfloat(Math.exp(mulx * width * 0.5))));
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigComplex tempbc = new BigComplex(ddxcenter, ddycenter);

        if(rotation.shouldRotate(ddxcenter, ddycenter)) {
            tempbc = rotation.rotate(tempbc);
        }

        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }

    @Override
    public MantExp getSize() {
        return getMaxSizeInImage();
    }
}
