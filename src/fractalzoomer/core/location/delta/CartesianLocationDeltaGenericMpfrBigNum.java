package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.location.normal.CartesianLocationNormalMpfrBigNumArbitrary;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public abstract class CartesianLocationDeltaGenericMpfrBigNum extends CartesianLocationNormalMpfrBigNumArbitrary {
    protected CartesianLocationDeltaGenericMpfrBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);

    }

    protected CartesianLocationDeltaGenericMpfrBigNum(CartesianLocationDeltaGenericMpfrBigNum other) {

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
        MpfrBigNumComplex tempbn = new MpfrBigNumComplex(ddxcenter, ddycenter);
        tempbn = rotation.rotate(tempbn);
        tempbn = fractal.getPlaneTransformedPixel(tempbn);
        return tempbn;
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(height_ratio == 1) {
            MpfrBigNum temp = ddsize.divide2();
            return temp.mult(MpfrBigNum.SQRT_TWO, temp).getMantExp();
        }
        else {
            MpfrBigNum temp = ddsize.divide2();
            MpfrBigNum temp2 = temp.mult(height_ratio);
            temp.square(temp);
            temp.add(temp2.square(temp2), temp);
            return temp.sqrt(temp).getMantExp();
        }
    }

}
