package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

public class TwinLamps extends GenericStatistic {
    private double min;
    private double max;
    private double sum;

    private double sum2;
    private Complex point;
    private int twlFunction;

    private double log_bailout_squared;

    public TwinLamps(double statistic_intensity, int twlFunction, double[] twlPoint, double log_bailout_squared, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, useSmoothing, false, lastXItems);
        point = new Complex(twlPoint[0], twlPoint[1]);
        this.twlFunction = twlFunction;
        this.log_bailout_squared = log_bailout_squared;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems){
            return;
        }

        sample(z);

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        min = Double.MAX_VALUE;
        max = -1;
        samples = 0;
        sum = 0;
        sum2 = 0;
    }

    private void sample(Complex z) {
        double norm = z.plus(point).norm();

        double ln = Math.exp(-norm);

        if(ln < min) {
            min = ln;
        }
        if(ln > max) {
            max = ln;
        }

        sum2 = sum;
        sum = sum + ln;

        samples++;
    }

    @Override
    protected void addSample(StatisticSample sam, double[] data) {
        double norm = sam.z_val.plus(point).norm();

        double ln = Math.exp(-norm);

        if(ln < min) {
            min = ln;
        }
        if(ln > max) {
            max = ln;
        }

        data[0] += ln;
        data[1]++;
    }

    private void sampleLastX() {
        min = Double.MAX_VALUE;
        max = -1;

        samples = 0;

        double[][] res = calculateLastX();
        double[] data1 = res[0];
        double[] data2 = res[1];

        samples = (int)Math.min(data1[1], data2[1]);
        sum = data1[0];
        sum2 = data2[0];
    }

    @Override
    protected double getValue() {

        if(keepLastXItems) {
            sampleLastX();
        }

        if(samples < 1) {
            return 0;
        }

        if(twlFunction == 1) {
            sum = Math.sqrt(sum);
        }

        if(!useSmoothing) {
            return statistic_intensity * sum / (1 + max - min);
        }

        if(twlFunction == 1) {
            sum2 = Math.sqrt(sum2);
        }

        double smoothing;

        if(escaping_smoothing_algorithm == 0 && !usePower) {
            smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout_squared);
        }
        else {
            smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout_squared, log_power) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout_squared);
        }

        return method.interpolate(sum / (1 + max - min), sum2 / (1 + max - min), smoothing) * statistic_intensity;

    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }
}
