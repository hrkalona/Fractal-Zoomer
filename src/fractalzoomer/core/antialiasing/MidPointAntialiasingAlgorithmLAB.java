package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorSpaceConverter;

import java.util.Arrays;

public class MidPointAntialiasingAlgorithmLAB extends AntialiasingAlgorithm {
    private double[] LValues;
    private double[] AValues;
    private double[] BValues;
    private int index;
    private int maxIndex;

    public MidPointAntialiasingAlgorithmLAB(int totalSamples) {
        super(totalSamples);
        LValues = new double[totalSamples];
        AValues = new double[totalSamples];
        BValues = new double[totalSamples];
        index = 0;
        maxIndex = totalSamples - 1;
    }

    public void initialize(int color) {
        index = 0;
        double[] res = ColorSpaceConverter.RGBtoLAB((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
        LValues[index] = res[0];
        AValues[index] = res[1];
        BValues[index] = res[2];
        index++;
    }

    public void addSample(int color) {
        double[] res = ColorSpaceConverter.RGBtoLAB((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
        LValues[index] = res[0];
        AValues[index] = res[1];
        BValues[index] = res[2];
        index++;
    }
    public int getColor() {
        Arrays.sort(LValues);
        Arrays.sort(AValues);
        Arrays.sort(BValues);

        int[] res = ColorSpaceConverter.LABtoRGB((LValues[0] - LValues[maxIndex]) / 2.0, (AValues[0] - AValues[maxIndex]) / 2.0, (BValues[0] - BValues[maxIndex]) / 2.0);
        return  0xff000000 | res[0] << 16 | res[1] << 8 | res[2];
    }

}
