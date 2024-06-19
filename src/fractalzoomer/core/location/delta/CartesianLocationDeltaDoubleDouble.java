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
    public CartesianLocationDeltaDoubleDouble(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super(xCenter, yCenter, size, height_ratio, width, height, rotation_center, rotation_vals, fractal, js);

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

    protected DDComplex getAntialiasingComplexInternal(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc).sub(reference);
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
    public GenericComplex getReferencePoint() {
        DDComplex tempbc = new DDComplex(ddxcenter, ddycenter);

        if(rotation.shouldRotate(ddxcenter, ddycenter)) {
            tempbc = rotation.rotate(tempbc);
        }

        tempbc = fractal.getPlaneTransformedPixel(tempbc);
        return tempbc;
    }

    @Override
    public MantExp getMaxSizeInImage() {
        DoubleDouble temp = ddtemp_size_image_size_x.multiply(width * 0.5);
        DoubleDouble temp2 = ddtemp_size_image_size_y.multiply(height * 0.5);

        temp = temp.sqr();
        temp2 = temp2.sqr();

        return temp.add(temp2).sqrt().getMantExp();
    }

    @Override
    public MantExp getSize() {
        return new MantExp(ddsize.doubleValue());
    }
}
