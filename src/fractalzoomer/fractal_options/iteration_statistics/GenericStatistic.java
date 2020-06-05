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

/**
 *
 * @author hrkalona2
 */
public abstract class GenericStatistic {
    protected int samples;
    protected double statistic_intensity;
    protected Complex z_val;
    protected Complex zold_val;
    protected Complex zold2_val;
    
    public GenericStatistic(double statistic_intensity) {
        this.statistic_intensity = statistic_intensity;
        z_val = new Complex();
        zold_val = new Complex();
        zold2_val = new Complex();
        samples = 0;
    }
    
    public abstract void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start);
    public abstract void initialize(Complex pixel);
    public abstract double getValue();
    public abstract int getType();
    
}