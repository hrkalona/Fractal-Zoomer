package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDeepBigIntNum extends CartesianLocationDeltaGenericBigIntNum {

    public CartesianLocationDeltaDeepBigIntNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, rotation_center, rotation_vals, fractal, js);

    }

    public CartesianLocationDeltaDeepBigIntNum(CartesianLocationDeltaDeepBigIntNum other) {

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
