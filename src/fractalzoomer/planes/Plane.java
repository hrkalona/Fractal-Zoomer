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

import fractalzoomer.core.*;

/**
 *
 * @author hrkalona
 */
public abstract class Plane {

    protected Plane() {

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

    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        return new BigIntNumComplex(transform(pixel.toComplex()));

    }

    //Todo fix, erf, gamma, factorial, rzeta, distort
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        return new MpfrBigNumComplex(transform(pixel.toComplex()));

    }

    //Todo fix the low precision ones
    public DDComplex transform(DDComplex pixel) {

        return new DDComplex(transform(pixel.toComplex()));

    }

    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        return new MpirBigNumComplex(transform(pixel.toComplex()));

    }

    public GenericComplex Transform(GenericComplex pixel) {

        if(pixel instanceof MpfrBigNumComplex) {
            return transform((MpfrBigNumComplex) pixel);
        }
        else if(pixel instanceof MpirBigNumComplex) {
            return transform((MpirBigNumComplex) pixel);
        }
        else if(pixel instanceof BigNumComplex) {
            return transform((BigNumComplex) pixel);
        }
        else if(pixel instanceof BigIntNumComplex) {
            return transform((BigIntNumComplex) pixel);
        }
        else if(pixel instanceof BigComplex) {
            return transform((BigComplex) pixel);
        }
        else if(pixel instanceof DDComplex) {
            return transform((DDComplex) pixel);
        }
        else {
            return transform((Complex) pixel);
        }

    }

}
