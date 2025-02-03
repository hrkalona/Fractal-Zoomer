package fractalzoomer.core.location.normal;

import fractalzoomer.core.Complex;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.BigComplex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import fractalzoomer.utils.BigPoint;
import org.apfloat.Apfloat;

public class PolarLocationNormalApfloatArbitrary extends Location {

    protected Apfloat ddxcenter;
    protected Apfloat ddycenter;
    protected Apfloat size;

    private double[] antialiasing_x;
    protected Rotation rotation;
    private double muly;
    protected double mulx;
    protected Apfloat ddmulx;
    private Apfloat expddstartx;
    private double starty;

    private double[] antialiasing_y_sin;
    private double[] antialiasing_y_cos;

    private double cosmuly;
    private double sinmuly;

    //Dont copy those
    private double temp_sf;
    private double temp_cf;
    private Apfloat temp_ddsf;
    private Apfloat temp_ddcf;
    private Apfloat temp_ddr;

    private Apfloat ddemulx;
    private Apfloat ddInvemulx;

    private JitterSettings js;

    private boolean requiresVariablePixelSize;

    protected int width;

    public PolarLocationNormalApfloatArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period, Apfloat[] rotation_center, Apfloat[] rotation_vals, Fractal fractal, JitterSettings js) {

        super();

        this.fractal = fractal;

        width = offset.getWidth(width);
        height = offset.getHeight(height);
        int image_size = Math.min(width, height);

        requiresVariablePixelSize = fractal.requiresVariablePixelSize();

        ddxcenter = xCenter;
        ddycenter = yCenter;
        this.size = size;

        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        muly = (2 * circle_period * Math.PI) / image_size;

        mulx = muly * height_ratio;
        ddmulx = new MyApfloat(mulx);

        expddstartx = MyApfloat.fp.multiply(size, new MyApfloat(Math.exp(-mulx * image_size * coefx)));
        starty = muly * image_size * (0.5 - coefy);

        rotation = new Rotation(new BigComplex(rotation_vals[0], rotation_vals[1]), new BigComplex(rotation_center[0], rotation_center[1]));

        double emulx = Math.exp(mulx);
        ddemulx = new MyApfloat(emulx);
        ddInvemulx = new MyApfloat(1 / emulx);

        cosmuly = Math.cos(muly);
        sinmuly = Math.sin(muly);

        this.js = js;
        this.width = width;

    }

    public PolarLocationNormalApfloatArbitrary(PolarLocationNormalApfloatArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;
        size = other.size;

        ddemulx = other.ddemulx;
        ddInvemulx = other.ddInvemulx;

        mulx = other.mulx;
        ddmulx = other.ddmulx;
        muly = other.muly;
        expddstartx = other.expddstartx;
        starty = other.starty;

        rotation = other.rotation;

        antialiasing_y_cos = other.antialiasing_y_cos;
        antialiasing_y_sin = other.antialiasing_y_sin;
        antialiasing_x = other.antialiasing_x;

        js = other.js;

        requiresVariablePixelSize = other.requiresVariablePixelSize;
        width = other.width;

        cosmuly = other.cosmuly;
        sinmuly = other.sinmuly;
    }

    public PolarLocationNormalApfloatArbitrary(Apfloat xCenter, Apfloat yCenter, Apfloat size, double height_ratio, int width, int height, double circle_period) {
        super();
        ddxcenter = xCenter;
        ddycenter = yCenter;
        this.size = size;

        int image_size = Math.min(width, height);
        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        muly = (2 * circle_period * Math.PI) / image_size;
        mulx = muly * height_ratio;
        ddmulx = new MyApfloat(mulx);
        expddstartx = MyApfloat.fp.multiply(size, new MyApfloat(-mulx * image_size * coefx));
        starty = muly * image_size * (0.5 - coefy);
    }

    public void setVariablePixelSize(Apfloat expValue) {
        fractal.setVariablePixelSize(getMantExp(MyApfloat.fp.multiply(expValue, ddmulx)));
    }

    @Override
    public GenericComplex getComplex(int x, int y) {
        return getComplexBase(offset.getX(x), offset.getY(y));
    }

    protected BigComplex getComplexBase(int x, int y) {

        if(js.enableJitter) {
            double[] res = GetPixelOffset(y, x, js.jitterSeed, js.jitterShape, js.jitterScale);
            temp_ddr = MyApfloat.fp.multiply(new MyApfloat(Math.exp((x + res[1]) * mulx)), expddstartx);
            double f = (y + res[0]) * muly + starty;
            temp_sf = Math.sin(f);
            temp_cf = Math.cos(f);
        }
        else {
            if (x == indexX + 1) {
                temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = MyApfloat.fp.multiply(new MyApfloat(Math.exp(x * mulx)), expddstartx);
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

        temp_ddsf = new MyApfloat(temp_sf);
        temp_ddcf = new MyApfloat(temp_cf);

        indexX = x;
        indexY = y;

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));

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

            temp_ddsf = new MyApfloat(temp_sf);
            temp_ddcf = new MyApfloat(temp_cf);
        }
        indexY = y;

    }

    @Override
    public void precalculateX(int x) {

        x = offset.getX(x);

        if(!js.enableJitter) {
            if (x == indexX + 1) {
                temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddemulx);
            } else if (x == indexX - 1) {
                temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddInvemulx);
            } else if (x != indexX) {
                temp_ddr = MyApfloat.fp.multiply(new MyApfloat(Math.exp(x * mulx)), expddstartx);
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

    protected BigComplex getComplexWithXBase(int x) {

        if(js.enableJitter) {
            return getComplexBase(x, indexY);
        }

        if(x == indexX + 1) {
            temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddemulx);
        }
        else if(x == indexX - 1) {
            temp_ddr = MyApfloat.fp.multiply(temp_ddr, ddInvemulx);
        }
        else if (x != indexX) {
            temp_ddr = MyApfloat.fp.multiply(new MyApfloat(Math.exp(x * mulx)), expddstartx);
        }

        indexX = x;

        if(requiresVariablePixelSize) {
            setVariablePixelSize(temp_ddr);
        }

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);
        return temp;
    }

    @Override
    public GenericComplex getComplexWithY(int y) {
        return getComplexWithYBase(offset.getY(y));
    }

    protected BigComplex getComplexWithYBase(int y) {
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

        temp_ddsf = new MyApfloat(temp_sf);
        temp_ddcf = new MyApfloat(temp_cf);

        indexY = y;
        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(temp_ddr, temp_ddcf)), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(temp_ddr, temp_ddsf)));
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

    protected BigComplex getAntialiasingComplexBase(int sample, int loc) {
        Apfloat r2;
        double sf2, cf2;

        if(aaJitter) {

            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            double[] antialiasing_x = precalculatedJitterDataPolarDouble[r][0];
            double[] antialiasing_y_sin = precalculatedJitterDataPolarDouble[r][1];
            double[] antialiasing_y_cos = precalculatedJitterDataPolarDouble[r][2];

            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            r2 = MyApfloat.fp.multiply(temp_ddr, new MyApfloat(antialiasing_x[sample]));
        }
        else {
            sf2 = temp_sf * antialiasing_y_cos[sample] + temp_cf * antialiasing_y_sin[sample];
            cf2 = temp_cf * antialiasing_y_cos[sample] - temp_sf * antialiasing_y_sin[sample];

            r2 = MyApfloat.fp.multiply(temp_ddr, new MyApfloat(antialiasing_x[sample]));
        }

        if(requiresVariablePixelSize) {
            setVariablePixelSize(r2);
        }

        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(r2, new MyApfloat(cf2))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(r2, new MyApfloat(sf2))));

        temp = rotation.rotate(temp);
        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public boolean isPolar() {return true;}

    public BigPoint getPoint(int x, int y) {
        double f = y * muly + starty;
        double sf = Math.sin(f);
        double cf = Math.cos(f);

        Apfloat r = MyApfloat.fp.multiply(new MyApfloat(Math.exp(x * mulx)), expddstartx);
        return new BigPoint(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(r, new MyApfloat(cf))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(r, new MyApfloat(sf))));
    }

    public Complex getComplexOrbit(int x, int y) {
        double f = y * muly + starty;
        double sf = Math.sin(f);
        double cf = Math.cos(f);

        Apfloat ddr = MyApfloat.fp.multiply(new MyApfloat(Math.exp(x * mulx)), expddstartx);
        BigComplex temp = new BigComplex(MyApfloat.fp.add(ddxcenter, MyApfloat.fp.multiply(ddr, new MyApfloat(cf))), MyApfloat.fp.add(ddycenter, MyApfloat.fp.multiply(ddr, new MyApfloat(sf))));
        return temp.toComplex();
    }
}
