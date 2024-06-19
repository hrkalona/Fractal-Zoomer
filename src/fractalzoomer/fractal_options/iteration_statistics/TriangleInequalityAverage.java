
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class TriangleInequalityAverage extends GenericStatistic {
    private double sum;
    private double sum2;
    private double mc;

    public TriangleInequalityAverage(double statistic_intensity, boolean useSmoothing, boolean useAverage, int lastXItems) {
        super(statistic_intensity, useSmoothing, useAverage, lastXItems);
        sum = 0;
        sum2 = 0;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        Complex temp = z.sub(c);

        if (iterations == 0) {
            mc = c.norm();
        }

        double mp = temp.norm();
        double m = Math.abs(mp - mc);
        double M = mp + mc;

        double denom = (M - m);

        if (denom != 0) {
            samples++;
            sum2 = sum;
            sum += (z.norm() - m) / denom;
        }

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        sum = 0;
        sum2 = 0;
        samples = 0;
    }

    @Override
    protected void addSample(StatisticSample sam, double[] data) {

        if(samples == 0) {
            mc = c_val.norm();
        }

        Complex temp = sam.z_val.sub(c_val);

        double mp = temp.norm();
        double m = Math.abs(mp - mc);
        double M = mp + mc;

        double denom = (M - m);

        if(denom != 0) {
            data[0] += (sam.z_val.norm() - m) / denom;
            data[1]++;
        }
    }

    private int[] sampleLastX() {

        samples = 0;

        double[][] res = calculateLastX();
        double[] data1 = res[0];
        double[] data2 = res[1];

        samples = (int)Math.min(data1[1], data2[1]);
        sum = data1[0];
        sum2 = data2[0];

        return new int [] {(int)data1[1], (int)data2[1]};

    }

    @Override
    protected double getValue() {

        if(keepLastXItems) {
            int[] sample_vals = sampleLastX();

            if(samples < 1) {
                return 0;
            }

            if(useAverage) {
                sum = sum / sample_vals[0];
                sum2 = sum2 / sample_vals[1];
            }
        }
        else {
            if (samples < 1) {
                return 0;
            }

            if (useAverage) {
                sum = sum / samples;
                sum2 = samples < 2 ? 0 : sum2 / (samples - 1);
            }
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
        }

        double smoothing;
        if(escaping_smoothing_algorithm == 0 && !usePower) {
            smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout, normSmoothingImpl);
        }
        else if(escaping_smoothing_algorithm == 2 && !usePower) {
            smoothing = OutColorAlgorithm.fractionalPartEscaping3(z_val, zold_val, bailout, normSmoothingImpl);
        }
        else {
            smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout, log_power, normSmoothingImpl) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout, normSmoothingImpl);
        }

        return method.interpolate(sum, sum2, smoothing) * statistic_intensity;
    }
    
    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }
    
}
