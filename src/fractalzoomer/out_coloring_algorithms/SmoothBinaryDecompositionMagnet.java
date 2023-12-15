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
public class SmoothBinaryDecompositionMagnet extends SmoothBinaryDecomposition {

    protected double log_convergent_bailout;
    protected int algorithm2;

    public SmoothBinaryDecompositionMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super(log_bailout_squared, algorithm);
        this.log_convergent_bailout = log_convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp3;
        if((Boolean)object[2]) {
            if(algorithm == 0) {
                temp3 = (Integer)object[0] + SmoothEscapeTimeMagnet.getEscSmoothing1(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared) + MAGNET_INCREMENT;
            }
            else {
                temp3 = (Integer)object[0] + SmoothEscapeTimeMagnet.getEscSmoothing2(object, Math.log(((Complex)object[1]).norm_squared()), log_bailout_squared) + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                temp3 = (Integer)object[0] + SmoothEscapeTimeMagnet.getConvSmoothing1(object, log_convergent_bailout);
            }
            else {
                temp3 = (Integer)object[0] + SmoothEscapeTimeMagnet.getConvSmoothing2(object, log_convergent_bailout);
            }
        }
        
        return ((Complex)object[1]).getIm() < 0 ? -(temp3 + INCREMENT) : temp3;
    }
}
