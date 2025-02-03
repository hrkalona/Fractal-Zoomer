package fractalzoomer.core.location.delta;

import fractalzoomer.core.location.normal.PolarLocationNormalMpirBigNumArbitrary;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.core.numerics.MpirBigNumComplex;
import fractalzoomer.core.numerics.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericMpirBigNum extends PolarLocationNormalMpirBigNumArbitrary {

    public PolarLocationDeltaGenericMpirBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaGenericMpirBigNum(PolarLocationDeltaGenericMpirBigNum other) {

        super(other);
        reference = other.reference;

    }


    protected MpirBigNumComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub_mutable(reference);
    }

    protected MpirBigNumComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub_mutable(reference);
    }

    protected MpirBigNumComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub_mutable(reference);
    }

    protected MpirBigNumComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub_mutable(reference);
    }

    @Override
    public GenericComplex getReferencePoint() {
        MpirBigNumComplex tempbn = new MpirBigNumComplex(new MpirBigNum(ddxcenter), new MpirBigNum(ddycenter));

        if(rotation.shouldRotate(ddxcenter, ddycenter)) {
            tempbn = rotation.rotate(tempbn);
        }

        tempbn = fractal.getPlaneTransformedPixel(tempbn);
        return tempbn;
    }

    @Override
    public MantExp getMaxSizeInImage() {

        return ddsize.mult(new MpirBigNum(Math.exp(mulx * width * 0.5))).getMantExp();

    }

    @Override
    public MantExp getSize() {
        return getMaxSizeInImage();
    }
}
