package fractalzoomer.core.location.normal;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.JitterSettings;
import org.apfloat.Apfloat;

public class PolarLocationNormalMpfrBigNumArbitrary extends Location {
    protected Rotation rotation;

    protected MpfrBigNum ddxcenter;
    protected MpfrBigNum ddycenter;

    private MpfrBigNum[] ddantialiasing_x;

    private MpfrBigNum ddmuly;
    protected MpfrBigNum ddmulx;
    private MpfrBigNum ddstartx;
    private MpfrBigNum ddstarty;
    protected MpfrBigNum ddcenter;
    private MpfrBigNum[] ddantialiasing_y_sin;
    private MpfrBigNum[] ddantialiasing_y_cos;


    //Dont copy those
    private MpfrBigNum temp_ddsf;
    private MpfrBigNum temp_ddcf;
    private MpfrBigNum temp_ddr;

    private MpfrBigNum ddemulx;
    private MpfrBigNum ddInvemulx;

    private MpfrBigNum ddcosmuly;
    private MpfrBigNum ddsinmuly;

    private MpfrBigNum tempResult;
    private MpfrBigNum tempResult2;

    private MpfrBigNum tempResult3;
    private MpfrBigNum tempResult4;

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

        ddcenter = new MpfrBigNum(size);
        ddcenter.log(ddcenter);

        double coefx = width == image_size ? 0.5 : (1 + (width - (double)height) / height) * 0.5;
        double coefy = height == image_size ? 0.5 : (1 + (height - (double)width) / width) * 0.5;

        ddmuly = MpfrBigNum.PI.mult(2.0 * circle_period);
        ddmuly.divide(image_size, ddmuly);


        ddmulx = ddmuly.mult(height_ratio);

        ddstartx = ddmulx.mult(image_size);
        ddstartx.mult(coefx, ddstartx);

        ddcenter.sub(ddstartx, ddstartx);

        ddstarty = ddmuly.mult(image_size);
        ddstarty.mult(0.5 - coefy, ddstarty);

        rotation = new Rotation(new MpfrBigNumComplex(rotation_vals[0], rotation_vals[1]), new MpfrBigNumComplex(rotation_center[0], rotation_center[1]));

        ddemulx = ddmulx.exp();
        ddInvemulx = ddemulx.reciprocal();

        MpfrBigNum[] res = ddmuly.sin_cos();
        ddcosmuly = res[1];
        ddsinmuly = res[0];

        temp_ddcf = new MpfrBigNum();
        temp_ddsf = new MpfrBigNum();
        temp_ddr = new MpfrBigNum();
        tempResult = new MpfrBigNum();
        tempResult2 = new MpfrBigNum();
        tempResult3 = new MpfrBigNum();
        tempResult4 = new MpfrBigNum();
        tempResultX = new MpfrBigNum();
        tempResultY = new MpfrBigNum();

