/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
public class CosArgDivideNormAverage extends GenericStatistic {
    private double sum;
    private double sum2;
    private double StripeDensity;
    private double log_bailout_squared;

    public CosArgDivideNormAverage(double statistic_intensity, double StripeDensity, double log_bailout_squared) {
        super(statistic_intensity);
        sum = 0;
        sum2 = 0;
        this.StripeDensity = StripeDensity;
        this.log_bailout_squared = log_bailout_squared;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start) {       
        z_val.assign(z);
        zold_val.assign(zold);
        
        samples++;
        sum2 = sum;
        sum += (0.5 * Math.cos(StripeDensity * (z.arg())) + 0.5) / z.norm();
    }

    @Override
    public void initialize(Complex pixel) {
        sum = 0;
        sum2 = 0;
        samples = 0;
    }

    @Override
    public double getValue() {
        if(samples < 1) {
            return 0;
        }
        
        double smoothing = OutColorAlgorithm.fractionalPartEscaping(z_val, zold_val, log_bailout_squared);
        sum = sum / samples;
        sum2 = samples < 2 ? 0 : sum2 / (samples - 1);
        return (sum + (sum2 - sum) * smoothing) * statistic_intensity;
    }
    
    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }
    
}
