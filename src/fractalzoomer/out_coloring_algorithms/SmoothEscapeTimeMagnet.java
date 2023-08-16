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

    public SmoothEscapeTimeMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[2]) {

            if(algorithm == 0) {
                return (Integer)object[0] + getEscSmoothing1(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared) + MAGNET_INCREMENT;
            }
            else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return (Integer)object[0] + 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;

                return (Integer)object[0] + getEscSmoothing2(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared) + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                return (Integer)object[0] + getConvSmoothing1(object, log_convergent_bailout);
            }
            else {
                return (Integer)object[0] + getConvSmoothing2(object, log_convergent_bailout);
            }
        }

    }

    public static double getConvSmoothing1(Object[] object, double log_convergent_bailout) {

        double temp = Math.log(((Complex)object[4]).distance_squared(1));
        return (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp);

    }

    public static double getConvSmoothing2(Object[] object, double log_convergent_bailout) {

        double temp4 = Math.log(((Double)object[3]));

        double power = temp4 / Math.log(((Complex)object[4]).distance_squared(1));

        return Math.log(log_convergent_bailout / temp4) / Math.log(power);

    }

    public static double getEscSmoothing1(Object[] object, double log_znnormsqr, double log_bailout_squared) {

        double temp = ((Complex)object[4]).norm_squared();
        if(temp == 0) {
            temp += 0.000000001;
        }
        temp = Math.log(temp);
        return (log_bailout_squared - temp) / (log_znnormsqr - temp);

    }

    public static double getEscSmoothing2(Object[] object, double log_znnormsqr, double log_bailout_squared) {

        double temp = ((Complex)object[4]).norm_squared();

        double p = log_znnormsqr / Math.log(temp);

        p = p <= 0 ? 1e-33 : p;
        log_znnormsqr = log_znnormsqr <= 0 ? 1e-33 : log_znnormsqr;

        double a = Math.log(log_znnormsqr / log_bailout_squared);
        return 1 - a / Math.log(p);

    }
}
