package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaApfloat extends PolarLocationDeltaGenericApfloat {

    public PolarLocationDeltaApfloat(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaApfloat(PolarLocationDeltaApfloat other) {

        super(other);

    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexInternal(offset.getX(x), offset.getY(y)).toComplex();
    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXInternal(offset.getX(x)).toComplex();
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYInternal(offset.getY(y)).toComplex();
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexInternal(sample, loc).toComplex();
    }

    @Override
    protected Apfloat expFunction(Apfloat val) {
        return MyApfloat.fastExp(val);
    }
}
