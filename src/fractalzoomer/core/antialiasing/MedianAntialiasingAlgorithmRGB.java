package fractalzoomer.core.antialiasing;

import java.util.Arrays;

public class MedianAntialiasingAlgorithmRGB extends AntialiasingAlgorithm {
    private int[] redValues;
    private int[] greenValues;
    private int[] blueValues;
    private int index;
    private int median;
    private boolean avgWithMean;
    private double redSum;
    private double greenSum;
    private double blueSum;

    public MedianAntialiasingAlgorithmRGB(int totalSamples, boolean avgWithMean) {
        super(totalSamples);
        redValues = new int[totalSamples];
        greenValues = new int[totalSamples];
        blueValues = new int[totalSamples];
        index = 0;
        median = (totalSamples >>> 1);
        this.avgWithMean = avgWithMean;
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
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

        index = 0;
        redValues[index] = red;
        greenValues[index] = green;
        blueValues[index] = blue;
        index++;
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

        redValues[index] = red;
        greenValues[index] = green;
        blueValues[index] = blue;
        index++;

        return true;
    }

    @Override
    public int getColor() {
        Arrays.sort(redValues);
        Arrays.sort(greenValues);
        Arrays.sort(blueValues);

        if(avgWithMean) {
            double redRes = ((redSum / totalSamples) + redValues[median]) * 0.5 + 0.5;
            double greenRes = ((greenSum / totalSamples) + greenValues[median]) * 0.5 + 0.5;
            double blueRes = ((blueSum / totalSamples) + blueValues[median]) * 0.5 + 0.5;

            return 0xff000000 | ((int)redRes) << 16 | ((int)greenRes) << 8 | ((int)blueRes);
        }
        else {
            return 0xff000000 | redValues[median] << 16 | greenValues[median] << 8 | blueValues[median];
        }
    }

}
