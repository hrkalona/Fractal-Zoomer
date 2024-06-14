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
public class Squares3 extends InColorAlgorithm {
    private int max_iterations;
    public Squares3(int max_iterations) {
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double x = ((Complex)object[0]).getRe();
        double y = ((Complex)object[0]).getIm();
        return max_iterations + Math.abs(Math.sin(x * 50)) * Math.abs(Math.sin(y * 50)) * 100;
        
    }
    
}
