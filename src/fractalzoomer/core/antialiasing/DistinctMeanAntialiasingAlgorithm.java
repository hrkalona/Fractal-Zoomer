package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

import java.util.HashSet;

public class DistinctMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private boolean avgWithMean;
    private double SumA2;
    private double SumB2;
    private double SumC2;
    private HashSet<Double> valsA;
    private HashSet<Double> valsB;
    private HashSet<Double> valsC;

    public DistinctMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        this.avgWithMean = avgWithMean;
        SumA2 = 0;
        SumB2 = 0;
        SumC2 = 0;
        valsA = new HashSet<>();
        valsB = new HashSet<>();
        valsC = new HashSet<>();
    }

    @Override
    public void initialize(int color) {

        valsA.clear();
        valsB.clear();
        valsC.clear();

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            SumA2 = result[0];
            SumB2 = result[1];
            SumC2 = result[2];
        }

        valsA.add(result[0]);
        valsB.add(result[1]);
        valsC.add(result[2]);

        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            SumA2 += result[0];
            SumB2 += result[1];
            SumC2 += result[2];
        }

        valsA.add(result[0]);
        valsB.add(result[1]);
        valsC.add(result[2]);

        addedSamples++;
        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double avgA = valsA.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
        double avgB = valsB.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
        double avgC = valsC.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();

        if(avgWithMean) {
            double finalA = (avgA + SumA2 * totalSamplesReciprocal) * 0.5;
            double finalB = (avgB + SumB2 * totalSamplesReciprocal) * 0.5;
            double finalC = (avgC + SumC2 * totalSamplesReciprocal) * 0.5;
            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        } else {
            int[] result = getColorChannels(avgA, avgB, avgC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }
}
