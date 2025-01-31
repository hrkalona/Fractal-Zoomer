package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class ClosestToMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {

    private double ASum;
    private double BSum;
    private double CSum;
    private double[] AValues;
    private double[] BValues;
    private double[] CValues;
    private boolean avgWithMean;

    public ClosestToMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        ASum = 0;
        BSum = 0;
        CSum = 0;
        AValues = new double[totalSamples];
        BValues = new double[totalSamples];
        CValues = new double[totalSamples];
        this.avgWithMean = avgWithMean;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        ASum = result[0];
        BSum = result[1];
        CSum = result[2];

        addedSamples = 0;
        AValues[addedSamples] = result[0];
        BValues[addedSamples] = result[1];
        CValues[addedSamples] = result[2];
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);
        ASum += result[0];
        BSum += result[1];
        CSum += result[2];

        AValues[addedSamples] = result[0];
        BValues[addedSamples] = result[1];
        CValues[addedSamples] = result[2];
        addedSamples++;
        return true;
    }
    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double avgA = ASum * totalSamplesReciprocal;
        double avgB = BSum * totalSamplesReciprocal;
        double avgC = CSum * totalSamplesReciprocal;

        int minDistanceIndexA = 0;
        int minDistanceIndexB = 0;
        int minDistanceIndexC = 0;

        double minDistanceA = Double.MAX_VALUE;
        double minDistanceB = Double.MAX_VALUE;
        double minDistanceC = Double.MAX_VALUE;

        for(int i = 0; i < AValues.length; i++) {
            double ADist = Math.abs(avgA - AValues[i]);
            double BDist = Math.abs(avgB - BValues[i]);
            double CDist = Math.abs(avgC - CValues[i]);
            if(ADist < minDistanceA) {
                minDistanceA = ADist;
                minDistanceIndexA = i;
            }
            if(BDist < minDistanceB) {
                minDistanceB = BDist;
                minDistanceIndexB = i;
            }
            if(CDist < minDistanceC) {
                minDistanceC = CDist;
                minDistanceIndexC = i;
            }
        }

        if(avgWithMean) {
            double finalA = ((ASum * totalSamplesReciprocal) + AValues[minDistanceIndexA]) * 0.5 + 0.5;
            double finalB = ((BSum * totalSamplesReciprocal) + BValues[minDistanceIndexB]) * 0.5 + 0.5;
            double finalC = ((CSum * totalSamplesReciprocal) + CValues[minDistanceIndexC]) * 0.5 + 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(AValues[minDistanceIndexA], BValues[minDistanceIndexB], CValues[minDistanceIndexC]);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }
}
