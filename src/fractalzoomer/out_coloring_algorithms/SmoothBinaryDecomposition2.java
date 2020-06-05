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
public class SmoothBinaryDecomposition2 extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected int algorithm;

    public SmoothBinaryDecomposition2(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
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

            return ((Complex)object[1]).getRe() < 0 ? -(temp3 + INCREMENT) : temp3;
        }
        else {
            double temp = ((Complex)object[2]).norm_squared();
            double temp2 = ((Complex)object[1]).norm_squared();

            temp2 = Math.log(temp2);
            double p = temp2 / Math.log(temp);
            
            p = p <= 0 ? 1e-33 : p;
            temp2 = temp2 <= 0 ? 1e-33 : temp2;

            double a = Math.log(temp2 / log_bailout_squared);
            double f = a / Math.log(p);

            double temp3 = (Integer)object[0] + 1 - f;
            
            return ((Complex)object[1]).getRe() < 0 ? -(temp3 + INCREMENT) : temp3;
        }

    }
}
