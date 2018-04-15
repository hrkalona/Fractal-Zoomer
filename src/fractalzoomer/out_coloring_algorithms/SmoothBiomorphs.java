/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
public class SmoothBiomorphs extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected double bailout;
    protected int algorithm;

    public SmoothBiomorphs(double log_bailout_squared, double bailout, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.bailout = bailout;
        this.algorithm = algorithm;
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0) {
            double temp = ((Complex)object[2]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();

            temp += 0.000000001;
            temp = Math.log(temp);

            double temp3 = (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp);

            double temp4 = ((Complex)object[1]).getRe();
            double temp5 = ((Complex)object[1]).getIm();

            return temp4 > -bailout && temp4 < bailout || temp5 > -bailout && temp5 < bailout ? temp3 : -(temp3 + INCREMENT);
        }
        else {
            double temp = ((Complex)object[2]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();

            temp2 = Math.log(temp2);
            double p = temp2 / Math.log(temp);
            
            p = p <= 0 ? 1e-33 : p;

            double a = Math.log(temp2 / log_bailout_squared);
            double f = a / Math.log(p);

            double temp3 = (Integer)object[0] + 1 - f;

            double temp4 = ((Complex)object[1]).getRe();
            double temp5 = ((Complex)object[1]).getIm();

            return temp4 > -bailout && temp4 < bailout || temp5 > -bailout && temp5 < bailout ? temp3 : -(temp3 + INCREMENT);
        }

    }
}
