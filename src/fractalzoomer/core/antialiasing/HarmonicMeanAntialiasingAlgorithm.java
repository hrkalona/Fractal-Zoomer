package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

import java.util.Arrays;

public class HarmonicMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private boolean avgWithMean;
    private double ASum;
    private double BSum;
    private double CSum;

    private double HASum;
    private double HBSum;
    private double HCSum;

    public HarmonicMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        this.avgWithMean = avgWithMean;
        ASum = 0;
        BSum = 0;
        CSum = 0;
        HASum = 0;
        HBSum = 0;
        HCSum = 0;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum = a;
            BSum = b;
            CSum = c;
        }

        HASum = 0;
        HBSum = 0;
        HCSum = 0;

        if (a > 0) HASum += 1.0 / a;
        if (b > 0) HBSum += 1.0 / b;
        if (c > 0) HCSum += 1.0 / c;

        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum += a;
            BSum += b;
            CSum += c;
        }

        if (a > 0) HASum += 1.0 / a;
        if (b > 0) HBSum += 1.0 / b;
        if (c > 0) HCSum += 1.0 / c;

        addedSamples++;

        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double harmonicA = (HASum > 0) ? addedSamples / HASum : 0;
        double harmonicB = (HBSum > 0) ? addedSamples / HBSum : 0;
        double harmonicC = (HCSum > 0) ? addedSamples / HCSum : 0;

        if(avgWithMean) {
            double finalA = (harmonicA + ASum * totalSamplesReciprocal) * 0.5;
            double finalB = (harmonicB + BSum * totalSamplesReciprocal) * 0.5;
            double finalC = (harmonicC + CSum * totalSamplesReciprocal) * 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(harmonicA, harmonicB, harmonicC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

}
