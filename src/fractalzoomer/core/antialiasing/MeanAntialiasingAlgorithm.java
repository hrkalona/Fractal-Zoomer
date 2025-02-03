package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class MeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double sumA;
    private double sumB;
    private double sumC;


    public MeanAntialiasingAlgorithm(int totalSamples,  int colorSpace) {
        super(totalSamples, colorSpace);
        sumA = 0;
        sumB = 0;
        sumC = 0;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        sumA = result[0];
        sumB = result[1];
        sumC = result[2];
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        sumA += result[0];
        sumB += result[1];
        sumC += result[2];
        addedSamples++;

        return true;
    }

    @Override
    public int getColor() {
        if (addedSamples == 0) {
            return 0xff000000;
        }
        double addedSamplesReciprocal = 1.0 / addedSamples;
        int[] result = getColorChannels(sumA * addedSamplesReciprocal, sumB * addedSamplesReciprocal, sumC * addedSamplesReciprocal);
        return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
    }
}
