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
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class SmoothEscapeTimeFieldLines2 extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected int algorithm;
    protected double log_power;
    protected boolean usePower;

    public SmoothEscapeTimeFieldLines2(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;

        OutNotUsingIncrement = false;
        usePower = false;
    }

    public SmoothEscapeTimeFieldLines2(double log_bailout_squared, int algorithm, double log_power) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;

        OutNotUsingIncrement = false;
        usePower = true;
        this.log_power = log_power;
    }
    
    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0 && !usePower) {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());

            double temp3 = (Integer)object[0] + SmoothEscapeTime.getSmoothing1(object, temp2, log_bailout_squared);
            
            double lineWidth = 0.07;  // freely adjustable
            double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
            double fy = temp2 / log_bailout_squared;  // radius within cell
            double fz = Math.pow(0.5, -fy);  // make wider on the outside

            boolean line = Math.abs(fx) < (1.0 - lineWidth)*fz && lineWidth * fz < Math.abs(fx);

            return line ? temp3 : -(temp3 + INCREMENT);
        }
        else {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());

            double temp3 = (Integer)object[0] + SmoothEscapeTime.getSmoothing2(object, temp2, log_bailout_squared, usePower, log_power);
            
            double lineWidth = 0.07;  // freely adjustable
            double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
            double fy = temp2 / log_bailout_squared;  // radius within cell
            double fz = Math.pow(0.5, -fy);  // make wider on the outside

            boolean line = Math.abs(fx) < (1.0 - lineWidth)*fz && lineWidth * fz < Math.abs(fx);
            
            return line ? temp3 : -(temp3 + INCREMENT);
        }
    }
    
}
