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

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class ReDivideIm extends InColorAlgorithm {
    private int max_iterations;

    public ReDivideIm(int max_iterations) { 
        
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;        
    
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return max_iterations + Math.abs(((Complex)object[0]).getRe() / ((Complex)object[0]).getIm()) * 8;

    }
    
}
