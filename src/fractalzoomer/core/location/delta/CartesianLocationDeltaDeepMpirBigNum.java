package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDeepMpirBigNum extends CartesianLocationDeltaGenericMpirBigNum {

    public CartesianLocationDeltaDeepMpirBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);

    }

    public CartesianLocationDeltaDeepMpirBigNum(CartesianLocationDeltaDeepMpirBigNum other) {

        super(other);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexInternal(offset.getX(x), offset.getY(y)).toMantExpComplex();
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXInternal(offset.getX(x)).toMantExpComplex();
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYInternal(offset.getY(y)).toMantExpComplex();
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {

        return getAntialiasingComplexInternal(sample, loc).toMantExpComplex();

    }

}
