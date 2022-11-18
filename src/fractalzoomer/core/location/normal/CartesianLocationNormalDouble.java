package fractalzoomer.core.location.normal;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class CartesianLocationNormalDouble extends Location {

    private double temp_size_image_size_x;
    private double temp_size_image_size_y;
    private double temp_xcenter_size;
    private double temp_ycenter_size;

    private double[] antialiasing_x;

    private double[] antialiasing_y;

    private double size;

    private double height_ratio;

    private double tempY;
    private double tempX;

    private JitterSettings js;

    public CartesianLocationNormalDouble(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        this.height_ratio = height_ratio;

        double dsize = size.doubleValue();

        this.size = dsize;

        double size_2_x = dsize * 0.5;
        double temp = dsize * height_ratio;
        double size_2_y = temp * 0.5;
        temp_size_image_size_x = dsize / image_size;
        temp_size_image_size_y = temp / image_size;

        temp_xcenter_size = xCenter.doubleValue() - size_2_x;
        temp_ycenter_size = yCenter.doubleValue() + size_2_y;

        this.js = js;

    }

    public CartesianLocationNormalDouble(CartesianLocationNormalDouble other) {

        super();

        fractal = other.fractal;

        temp_size_image_size_x = other.temp_size_image_size_x;
        temp_size_image_size_y = other.temp_size_image_size_y;
        temp_xcenter_size = other.temp_xcenter_size;
        temp_ycenter_size = other.temp_ycenter_size;

        size = other.size;
        height_ratio = other.height_ratio;

        antialiasing_y = other.antialiasing_y;
        antialiasing_x = other.antialiasing_x;

        js = other.js;
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        //No need to do the index optimization as adding some ifs wont really improve the performance
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            tempX = temp_xcenter_size + temp_size_image_size_x * (x + res[1]);
            tempY = temp_ycenter_size - temp_size_image_size_y * (y + res[0]);
        }
        else {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
        }

        indexX = x;
        indexY = y;

        return new Complex(tempX, tempY);
    }

    @Override
    public void precalculateY(int y) {

        if(!js.enableJitter) {
            tempY = temp_ycenter_size - temp_size_image_size_y * y;
        }
        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        if(!js.enableJitter) {
            tempX = temp_xcenter_size + temp_size_image_size_x * x;
        }
        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        if(js.enableJitter) {
            return getComplex(x, indexY);
        }

        tempX = temp_xcenter_size + temp_size_image_size_x * x;
        indexX = x;
        return new Complex(tempX, tempY);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {

        if(js.enableJitter) {
            return getComplex(indexX, y);
        }

        tempY = temp_ycenter_size - temp_size_image_size_y * y;
        indexY = y;
        return new Complex(tempX, tempY);
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive) {
        double[][] steps = createAntialiasingStepsDouble(temp_size_image_size_x, temp_size_image_size_y, adaptive);
        antialiasing_x = steps[0];
        antialiasing_y = steps[1];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample) {
        return new Complex(tempX + antialiasing_x[sample], tempY + antialiasing_y[sample]);
    }
}