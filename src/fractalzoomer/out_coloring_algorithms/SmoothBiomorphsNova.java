/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

        super(0, 2.0, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if(algorithm == 0) {
            double temp2 = Math.log(((Complex)object[3]).distance_squared((Complex)object[4]));
            temp3 = (Integer)object[0] + (log_convergent_bailout - temp2) / (Math.log((Double)object[2]) - temp2);
        }
        else {
            double temp4 = Math.log(((Double)object[2]) + 1e-33);

            double power = temp4 / Math.log(((Complex)object[3]).distance_squared(((Complex)object[4])));
            
            power = power <= 0 ? 1e-33 : power;

            double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

            temp3 = (Integer)object[0] + f;
        }

        double temp4 = ((Complex)object[1]).getRe();
        double temp5 = ((Complex)object[1]).getIm();

        return temp4 > -bailout && temp4 < bailout || temp5 > -bailout && temp5 < bailout ? temp3 + MAGIC_OFFSET_NUMBER : -(temp3 + MAGIC_OFFSET_NUMBER + INCREMENT);

    }
}
