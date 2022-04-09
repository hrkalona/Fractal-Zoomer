package fractalzoomer.core.antialiasing;

public class ClosestToMeanAntialiasingAlgorithmRGB  extends AntialiasingAlgorithm {

    private double redSum;
    private double greenSum;
    private double blueSum;
    private int[] redValues;
    private int[] greenValues;
    private int[] blueValues;
    private int index;
    private boolean avgWithMean;

    public ClosestToMeanAntialiasingAlgorithmRGB(int totalSamples,  boolean avgWithMean) {
        super(totalSamples);
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
        redValues = new int[totalSamples];
        greenValues = new int[totalSamples];
        blueValues = new int[totalSamples];
        index = 0;
        this.avgWithMean = avgWithMean;
    }

    public void initialize(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        redSum = red;
        greenSum = green;
        blueSum = blue;

        index = 0;
        redValues[index] = red;
        greenValues[index] = green;
        blueValues[index] = blue;
        index++;
    }

    public void addSample(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        redSum += red;
        greenSum += green;
        blueSum += blue;

        redValues[index] = red;
        greenValues[index] = green;
        blueValues[index] = blue;
        index++;
    }
    public int getColor() {

        double avgRed = redSum / totalSamples;
        double avgGreen = greenSum / totalSamples;
        double avgBlue = blueSum / totalSamples;

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
            double redRes = ((redSum / totalSamples) + redValues[minDistanceIndexRed]) * 0.5 + 0.5;
            double greenRes = ((greenSum / totalSamples) + greenValues[minDistanceIndexGreen]) * 0.5 + 0.5;
            double blueRes = ((blueSum / totalSamples) + blueValues[minDistanceIndexBlue]) * 0.5 + 0.5;

            return 0xff000000 | ((int)redRes) << 16 | ((int)greenRes) << 8 | ((int)blueRes);
        }
        else {
            return 0xff000000 | (redValues[minDistanceIndexRed] << 16) | (greenValues[minDistanceIndexGreen] << 8) | (blueValues[minDistanceIndexBlue]);
        }
    }
}
