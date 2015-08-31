/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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
public class Squares extends InColorAlgorithm {
  private double pi2;
  private double pi59;
    

    public Squares() { 
       
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[0]).getRe();
        double im = ((Complex)object[0]).getIm();
        
        return ((Math.abs((int)(re * 40)) % 2) ^ (Math.abs((int)(im * 40)) % 2)) == 1 ? Math.abs((Math.atan2(im, re) / (pi2)  + 0.75) * pi59) + 100820 :  Math.abs((Math.atan2(re, im) / (pi2)  + 0.75) * pi59) + 100820;

    }
    
}
