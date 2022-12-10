package fractalzoomer.core.antialiasing;

public class AdaptiveMeanAntialiasingAlgorithmRGB extends AntialiasingAlgorithm {
    private double MeanR;
    private double MeanG;
    private double MeanB;

    private double VarR;

    private double VarG;

    private double VarB;

    private int sampleCount;

    private int MinSamples;
    private double AdaptiveThreshold;

    private double [] sampleCountReciprocals;

    public AdaptiveMeanAntialiasingAlgorithmRGB(int totalSamples, int minAdaptiveSteps) {
        super(totalSamples);
        MeanR = 0;
        MeanG = 0;
        MeanB = 0;
        VarR = 0;
        VarG = 0;
        VarB = 0;
        sampleCount = 0;
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

        MeanR = (color >> 16) & 0xff;
        MeanG = (color >> 8) & 0xff;
        MeanB = color & 0xff;
        sampleCount = 1;

    }

    @Override
    public boolean addSample(int color) {
        double red = (color >> 16) & 0xff;
        double green = (color >> 8) & 0xff;
        double blue = color & 0xff;
        sampleCount++;

        double rec = sampleCountReciprocals[sampleCount];

        double NewMeanR = MeanR + (red - MeanR) * rec;
        double NewMeanG = MeanG + (green - MeanG) * rec;
        double NewMeanB = MeanB + (blue - MeanB) * rec;

        VarR = VarR + (red - NewMeanR) * (red - MeanR);
        VarG = VarG + (green - NewMeanG) * (green - MeanG);
        VarB = VarB + (blue - NewMeanB) * (blue - MeanB);

        MeanR = NewMeanR;
        MeanG = NewMeanG;
        MeanB = NewMeanB;

        if(sampleCount <= MinSamples) {
            return true;
        }

        //The three variances need to be divided by sampleCount, but instead we multiply the test with sampleCount
        double VarianceSum = VarR + VarG + VarB;

        return VarianceSum >= AdaptiveThreshold * sampleCount * sampleCount;
    }

    @Override
    public int getColor() {
        return  0xff000000 | (((int)(MeanR + 0.5)) << 16) | (((int)(MeanG + 0.5)) << 8) | ((int)(MeanB + 0.5));
    }
}
