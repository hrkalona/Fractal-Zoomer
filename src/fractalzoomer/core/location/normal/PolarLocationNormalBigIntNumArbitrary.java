package fractalzoomer.core.location.normal;

import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.BigIntNum;
import fractalzoomer.core.numerics.BigIntNumComplex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationNormalBigIntNumArbitrary extends Location {
    protected Rotation rotation;

    protected int width;

    protected BigIntNum ddxcenter;
    protected BigIntNum ddycenter;
    protected BigIntNum ddsize;
    private double[] antialiasing_x;

    private double muly;
    protected double mulx;
    protected BigIntNum ddmulx;
    private BigIntNum expddstartx;
    private double starty;
    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;


    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private BigIntNum temp_ddsf;
    private BigIntNum temp_ddcf;
    private BigIntNum temp_ddr;

    private BigIntNum ddemulx;
    private BigIntNum ddInvemulx;

    private double cosmuly;
    private double sinmuly;

    private JitterSettings js;

    private boolean requiresVariablePixelSize;

    public PolarLocationNormalBigIntNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;
        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);

        ddsize = new BigIntNum(size);

        requiresVariablePixelSize = fractal.requiresVariablePixelSize();

        ddxcenter = new BigIntNum(xCenter);
        ddycenter = new BigIntNum(yCenter);

        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        muly = (2 * circle_period * Math.PI) / image_size;

        mulx = muly * height_ratio;

        ddmulx = new BigIntNum(mulx);

        expddstartx = ddsize.mult(new BigIntNum(Math.exp(-mulx * image_size * coefx)));
        starty = muly * image_size * (0.5 - coefy);

        rotation = new Rotation(new BigIntNumComplex(rotation_vals[0], rotation_vals[1]), new BigIntNumComplex(rotation_center[0], rotation_center[1]));

        double emulx = Math.exp(mulx);
        ddemulx = new BigIntNum(emulx);
        ddInvemulx = new BigIntNum(1 / emulx);

        cosmuly = Math.cos(muly);
        sinmuly = Math.sin(muly);

        this.js = js;
        this.width = width;
    }

    public PolarLocationNormalBigIntNumArbitrary(PolarLocationNormalBigIntNumArbitrary other) {

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

    public void setVariablePixelSize(BigIntNum expValue) {
        fractal.setVariablePixelSize(expValue.mult(ddmulx).getMantExp());
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected BigIntNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            temp_ddr = new BigIntNum(Math.exp((x + res[1]) * mulx)).mult(expddstartx);

            double f = (y + res[0]) * muly + starty;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }
        else {
            if (x == indexX + 1) {
                temp_ddr = temp_ddr.mult(ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = temp_ddr.mult(ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = new BigIntNum(Math.exp(x * mulx)).mult(expddstartx);
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

        temp_ddcf = new BigIntNum(temp_cf);
        temp_ddsf = new BigIntNum(temp_sf);

        indexX = x;
        indexY = y;

        //As alternative as it works with low prec as well sin/cos

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        BigIntNumComplex temp = new BigIntNumComplex(ddxcenter.add(temp_ddr.mult(temp_ddcf)), ddycenter.add(temp_ddr.mult(temp_ddsf)));

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

            temp_ddcf = new BigIntNum(temp_cf);
            temp_ddsf = new BigIntNum(temp_sf);
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                temp_ddr = temp_ddr.mult(ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = temp_ddr.mult(ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = new BigIntNum(Math.exp(x * mulx)).mult(expddstartx);
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

    protected BigIntNumComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if (x == indexX + 1) {
            temp_ddr = temp_ddr.mult(ddemulx);
        } else if (x == indexX - 1) {
            temp_ddr = temp_ddr.mult(ddInvemulx);
        } else if (x != indexX) {
            temp_ddr = new BigIntNum(Math.exp(x * mulx)).mult(expddstartx);
        }

        indexX = x;

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        BigIntNumComplex temp = new BigIntNumComplex(ddxcenter.add(temp_ddr.mult(temp_ddcf)), ddycenter.add(temp_ddr.mult(temp_ddsf)));

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

    protected BigIntNumComplex getComplexWithYBase(int y) {

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

        temp_ddcf = new BigIntNum(temp_cf);
        temp_ddsf = new BigIntNum(temp_sf);

        indexY = y;

        BigIntNumComplex temp = new BigIntNumComplex(ddxcenter.add(temp_ddr.mult(temp_ddcf)), ddycenter.add(temp_ddr.mult(temp_ddsf)));

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

    protected BigIntNumComplex getAntialiasingComplexBase(int sample, int loc) {

        BigIntNum ddr2;
        double sf2, cf2;

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataPolarDouble[r][0];
            double[] antialiasing_y_sin = precalculatedJitterDataPolarDouble[r][1];
            double[] antialiasing_y_cos = precalculatedJitterDataPolarDouble[r][2];

            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            ddr2 = temp_ddr.mult(new BigIntNum(antialiasing_x[sample]));
        }
        else {
            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            ddr2 = temp_ddr.mult(new BigIntNum(antialiasing_x[sample]));
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(ddr2);
        }

        BigIntNumComplex temp = new BigIntNumComplex(ddxcenter.add(ddr2.mult(new BigIntNum(cf2))), ddycenter.add(ddr2.mult(new BigIntNum(sf2))));

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
