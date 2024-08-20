package fractalzoomer.core.antialiasing;

import java.util.Arrays;

public class MeanNoOutliersAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double[] redValues;
    private double[] greenValues;
    private double[] blueValues;

    public MeanNoOutliersAntialiasingAlgorithm(int totalSamples,  int colorSpace) {
        super(totalSamples, colorSpace);
        redValues = new double[totalSamples];
        greenValues = new double[totalSamples];
        blueValues = new double[totalSamples];
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        addedSamples = 0;
        redValues[addedSamples] = result[0];
        greenValues[addedSamples] =  result[1];
        blueValues[addedSamples] = result[2];
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);


        redValues[addedSamples] = result[0];
        greenValues[addedSamples] = result[1];
        blueValues[addedSamples] = result[2];
        addedSamples++;

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

            double temp = 1.5 * iqr;
            double lower_fence2 = lower_quartile - temp;
            double upper_fence2 = upper_quartile + temp;

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
            double temp2 = 3 * sigma;
            double lower_fence1 = mean - temp2;
            double upper_fence1 = mean + temp2;

            lower_fence = Math.max(lower_fence1, lower_fence2);
            upper_fence = Math.min(upper_fence1, upper_fence2);

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
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        Object[] res1 = getSumAndSamples(redValues);
        Object[] res2 = getSumAndSamples(greenValues);
        Object[] res3 = getSumAndSamples(blueValues);

        int[] result = getAveragedColorChannels((double)res1[0], (double)res2[0], (double)res3[0], (int)res1[1], (int)res2[1], (int)res3[1]);
        return  0xff000000 | (result[0] << 16) | (result[1] << 8) | result[2];
    }

}
