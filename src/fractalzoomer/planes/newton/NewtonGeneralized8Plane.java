/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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

package fractalzoomer.planes.newton;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class NewtonGeneralized8Plane extends Plane {
    
    public NewtonGeneralized8Plane() {
        
        super();
        
    }

    @Override
    public Complex transform(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.eighth().plus_mutable(temp.fourth().times_mutable(15)).sub_mutable(16);
            Complex dfz = temp.seventh().times_mutable(8).plus_mutable(temp.cube().times_mutable(60));
            
            temp.sub_mutable(fz.divide_mutable(dfz));
        }

        return temp;
 
    }
}
