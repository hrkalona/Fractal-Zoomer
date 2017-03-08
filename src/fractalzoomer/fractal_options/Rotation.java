/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class Rotation {
  private Complex rotation;
  private Complex inv_rotation;
  private Complex center;
  
    public Rotation(double cos_theta, double sin_theta, double x, double y) {
        
        rotation = new Complex(cos_theta, sin_theta);
        inv_rotation = rotation.conjugate();
        center = new Complex(x, y);
        
    }
    
    public Complex getPixel(Complex pixel, boolean inv) {
        
         Complex temp = pixel.sub(center);
         return inv == true ? temp.times(inv_rotation).plus_mutable(center) : temp.times(rotation).plus_mutable(center);
         
    }
    
}
