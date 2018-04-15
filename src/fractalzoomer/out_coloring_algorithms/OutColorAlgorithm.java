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

import fractalzoomer.utils.ColorAlgorithm;

public abstract class OutColorAlgorithm extends ColorAlgorithm {
    public abstract double getResult(Object[] object);
    
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
    public static double transformResultToHeight(double result, int max_iterations) {
        
        return Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS ? max_iterations : getResultWithoutIncrement(result);
        
    }
 
}
