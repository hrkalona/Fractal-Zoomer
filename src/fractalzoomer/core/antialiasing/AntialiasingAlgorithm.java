package fractalzoomer.core.antialiasing;

import fractalzoomer.core.location.Location;
import fractalzoomer.utils.ColorSpaceConverter;

public abstract class AntialiasingAlgorithm {
    protected int colorSpace;
    protected double totalSamplesReciprocal;

    protected int totalSamples;
    protected boolean needsPostProcessing;

    protected AntialiasingAlgorithm(int totalSamples, int colorSpace) {
        this.colorSpace = colorSpace;
        totalSamplesReciprocal = 1.0 / totalSamples;
        this.totalSamples = totalSamples;
    }

    public void setNeedsPostProcessing(boolean needsPostProcessing) {
        this.needsPostProcessing = needsPostProcessing;
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

    protected int[] getAveragedColorChannels(double c1, double c2, double c3) {
        if(colorSpace == 0) {
            return new int[] {(int)(function(c1) + 0.5), (int)(function(c2) + 0.5), (int)(function(c3) + 0.5)};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.XYZtoRGB(function(c1), function(c2), function(c3));
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.LABtoRGB(function(c1), function(c2), function(c3));
        }
        else  if(colorSpace == 3) {
            return ColorSpaceConverter.RYBtoRGB(function(c1), function(c2), function(c3));
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.LUVtoRGB(function(c1), function(c2), function(c3));
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.OKLABtoRGB(function(c1), function(c2), function(c3));
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.JzAzBztoRGB(function(c1), function(c2), function(c3));
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.LinearRGBtoRGB(function(c1), function(c2), function(c3));
        }

        return new int[] {0, 0, 0};
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

        return new int[] {0, 0, 0};
    }

    /*protected int[] getAveragedColorChannels(double sinHue, double cosHue, double c2, double c3) {

        if(colorSpace == 6) {
            double avgSinHue = function(sinHue);
            double avgCosHue = function(cosHue);
            double hue = ((Math.toDegrees(Math.atan2(avgSinHue, avgCosHue)) + 360) % 360.0) / 360.0;
            return ColorSpaceConverter.HSBtoRGB(hue, function(c2), function(c3));
        }
        else if(colorSpace == 7) {
            double avgSinHue = function(sinHue);
            double avgCosHue = function(cosHue);
            double hue = ((Math.toDegrees(Math.atan2(avgSinHue, avgCosHue)) + 360) % 360.0) / 360.0;
            return ColorSpaceConverter.HSLtoRGB(hue, function(c2), function(c3));
        }
        else if(colorSpace == 8) {
            double avgSinHue = function(sinHue);
            double avgCosHue = function(cosHue);
            double hue = ((Math.toDegrees(Math.atan2(avgSinHue, avgCosHue)) + 360) % 360.0);
            return ColorSpaceConverter.LCH_abtoRGB(function(c2), function(c3), hue);
        }
        else {
            double avgSinHue = function(sinHue);
            double avgCosHue = function(cosHue);
            double hue = ((Math.toDegrees(Math.atan2(avgSinHue, avgCosHue)) + 360) % 360.0);
            return ColorSpaceConverter.LCH_uvtoRGB(function(c2), function(c3), hue);
        }
    }*/

    protected int[] getAveragedColorChannels(double c1, double c2, double c3, int samples1, int samples2, int samples3) {
        if(colorSpace == 0) {
            return new int[] {(int)(function(c1, samples1) + 0.5), (int)(function(c2, samples2) + 0.5), (int)(function(c3, samples3) + 0.5)};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.XYZtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.LABtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 3) {
            return ColorSpaceConverter.RYBtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.LUVtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.OKLABtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.JzAzBztoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.LinearRGBtoRGB(function(c1, samples1), function(c2, samples2), function(c3, samples3));
        }
        return new int[] {0, 0, 0};
    }

    protected int[] getAveragedColorChannels(double c1, double c2, double c3, double c4, double c5, double c6) {
        if(colorSpace == 0) {
            return new int[] {(int)(function(c1, c4) + 0.5), (int)(function(c2, c5) + 0.5), (int)(function(c3, c6) + 0.5)};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.XYZtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.LABtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 3) {
            return ColorSpaceConverter.RYBtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.LUVtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.OKLABtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.JzAzBztoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.LinearRGBtoRGB(function(c1, c4), function(c2, c5), function(c3, c6));
        }
        return new int[] {0, 0, 0};
    }

    protected int[] getAveragedColorChannels(double c1, double c2, double c3, double c4, double c5, double c6, double c7, double c8, double c9) {
        if(colorSpace == 0) {
            return new int[] {(int)(function(c1, c4, c7) + 0.5), (int)(function(c2, c5, c8) + 0.5), (int)(function(c3, c6, c9) + 0.5)};
        }
        else if(colorSpace == 1) {
            return ColorSpaceConverter.XYZtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 2) {
            return ColorSpaceConverter.LABtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 3) {
            return ColorSpaceConverter.RYBtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 4) {
            return ColorSpaceConverter.LUVtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 5) {
            return ColorSpaceConverter.OKLABtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 6) {
            return ColorSpaceConverter.JzAzBztoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }
        else if(colorSpace == 7) {
            return ColorSpaceConverter.LinearRGBtoRGB(function(c1, c4, c7), function(c2, c5, c8), function(c3, c6, c9));
        }

        return new int[] {0, 0, 0};
    }

    protected double function(double value) {
        return 0;
    }

    protected double function(double value, int samples) {
        return 0;
    }

    protected double function(double value, double value2) {
        return 0;
    }

    protected double function(double value, double value2, double value3) {
        return 0;
    }


    public static AntialiasingAlgorithm getAntialiasingAlgorithm(int totalSamples, int method, boolean avgWithMean, int colorSpace, Location location) {

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
                return new MidPointAntialiasingAlgorithm(totalSamples, avgWithMean, colorSpace);
//            }
//            else {
//                return new MidPointAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 3) {
            return new ClosestToMeanAntialiasingAlgorithm(totalSamples, avgWithMean);
        }
        else if (method == 4) {
            return new ClosestToMidPointAntialiasingAlgorithm(totalSamples, avgWithMean);
        }
        else if (method == 5) {
            return new AdaptiveMeanAntialiasingAlgorithm(totalSamples, 5);
        }
        else if (method == 6) {
            return new GaussianAntialiasingAlgorithm(totalSamples, location.getGaussianCoefficients(totalSamples), avgWithMean, colorSpace);
        }
        else {
            return new MeanNoOutliersAntialiasingAlgorithm(totalSamples, colorSpace);
        }
//        else {
//            return new AdaptiveMeanAntialiasingAlgorithmRGB(totalSamples, 9);
//        }
    }
}
