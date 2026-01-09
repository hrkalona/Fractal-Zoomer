package fractalzoomer.core.location.delta;

import fractalzoomer.core.location.normal.PolarLocationNormalBigNumArbitrary;
import fractalzoomer.core.numerics.BigNum;
import fractalzoomer.core.numerics.BigNumComplex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericBigNum extends PolarLocationNormalBigNumArbitrary {

    public PolarLocationDeltaGenericBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int widht, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, widht, height, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaGenericBigNum(PolarLocationDeltaGenericBigNum other) {

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
    public MantExp getMaxSizeInImage() {
        return ddsize.mult(BigNum.create(Math.exp(mulx * width * 0.5))).getMantExp();
    }

    @Override
    public GenericComplex getReferencePoint() {
        BigNumComplex tempbc = new BigNumComplex(ddxcenter, ddycenter);

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
