package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.location.normal.PolarLocationNormalMpfrBigNumArbitrary;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaGenericMpfrBigNum extends PolarLocationNormalMpfrBigNumArbitrary {

    public PolarLocationDeltaGenericMpfrBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaGenericMpfrBigNum(PolarLocationDeltaGenericMpfrBigNum other) {

        super(other);
        reference = other.reference;

    }


    protected MpfrBigNumComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub_mutable(reference);
    }

    protected MpfrBigNumComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub_mutable(reference);
    }

    protected MpfrBigNumComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub_mutable(reference);
    }

    protected MpfrBigNumComplex getAntialiasingComplexInternal(int sample) {
        return getAntialiasingComplexBase(sample).sub_mutable(reference);
    }

    @Override
    public GenericComplex getReferencePoint() {
        MpfrBigNumComplex tempbn = new MpfrBigNumComplex(new MpfrBigNum(ddxcenter), new MpfrBigNum(ddycenter));
        tempbn = rotation.rotate(tempbn);
        tempbn = fractal.getPlaneTransformedPixel(tempbn);
        return tempbn;
    }

    @Override
    public MantExp getMaxSizeInImage() {

        MpfrBigNum temp = ddmulx.mult(image_size * 0.5);
        temp.add(ddcenter, temp);
        temp.exp(temp);
        return temp.getMantExp();

    }
}
