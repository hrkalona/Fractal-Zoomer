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
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class SmoothEscapeTimeFieldLines extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected double pi2;
    protected int algorithm;

    public SmoothEscapeTimeFieldLines(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;
        this.algorithm = algorithm;

        OutNotUsingIncrement = false;
    }
    
    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0) {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());
            double temp = ((Complex)object[2]).norm_squared();

            temp += 0.000000001;
            temp = Math.log(temp);

            double temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (temp2 - temp);
            
            double lineWidth = 0.008;  // freely adjustable
            double fx = ((Complex)object[1]).arg() / (pi2);  // angle within cell
            double fy = temp2 / log_bailout_squared;  // radius within cell
            double fz = Math.pow(0.5, -fy);  // make wider on the outside

            boolean line = Math.abs(fx) > lineWidth * fz;

            return line ? temp3 : -(temp3 + INCREMENT);
        }
        else {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());

            double temp = ((Complex)object[2]).norm_squared();

            double p = temp2 / Math.log(temp);
            
            p = p <= 0 ? 1e-33 : p;
            temp2 = temp2 <= 0 ? 1e-33 : temp2;

            double a = Math.log(temp2 / log_bailout_squared);
            double f = a / Math.log(p);

            double temp3 = (Integer)object[0] + 1 - f;
            
            double lineWidth = 0.008;  // freely adjustable
            double fx = ((Complex)object[1]).arg() / (pi2);  // angle within cell
            double fy = temp2 / log_bailout_squared;  // radius within cell
            double fz = Math.pow(0.5, -fy);  // make wider on the outside

            boolean line = Math.abs(fx) > lineWidth * fz;
            
            return line ? temp3 : -(temp3 + INCREMENT);
        }
    }
    
}
