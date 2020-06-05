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

/**
 *
 * @author hrkalona2
 */
public class CosArgDivideInverseNorm extends GenericStatistic {
    private double sum;
    private double StripeDensity;
    private double StripeDenominatorFactor;
    
    public CosArgDivideInverseNorm(double statistic_intensity, double StripeDensity, double StripeDenominatorFactor) {
        super(statistic_intensity);
        sum = 0;
        this.StripeDenominatorFactor = StripeDenominatorFactor;
        this.StripeDensity = StripeDensity;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start) {
        
        Complex sub = z.sub(zold);
        sum += (0.5 * Math.cos(StripeDensity * (sub.arg() + Math.PI)) + 0.5) / (StripeDenominatorFactor + 1 / (sub.norm()));
              
    }

    @Override
    public void initialize(Complex pixel) {
        sum = 0;
    }

    @Override
    public double getValue() {
        
        return sum * statistic_intensity;
        
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
