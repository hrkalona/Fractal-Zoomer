package fractalzoomer.core.antialiasing;

import java.util.Arrays;

public class MeanNoOutliersAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double[] redValues;
    private double[] greenValues;
    private double[] blueValues;
    private int index;

    public MeanNoOutliersAntialiasingAlgorithm(int totalSamples,  int colorSpace) {
        super(totalSamples, colorSpace);
        redValues = new double[totalSamples];
        greenValues = new double[totalSamples];
        blueValues = new double[totalSamples];
        index = 0;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        index = 0;
        redValues[index] = result[0];
        greenValues[index] =  result[1];
        blueValues[index] = result[2];
        index++;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);


        redValues[index] = result[0];
        greenValues[index] = result[1];
        blueValues[index] = result[2];
        index++;

        return true;
    }

    private double calculateMedian(double[] values, int start, int end) {
        int length = end - start;
        int middle = start + length / 2;

        if (length % 2 == 0) {
            return (values[middle] + values[middle - 1]) * 0.5;
        }

        return values[middle];
    }

    private Object[] getSumAndSamples(double[] vals) {

        boolean allTheSame = true;
        for(int i = 0; i < vals.length; i++) {
            if(vals[i] != vals[0]) {
                allTheSame = false;
                break;
            }
        }

        double lower_fence = -Double.MAX_VALUE;
        double upper_fence = Double.MAX_VALUE;

        if(!allTheSame) {
            Arrays.sort(vals);

            // double median = calculateMedian(vals, 0, vals.length);
            double lower_quartile = calculateMedian(vals, 0, vals.length / 2);
            double upper_quartile = calculateMedian(vals, (vals.length + 1) / 2, vals.length);
            double iqr = upper_quartile - lower_quartile;

            if (iqr == 0) {
                double mean = 0;
                double variance = 0;
                int samples = 0;
                for (int i = 0; i < vals.length; i++) {
                    samples++;
                    double delta = vals[i] - mean;
                    mean += delta / samples;
                    double delta2 = vals[i] - mean;
                    variance += delta * delta2;
                }
                double sigma = Math.sqrt(variance / samples);
                double temp = 3 * sigma;
                lower_fence = mean - temp;
                upper_fence = mean + temp;
            } else {
                double temp = 1.5 * iqr;
                lower_fence = lower_quartile - temp;
                upper_fence = upper_quartile + temp;
            }
        }

        double sum = 0;
        int samples = 0;
        for(int i = 0; i < vals.length; i++) {
            if(vals[i] >= lower_fence && vals[i] <= upper_fence) {
                sum += vals[i];
                samples++;
            }
        }

        return new Object[] {sum, samples};
    }

    @Override
    public int getColor() {
        Object[] res1 = getSumAndSamples(redValues);
        Object[] res2 = getSumAndSamples(greenValues);
        Object[] res3 = getSumAndSamples(blueValues);

        int[] result = getAveragedColorChannels((double)res1[0], (double)res2[0], (double)res3[0], (int)res1[1], (int)res2[1], (int)res3[1]);
        return  0xff000000 | (result[0] << 16) | (result[1] << 8) | result[2];
    }

    @Override
    protected double function(double value, int samples) {
        return value / samples;
    }

}
