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
public class CurvatureAverage extends GenericStatistic {
    private double sum;
    private double sum2;
    private double log_bailout_squared;
    
    public CurvatureAverage(double statistic_intensity, double log_bailout_squared, boolean useSmoothing, boolean useAverage) {
        super(statistic_intensity, useSmoothing, useAverage);
        sum = 0;
        sum2 = 0;
        this.log_bailout_squared = log_bailout_squared;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start) {
        super.insert(z, zold, zold2, iterations, c, start);
        
        Complex a = z.sub(zold);
        Complex b = zold.sub(zold2);
        Complex d = a.divide_mutable(b);
        double temp = Math.atan(d.getIm() / d.getRe());
        
        if(!Double.isNaN(temp) && !Double.isInfinite(temp)) {
            samples++;
            sum2 = sum;
            sum += Math.abs(temp);        
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
    public double getValue() {
        if(samples < 1) {
            return 0;
        }

        if(useAverage) {
            sum = sum / samples;
            sum2 = samples < 2 ? 0 : sum2 / (samples - 1);
        }

        if(!useSmoothing) {
            return sum * statistic_intensity;
        }
        
        double smoothing = OutColorAlgorithm.fractionalPartEscaping(z_val, zold_val, log_bailout_squared);
        return (sum + (sum2 - sum) * smoothing) * statistic_intensity;
    }
    
    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }
    
}
