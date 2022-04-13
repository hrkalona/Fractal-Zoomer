package fractalzoomer.core.antialiasing;

public class MeanAntialiasingAlgorithmRGB extends AntialiasingAlgorithm {
    private double redSum;
    private double greenSum;
    private double blueSum;

    public MeanAntialiasingAlgorithmRGB(int totalSamples) {
        super(totalSamples);
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
    }

    @Override
    public void initialize(int color) {
        redSum = (color >> 16) & 0xff;
        greenSum = (color >> 8) & 0xff;
        blueSum = color & 0xff;
    }

    @Override
    public boolean addSample(int color) {
        redSum += (color >> 16) & 0xff;
        greenSum += (color >> 8) & 0xff;
        blueSum += color & 0xff;
        return true;
    }

    @Override
    public int getColor() {
        return  0xff000000 | (((int)(redSum / totalSamples + 0.5)) << 16) | (((int)(greenSum / totalSamples + 0.5)) << 8) | ((int)(blueSum / totalSamples + 0.5));
    }
}
