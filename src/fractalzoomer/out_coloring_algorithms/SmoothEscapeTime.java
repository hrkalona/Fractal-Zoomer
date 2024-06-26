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
    protected double bailout;

    public SmoothEscapeTime(double bailout, double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;
        OutUsingIncrement = false;
        usePower = false;
        this.bailout = bailout;
        smooth = true;

    }

    public SmoothEscapeTime(double bailout, double log_bailout_squared, int algorithm, double log_power) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        this.algorithm = algorithm;
        OutUsingIncrement = false;
        this.log_power = log_power;
        usePower = true;
        this.bailout = bailout;

    }

    @Override
    public double getResult(Object[] object) {
        
        if(algorithm == 0 && !usePower) {
            return (int)object[0] + getSmoothing1((Complex)object[1], (Complex)object[2], log_bailout_squared);
        }
        else if(algorithm == 2 && !usePower) {
            return (int)object[0] + getSmoothing3((Complex)object[1], (Complex)object[2], bailout);
        }
        else {
            //double temp2 = ((Complex)object[1]).norm_squared();
            //return (int)object[0] + 1 - Math.log(Math.log(temp2) / log_bailout_squared) / log_power;

            return (int)object[0] + getSmoothing2((Complex)object[1], (Complex)object[2], log_bailout_squared, usePower, log_power);

        }

    }

    protected static double getSmoothing1(Complex z, Complex zold, double log_bailout_squared) {

        return 1 - fractionalPartEscaping1(z, zold, log_bailout_squared);

    }

    protected static double getSmoothing2(Complex z, Complex zold, double log_bailout_squared, boolean usePower, double log_power) {

        if(usePower) {
            return 1 - fractionalPartEscapingWithPower(z, log_bailout_squared, log_power);
        }

        return 1 - fractionalPartEscaping2(z, zold, log_bailout_squared);

    }

    protected static double getSmoothing3(Complex z, Complex zold, double bailout) {
        return 1 - fractionalPartEscaping3(z, zold, bailout);
    }
}