        this.js = js;
        this.width = width;
    }

    public PolarLocationNormalMpfrBigNumArbitrary(PolarLocationNormalMpfrBigNumArbitrary other) {

        super(other);

        fractal = other.fractal;

        ddcenter = other.ddcenter;

        ddxcenter = other.ddxcenter;
        ddycenter = other.ddycenter;

        ddmulx = other.ddmulx;
        ddmuly = other.ddmuly;
        ddstartx = other.ddstartx;
        ddstarty = other.ddstarty;

        ddemulx = other.ddemulx;
        ddInvemulx = other.ddInvemulx;
        ddcosmuly = other.ddcosmuly;
        ddsinmuly = other.ddsinmuly;

        width = other.width;

        rotation = other.rotation;

        ddantialiasing_y_cos = other.ddantialiasing_y_cos;
        ddantialiasing_y_sin = other.ddantialiasing_y_sin;
        ddantialiasing_x = other.ddantialiasing_x;

        temp_ddcf = new MpfrBigNum();
        temp_ddsf = new MpfrBigNum();
        temp_ddr = new MpfrBigNum();
        tempResult = other.tempResult;
        tempResult2 = other.tempResult2;
        tempResult3 = other.tempResult3;
        tempResult4 = other.tempResult4;
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

            ddmulx.mult(x + res[1], temp_ddr);
            temp_ddr.add(ddstartx, temp_ddr);
            temp_ddr.exp(temp_ddr);

            ddmuly.mult(y + res[0], tempResult);
            tempResult.add(ddstarty, tempResult);
            tempResult.sin_cos(tempResult, tempResult3);

            //temp_ddsf.set(tempResult);
            //temp_ddcf.set(tempResult3);
            MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
        }
        else {
            if (x == indexX + 1) {
                temp_ddr.mult(ddemulx, temp_ddr);
            } else if (x == indexX - 1) {
                temp_ddr.mult(ddInvemulx, temp_ddr);
            } else if (x != indexX) {
                ddmulx.mult(x, temp_ddr);
                temp_ddr.add(ddstartx, temp_ddr);
                temp_ddr.exp(temp_ddr);
            }

            if (y == indexY + 1) {

                temp_ddsf.mult(ddcosmuly, tempResult);
                temp_ddcf.mult(ddsinmuly, tempResult2);
                tempResult.add(tempResult2, tempResult);

                temp_ddcf.mult(ddcosmuly, tempResult3);
                temp_ddsf.mult(ddsinmuly, tempResult4);
                tempResult3.sub(tempResult4, tempResult3);


                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);

                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            } else if (y == indexY - 1) {
                temp_ddsf.mult(ddcosmuly, tempResult);
                temp_ddcf.mult(ddsinmuly, tempResult2);
                tempResult.sub(tempResult2, tempResult);

                temp_ddcf.mult(ddcosmuly, tempResult3);
                temp_ddsf.mult(ddsinmuly, tempResult4);
                tempResult3.add(tempResult4, tempResult3);

                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);
                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            } else if (y != indexY) {
                ddmuly.mult(y, tempResult);
                tempResult.add(ddstarty, tempResult);
                tempResult.sin_cos(tempResult, tempResult3);

                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);
                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            }
        }

        indexX = x;
        indexY = y;

        //ddmuly.mult(y, tempResult); //As alternative as it works with low prec as well
        //temp_ddsf.set(Math.sin(tempResult.doubleValue()));
        //temp_ddcf.set(Math.cos(tempResult.doubleValue()));

//        temp_ddr.mult(temp_ddcf, tempResultX);
//        tempResultX.add(ddxcenter, tempResultX);
//
//        temp_ddr.mult(temp_ddsf, tempResultY);
//        tempResultY.add(ddycenter, tempResultY);

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

                temp_ddsf.mult(ddcosmuly, tempResult);
                temp_ddcf.mult(ddsinmuly, tempResult2);
                tempResult.add(tempResult2, tempResult);

                temp_ddcf.mult(ddcosmuly, tempResult3);
                temp_ddsf.mult(ddsinmuly, tempResult4);
                tempResult3.sub(tempResult4, tempResult3);


                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);
                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            } else if (y == indexY - 1) {
                temp_ddsf.mult(ddcosmuly, tempResult);
                temp_ddcf.mult(ddsinmuly, tempResult2);
                tempResult.sub(tempResult2, tempResult);

                temp_ddcf.mult(ddcosmuly, tempResult3);
                temp_ddsf.mult(ddsinmuly, tempResult4);
                tempResult3.add(tempResult4, tempResult3);

                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);
                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            } else if (y != indexY) {

                ddmuly.mult(y, tempResult);
                tempResult.add(ddstarty, tempResult);
                tempResult.sin_cos(tempResult, tempResult3);

                //temp_ddsf.set(tempResult);
                //temp_ddcf.set(tempResult3);
                MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
            }
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
                ddmulx.mult(x, temp_ddr);
                temp_ddr.add(ddstartx, temp_ddr);
                temp_ddr.exp(temp_ddr);
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
            ddmulx.mult(x, temp_ddr);
            temp_ddr.add(ddstartx, temp_ddr);
            temp_ddr.exp(temp_ddr);
        }

        indexX = x;

