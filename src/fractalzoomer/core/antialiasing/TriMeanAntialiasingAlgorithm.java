package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

import java.util.Arrays;

public class TriMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double[] AValues;
    private double[] BValues;
    private double[] CValues;
    private boolean avgWithMean;
    private double ASum;
    private double BSum;
    private double CSum;

    public TriMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        AValues = new double[totalSamples];
        BValues = new double[totalSamples];
        CValues = new double[totalSamples];
        this.avgWithMean = avgWithMean;
        ASum = 0;
        BSum = 0;
        CSum = 0;
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

        addedSamples = 0;
        AValues[addedSamples] = a;
        BValues[addedSamples] = b;
        CValues[addedSamples] = c;
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

        AValues[addedSamples] = a;
        BValues[addedSamples] = b;
        CValues[addedSamples] = c;
        addedSamples++;

        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        Arrays.sort(AValues);
        Arrays.sort(BValues);
        Arrays.sort(CValues);

        double medianA = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(AValues, 0, AValues.length);
        double q1A = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(AValues, 0, AValues.length / 2);
        double q3A = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(AValues, (AValues.length + 1) / 2, AValues.length);

        double medianB = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(BValues, 0, BValues.length);
        double q1B = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(BValues, 0, BValues.length / 2);
        double q3B = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(BValues, (BValues.length + 1) / 2, BValues.length);

        double medianC = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(CValues, 0, CValues.length);
        double q1C = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(CValues, 0, CValues.length / 2);
        double q3C = MeanNoOutliersAntialiasingAlgorithm.calculateMedian(CValues, (CValues.length + 1) / 2, CValues.length);

        double triMeanA = (q1A + 2 * medianA + q3A) * 0.25;
        double triMeanB = (q1B + 2 * medianB + q3B) * 0.25;
        double triMeanC = (q1C + 2 * medianC + q3C) * 0.25;

        if(avgWithMean) {
            double finalA = (triMeanA + ASum * totalSamplesReciprocal) * 0.5;
            double finalB = (triMeanB + BSum * totalSamplesReciprocal) * 0.5;
            double finalC = (triMeanC + CSum * totalSamplesReciprocal) * 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(triMeanA, triMeanB, triMeanC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

}
