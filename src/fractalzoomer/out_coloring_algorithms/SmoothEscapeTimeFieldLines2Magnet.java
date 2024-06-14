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
public class SmoothEscapeTimeFieldLines2Magnet extends SmoothEscapeTimeFieldLines2 {

    private double log_convergent_bailout;
    private int algorithm2;

    public SmoothEscapeTimeFieldLines2Magnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double lineWidth = 0.008;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = temp2 / log_bailout_squared;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) > lineWidth * fz;

        double temp3 = line ? (int)object[0] : -((int)object[0] + INCREMENT);

        if((boolean)object[2]) {
            if(algorithm == 0) {
                if(temp3 < 0) {
                    return temp3 -(SmoothEscapeTimeMagnet.getEscSmoothing1(object, temp2, log_bailout_squared) + MAGNET_INCREMENT);
                }
                else {
                    return temp3 + SmoothEscapeTimeMagnet.getEscSmoothing1(object, temp2, log_bailout_squared) + MAGNET_INCREMENT;
                }           
            }
            else {
                if(temp3 < 0) {
                    return temp3 - (SmoothEscapeTimeMagnet.getEscSmoothing2(object, temp2, log_bailout_squared) + MAGNET_INCREMENT);
                }
                else {
                    return temp3 + SmoothEscapeTimeMagnet.getEscSmoothing2(object, temp2, log_bailout_squared) + MAGNET_INCREMENT;
                }               
            }
        }
        else {
            if(algorithm2 == 0) {
                if(temp3 < 0) {
                    return temp3 - SmoothEscapeTimeMagnet.getConvSmoothing1(object, log_convergent_bailout);
                }
                else {
                    return temp3 + SmoothEscapeTimeMagnet.getConvSmoothing1(object, log_convergent_bailout);
                }
            }
            else {
                double f = SmoothEscapeTimeMagnet.getConvSmoothing2(object, log_convergent_bailout);

                if(temp3 < 0) {
                    return temp3 - f;
                }
                else {
                    return temp3 + f;
                }              
            }
        }
    }
}
