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

/**
 *
 * @author hrkalona2
 */
public class CosArgDivideInverseNorm extends GenericStatistic {
    private double sum;
    private double sum2;
    private double StripeDensity;
    private double StripeDenominatorFactor;

    private double log_convergent_bailout;

    private double log_bailout_squared;
    private Complex root;
    
    public CosArgDivideInverseNorm(double statistic_intensity, double StripeDensity, double StripeDenominatorFactor, boolean useSmoothing, double log_convergent_bailout, double log_bailout_squared, int lastXItems) {
        super(statistic_intensity, useSmoothing, false, lastXItems);
        sum = 0;
        sum2 = 0;
        this.StripeDenominatorFactor = StripeDenominatorFactor;
        this.StripeDensity = StripeDensity;
        this.log_convergent_bailout = log_convergent_bailout;
        this.log_bailout_squared = log_bailout_squared;
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
    public double getValue() {

        if(keepLastXItems) {
            sampleLastX();
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
        }

        double smoothing;
        if(mode == NORMAL_ESCAPE) {
            if(escaping_smoothing_algorithm == 0 && !usePower) {
                smoothing = OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout_squared);
            }
            else {
                smoothing = usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout_squared, log_power) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout_squared);
            }
        }
        else if (mode == NORMAL_CONVERGE){
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartConverging1(z_val, zold_val, zold2_val, log_convergent_bailout);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartConverging2(z_val, zold_val, zold2_val, log_convergent_bailout);
            }
        }
        else {
            if(converging_smoothing_algorithm == 0) {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging1(z_val, zold_val, root, log_convergent_bailout);
            }
            else {
                smoothing = OutColorAlgorithm.fractionalPartMagnetConverging2(z_val, zold_val, root, log_convergent_bailout);
            }
        }

        return method.interpolate(sum, sum2, smoothing) * statistic_intensity;
        
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
