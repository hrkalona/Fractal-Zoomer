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

package fractalzoomer.planes.general;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuVariationPlane extends Plane {
    
    public MuVariationPlane() {

        super();

    }

    @Override
    public Complex transform(Complex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(0.25));

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(new MyApfloat(0.25)));

    }
}
