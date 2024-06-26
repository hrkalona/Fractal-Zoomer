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
 * @author hrkalona
 */
public class SmoothEscapeTimeMagnet extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private int algorithm2;

    public SmoothEscapeTimeMagnet(double bailout, double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(bailout, log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        if((boolean)object[2]) {

            if(algorithm == 0) {
                return (int)object[0] + getSmoothing1((Complex)object[1], (Complex)object[3], log_bailout_squared) + MAGNET_INCREMENT;
            }
            else if(algorithm == 2) {
                return (int)object[0] + getSmoothing3((Complex)object[1], (Complex)object[3], bailout) + MAGNET_INCREMENT;
            }
            else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return (int)object[0] + 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;

                return (int)object[0] + getSmoothing2((Complex)object[1], (Complex)object[3], log_bailout_squared, usePower, log_power) + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                return (int)object[0] + getConvSmoothing1((Complex)object[1], (Complex)object[3], log_convergent_bailout);
            }
            else {
                return (int)object[0] + getConvSmoothing2((Complex)object[1], (Complex)object[3], log_convergent_bailout);
            }
        }

    }

    private static double getConvSmoothing1(Complex z, Complex zold, double log_convergent_bailout) {

        double temp = Math.log(zold.distance_squared(1));
        return (log_convergent_bailout - temp) / (Math.log(z.distance_squared(1)) - temp);

    }

    private static double getConvSmoothing2(Complex z, Complex zold, double log_convergent_bailout) {

        double temp4 = Math.log(z.distance_squared(1));

        double power = temp4 / Math.log(zold.distance_squared(1));

        return Math.log(log_convergent_bailout / temp4) / Math.log(power);

    }
}
