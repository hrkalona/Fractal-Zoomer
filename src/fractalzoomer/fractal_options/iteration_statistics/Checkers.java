/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
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
    private double log_bailout_squared;
    private Complex point05point05 = new Complex(0.5, 0.5);

    private int normType;
    private double normValue;
    private double normValueReciprocal;

    public Checkers(double statistic_intensity, double PatternScale, int normType, double normValue, double log_bailout_squared, boolean useSmoothing, boolean useAverage, int lastXItems) {
        super(statistic_intensity, useSmoothing, useAverage, lastXItems);
        sum = 0;
        sum2 = 0;
        this.PatternScale = PatternScale;
        this.log_bailout_squared = log_bailout_squared;
        this.normType = normType;
        this.normValue = normValue;
        normValueReciprocal = 1 / normValue;
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
        if (norm(new Complex(wx, wy).sub_mutable(point05point05))< 0.5) {
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

    private double norm(Complex z) {
        if(normType == 0) {
            return z.norm();
        }
        else if(normType == 2) {
            return Math.max(z.getAbsRe(), z.getAbsIm());
        }
        else if(normType == 1) {
            return z.getAbsRe() + z.getAbsIm();
        }
        else {
            return z.nnorm(normValue, normValueReciprocal);
        }
    }

    @Override
    protected void addSample(StatisticSample sam, double[] data) {

        double wx = MathUtils.fract(sam.z_val.getRe()*PatternScale);
        double wy = MathUtils.fract(sam.z_val.getIm()*PatternScale);
        double a = ((wx<0.5 && wy<0.5) || (wx>0.5 && wy>0.5)) ? 1.0 : 0.0;
        wx=MathUtils.fract(wx*2.0);
        wy=MathUtils.fract(wy*2.0);
        if (norm(new Complex(wx, wy).sub(point05point05)) < 0.5) {
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
            smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout_squared);
        }
        else {
            smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout_squared, log_power) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout_squared);
        }

        return method.interpolate(sum, sum2, smoothing) * statistic_intensity;


    }
    
    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }

}
