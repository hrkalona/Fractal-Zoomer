package fractalzoomer.core.location.delta;

import fractalzoomer.core.location.normal.PolarLocationNormalBigIntNumArbitrary;
import fractalzoomer.core.numerics.BigIntNum;
import fractalzoomer.core.numerics.BigIntNumComplex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericBigIntNum extends PolarLocationNormalBigIntNumArbitrary {

    public PolarLocationDeltaGenericBigIntNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int widht, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, widht, height, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaGenericBigIntNum(PolarLocationDeltaGenericBigIntNum other) {

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
    public MantExp getMaxSizeInImage() {
        return ddsize.mult(new BigIntNum(Math.exp(mulx * width * 0.5))).getMantExp();
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigIntNumComplex tempbc = new BigIntNumComplex(ddxcenter, ddycenter);

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
