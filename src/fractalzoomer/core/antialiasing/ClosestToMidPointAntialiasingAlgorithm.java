package fractalzoomer.core.antialiasing;

public class ClosestToMidPointAntialiasingAlgorithm extends AntialiasingAlgorithm {

    private double redSum;
    private double greenSum;
    private double blueSum;
    private int[] redValues;
    private int[] greenValues;
    private int[] blueValues;
    private boolean avgWithMean;
    private int minRed, maxRed;
    private int minGreen, maxGreen;
    private int minBlue, maxBlue;

    public ClosestToMidPointAntialiasingAlgorithm(int totalSamples, boolean avgWithMean) {
        super(totalSamples, 0);
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
        redValues = new int[totalSamples];
        greenValues = new int[totalSamples];
        blueValues = new int[totalSamples];
        this.avgWithMean = avgWithMean;
        minRed = Integer.MAX_VALUE;
        minGreen = Integer.MAX_VALUE;
        minBlue = Integer.MAX_VALUE;
        maxRed = -1;
        maxGreen = -1;
        maxBlue = -1;
    }

    @Override
    public void initialize(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;

        if(avgWithMean) {
            redSum = red;
            greenSum = green;
            blueSum = blue;
        }

        addedSamples = 0;
        redValues[addedSamples] = red;
        greenValues[addedSamples] = green;
        blueValues[addedSamples] = blue;
        addedSamples = 1;

        minRed = maxRed = red;
        minGreen = maxGreen = green;
        minBlue = maxBlue = blue;
    }
    @Override
    public boolean addSample(int color) {
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;

        if(avgWithMean) {
            redSum += red;
            greenSum += green;
            blueSum += blue;
        }

        redValues[addedSamples] = red;
        greenValues[addedSamples] = green;
        blueValues[addedSamples] = blue;
        addedSamples++;

        if(red < minRed) {
            minRed = red;
        }
        if(green < minGreen) {
            minGreen = green;
        }
        if(blue < minBlue) {
            minBlue = blue;
        }

        if(red > maxRed) {
            maxRed = red;
        }
        if(green > maxGreen) {
            maxGreen = green;
        }
        if(blue > maxBlue) {
            maxBlue = blue;
        }

        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double avgRed = (minRed + maxRed) * 0.5;
        double avgGreen = (minGreen + maxGreen) * 0.5;
        double avgBlue = (minBlue + maxBlue) * 0.5;

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
