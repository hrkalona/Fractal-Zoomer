package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class RMSMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private boolean avgWithMean;
    private double ASum;
    private double BSum;
    private double CSum;

    private double RMSASum;
    private double RMSBSum;
    private double RMSCSum;

    public RMSMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        this.avgWithMean = avgWithMean;
        ASum = 0;
        BSum = 0;
        CSum = 0;
        RMSASum = 0;
        RMSBSum = 0;
        RMSCSum = 0;
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

        RMSASum = a * a;
        RMSBSum = b * b;
        RMSCSum = c * c;

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

        RMSASum += a * a;
        RMSBSum += b * b;
        RMSCSum += c * c;

        addedSamples++;

        return true;
    }

    @Override
    public int getColor() {
        if (addedSamples == 0) {
            return 0xff000000;
        }

        double addedSamplesReciprocal = 1.0 / addedSamples;
        double rmsA = Math.sqrt(RMSASum * addedSamplesReciprocal);
        double rmsB = Math.sqrt(RMSBSum * addedSamplesReciprocal);
        double rmsC = Math.sqrt(RMSCSum * addedSamplesReciprocal);

        if(avgWithMean) {
            double finalA = (rmsA + ASum * addedSamplesReciprocal) * 0.5;
            double finalB = (rmsB + BSum * addedSamplesReciprocal) * 0.5;
            double finalC = (rmsC + CSum * addedSamplesReciprocal) * 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(rmsA, rmsB, rmsC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

}
