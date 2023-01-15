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
public class SmoothEscapeTime extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected int algorithm;
    protected double log_power;
    protected boolean usePower;

    public SmoothEscapeTime(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;
        OutNotUsingIncrement = true;
        usePower = false;

    }

    public SmoothEscapeTime(double log_bailout_squared, int algorithm, double log_power) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;
        OutNotUsingIncrement = true;
        this.log_power = log_power;
        usePower = true;

    }

    @Override
    public double getResult(Object[] object) {
        
        if(algorithm == 0 && !usePower) {
            return (Integer)object[0] + getSmoothing1(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared);
        }
        else {
            //double temp2 = ((Complex)object[1]).norm_squared();
            //return (Integer)object[0] + 1 - Math.log(Math.log(temp2) / log_bailout_squared) / log_power;

            return (Integer)object[0] + getSmoothing2(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared, usePower, log_power);

        }

    }

    public static double getSmoothing1(Object[] object, double log_znnormsqr, double log_bailout_squared) {

        double temp = ((Complex)object[2]).norm_squared();

        if(temp == 0) {
            temp += 0.000000001;
        }
        temp = Math.log(temp);

        return (log_bailout_squared - temp) / (log_znnormsqr - temp);

    }

    public static double getSmoothing2(Object[] object, double log_znnormsqr, double log_bailout_squared, boolean usePower, double log_power) {

        double p;
        if(usePower) {
            p = log_power;
        }
        else {
            double temp = ((Complex)object[2]).norm_squared();
            p = log_znnormsqr / Math.log(temp);
            p = p <= 0 ? 1e-33 : p;
            p = Math.log(p);
        }


        log_znnormsqr = log_znnormsqr <= 0 ? 1e-33 : log_znnormsqr;

        double a = Math.log(log_znnormsqr / log_bailout_squared);
        return  1 - a / p;

    }
}
