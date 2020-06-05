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
        OutNotUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());
        double zabs = temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        double temp3 = (Integer)object[0] + zabs + zarg;

        if((Boolean)object[2]) {
            if(algorithm == 0) {
                double temp = ((Complex)object[4]).norm_squared();
                temp += 0.000000001;
                temp = Math.log(temp);
                return temp3 + (log_bailout_squared - temp) / (temp2 - temp) + MAGNET_INCREMENT;
            }
            else {
                double temp = ((Complex)object[4]).norm_squared();

                double p = temp2 / Math.log(temp);
                
                p = p <= 0 ? 1e-33 : p;
                temp2 = temp2 <= 0 ? 1e-33 : temp2;

                double a = Math.log(temp2 / log_bailout_squared);
                double f = a / Math.log(p);

                return temp3 + 1 - f + MAGNET_INCREMENT;
            }
        }
        else {
            if(algorithm2 == 0) {
                double temp = Math.log(((Complex)object[4]).distance_squared(1));
                return temp3 + (log_convergent_bailout - temp) / (Math.log((Double)object[3]) - temp);
            }
            else {
                double temp4 = Math.log(((Double)object[3]));

                double power = temp4 / Math.log(((Complex)object[4]).distance_squared(1));

                double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

                return temp3 + f;
            }
        }

    }
}
