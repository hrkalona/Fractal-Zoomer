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
public class SmoothEscapeTimeEscapeRadiusMagnet extends OutColorAlgorithm {

    private double log_convergent_bailout;
    private double log_bailout_squared;
    protected double pi2;
    protected int algorithm;
    protected int algorithm2;

    public SmoothEscapeTimeEscapeRadiusMagnet(double log_bailout_squared, double log_convergent_bailout, int algorithm, int algorithm2) {

        super();

        this.log_bailout_squared = log_bailout_squared;
        this.log_convergent_bailout = log_convergent_bailout;
        pi2 = Math.PI * 2;
        this.algorithm = algorithm;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());
        double zabs = temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        double temp3 = (Integer)object[0] + zabs + zarg;

        if((Boolean)object[2]) {
            if(algorithm == 0) {
                return temp3 + SmoothEscapeTimeMagnet.getEscSmoothing1(object, temp2, log_bailout_squared) + MAGNET_INCREMENT;
            }
            else {
                return temp3 + SmoothEscapeTimeMagnet.getEscSmoothing2(object, temp2, log_bailout_squared) + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                return temp3 + SmoothEscapeTimeMagnet.getConvSmoothing1(object, log_convergent_bailout);
            }
            else {
                return temp3 + SmoothEscapeTimeMagnet.getConvSmoothing2(object, log_convergent_bailout);
            }
        }

    }
}
