package fractalzoomer.core.location.normal;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.location.Location;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationNormalDouble extends Location {

    private double muly;
    private double mulx;
    private double start;

    private double xcenter;
    private double ycenter;

    private int image_size;

    private double center;

    private double[] antialiasing_x;

    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;

    private double emulx;
    private double Invemulx;

    private double cosmuly;
    private double sinmuly;

    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private double temp_r;

    private JitterSettings js;

    private boolean requiresVariablePixelSize;


    public PolarLocationNormalDouble(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int image_size_in, double circle_period, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;
        this.image_size = offset.getImageSize(image_size_in);

        requiresVariablePixelSize = fractal.requiresVariablePixelSize();

        xcenter = xCenter.doubleValue();
        ycenter = yCenter.doubleValue();
        double dsize = size.doubleValue();

        center = Math.log(dsize);

        muly = (2 * circle_period * Math.PI) / image_size;

        mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        emulx = Math.exp(mulx);
        Invemulx = 1 / emulx;

        cosmuly = Math.cos(muly);
        sinmuly = Math.sin(muly);

        this.js = js;

    }

    public PolarLocationNormalDouble(PolarLocationNormalDouble other) {

        super(other);

        fractal = other.fractal;

        xcenter = other.xcenter;
        ycenter = other.ycenter;

        mulx = other.mulx;
        muly = other.muly;
        start = other.start;

        emulx = other.emulx;
        Invemulx = other.Invemulx;
        cosmuly = other.cosmuly;
        sinmuly = other.sinmuly;

        center = other.center;

        image_size = other.image_size;

        antialiasing_y_cos = other.antialiasing_y_cos;
        antialiasing_y_sin = other.antialiasing_y_sin;
        antialiasing_x = other.antialiasing_x;

        js = other.js;

        requiresVariablePixelSize = other.requiresVariablePixelSize;
    }

    public void setVariablePixelSize(double expValue) {
        fractal.setVariablePixelSize(new MantExp(mulx * expValue));
    }

    private Complex getComplexBase(int x, int y) {
        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);

            temp_r = Math.exp((x + res[1]) * mulx + start);

            double f = (y + res[0]) * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }
        else {
            if (y == indexY + 1) {
                double tempSin = temp_sf * cosmuly + temp_cf * sinmuly;
                double tempCos = temp_cf * cosmuly - temp_sf * sinmuly;

                temp_sf = tempSin;
                temp_cf = tempCos;
            } else if (y == indexY - 1) {
                double tempSin = temp_sf * cosmuly - temp_cf * sinmuly;
                double tempCos = temp_cf * cosmuly + temp_sf * sinmuly;

                temp_sf = tempSin;
                temp_cf = tempCos;
            } else {
                double f = y * muly;
                temp_sf = Math.sin(f);
                temp_cf = Math.cos(f);
            }

            if (x == indexX + 1) {
                temp_r = temp_r * emulx;
            } else if (x == indexX - 1) {
                temp_r = temp_r * Invemulx;
            } else {
                temp_r = Math.exp(x * mulx + start);
            }
        }

        indexX = x;
        indexY = y;

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_r);
        }

        return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
    }
    @Override
    public GenericComplex getComplex(int x, int y) {

        return getComplexBase(offset.getX(x), offset.getY(y));

    }

    @Override
    public void precalculateY(int y) {

        y = offset.getY(y);

        if(!js.enableJitter) {
            if (y == indexY + 1) {
                double tempSin = temp_sf * cosmuly + temp_cf * sinmuly;
                double tempCos = temp_cf * cosmuly - temp_sf * sinmuly;

                temp_sf = tempSin;
                temp_cf = tempCos;
            } else if (y == indexY - 1) {
                double tempSin = temp_sf * cosmuly - temp_cf * sinmuly;
                double tempCos = temp_cf * cosmuly + temp_sf * sinmuly;

                temp_sf = tempSin;
                temp_cf = tempCos;
            } else {
                double f = y * muly;
                temp_sf = Math.sin(f);
                temp_cf = Math.cos(f);
            }
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                temp_r = temp_r * emulx;
            } else if (x == indexX - 1) {
                temp_r = temp_r * Invemulx;
            } else {
                temp_r = Math.exp(x * mulx + start);
            }
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_r);
        }

        indexX = x;

    }


    private Complex getComplexWithXBase(int x) {
        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            temp_r = temp_r * emulx;
        }
        else if(x == indexX - 1) {
            temp_r = temp_r * Invemulx;
        }
        else {
            temp_r = Math.exp(x * mulx + start);
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_r);
        }

        indexX = x;
        return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
    }
    @Override
    public GenericComplex getComplexWithX(int x) {

        return getComplexWithXBase(offset.getX(x));

    }

    @Override
    public boolean isPolar() {return true;}

    private Complex getComplexWithYBase(int y) {
        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

        if(y == indexY + 1) {
            double tempSin = temp_sf * cosmuly + temp_cf * sinmuly;
            double tempCos = temp_cf * cosmuly - temp_sf * sinmuly;

            temp_sf = tempSin;
            temp_cf = tempCos;
        }
        else if(y == indexY - 1) {
            double tempSin = temp_sf * cosmuly - temp_cf * sinmuly;
            double tempCos = temp_cf * cosmuly + temp_sf * sinmuly;

            temp_sf = tempSin;
            temp_cf = tempCos;
        }
        else {
            double f = y * muly;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }

        indexY = y;

        return new Complex(xcenter + temp_r * temp_cf, ycenter + temp_r * temp_sf);
    }

    @Override
    public GenericComplex getComplexWithY(int y) {

        return getComplexWithYBase(offset.getY(y));

    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter) {
        super.createAntialiasingSteps(adaptive, jitter);
        double[][] steps = createAntialiasingPolarStepsDouble(mulx, muly, adaptive, jitter);
        antialiasing_x = steps[0];
        antialiasing_y_sin = steps[1];
        antialiasing_y_cos = steps[2];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        double r2, sf2, cf2;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataPolarDouble[r][0];
            double[] antialiasing_y_sin = precalculatedJitterDataPolarDouble[r][1];
            double[] antialiasing_y_cos = precalculatedJitterDataPolarDouble[r][2];

            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            r2 = temp_r * antialiasing_x[sample];
        }
        else {
            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            r2 = temp_r * antialiasing_x[sample];
        }


        if(requiresVariablePixelSize) {
            setVariablePixelSize(r2);
        }

        return new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2);
    }
}
