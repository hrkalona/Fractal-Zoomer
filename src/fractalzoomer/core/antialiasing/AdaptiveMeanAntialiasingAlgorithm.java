package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class AdaptiveMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double MeanA;
    private double MeanB;
    private double MeanC;

    private double VarA;

    private double VarB;

    private double VarC;

    private int MinSamples;
    private double AdaptiveThreshold;

    private double [] sampleCountReciprocals;

    public AdaptiveMeanAntialiasingAlgorithm(int totalSamples, int minAdaptiveSteps) {
        super(totalSamples, 0);
        MeanA = 0;
        MeanB = 0;
        MeanC = 0;
        VarA = 0;
        VarB = 0;
        VarC = 0;
        MinSamples = minAdaptiveSteps;

        sampleCountReciprocals = new double[totalSamples + 1];

        double samplecnt = 1;
        for(int i = 1; i < sampleCountReciprocals.length; i++) {
            sampleCountReciprocals[i] = 1 / samplecnt;
            samplecnt++;
        }

        if(MinSamples == 5) {
            AdaptiveThreshold = 2.5;
        }
        else {
            AdaptiveThreshold = 6;
        }
    }

    @Override
    public void initialize(int color) {

        double[] result = getColorChannels(color);

        MeanA = result[0];
        MeanB = result[1];
        MeanC = result[2];
        VarA = 0;
        VarB = 0;
        VarC = 0;
        addedSamples = 1;

    }

    @Override
    public boolean addSample(int color) {

        double[] result = getColorChannels(color);

        addedSamples++;

        double rec = sampleCountReciprocals[addedSamples];

        double a = result[0];
        double b = result[1];
        double c = result[2];

        double deltaA = a - MeanA;
        double deltaB = b - MeanB;
        double deltaC = c - MeanC;

        MeanA += deltaA * rec;
        MeanB += deltaB * rec;
        MeanC += deltaC * rec;

        if(needsAllSamples) {
            return true;
        }

        VarA += (a - MeanA) * deltaA;
        VarB += (b - MeanB) * deltaB;
        VarC += (c - MeanC) * deltaC;

        if(addedSamples <= MinSamples) {
            return true;
        }

        //The three variances need to be divided by sampleCount, but instead we multiply the test with sampleCount
        double VarianceSum = (VarA + VarB + VarC) / 3;

        return VarianceSum >= AdaptiveThreshold * addedSamples * addedSamples;
    }

    @Override
    public int getColor() {

        return ColorCorrection.linearToGamma(MeanA, MeanB, MeanC);

    }
}
