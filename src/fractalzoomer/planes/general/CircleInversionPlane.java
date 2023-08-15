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

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class CircleInversionPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;
    private double plane_transform_radius;
    private Apfloat ddplane_transform_radius;

    private MpfrBigNumComplex mpfrbncenter;
    private MpfrBigNum mpfrbnplane_transform_radius;

    private DDComplex ddccenter;
    private DoubleDouble ddcplane_transform_radius;

    private BigIntNumComplex bnicenter;
    private BigIntNum bniplane_transform_radius;

    private MpirBigNumComplex mpirbncenter;
    private MpirBigNum mpirbnplane_transform_radius;

    public CircleInversionPlane(double[] plane_transform_center, double plane_transform_radius) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_radius = plane_transform_radius * plane_transform_radius;

        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            ddplane_transform_radius = new MyApfloat(this.plane_transform_radius);
            ddcenter = new BigComplex(center);

            if(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE || ThreadDraw.HIGH_PRECISION_CALCULATION) {

                bnicenter = new BigIntNumComplex(center);
                bniplane_transform_radius = new BigIntNum(this.plane_transform_radius);

                ddccenter = new DDComplex(center);
                ddcplane_transform_radius = new DoubleDouble(this.plane_transform_radius);

                if (ThreadDraw.allocateMPFR()) {
                    mpfrbncenter = new MpfrBigNumComplex(center);
                    mpfrbnplane_transform_radius = new MpfrBigNum(this.plane_transform_radius);
                } else if (ThreadDraw.allocateMPIR()) {
                    mpirbncenter = new MpirBigNumComplex(center);
                    mpirbnplane_transform_radius = new MpirBigNum(this.plane_transform_radius);
                }
            }
        }

    }

    @Override
    public Complex transform(Complex pixel) {

        if(pixel.compare(center) == 0) {
            return pixel;
        }
        
        return pixel.circle_inversion(center, plane_transform_radius);
        
    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        if(pixel.compare(ddcenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(ddcenter, ddplane_transform_radius);

    }

    @Override
    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        if(pixel.compare(bnicenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(bnicenter, bniplane_transform_radius);

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        if(pixel.compare(mpfrbncenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(mpfrbncenter, mpfrbnplane_transform_radius);

    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        if(pixel.compare(mpirbncenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(mpirbncenter, mpirbnplane_transform_radius);

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        if(pixel.compare(ddccenter) == 0) {
            return pixel;
        }

        return pixel.circle_inversion(ddccenter, ddcplane_transform_radius);

    }

}
