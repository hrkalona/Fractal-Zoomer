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
public class SmoothBinaryDecomposition2RootFindingMethod extends OutColorAlgorithm {

    protected double log_convergent_bailout;
    protected int algorithm;

    public SmoothBinaryDecomposition2RootFindingMethod(double log_convergent_bailout, int algorithm) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm = algorithm;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0) {
            double temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing1(object, log_convergent_bailout);

            return ((Complex)object[1]).getRe() < 0 ? -(temp3 + INCREMENT) : temp3;
        }
        else {
            double temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing2(object, log_convergent_bailout);
            
            return ((Complex)object[1]).getRe() < 0 ? -(temp3 + INCREMENT) : temp3;
        }

    }
}
