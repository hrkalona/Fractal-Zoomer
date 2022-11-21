package fractalzoomer.core.location.delta;

import fractalzoomer.core.DDComplex;
import fractalzoomer.core.DoubleDouble;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.location.normal.CartesianLocationNormalDoubleDoubleArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationDeltaDoubleDouble extends CartesianLocationNormalDoubleDoubleArbitrary {
    public CartesianLocationDeltaDoubleDouble(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, image_size, rotation_center, rotation_vals, fractal, js);

    }

    public CartesianLocationDeltaDoubleDouble(CartesianLocationDeltaDoubleDouble other) {

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
    public GenericComplex getReferencePoint() {
        DDComplex tempbc = new DDComplex(ddxcenter, ddycenter);
        tempbc = rotation.rotate(tempbc);
        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }

    @Override
    public MantExp getMaxSizeInImage() {
        if(height_ratio == 1) { // ((size * 0.5) / image_size) * sqrt(image_size^2 + image_size^2) = ((size * 0.5) / image_size) * sqrt(2) * image_size
            DoubleDouble temp = ddsize.multiply(0.5);
            return temp.multiply(new DoubleDouble(2).sqrt()).getMantExp();
        }
        else {
            DoubleDouble temp = ddsize.multiply(0.5);
            DoubleDouble temp2 = temp.multiply(height_ratio);
            return temp.sqr().add(temp2.sqr()).sqrt().getMantExp();
        }
    }
}
