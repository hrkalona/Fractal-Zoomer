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

    protected CartesianLocationDeltaGenericApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {
        super(xCenter, yCenter, size, height_ratio, width, height, rotation_center, rotation_vals, fractal, js);
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

    protected BigComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub(reference);
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
    public MantExp getMaxSizeInImage() {
        Apfloat temp = MyApfloat.fp.multiply(ddtemp_xcenter_size, new MyApfloat(width * 0.5));

        Apfloat temp2 = MyApfloat.fp.multiply(ddtemp_ycenter_size, new MyApfloat(height * 0.5));

        temp = MyApfloat.fp.multiply(temp, temp);
        temp2 = MyApfloat.fp.multiply(temp2, temp2);
        return new MantExp(MyApfloat.fp.sqrt(MyApfloat.fp.add(temp, temp2)));
    }

    @Override
    public MantExp getSize() {
        return new MantExp(ddsize);
    }
}
