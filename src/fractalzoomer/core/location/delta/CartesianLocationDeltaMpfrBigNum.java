package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaMpfrBigNum extends CartesianLocationDeltaGenericMpfrBigNum {
    public CartesianLocationDeltaMpfrBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);

    }

    public CartesianLocationDeltaMpfrBigNum(CartesianLocationDeltaMpfrBigNum other) {

        super(other);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexInternal(x, y).toComplex();
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXInternal(x).toComplex();
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYInternal(y).toComplex();
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {

        return getAntialiasingComplexInternal(sample).toComplex();

    }

}