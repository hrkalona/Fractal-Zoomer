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
public class SmoothEscapeTimeGrid extends OutColorAlgorithm {

    protected double log_bailout_squared;
    protected double pi2;
    protected int algorithm;
    protected double log_power;
    protected boolean usePower;

    public SmoothEscapeTimeGrid(double log_bailout_squared, int algorithm) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;
        this.algorithm = algorithm;
        usePower = false;

        OutUsingIncrement = true;
    }

    public SmoothEscapeTimeGrid(double log_bailout_squared, int algorithm, double log_power) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;
        this.algorithm = algorithm;
        usePower = true;
        this.log_power = log_power;

        OutUsingIncrement = true;
    }

    @Override
    public double getResult(Object[] object) {

        if(algorithm == 0 && !usePower) {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());

            double zabs = temp2 / log_bailout_squared - 1.0f;
            double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

            double k = Math.pow(0.5, 0.5 - zabs);

            double grid_weight = 0.05;

            boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);


            double temp3 = (Integer)object[0] + SmoothEscapeTime.getSmoothing1(object, temp2, log_bailout_squared);

            return grid ? temp3 : -(temp3 + INCREMENT);
        }
        else {
            double temp2 = Math.log(((Complex)object[1]).norm_squared());
            
            double zabs = temp2 / log_bailout_squared - 1.0f;
            double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

            double k = Math.pow(0.5, 0.5 - zabs);

            double grid_weight = 0.05;

            boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);

            double temp3 = (Integer)object[0] + SmoothEscapeTime.getSmoothing2(object, temp2, log_bailout_squared, usePower, log_power);
            
            return grid ? temp3 : -(temp3 + INCREMENT);
        }

    }
}
