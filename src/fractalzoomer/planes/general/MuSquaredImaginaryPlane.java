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

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuSquaredImaginaryPlane extends Plane {
    private Complex exponent;
    private MpfrBigNumComplex mpfrbnexponent;

    private DDComplex ddcexponent;

    public MuSquaredImaginaryPlane() {

        super();
        exponent = new Complex(0, 2);

        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {

            ddcexponent = new DDComplex(0, 2);

            if(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE || ThreadDraw.HIGH_PRECISION_CALCULATION) {

                if(LibMpfr.LOAD_ERROR == null) {
                    mpfrbnexponent = new MpfrBigNumComplex(0, 2);
                }
            }
        }

    }

    @Override
    public Complex transform(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.pow(exponent);

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.pow(mpfrbnexponent);

    }

    @Override
    public DDComplex transform(DDComplex pixel) {
        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.pow(ddcexponent);

    }
    
}
