
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import fractalzoomer.utils.MathUtils;

/**
 *
 * @author hrkalona2
 */
public class Checkers extends GenericStatistic {
    private double sum;
    private double sum2;
    private double PatternScale;
    private Complex point05point05 = new Complex(0.5, 0.5);
    private Norm normImpl;

    public Checkers(double statistic_intensity, double PatternScale, int normType, double normValue, boolean useSmoothing, boolean useAverage, int lastXItems, double a, double b) {
        super(statistic_intensity, useSmoothing, useAverage, lastXItems);
        sum = 0;
        sum2 = 0;
        this.PatternScale = PatternScale;

        switch (normType) {
            case 0:
                normImpl = new Norm2();
                break;
            case 1: //Rhombus
                normImpl = new Norm1();
                break;
            case 2: //Square
                normImpl = new NormInfinity();
                break;
            case 3:
                normImpl = new NormN(normValue, a, b);
                break;
        }
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        double wx = MathUtils.fract(z.getRe()*PatternScale);
        double wy = MathUtils.fract(z.getIm()*PatternScale);
        double a = ((wx<0.5 && wy<0.5) || (wx>0.5 && wy>0.5)) ? 1.0 : 0.0;
        wx=MathUtils.fract(wx*2.0);
        wy=MathUtils.fract(wy*2.0);
        if (normImpl.computeWithRoot(new Complex(wx, wy).sub_mutable(point05point05))< 0.5) {
            a = (1.0 - a);
        }

        sum2 = sum;
        sum += a;
        samples++;
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

        double wx = MathUtils.fract(sam.z_val.getRe()*PatternScale);
        double wy = MathUtils.fract(sam.z_val.getIm()*PatternScale);
        double a = ((wx<0.5 && wy<0.5) || (wx>0.5 && wy>0.5)) ? 1.0 : 0.0;
        wx=MathUtils.fract(wx*2.0);
        wy=MathUtils.fract(wy*2.0);
        if (normImpl.computeWithRoot(new Complex(wx, wy).sub(point05point05)) < 0.5) {
            a = (1.0 - a);
        }

        data[0] += a;

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
            if(samples < 1) {
                return 0;
            }

            if(useAverage) {
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
