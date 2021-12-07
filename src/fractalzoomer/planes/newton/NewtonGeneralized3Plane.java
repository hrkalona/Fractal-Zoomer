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

package fractalzoomer.planes.newton;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class NewtonGeneralized3Plane extends Plane {
    
    public NewtonGeneralized3Plane() {
        
        super();
        
    }

    @Override
    public Complex transform(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub_mutable(temp.times(2)).plus_mutable(2);
            Complex dfz = temp.square().times_mutable(3).sub_mutable(2);
            
            temp.sub_mutable(fz.divide_mutable(dfz));
        }

        return temp;
 
    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        BigComplex temp = pixel;
        Apfloat three = new MyApfloat(3.0);

        for(int iterations = 0; iterations < 5; iterations++) {
            BigComplex fz = temp.cube().sub(temp.times(MyApfloat.TWO)).plus(MyApfloat.TWO);
            BigComplex dfz = temp.square().times(three).sub(MyApfloat.TWO);

            temp.sub(fz.divide(dfz));
        }

        return temp;

    }
}
