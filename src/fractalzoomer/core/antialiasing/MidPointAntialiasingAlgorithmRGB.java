package fractalzoomer.core.antialiasing;


public class MidPointAntialiasingAlgorithmRGB extends AntialiasingAlgorithm {
    private int minRed, maxRed;
    private int minGreen, maxGreen;
    private int minBlue, maxBlue;
    private boolean avgWithMean;
    private double redSum;
    private double greenSum;
    private double blueSum;

    public MidPointAntialiasingAlgorithmRGB(int totalSamples, boolean avgWithMean) {
        super(totalSamples);
        minRed = Integer.MAX_VALUE;
        minGreen = Integer.MAX_VALUE;
        minBlue = Integer.MAX_VALUE;
        maxRed = -1;
        maxGreen = -1;
        maxBlue = -1;
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

        if(avgWithMean) {
            double redRes = ((redSum / totalSamples) + (minRed + maxRed) * 0.5) * 0.5 + 0.5;
            double greenRes = ((greenSum / totalSamples) + (minGreen + maxGreen) * 0.5) * 0.5 + 0.5;
            double blueRes = ((blueSum / totalSamples) + (minBlue + maxBlue) * 0.5) * 0.5 + 0.5;

            return 0xff000000 | ((int)redRes) << 16 | ((int)greenRes) << 8 | ((int)blueRes);
        }
        else {
            int red = (int) ((minRed + maxRed) * 0.5 + 0.5);
            int green = (int) ((minGreen + maxGreen) * 0.5 + 0.5);
            int blue = (int) ((minBlue + maxBlue) * 0.5 + 0.5);
            return 0xff000000 | red << 16 | green << 8 | blue;
        }
    }
}
