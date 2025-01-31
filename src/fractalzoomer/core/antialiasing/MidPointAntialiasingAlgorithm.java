package fractalzoomer.core.antialiasing;


import fractalzoomer.utils.ColorCorrection;

@Deprecated
public class MidPointAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double minRed, maxRed;
    private double minGreen, maxGreen;
    private double minBlue, maxBlue;
    private boolean avgWithMean;
    private double redSum;
    private double greenSum;
    private double blueSum;

    public MidPointAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        minRed = Double.MAX_VALUE;
        minGreen = Double.MAX_VALUE;
        minBlue = Double.MAX_VALUE;
        maxRed = -Double.MAX_VALUE;
        maxGreen = -Double.MAX_VALUE;
        maxBlue = -Double.MAX_VALUE;
        this.avgWithMean = avgWithMean;
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        double res0 = result[0];
        double res1 = result[1];
        double res2 = result[2];

        if (avgWithMean) {
            redSum = res0;
            greenSum = res1;
            blueSum = res2;
        }

        minRed = maxRed = res0;
        minGreen = maxGreen = res1;
        minBlue = maxBlue = res2;
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        double res0 = result[0];
        double res1 = result[1];
        double res2 = result[2];

        if (avgWithMean) {
            redSum += res0;
            greenSum += res1;
            blueSum += res2;
        }

        if (res0 < minRed) {
            minRed = res0;
        }
        if (res1 < minGreen) {
            minGreen = res1;
        }
        if (res2 < minBlue) {
            minBlue = res2;
        }

        if (res0 > maxRed) {
            maxRed = res0;
        }
        if (res1 > maxGreen) {
            maxGreen = res1;
        }
        if (res2 > maxBlue) {
            maxBlue = res2;
        }

        addedSamples++;
        return true;
    }

    @Override
    public int getColor() {
        if (addedSamples != totalSamples) {
            return 0xff000000;
        }

        if (avgWithMean) {
            double finalA = ((minRed + maxRed) * 0.5 + redSum * totalSamplesReciprocal) * 0.5;
            double finalB = ((minGreen + maxGreen) * 0.5 + greenSum * totalSamplesReciprocal) * 0.5;
            double finalC = ((minBlue + maxBlue) * 0.5 + blueSum * totalSamplesReciprocal) * 0.5;
            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        } else {
            int[] result = getColorChannels((minRed + maxRed) * 0.5, (minGreen + maxGreen) * 0.5, (minBlue + maxBlue) * 0.5);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }
}
