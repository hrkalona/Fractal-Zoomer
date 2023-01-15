package fractalzoomer.core.antialiasing;


public class MidPointAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double minRed, maxRed;
    private double minGreen, maxGreen;
    private double minBlue, maxBlue;
    private boolean avgWithMean;
    private double redSum;
    private double greenSum;
    private double blueSum;

    public MidPointAntialiasingAlgorithm(int totalSamples, boolean avgWithMean,  int colorSpace) {
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

        if(avgWithMean) {
            redSum = res0;
            greenSum = res1;
            blueSum = res2;
        }

        minRed = maxRed = res0;
        minGreen = maxGreen = res1;
        minBlue = maxBlue = res2;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        double res0 = result[0];
        double res1 = result[1];
        double res2 = result[2];

        if(avgWithMean) {
            redSum += res0;
            greenSum += res1;
            blueSum += res2;
        }

        if(res0 < minRed) {
            minRed = res0;
        }
        if(res1 < minGreen) {
            minGreen = res1;
        }
        if(res2 < minBlue) {
            minBlue = res2;
        }

        if(res0 > maxRed) {
            maxRed = res0;
        }
        if(res1 > maxGreen) {
            maxGreen = res1;
        }
        if(res2 > maxBlue) {
            maxBlue = res2;
        }

        return true;
    }

    @Override
    public int getColor() {
        int[] result = getAveragedColorChannels(minRed, minGreen, minBlue, maxRed, maxGreen, maxBlue, redSum, greenSum, blueSum);
        return  0xff000000 | (result[0] << 16) | (result[1] << 8) | result[2];
    }

    @Override
    protected double function(double value, double value2, double value3) {
        if(avgWithMean) {
            return ((value + value2) * 0.5 + value3 * totalSamplesReciprocal) * 0.5;
        }
        else {
            return (value + value2) * 0.5;
        }
    }
}
