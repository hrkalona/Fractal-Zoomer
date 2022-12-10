package fractalzoomer.core.location.delta;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDeepApfloat extends CartesianLocationDeltaGenericApfloat {
    public CartesianLocationDeltaDeepApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);

    }

    public CartesianLocationDeltaDeepApfloat(CartesianLocationDeltaDeepApfloat other) {

        super(other);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        BigComplex temp = getComplexInternal(offset.getX(x), offset.getY(y));
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        BigComplex temp = getComplexWithXInternal(offset.getX(x));
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        BigComplex temp = getComplexWithYInternal(offset.getY(y));
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        BigComplex temp = getAntialiasingComplexInternal(sample);
        return new MantExpComplex(getMantExp(temp.getRe()), getMantExp(temp.getIm()));
    }

}
