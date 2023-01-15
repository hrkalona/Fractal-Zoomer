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
public class SmoothEscapeTimeGridMagnet extends SmoothEscapeTimeGrid {

    private double log_convergent_bailout;
    private int algorithm2;

    public SmoothEscapeTimeGridMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double zabs = temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        double k = Math.pow(0.5, 0.5 - zabs);

        double grid_weight = 0.05;

        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);

        double temp3 = grid ? (Integer)object[0] : -((Integer)object[0] + INCREMENT);

        if((Boolean)object[2]) {
            if(algorithm == 0) {
                if(temp3 < 0) {
                    return temp3 - (SmoothEscapeTimeMagnet.getEscSmoothing1(object, temp2, log_bailout_squared) + MAGNET_INCREMENT);
                }
                else {
                    return temp3 + SmoothEscapeTimeMagnet.getEscSmoothing1(object, temp2, log_bailout_squared)+ MAGNET_INCREMENT;
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
