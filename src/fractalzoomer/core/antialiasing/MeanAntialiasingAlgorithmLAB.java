package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorSpaceConverter;

public class MeanAntialiasingAlgorithmLAB extends AntialiasingAlgorithm {
    private double LSum;
    private double ASum;
    private double BSum;

    public MeanAntialiasingAlgorithmLAB(int totalSamples) {
        super(totalSamples);
        LSum = 0;
        ASum = 0;
        BSum = 0;
    }

    @Override
    public void initialize(int color) {
        double[] res = ColorSpaceConverter.RGBtoLAB((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
        LSum = res[0];
        ASum = res[1];
        BSum = res[2];
    }

    @Override
    public boolean addSample(int color) {
        double[] res = ColorSpaceConverter.RGBtoLAB((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
        LSum += res[0];
        ASum += res[1];
        BSum += res[2];
        return true;
    }
    @Override
    public int getColor() {

        int[] res = ColorSpaceConverter.LABtoRGB(LSum / totalSamples, ASum / totalSamples, BSum / totalSamples);
        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }
}
