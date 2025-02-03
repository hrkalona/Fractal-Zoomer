package fractalzoomer.core.location.normal;

import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MpfrBigNumComplex;
import fractalzoomer.core.numerics.mpfr.MpfrBigNum;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationNormalMpfrBigNumArbitrary extends Location {
    protected Rotation rotation;

    protected MpfrBigNum ddxcenter;
    protected MpfrBigNum ddycenter;
    protected MpfrBigNum ddsize;

    private double[] antialiasing_x;

    private double muly;
    protected double mulx;
    protected MpfrBigNum ddmulx;
    private MpfrBigNum expddstartx;
    private double starty;
    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;


    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private MpfrBigNum temp_ddsf;
    private MpfrBigNum temp_ddcf;
    private MpfrBigNum temp_ddr;

    private MpfrBigNum ddemulx;
    private MpfrBigNum ddInvemulx;

    private double cosmuly;
    private double sinmuly;

    private MpfrBigNum tempResult;
    private MpfrBigNum tempResult2;

    private MpfrBigNum tempResult3;

    private MpfrBigNum tempResultX;
    private MpfrBigNum tempResultY;

    private JitterSettings js;

    private boolean requiresVariablePixelSize;

    protected int width;

    public PolarLocationNormalMpfrBigNumArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;
        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);

        requiresVariablePixelSize = fractal.requiresVariablePixelSize();

        ddxcenter = new MpfrBigNum(xCenter);
        ddycenter = new MpfrBigNum(yCenter);
        ddsize = new MpfrBigNum(size);

        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        muly = (2 * circle_period * Math.PI) / image_size;

        mulx = muly * height_ratio;

        ddmulx = new MpfrBigNum(mulx);

        expddstartx = ddsize.mult(Math.exp(-mulx * image_size * coefx));

        starty = muly * image_size * (0.5 - coefy);

        rotation = new Rotation(new MpfrBigNumComplex(rotation_vals[0], rotation_vals[1]), new MpfrBigNumComplex(rotation_center[0], rotation_center[1]));

        double emulx = Math.exp(mulx);
        ddemulx = new MpfrBigNum(emulx);
        ddInvemulx = new MpfrBigNum(1 / emulx);

        cosmuly = Math.cos(muly);
        sinmuly = Math.sin(muly);

        temp_ddr = new MpfrBigNum();
        temp_ddcf = new MpfrBigNum();
        temp_ddsf = new MpfrBigNum();
        tempResult = new MpfrBigNum();
        tempResult2 = new MpfrBigNum();
        tempResult3 = new MpfrBigNum();
        tempResultX = new MpfrBigNum();
        tempResultY = new MpfrBigNum();

        this.js = js;
        this.width = width;
    }

    public PolarLocationNormalMpfrBigNumArbitrary(PolarLocationNormalMpfrBigNumArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        ddsize = other.ddsize;

        mulx = other.mulx;
        ddmulx = other.ddmulx;
        muly = other.muly;
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

        temp_ddcf = new MpfrBigNum();
        temp_ddsf = new MpfrBigNum();
        temp_ddr = new MpfrBigNum();
        tempResult = other.tempResult;
        tempResult2 = other.tempResult2;
        tempResult3 = other.tempResult3;
        tempResultX = other.tempResultX;
        tempResultY = other.tempResultY;

        js = other.js;
        requiresVariablePixelSize = other.requiresVariablePixelSize;

    }

    public void setVariablePixelSize(MpfrBigNum expValue) {
        expValue.mult(ddmulx, tempResult);
        fractal.setVariablePixelSize(tempResult.getMantExp());
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected MpfrBigNumComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            expddstartx.mult(Math.exp((x + res[1]) * mulx), temp_ddr);

            double f = (y + res[0]) * muly + starty;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }
        else {
            if (x == indexX + 1) {
                temp_ddr.mult(ddemulx, temp_ddr);
            } else if (x == indexX - 1) {
                temp_ddr.mult(ddInvemulx, temp_ddr);
            } else if (x != indexX) {
                expddstartx.mult(Math.exp(x * mulx), temp_ddr);
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

        temp_ddsf.set(temp_sf);
        temp_ddcf.set(temp_cf);

        indexX = x;
        indexY = y;


        MpfrBigNum.ApBmC_DpEmG(tempResultX, tempResultY, ddxcenter, temp_ddr, temp_ddcf, ddycenter, temp_ddr, temp_ddsf);

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

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
            temp_ddsf.set(temp_sf);
            temp_ddcf.set(temp_cf);
        }

        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                temp_ddr.mult(ddemulx, temp_ddr);
            } else if (x == indexX - 1) {
                temp_ddr.mult(ddInvemulx, temp_ddr);
            } else if (x != indexX) {
                expddstartx.mult(Math.exp(x * mulx), temp_ddr);
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

    protected MpfrBigNumComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            temp_ddr.mult(ddemulx, temp_ddr);
        }
        else if(x == indexX - 1) {
            temp_ddr.mult(ddInvemulx, temp_ddr);
        }
        else if (x != indexX) {
            expddstartx.mult(Math.exp(x * mulx), temp_ddr);
        }

        indexX = x;

        MpfrBigNum.ApBmC_DpEmG(tempResultX, tempResultY, ddxcenter, temp_ddr, temp_ddcf, ddycenter, temp_ddr, temp_ddsf);

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

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

    protected MpfrBigNumComplex getComplexWithYBase(int y) {

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

        temp_ddsf.set(temp_sf);
        temp_ddcf.set(temp_cf);

        indexY = y;

        MpfrBigNum.ApBmC_DpEmG(tempResultX, tempResultY, ddxcenter, temp_ddr, temp_ddcf, ddycenter, temp_ddr, temp_ddsf);


        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

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

    protected MpfrBigNumComplex getAntialiasingComplexBase(int sample, int loc) {

        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataPolarDouble[r][0];
            double[] antialiasing_y_sin = precalculatedJitterDataPolarDouble[r][1];
            double[] antialiasing_y_cos = precalculatedJitterDataPolarDouble[r][2];

            double sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            double cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            tempResult.set(sf2); //sf2
            tempResult3.set(cf2); //cf2

            temp_ddr.mult(antialiasing_x[sample], tempResult2); //r2
        }
        else {
            double sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            double cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            tempResult.set(sf2); //sf2
            tempResult3.set(cf2); //cf2

            temp_ddr.mult(antialiasing_x[sample], tempResult2); //r2
        }

        MpfrBigNum.ApBmC_DpEmG(tempResultX, tempResultY, ddxcenter, tempResult2, tempResult3, ddycenter, tempResult2, tempResult);

        if(requiresVariablePixelSize) {
            setVariablePixelSize(tempResult2);
        }

        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }
}
