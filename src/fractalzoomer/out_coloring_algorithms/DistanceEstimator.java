/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
public class DistanceEstimator  extends OutColorAlgorithm {
    
    public DistanceEstimator() {
        super();
        OutNotUsingIncrement = true;
    }

    @Override
    public double getResult(Object[] object) {

         double temp2 = (((Complex)object[1]).norm_squared());
         double temp3 = Math.log(temp2);      
         double temp = -2.0 * Math.log(temp3 * temp3 * temp2 / ((Complex)object[2]).norm_squared());
         
         return temp < 0 ? 0 : temp;

    }
    
}
