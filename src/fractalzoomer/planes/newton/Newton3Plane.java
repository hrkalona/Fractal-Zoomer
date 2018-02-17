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

package fractalzoomer.planes.newton;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class Newton3Plane extends Plane {
    
    public Newton3Plane() {
        
        super();
        
    }

    @Override
    public Complex transform(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub(1);
            Complex dfz = temp.square().times(3);
            
            temp = temp.sub(fz.divide(dfz));
        }

        return temp;
 
    }
}
/*//Complex fz = pixel.cube().sub(1);
        //Complex dfz = pixel.square().times(3);
        
        //Complex fz = pixel.fourth().sub(1);
        //Complex dfz = pixel.cube().times(4);
        
        //Complex[] temp = pixel.der01_cos();
        
        //Complex fz = temp[0];
        //Complex dfz = temp[1];
        
        Complex fz = pixel.cube().sub_mutable(pixel.times(2)).plus_mutable(2);
        Complex dfz = pixel.square().times_mutable(3).sub_mutable(2);*/
        
        