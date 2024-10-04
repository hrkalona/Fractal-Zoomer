package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class GeometricMeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private boolean avgWithMean;
    private double ASum;
    private double BSum;
    private double CSum;

    private double AProduct;
    private double BProduct;
    private double CProduct;

    public GeometricMeanAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        this.avgWithMean = avgWithMean;
        ASum = 0;
        BSum = 0;
        CSum = 0;
        AProduct = 0;
        BProduct = 0;
        CProduct = 0;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum = a;
            BSum = b;
            CSum = c;
        }

        AProduct = 1;
        BProduct = 1;
        CProduct = 1;

        if (a > 0) AProduct *= a;
        if (b > 0) BProduct *= b;
        if (c > 0) CProduct *= c;

        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum += a;
            BSum += b;
            CSum += c;
        }

        if (a > 0) AProduct *= a;
        if (b > 0) BProduct *= b;
        if (c > 0) CProduct *= c;

        addedSamples++;

        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double geometricA = (AProduct > 0) ? Math.pow(AProduct, 1.0 / addedSamples) : 0;
        double geometricB = (BProduct > 0) ? Math.pow(BProduct, 1.0 / addedSamples) : 0;
        double geometricC = (CProduct > 0) ? Math.pow(CProduct, 1.0 / addedSamples) : 0;

        if(avgWithMean) {
            double finalA = (geometricA + ASum * totalSamplesReciprocal) * 0.5;
            double finalB = (geometricB + BSum * totalSamplesReciprocal) * 0.5;
            double finalC = (geometricC + CSum * totalSamplesReciprocal) * 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(geometricA, geometricB, geometricC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

}
