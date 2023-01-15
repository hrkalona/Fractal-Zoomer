package fractalzoomer.core.antialiasing;

public class GaussianAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double redSum;
    private double greenSum;
    private double blueSum;

    private double redSum2;
    private double greenSum2;
    private double blueSum2;

    private int sample;

    private boolean avgWithMean;

    private double[] coefficients;

    public GaussianAntialiasingAlgorithm(int totalSamples, double[] coefficients, boolean avgWithMean,  int colorSpace) {
        super(totalSamples, colorSpace);
        redSum = 0;
        greenSum = 0;
        blueSum = 0;
        redSum2 = 0;
        greenSum2 = 0;
        blueSum2 = 0;
        sample = 0;
        this.coefficients = coefficients;
        this.avgWithMean = avgWithMean;
    }

    @Override
    public void initialize(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            redSum2 = result[0];
            greenSum2 = result[1];
            blueSum2 = result[2];
        }

        sample = 0;
        double coefficient = coefficients[sample];
        redSum = coefficient * result[0];
        greenSum = coefficient * result[1];
        blueSum = coefficient * result[2];
    }

    @Override
    public boolean addSample(int color) {

        double[] result = getColorChannels(color);

        if(avgWithMean) {
            redSum2 += result[0];
            greenSum2 += result[1];
            blueSum2 += result[2];
        }

        sample++;
        double coefficient = coefficients[sample];
        redSum += coefficient * result[0];
        greenSum += coefficient * result[1];
        blueSum += coefficient * result[2];
        return true;
    }

    @Override
    public int getColor() {
        int[] result = getAveragedColorChannels(redSum, greenSum, blueSum, redSum2, greenSum2, blueSum2);
        return  0xff000000 | (result[0] << 16) | (result[1] << 8) | result[2];
    }

    @Override
    protected double function(double value, double value2) {
        if(avgWithMean) {
            return (value + value2 * totalSamplesReciprocal) * 0.5;
        }
        else {
            return value;
        }
    }

    public static double[] createGaussianKernel(int length, int[] X, int[] Y) {
        double[] gaussian_kernel = new double[X.length];
        double sumTotal = 0;

        //OpenCv
        double sigma=0.3*((length-1)*0.5 - 1) + 0.8;

        double distance = 0;
        double sigmaSqr2 = 2.0 * sigma * sigma;

        double calculatedEuler = 1.0 / (sigmaSqr2 * Math.PI);

        double temp;

        for(int i = 0; i < X.length && i < Y.length; i++) {

            int filterX = X[i];
            int filterY = Y[i];

            distance = ((filterX * filterX) + (filterY * filterY)) / (sigmaSqr2);
            temp = gaussian_kernel[i] = calculatedEuler * Math.exp(-distance);
            sumTotal += temp;
        }

        for(int i = 0; i < X.length && i < Y.length; i++) {
            gaussian_kernel[i] = gaussian_kernel[i] / sumTotal;
        }

        return gaussian_kernel;

    }
}
