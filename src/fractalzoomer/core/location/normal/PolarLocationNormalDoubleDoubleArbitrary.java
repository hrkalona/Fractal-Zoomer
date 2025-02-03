package fractalzoomer.core.location.normal;

import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.DDComplex;
import fractalzoomer.core.numerics.DoubleDouble;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationNormalDoubleDoubleArbitrary extends Location {
    protected Rotation rotation;

    protected int width;

    protected DoubleDouble ddxcenter;
    protected DoubleDouble ddycenter;
    protected DoubleDouble ddsize;
    private double[] antialiasing_x;

    private double muly;
    protected double mulx;
    protected DoubleDouble ddmulx;
    private DoubleDouble expddstartx;
    private double starty;
    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;


    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private DoubleDouble temp_ddsf;
    private DoubleDouble temp_ddcf;
    private DoubleDouble temp_ddr;

    private DoubleDouble ddemulx;
    private DoubleDouble ddInvemulx;

    private double cosmuly;
    private double sinmuly;

    private JitterSettings js;

    private boolean requiresVariablePixelSize;

    public PolarLocationNormalDoubleDoubleArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;
        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);

        ddsize = new DoubleDouble(size);

        requiresVariablePixelSize = fractal.requiresVariablePixelSize();

        ddxcenter = new DoubleDouble(xCenter);
        ddycenter = new DoubleDouble(yCenter);

        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        muly = (2 * circle_period * Math.PI) / image_size;

        mulx = muly * height_ratio;

        ddmulx = new DoubleDouble(mulx);

        expddstartx = ddsize.multiply(new DoubleDouble(Math.exp(-mulx * image_size * coefx)));
        starty = muly * image_size * (0.5 - coefy);

        rotation = new Rotation(new DDComplex(rotation_vals[0], rotation_vals[1]), new DDComplex(rotation_center[0], rotation_center[1]));

        double emulx = Math.exp(mulx);
        ddemulx = new DoubleDouble(emulx);
        ddInvemulx = new DoubleDouble(1 / emulx);

        cosmuly = Math.cos(muly);
        sinmuly = Math.sin(muly);

        this.js = js;
        this.width = width;
    }

    public PolarLocationNormalDoubleDoubleArbitrary(PolarLocationNormalDoubleDoubleArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        ddsize = other.ddsize;

        ddmulx = other.ddmulx;
        muly = other.muly;
        mulx = other.mulx;
        expddstartx = other.expddstartx;
        starty = other.starty;

        ddemulx = other.ddemulx;
        ddInvemulx = other.ddInvemulx;
        cosmuly = other.cosmuly;
        sinmuly = other.sinmuly;

        width = other.width;

        rotation = other.rotation;

        antialiasing_y_cos = other.antialiasing_y_cos;
        antialiasing_y_sin = other.antialiasing_y_sin;
        antialiasing_x = other.antialiasing_x;

        js = other.js;
        requiresVariablePixelSize = other.requiresVariablePixelSize;

    }

    public void setVariablePixelSize(DoubleDouble expValue) {
        fractal.setVariablePixelSize(expValue.multiply(ddmulx).getMantExp());
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected DDComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            temp_ddr = new DoubleDouble(Math.exp((x + res[1]) * mulx)).multiply(expddstartx);

            double f = (y + res[0]) * muly + starty;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }
        else {
            if (x == indexX + 1) {
                temp_ddr = temp_ddr.multiply(ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = temp_ddr.multiply(ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = new DoubleDouble(Math.exp(x * mulx)).multiply(expddstartx);
            }

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
            } else if (y != indexY) {
                double f = y * muly + starty;
                temp_sf = Math.sin(f);
                temp_cf = Math.cos(f);
            }
        }

        temp_ddsf = new DoubleDouble(temp_sf);
        temp_ddcf = new DoubleDouble(temp_cf);

        indexX = x;
        indexY = y;

        //As alternative as it works with low prec as well sin/cos

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        DDComplex temp = new DDComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
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
            } else if (y != indexY) {
                double f = y * muly + starty;
                temp_sf = Math.sin(f);
                temp_cf = Math.cos(f);
            }

            temp_ddsf = new DoubleDouble(temp_sf);
            temp_ddcf = new DoubleDouble(temp_cf);
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                temp_ddr = temp_ddr.multiply(ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = temp_ddr.multiply(ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = new DoubleDouble(Math.exp(x * mulx)).multiply(expddstartx);
            }
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        indexX = x;

    }

    @Override
    public GenericComplex getComplexWithX(int x) {
        return getComplexWithXBase(offset.getX(x));
    }

    protected DDComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if (x == indexX + 1) {
            temp_ddr = temp_ddr.multiply(ddemulx);
        } else if (x == indexX - 1) {
            temp_ddr = temp_ddr.multiply(ddInvemulx);
        } else if (x != indexX) {
            temp_ddr = new DoubleDouble(Math.exp(x * mulx)).multiply(expddstartx);
        }

        indexX = x;

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        DDComplex temp = new DDComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public boolean isPolar() {return true;}

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected DDComplex getComplexWithYBase(int y) {

        if(js.enableJitter) {
            return getComplexBase(indexX, y);
        }

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
        } else if (y != indexY) {
            double f = y * muly + starty;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }

        temp_ddsf = new DoubleDouble(temp_sf);
        temp_ddcf = new DoubleDouble(temp_cf);

        indexY = y;

        DDComplex temp = new DDComplex(ddxcenter.add(temp_ddr.multiply(temp_ddcf)), ddycenter.add(temp_ddr.multiply(temp_ddsf)));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int aaType, int numberOfExtraSamples, boolean gaussian) {
        super.createAntialiasingSteps(adaptive, jitter, aaType, numberOfExtraSamples, gaussian);
        double[][] steps = createAntialiasingPolarStepsDouble(mulx, muly, adaptive, jitter, aaType, numberOfExtraSamples, gaussian);
        antialiasing_x = steps[0];
        antialiasing_y_sin = steps[1];
        antialiasing_y_cos = steps[2];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }

    protected DDComplex getAntialiasingComplexBase(int sample, int loc) {

        DoubleDouble ddr2;
        double sf2, cf2;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataPolarDouble[r][0];
            double[] antialiasing_y_sin = precalculatedJitterDataPolarDouble[r][1];
            double[] antialiasing_y_cos = precalculatedJitterDataPolarDouble[r][2];

            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            ddr2 = temp_ddr.multiply(new DoubleDouble(antialiasing_x[sample]));
        }
        else {
            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            ddr2 = temp_ddr.multiply(new DoubleDouble(antialiasing_x[sample]));
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(ddr2);
        }

        DDComplex temp = new DDComplex(ddxcenter.add(ddr2.multiply(new DoubleDouble(cf2))), ddycenter.add(ddr2.multiply(new DoubleDouble(sf2))));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
