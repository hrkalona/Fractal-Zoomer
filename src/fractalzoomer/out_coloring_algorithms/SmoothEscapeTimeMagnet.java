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
 * @author hrkalona
 */
public class SmoothEscapeTimeMagnet extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private int algorithm2;

    public SmoothEscapeTimeMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutNotUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[2]) {

            if(algorithm == 0) {
                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();
                temp += 0.000000001;
                temp = Math.log(temp);
                return (Integer)object[0] + (log_bailout_squared - temp) / (Math.log(temp2) - temp) + MAGNET_INCREMENT;
            }
            else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return (Integer)object[0] + 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;

                double temp = ((Complex)object[4]).norm_squared();
                double temp2 = ((Complex)object[1]).norm_squared();

                temp2 = Math.log(temp2);
                double p = temp2 / Math.log(temp);
                
                p = p <= 0 ? 1e-33 : p;
                temp2 = temp2 <= 0 ? 1e-33 : temp2;

                double a = Math.log(temp2 / log_bailout_squared);
                double f = a / Math.log(p);

                return (Integer)object[0] + 1 - f + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                double temp = Math.log(((Complex)object[4]).distance_squared(1));
                return (Integer)object[0] + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp);
            }
            else {
                double temp4 = Math.log(((Double)object[3]));

                double power = temp4 / Math.log(((Complex)object[4]).distance_squared(1));

                double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

                return (Integer)object[0] + f;
            }
        }

    }
}
