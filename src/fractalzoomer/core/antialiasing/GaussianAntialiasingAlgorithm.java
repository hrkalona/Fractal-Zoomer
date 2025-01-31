package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class GaussianAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double SumA;
    private double SumB;
    private double SumC;

    private double SumA2;
    private double SumB2;
    private double SumC2;

    private boolean avgWithMean;

    private double[] gaussian_coefficients;

    public GaussianAntialiasingAlgorithm(int totalSamples, double[] gaussian_coefficients, boolean avgWithMean,  int colorSpace) {
        super(totalSamples, colorSpace);
        SumA = 0;
        SumB = 0;
        SumC = 0;
        SumA2 = 0;
        SumB2 = 0;
        SumC2 = 0;
        this.gaussian_coefficients = gaussian_coefficients;
        this.avgWithMean = avgWithMean;
    }

    @Override
    public void initialize(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            SumA2 = result[0];
            SumB2 = result[1];
            SumC2 = result[2];
        }

        addedSamples = 0;
        double coefficient = gaussian_coefficients[addedSamples];
        SumA = coefficient * result[0];
        SumB = coefficient * result[1];
        SumC = coefficient * result[2];
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            SumA2 += result[0];
            SumB2 += result[1];
            SumC2 += result[2];
        }

        double coefficient = gaussian_coefficients[addedSamples];
        SumA += coefficient * result[0];
        SumB += coefficient * result[1];
        SumC += coefficient * result[2];

        addedSamples++;
        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        if(avgWithMean) {
            double finalA = (SumA + SumA2 * totalSamplesReciprocal) * 0.5;
            double finalB = (SumB + SumB2 * totalSamplesReciprocal) * 0.5;
            double finalC = (SumC + SumC2 * totalSamplesReciprocal) * 0.5;
            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        } else {
            int[] result = getColorChannels(SumA, SumB, SumC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

    public static double getSigma(int kernel_size) {
        return 0.3*((kernel_size-1)*0.5 - 1) + 0.8;
    }

    public static double[] createGaussianKernel(int length, int[] X, int[] Y, double sigma) {
        double[] gaussian_kernel = new double[X.length];
        double sumTotal = 0;

        //OpenCv
        if(sigma == 0) {
            sigma = getSigma(length);
        }

        double distance = 0;
        double sigmaSqr2 = 2.0 * sigma * sigma;

        double calculatedEuler = 1.0 / (sigmaSqr2 * Math.PI);

        double temp;

        for(int i = 0; i < X.length && i < Y.length; i++) {

            int filterX = X[i];
            int filterY = Y[i];

            distance = ((filterX * filterX) + (filterY * filterY)) / (sigmaSqr2);
            temp = gaussian_kernel[i] = calculatedEuler * Math.exp(-distance);
            sumTotal += temp;
        }

        for(int i = 0; i < X.length && i < Y.length; i++) {
            gaussian_kernel[i] = gaussian_kernel[i] / sumTotal;
        }

        return gaussian_kernel;

    }
}
