package fractalzoomer.core.location.delta;

import fractalzoomer.core.DDComplex;
import fractalzoomer.core.DoubleDouble;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.location.normal.PolarLocationNormalDoubleDoubleArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationDeltaDoubleDouble extends PolarLocationNormalDoubleDoubleArbitrary {

    public PolarLocationDeltaDoubleDouble(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, js);

    }

    public PolarLocationDeltaDoubleDouble(PolarLocationDeltaDoubleDouble other) {

        super(other);
        reference = other.reference;

    }

    protected DDComplex getComplexInternal(int x, int y) {
        return getComplexBase(x, y).sub(reference);
    }

    protected DDComplex getComplexWithXInternal(int x) {
        return getComplexWithXBase(x).sub(reference);
    }

    protected DDComplex getComplexWithYInternal(int y) {
        return getComplexWithYBase(y).sub(reference);
    }

    protected DDComplex getAntialiasingComplexInternal(int sample) {
        return getAntialiasingComplexBase(sample).sub(reference);
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
    public GenericComplex getAntialiasingComplex(int sample) {
        return getAntialiasingComplexInternal(sample).toComplex();
    }

    @Override
    public MantExp getMaxSizeInImage() {
        DoubleDouble end = ddcenter.add(ddmulx.multiply(image_size * 0.5));
        return end.exp().getMantExp();
    }

    @Override
    public GenericComplex getReferencePoint() {
        DDComplex tempbc = new DDComplex(ddxcenter, ddycenter);
        tempbc = rotation.rotate(tempbc);
        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }
}
