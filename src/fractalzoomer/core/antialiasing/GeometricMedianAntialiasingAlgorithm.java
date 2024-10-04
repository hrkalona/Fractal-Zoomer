package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;
import fractalzoomer.utils.ColorSpaceConverter;

public class GeometricMedianAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double[] AValues;
    private double[] BValues;
    private double[] CValues;
    private boolean avgWithMean;
    private double ASum;
    private double BSum;
    private double CSum;

    private Integer Value1;
    private Integer Value2;
    private boolean moreThanTwoValues;

    public GeometricMedianAntialiasingAlgorithm(int totalSamples, boolean avgWithMean, int colorSpace) {
        super(totalSamples, colorSpace);
        AValues = new double[totalSamples];
        BValues = new double[totalSamples];
        CValues = new double[totalSamples];
        this.avgWithMean = avgWithMean;
        ASum = 0;
        BSum = 0;
        CSum = 0;
        Value1 = null;
        Value2 = null;
        moreThanTwoValues = false;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

        Value1 = null;
        Value2 = null;
        moreThanTwoValues = false;

        if (!moreThanTwoValues && (Value1 == null || color == Value1)) {
            Value1 = color;
        } else if (!moreThanTwoValues && (Value2 == null || color == Value2)) {
            Value2 = color;
        } else {
            moreThanTwoValues = true;
        }

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum = a;
            BSum = b;
            CSum = c;
        }

        addedSamples = 0;
        AValues[addedSamples] = a;
        BValues[addedSamples] = b;
        CValues[addedSamples] = c;
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

        if (!moreThanTwoValues && (Value1 == null || color == Value1)) {
            Value1 = color;
        } else if (!moreThanTwoValues && (Value2 == null || color == Value2)) {
            Value2 = color;
        } else {
            moreThanTwoValues = true;
        }

        double a = result[0];
        double b = result[1];
        double c = result[2];

        if(avgWithMean) {
            ASum += a;
            BSum += b;
            CSum += c;
        }

        AValues[addedSamples] = a;
        BValues[addedSamples] = b;
        CValues[addedSamples] = c;
        addedSamples++;

        return true;
    }

    private void getVarI(double[][] vars, int i, double[] result) {
        for(int k = 0; k < result.length; k++) {
            result[k] = vars[k][i];
        }
    }

    private double[] getInitCentroid() {
        if(colorSpace == 0 || colorSpace == 1 || colorSpace == 3 || colorSpace == 7) { //RGB, XYZ, RYB, LinearRGB
            return new double[3];
        }
        else if(colorSpace == 2 || colorSpace == 4 || colorSpace == 5 || colorSpace == 6 || colorSpace == 8) { //Lab, LUV, OKLab, JzAzBz, YCbCr
            return new double[2];
        }
        return null;
    }

    public double[][] getVars() {
        if(colorSpace == 0 || colorSpace == 1 || colorSpace == 3 || colorSpace == 7) { //RGB, XYZ, RYB, LinearRGB
            double[][] vars = new double[3][];
            vars[0] = AValues;
            vars[1] = BValues;
            vars[2] = CValues;
            return vars;
        }
        else if(colorSpace == 2 || colorSpace == 4 || colorSpace == 5 || colorSpace == 6 || colorSpace == 8) { //Lab, LUV, OKLab, JzAzBz, YCbCr
            double[][] vars = new double[2][];
            vars[0] = BValues;
            vars[1] = CValues;
            return vars;
        }
        return null;
    }

    private double[] finalizeResult(double[] centroid) {

        if(colorSpace == 0 || colorSpace == 1 || colorSpace == 3 || colorSpace == 7) { //RGB, XYZ, RYB, LinearRGB
            return centroid;
        }
        else if(colorSpace == 2 || colorSpace == 4 || colorSpace == 5 || colorSpace == 6 || colorSpace == 8) { //Lab, LUV, OKLab, JzAzBz, YCbCr
            double sum = 0;
            for(int i = 0; i < AValues.length; i++) {
                sum += AValues[i];
            }
            sum *= totalSamplesReciprocal;
            return new double[] {sum, centroid[0], centroid[1]};
        }
        return null;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        double[] centroid = getInitCentroid();

        double[][] vars = getVars();

        int length = centroid.length;

        for (int i = 0; i < totalSamples; i++) {
            for(int k = 0; k < length; k++) {
                centroid[k] += vars[k][i];
            }
        }

        for(int k = 0; k < length; k++) {
            centroid[k] *= totalSamplesReciprocal;
        }
//        for (int i = 0; i < AValues.length; i++) {
//            centroid[0] += weight[i] * AValues[i];
//            centroid[1] += weight[i] * BValues[i];
//            centroid[2] += weight[i] * CValues[i];
//            totalWeight += weight[i];
//        }
//
//        if (totalWeight > 0) {
//            centroid[0] /= totalWeight;
//            centroid[1] /= totalWeight;
//            centroid[2] /= totalWeight;
//        }

        // Iterative minimization
        boolean converged = !moreThanTwoValues;

        double epsilon = 1e-3; // Convergence threshold
        int maxIterations = 250;
        double epsilon_squared = epsilon * epsilon;
        double[] distances = new double[totalSamples];
        double[] var = new double[length];

        int iter;
        for (iter = 0; iter < maxIterations && !converged; iter++) {
            double[] newCentroid = new double[length];
            double totalWeight = 0.0;

            boolean hasZeroDistance = false;
            for (int i = 0; i < totalSamples; i++) {
                getVarI(vars, i, var);
                distances[i] = distance(centroid, var);
                if(distances[i] == 0) {
                    hasZeroDistance = true;
                    break;
                }
            }
            if(hasZeroDistance) {
                for(int i = 0; i < length; i++) {
                    centroid[i] += centroid[i] == 0 ? 1e-8 : 0.00161803398 * centroid[i];
                }
            }
            // Adjust centroid based on weighted distances
            for (int i = 0; i < totalSamples; i++) {
                double distance = 0;
                if(hasZeroDistance) {
                    getVarI(vars, i, var);
                    distance = distance(centroid, var);
                }
                else {
                    distance = distances[i];
                }
                if (distance > 0) {
                    double weight = 1 / distance; // weight[i] / distance // Adjust weight inversely to distance
                    for(int k = 0; k < length; k++) {
                        newCentroid[k] += weight * vars[k][i];
                    }
                    totalWeight += weight;
                }
            }

            if(totalWeight > 0) {
                for(int k = 0; k < length; k++) {
                    newCentroid[k] /= totalWeight;
                }
            } else {
                for(int k = 0; k < length; k++) {
                    newCentroid[k] = centroid[k];
                }
            }

            // Check for convergence
            converged = distance_squared(newCentroid, centroid) < epsilon_squared;

            centroid = newCentroid;
        }

        double[] finalCentroid = finalizeResult(centroid);

        if(avgWithMean) {
            double finalA = (finalCentroid[0] + ASum * totalSamplesReciprocal) * 0.5;
            double finalB = (finalCentroid[1] + BSum * totalSamplesReciprocal) * 0.5;
            double finalC = (finalCentroid[2] + CSum * totalSamplesReciprocal) * 0.5;

            int[] result = getColorChannels(finalA, finalB, finalC);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
        else {
            int[] result = getColorChannels(finalCentroid[0], finalCentroid[1], finalCentroid[2]);
            return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
        }
    }

    private static double distance_squared(double[] point1, double[] point2) {
        double sum = 0;
        for(int i = 0; i < point1.length && i < point2.length; i++) {
            double diff = point1[i] - point2[i];
            sum += diff * diff;
        }
        return sum;
    }

    private static double distance(double[] point1, double[] point2) {
        double sum = 0;
        for(int i = 0; i < point1.length && i < point2.length; i++) {
            double diff = point1[i] - point2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

}
