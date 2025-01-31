package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

//Does not work for Anti-Aliasing, time to say good-bye
@Deprecated
public class BilateralAntialiasingAlgorithm extends AntialiasingAlgorithm {
    /*
        Huber:  1 / sigma if |x| <= sigma
                1 / |x|      otherwise
                sigma = sigma

        Lorentz: 2 / (2 + (x^2 / sigma^2))
                sigma = sigma / sqrt(2)

        Tukey: 0.5 * (1 - (x/sigma)^2)^2  if |x| <= sigma
               0            otherwise
               sigma = sigma * sqrt(5)

        Gauss: e^(- x^2 / (2*sigma^2))
              sigma = sigma

     */
    private double CentralA;
    private double CentralB;
    private double CentralC;
    private double SumA;
    private double SumB;
    private double SumC;

    private double SumA2;
    private double SumB2;
    private double SumC2;

    private boolean avgWithMean;

    private double[] gaussian_coefficients;

    private double combined_coefficient_sumA;
    private double combined_coefficient_sumB;
    private double combined_coefficient_sumC;

    private double sigmaS_reciprocal;

    private double[] gaussSimilarity;

    public BilateralAntialiasingAlgorithm(int totalSamples, double[] gaussian_coefficients, int kernel_size, double sigmaS, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        SumA = 0;
        SumB = 0;
        SumC = 0;
        SumA2 = 0;
        SumB2 = 0;
        SumC2 = 0;
        CentralA = 0;
        CentralB = 0;
        CentralC = 0;
        combined_coefficient_sumA = 0;
        combined_coefficient_sumB = 0;
        combined_coefficient_sumC = 0;
        this.gaussian_coefficients = gaussian_coefficients;
        this.avgWithMean = avgWithMean;

        if(sigmaS == 0) {
            sigmaS = GaussianAntialiasingAlgorithm.getSigma(kernel_size);
        }
        sigmaS_reciprocal = 1 / sigmaS;

        // precomute all possible similarity values for
        // performance resaons
        if (colorSpace == 0) {
            this.gaussSimilarity = new double[256];
            double twoSigmaRSquared = 2 * sigmaS * sigmaS;
            for (int i = 0; i < 256; i++) {
                this.gaussSimilarity[i] = Math.exp(-(((long)i * i) / twoSigmaRSquared));
            }
        }
    }

    @Override
    public void initialize(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            SumA2 = result[0];
            SumB2 = result[1];
            SumC2 = result[2];
        }

        CentralA = result[0];
        CentralB = result[1];
        CentralC = result[2];

        addedSamples = 0;
        double coefficient = gaussian_coefficients[addedSamples];

        double ccA = coefficient;// * similarity(result[0], CentralA);
        double ccB = coefficient;// * similarity(result[1], CentralB);
        double ccC = coefficient;// * similarity(result[2], CentralC);

        SumA = result[0] * ccA;
        SumB = result[1] * ccB;
        SumC = result[2] * ccC;

        combined_coefficient_sumA = ccA;
        combined_coefficient_sumB = ccB;
        combined_coefficient_sumC = ccC;

        addedSamples = 1;
    }

    private double similarity(double val, double centralVal) {
        double distance = Math.abs(val - centralVal);
        if (colorSpace == 0) {
            return gaussSimilarity[(int)distance];
        }
        else {
            double exponent = distance * sigmaS_reciprocal;
            exponent = exponent * exponent;
            return Math.exp(-0.5 * exponent);
        }
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

        double ccA = coefficient * similarity(result[0], CentralA);
        double ccB = coefficient * similarity(result[1], CentralB);
        double ccC = coefficient * similarity(result[2], CentralC);

        SumA += result[0] * ccA;
        SumB += result[1] * ccB;
        SumC += result[2] * ccC;

        combined_coefficient_sumA += ccA;
        combined_coefficient_sumB += ccB;
        combined_coefficient_sumC += ccC;

        addedSamples++;
        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        if(avgWithMean) {
            double finalA = (SumA / combined_coefficient_sumA + SumA2 * totalSamplesReciprocal) * 0.5;
            double finalB = (SumB / combined_coefficient_sumB + SumB2 * totalSamplesReciprocal) * 0.5;
            double finalC = (SumC / combined_coefficient_sumC + SumC2 * totalSamplesReciprocal) * 0.5;
            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        } else {
            int[] result = getColorChannels(SumA / combined_coefficient_sumA, SumB / combined_coefficient_sumB, SumC / combined_coefficient_sumC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }
}
