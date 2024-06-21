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
public class EscapeTimeGrid extends OutColorAlgorithm {
  protected double log_bailout_squared;
  protected double pi2;
  protected OutColorAlgorithm EscapeTimeAlg;
  boolean abs;
    
    public EscapeTimeGrid(double log_bailout_squared, OutColorAlgorithm EscapeTimeAlg, boolean abs) {

        super();
        this.log_bailout_squared = log_bailout_squared;
        pi2 = Math.PI * 2;
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        this.abs = abs;

    }

    @Override
    public double getResult(Object[] object) {

        double zabs = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared - 1.0f;

        if(abs) {
            zabs = Math.abs(zabs);
        }

        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        boolean grid;
        if(EscapeTimeAlg.smooth) {
            double k = Math.pow(0.5, 0.5 - zabs);
            double grid_weight = 0.05;
            grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);
        }
        else {
            grid = 0.05 < zabs && zabs < 0.95 && 0.05 < zarg && zarg < 0.95;
        }

        double result = EscapeTimeAlg.getResult(object);
        return grid ? result : -(result + INCREMENT);

    }
    
}
