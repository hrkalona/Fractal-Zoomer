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
public class SmoothBiomorphsNova extends SmoothBiomorphs {

    protected double log_convergent_bailout;

    public SmoothBiomorphsNova(double log_convergent_bailout, int algorithm) {

        super(0, 4.0, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if(algorithm == 0) {
            temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing1(object, log_convergent_bailout);
        }
        else {
            temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing2(object, log_convergent_bailout);
        }

        double temp4 = ((Complex)object[1]).getRe();
        double temp5 = ((Complex)object[1]).getIm();

        return temp4 > -bailout && temp4 < bailout || temp5 > -bailout && temp5 < bailout ? temp3 : -(temp3 + INCREMENT);

    }
}
