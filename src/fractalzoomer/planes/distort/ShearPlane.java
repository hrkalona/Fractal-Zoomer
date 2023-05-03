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

package fractalzoomer.planes.distort;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ShearPlane extends Plane {

    private Complex scales;
    private BigComplex ddscales;
    private BigNumComplex bnscales;
    private MpfrBigNumComplex mpfrbnscales;

    private MpirBigNumComplex mpirbnscales;

    private DDComplex ddcscales;

    public ShearPlane(double[] plane_transform_scales) {

        super();
        scales = new Complex(plane_transform_scales[0], plane_transform_scales[1]);

        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            ddscales = new BigComplex(scales);
            ddcscales = new DDComplex(scales);
            if (ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE || ThreadDraw.HIGH_PRECISION_CALCULATION) {
                bnscales = new BigNumComplex(scales);

                if (ThreadDraw.allocateMPFR()) {
                    mpfrbnscales = new MpfrBigNumComplex(scales);
                } else if (ThreadDraw.allocateMPIR()) {
                    mpirbnscales = new MpirBigNumComplex(scales);
                }
            }
        }

    }

    @Override
    public Complex transform(Complex pixel) {

        return pixel.shear(scales);

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        return pixel.shear(ddscales);

    }

    @Override
    public BigNumComplex transform(BigNumComplex pixel) {

        return pixel.shear(bnscales);

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        return pixel.shear(mpfrbnscales);

    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        return pixel.shear(mpirbnscales);

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        return pixel.shear(ddcscales);

    }
}
