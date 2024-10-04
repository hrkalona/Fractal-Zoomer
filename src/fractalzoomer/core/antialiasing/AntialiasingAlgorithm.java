package fractalzoomer.core.antialiasing;

import fractalzoomer.core.location.Location;
import fractalzoomer.utils.ColorCorrection;
import fractalzoomer.utils.ColorSpaceConverter;

public abstract class AntialiasingAlgorithm {
    protected int colorSpace;
    protected double totalSamplesReciprocal;

    protected int totalSamples;
    protected boolean needsAllSamples;
    protected int addedSamples;

    protected AntialiasingAlgorithm(int totalSamples, int colorSpace) {
        this.colorSpace = colorSpace;
        totalSamplesReciprocal = 1.0 / totalSamples;
        this.totalSamples = totalSamples;
        addedSamples = 0;
    }

    public void setNeedsAllSamples(boolean needsAllSamples) {
        this.needsAllSamples = needsAllSamples;
    }

    public abstract void initialize(int color);
    public abstract boolean addSample(int color);
    public abstract int getColor();

    public int getTotalSamples() {
        return totalSamples;
    }

    protected double[] getColorChannels(int color) {
        int r = (color >> 16) & 0xff;
        int g = (color >> 8) & 0xff;
        int b = color & 0xff;
        r = ColorCorrection.gammaToLinear(r);
        g = ColorCorrection.gammaToLinear(g);
        b = ColorCorrection.gammaToLinear(b);
        if(colorSpace == 0) {
            return new double[] {r, g, b};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.RGBtoXYZ(r, g, b);
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.RGBtoLAB(r, g, b);
        }
        else if(colorSpace == 3) {
            return ColorSpaceConverter.RGBtoRYB(r, g, b);
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.RGBtoLUV(r, g, b);
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.RGBtoOKLAB(r, g, b);
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.RGBtoJzAzBz(r, g, b);
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.RGBtoLinearRGB(r, g, b);
        }
        else if(colorSpace == 8) {
            int[]  result = ColorSpaceConverter.RGBtoYCbCr(r, g, b);
            return new double[] {result[0], result[1], result[2]};
        }

        return null;
        /*else if(colorSpace == 5) {
            return ColorSpaceConverter.RGBtoHSB(r, g, b);
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.RGBtoHSL(r, g, b);
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.RGBtoLCH_ab(r, g, b);
        }
        else {
            return ColorSpaceConverter.RGBtoLCH_uv(r, g, b);
        }*/
    }

    protected int[] getColorChannels(double c1, double c2, double c3) {
        if(colorSpace == 0) {
            return new int[] {(int)(c1 + 0.5), (int)(c2 + 0.5), (int)(c3 + 0.5)};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.XYZtoRGB(c1, c2, c3);
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.LABtoRGB(c1, c2, c3);
        }
        else  if(colorSpace == 3) {
            return ColorSpaceConverter.RYBtoRGB(c1, c2, c3);
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.LUVtoRGB(c1, c2, c3);
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.OKLABtoRGB(c1, c2, c3);
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.JzAzBztoRGB(c1, c2, c3);
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.LinearRGBtoRGB(c1, c2, c3);
        }
        else if(colorSpace == 8) {
            return ColorSpaceConverter.YCbCrtoRGB((int)(c1 + 0.5), (int)(c2 + 0.5), (int)(c3 + 0.5));
        }

        return new int[] {0, 0, 0};
    }

    public static AntialiasingAlgorithm getAntialiasingAlgorithm(int totalSamples, int method, boolean avgWithMean, int colorSpace, double sigmaR) {

        //int colorspace = 0;
        if(method == 0) {
            return new MeanAntialiasingAlgorithm(totalSamples, colorSpace);

            //if(colorspace == 0) {
                //return new MeanAntialiasingAlgorithmRGB(totalSamples);

//            }
//            else {
//                return new MeanAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 1){
            //if(colorspace == 0) {
                return new MedianAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
//            }
//            else {
//                return new MedianAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 2) {
           // if(colorspace == 0) {
                return new GeometricMedianAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
//            }
//            else {
//                return new MidPointAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 3) {
            return new ClosestToMeanAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
        }
        else if (method == 4) {
            return new TriMeanAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
        }
        else if (method == 5) {
            return new AdaptiveMeanAntialiasingAlgorithm(totalSamples, 5);
        }
        else if (method == 6) {
            return new GaussianAntialiasingAlgorithm(totalSamples, (double [])Location.getGaussianCoefficients(totalSamples, sigmaR)[0], avgWithMean, colorSpace);
        }
        else if (method == 7) {
            return new MeanNoOutliersAntialiasingAlgorithm(totalSamples, colorSpace);
        }
        else if (method == 8) {
            return new GeometricMeanAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
        }
        else {
            return new RMSMeanAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
        }
    }
}
