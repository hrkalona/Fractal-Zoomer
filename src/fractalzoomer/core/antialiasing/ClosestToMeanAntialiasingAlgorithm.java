package fractalzoomer.core.antialiasing;

public class ClosestToMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {

    private double redSum;
    private double greenSum;
    private double blueSum;
    private int[] redValues;
    private int[] greenValues;
    private int[] blueValues;
    private boolean avgWithMean;

    public ClosestToMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean) {
        super(totalSamples, 0);
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
        redValues = new int[totalSamples];
        greenValues = new int[totalSamples];
        blueValues = new int[totalSamples];
        this.avgWithMean = avgWithMean;
    }

    @Override
    public void initialize(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        redSum = red;
        greenSum = green;
        blueSum = blue;

        addedSamples = 0;
        redValues[addedSamples] = red;
        greenValues[addedSamples] = green;
        blueValues[addedSamples] = blue;
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        redSum += red;
        greenSum += green;
        blueSum += blue;

        redValues[addedSamples] = red;
        greenValues[addedSamples] = green;
        blueValues[addedSamples] = blue;
        addedSamples++;
        return true;
    }
    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double avgRed = redSum * totalSamplesReciprocal;
        double avgGreen = greenSum * totalSamplesReciprocal;
        double avgBlue = blueSum * totalSamplesReciprocal;

        int minDistanceIndexRed = 0;
        int minDistanceIndexGreen = 0;
        int minDistanceIndexBlue = 0;

        double minDistanceRed = Double.MAX_VALUE;
        double minDistanceGreen = Double.MAX_VALUE;
        double minDistanceBlue = Double.MAX_VALUE;

        for(int i = 0; i < redValues.length; i++) {
            double redDist = Math.abs(avgRed - redValues[i]);
            double greenDist = Math.abs(avgGreen - greenValues[i]);
            double blueDist = Math.abs(avgBlue - blueValues[i]);
            if(redDist < minDistanceRed) {
                minDistanceRed = redDist;
                minDistanceIndexRed = i;
            }
            if(greenDist < minDistanceGreen) {
                minDistanceGreen = greenDist;
                minDistanceIndexGreen = i;
            }
            if(blueDist < minDistanceBlue) {
                minDistanceBlue = blueDist;
                minDistanceIndexBlue = i;
            }
        }

        if(avgWithMean) {
            double redRes = ((redSum * totalSamplesReciprocal) + redValues[minDistanceIndexRed]) * 0.5 + 0.5;
            double greenRes = ((greenSum * totalSamplesReciprocal) + greenValues[minDistanceIndexGreen]) * 0.5 + 0.5;
            double blueRes = ((blueSum * totalSamplesReciprocal) + blueValues[minDistanceIndexBlue]) * 0.5 + 0.5;

            return 0xff000000 | ((int)redRes) << 16 | ((int)greenRes) << 8 | ((int)blueRes);
        }
        else {
            return 0xff000000 | (redValues[minDistanceIndexRed] << 16) | (greenValues[minDistanceIndexGreen] << 8) | (blueValues[minDistanceIndexBlue]);
        }
    }
}
