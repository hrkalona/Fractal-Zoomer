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

package fractalzoomer.planes;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.BigNumComplex;
import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona
 */
public abstract class Plane {

    public Plane() {

    }

    public abstract Complex transform(Complex pixel);

    //Todo fix the low precision ones
    public BigComplex transform(BigComplex pixel) {

        return new BigComplex(transform(pixel.toComplex()));

    }

    //Todo fix the low precision ones
    public BigNumComplex transform(BigNumComplex pixel) {

        return new BigNumComplex(transform(pixel.toComplex()));

    }

}
