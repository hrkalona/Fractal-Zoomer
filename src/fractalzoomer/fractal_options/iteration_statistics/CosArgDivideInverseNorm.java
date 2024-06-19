
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class CosArgDivideInverseNorm extends GenericStatistic {
    private double sum;
    private double sum2;
    private double StripeDensity;
    private double StripeDenominatorFactor;
    private Complex root;
    
    public CosArgDivideInverseNorm(double statistic_intensity, double StripeDensity, double StripeDenominatorFactor, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, useSmoothing, false, lastXItems);
        sum = 0;
        sum2 = 0;
        this.StripeDenominatorFactor = StripeDenominatorFactor;
        this.StripeDensity = StripeDensity;
        mode = NORMAL_CONVERGE;
        root = new Complex(1, 0);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        Complex sub = z.sub(zold);
        sum2 = sum;
        sum += (0.5 * Math.cos(StripeDensity * (sub.arg() + Math.PI)) + 0.5) / (StripeDenominatorFactor + 1 / (sub.norm()));
        samples++;
    }

    @Override
    protected void addSample(StatisticSample sam, double[] data) {
        Complex sub = sam.z_val.sub(sam.zold_val);
        data[0] += (0.5 * Math.cos(StripeDensity * (sub.arg() + Math.PI)) + 0.5) / (StripeDenominatorFactor + 1 / (sub.norm()));
        data[1]++;
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
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        sum = 0;
        sum2 = 0;
    }

    @Override
    protected double getValue() {

        if(keepLastXItems) {
            sampleLastX();
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
        }

        double smoothing;
        if(mode == NORMAL_ESCAPE) {
            if(escaping_smoothing_algorithm == 0 && !usePower) {
                smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout, normSmoothingImpl);
            }
            else if(escaping_smoothing_algorithm == 2 && !usePower) {
                smoothing = OutColorAlgorithm.fractionalPartEscaping3(z_val, zold_val, bailout, normSmoothingImpl);
            }
            else {
                smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout, log_power, normSmoothingImpl) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout, normSmoothingImpl);
            }
        }
        else if (mode == NORMAL_CONVERGE) {
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartConverging1(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartConverging2(z_val, zold_val, zold2_val, log_convergent_bailout, cNormSmoothingImpl);
            }
        }
        else {
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging1(z_val, zold_val, root, log_convergent_bailout, cNormSmoothingImpl);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging2(z_val, zold_val, root, log_convergent_bailout, cNormSmoothingImpl);
            }
        }

        return method.interpolate(sum, sum2, smoothing) * statistic_intensity;
        
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
