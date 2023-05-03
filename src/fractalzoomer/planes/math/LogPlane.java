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

package fractalzoomer.planes.math;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class LogPlane extends Plane {
    
    public LogPlane() {
        
        super();
        
    }

    @Override
    public Complex transform(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return new BigComplex(transform(pixel.toComplex()));

    }
    
}
