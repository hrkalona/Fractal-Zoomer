package fractalzoomer.core.location.delta;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaMpfrBigNum extends PolarLocationDeltaGenericMpfrBigNum {

    public PolarLocationDeltaMpfrBigNum(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaMpfrBigNum(PolarLocationDeltaMpfrBigNum other) {

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
}