//        temp_ddr.mult(temp_ddcf, tempResultX);
//        tempResultX.add(ddxcenter, tempResultX);
//
//        temp_ddr.mult(temp_ddsf, tempResultY);
//        tempResultY.add(ddycenter, tempResultY);

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

        if(y == indexY + 1) {

            temp_ddsf.mult(ddcosmuly, tempResult);
            temp_ddcf.mult(ddsinmuly, tempResult2);
            tempResult.add(tempResult2, tempResult);

            temp_ddcf.mult(ddcosmuly, tempResult3);
            temp_ddsf.mult(ddsinmuly, tempResult4);
            tempResult3.sub(tempResult4, tempResult3);


            //temp_ddsf.set(tempResult);
            //temp_ddcf.set(tempResult3);
            MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
        }
        else if(y == indexY - 1) {
            temp_ddsf.mult(ddcosmuly, tempResult);
            temp_ddcf.mult(ddsinmuly, tempResult2);
            tempResult.sub(tempResult2, tempResult);

            temp_ddcf.mult(ddcosmuly, tempResult3);
            temp_ddsf.mult(ddsinmuly, tempResult4);
            tempResult3.add(tempResult4, tempResult3);

            //temp_ddsf.set(tempResult);
            //temp_ddcf.set(tempResult3);
            MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
        }
        else if (y != indexY) {

            ddmuly.mult(y, tempResult);
            tempResult.add(ddstarty, tempResult);
            tempResult.sin_cos(tempResult, tempResult3);

            //temp_ddsf.set(tempResult);
            //temp_ddcf.set(tempResult3);
            MpfrBigNum.set(temp_ddsf, temp_ddcf, tempResult, tempResult3);
        }

        indexY = y;

//        temp_ddr.mult(temp_ddcf, tempResultX);
//        tempResultX.add(ddxcenter, tempResultX);
//
//        temp_ddr.mult(temp_ddsf, tempResultY);
//        tempResultY.add(ddycenter, tempResultY);

        MpfrBigNum.ApBmC_DpEmG(tempResultX, tempResultY, ddxcenter, temp_ddr, temp_ddcf, ddycenter, temp_ddr, temp_ddsf);


        MpfrBigNumComplex temp = new MpfrBigNumComplex(tempResultX, tempResultY);

        temp = rotation.rotate(temp);

        temp = fractal.getPlaneTransformedPixel(temp);

        return temp;
    }

    @Override
    public void createAntialiasingSteps(boolean adaptive, boolean jitter, int numberOfExtraSamples) {
        super.createAntialiasingSteps(adaptive, jitter, numberOfExtraSamples);
        MpfrBigNum[][] steps = createAntialiasingPolarStepsMpfrBigNum(ddmulx, ddmuly, adaptive, jitter, numberOfExtraSamples);
        ddantialiasing_x = steps[0];
        ddantialiasing_y_sin = steps[1];
        ddantialiasing_y_cos = steps[2];
    }

    @Override
    public GenericComplex getAntialiasingComplex(int sample, int loc) {
        return getAntialiasingComplexBase(sample, loc);
    }

    protected MpfrBigNumComplex getAntialiasingComplexBase(int sample, int loc) {



        if(aaJitter) {
            int r = (int)(hash(loc) % NUMBER_OF_AA_JITTER_KERNELS);
            MpfrBigNum[] ddantialiasing_x = precalculatedJitterDataPolarMpfrBigNum[r][0];
            MpfrBigNum[] ddantialiasing_y_sin = precalculatedJitterDataPolarMpfrBigNum[r][1];
            MpfrBigNum[] ddantialiasing_y_cos = precalculatedJitterDataPolarMpfrBigNum[r][2];

            temp_ddsf.mult(ddantialiasing_y_cos[sample], tempResult);
            temp_ddcf.mult(ddantialiasing_y_sin[sample], tempResult2);
            tempResult.add(tempResult2, tempResult); //sf2

            temp_ddcf.mult(ddantialiasing_y_cos[sample], tempResult3);
            temp_ddsf.mult(ddantialiasing_y_sin[sample], tempResult4);
            tempResult3.sub(tempResult4, tempResult3); //cf2

            temp_ddr.mult(ddantialiasing_x[sample], tempResult2); //r2
        }
        else {
            temp_ddsf.mult(ddantialiasing_y_cos[sample], tempResult);
            temp_ddcf.mult(ddantialiasing_y_sin[sample], tempResult2);
            tempResult.add(tempResult2, tempResult); //sf2

            temp_ddcf.mult(ddantialiasing_y_cos[sample], tempResult3);
            temp_ddsf.mult(ddantialiasing_y_sin[sample], tempResult4);
            tempResult3.sub(tempResult4, tempResult3); //cf2

            temp_ddr.mult(ddantialiasing_x[sample], tempResult2); //r2
        }

//        tempResult2.mult(tempResult3, tempResultX);
//        tempResultX.add(ddxcenter, tempResultX); //ddxcenter + r2 * cf2
//
//        tempResult2.mult(tempResult, tempResultY);
//        tempResultY.add(ddycenter, tempResultY); //ddycenter + r2 * sf2

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
