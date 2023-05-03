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
public class SmoothEscapeTimeGridNova extends OutColorAlgorithm {

    private double log_convergent_bailout;
    protected double pi2;
    protected double log_bailout_squared;
    protected int algorithm;

    public SmoothEscapeTimeGridNova(double log_convergent_bailout, int algorithm) {

        super();
        this.log_convergent_bailout = log_convergent_bailout;
        pi2 = Math.PI * 2;
        log_bailout_squared = Math.log(16.0);
        this.algorithm = algorithm;
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double zabs = Math.abs(temp2 / log_bailout_squared - 1.0f);
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        double k = Math.pow(0.5, 0.5 - zabs);

        double grid_weight = 0.05;

        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);


        double temp3;
        if(algorithm == 0) {
            temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing1(object, log_convergent_bailout);
        }
        else {
            temp3 = (Integer)object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing2(object, log_convergent_bailout);
        }

        return grid ? temp3 : -(temp3 + INCREMENT);

    }
    
}
