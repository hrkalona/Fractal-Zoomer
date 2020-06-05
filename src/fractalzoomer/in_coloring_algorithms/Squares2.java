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
public class Squares2 extends InColorAlgorithm {
    private int max_iterations;
    
    public Squares2(int max_iterations) { 
        super();
        InNotUsingIncrement = false;
        this.max_iterations = max_iterations;
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double x = ((Complex)object[0]).getRe() * 16;
        double y = ((Complex)object[0]).getIm() * 16;
        
        double dx = Math.abs(x - Math.floor(x));
        double dy = Math.abs(y - Math.floor(y));
        
        return (dx < 0.5 && dy < 0.5) || (dx > 0.5 && dy > 0.5) ? -(INCREMENT + max_iterations) : max_iterations;
        
    }
    
}
