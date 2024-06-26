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
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class ShearPlane extends Plane {

    private Complex scales;
    private BigComplex ddscales;
    private BigNumComplex bnscales;
    private BigIntNumComplex binscales;
    private MpfrBigNumComplex mpfrbnscales;

    private MpirBigNumComplex mpirbnscales;

    private DDComplex ddcscales;
    private boolean use_center;

    private Complex centerd;
    private BigComplex centerbc;
    private DDComplex centerdd;
    private BigNumComplex centerbn;
    private BigIntNumComplex centerbin;
    private MpfrBigNumComplex centermpfr;
    private MpirBigNumComplex centermpir;

    public ShearPlane(double[] plane_transform_scales, Apfloat[] center) {

        super();
        use_center = center[0].compareTo(Apfloat.ZERO) != 0 && center[1].compareTo(Apcomplex.ZERO) != 0;

        scales = new Complex(plane_transform_scales[0], plane_transform_scales[1]);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddscales = new BigComplex(scales);
            ddcscales = new DDComplex(scales);
            bnscales = new BigNumComplex(scales);
            binscales = new BigIntNumComplex(scales);

            if (TaskRender.allocateMPFR()) {
                mpfrbnscales = new MpfrBigNumComplex(scales);
            } else if (TaskRender.allocateMPIR()) {
                mpirbnscales = new MpirBigNumComplex(scales);
            }
        }

        if(use_center) {
            Apfloat xcenter = center[0];
            Apfloat ycenter = center[1];

            centerbc = new BigComplex(xcenter, ycenter);
            centerd = new Complex(xcenter.doubleValue(), ycenter.doubleValue());

            if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
                centerdd = new DDComplex(new DoubleDouble(xcenter), new DoubleDouble(ycenter));

                centerbn = new BigNumComplex(BigNum.create(xcenter), BigNum.create(ycenter));

                centerbin = new BigIntNumComplex(new BigIntNum(xcenter), new BigIntNum(ycenter));

                if (TaskRender.allocateMPFR()) {
                    centermpfr = new MpfrBigNumComplex(new MpfrBigNum(xcenter), new MpfrBigNum(ycenter));
                } else if (TaskRender.allocateMPIR()) {
                    centermpir = new MpirBigNumComplex(MpirBigNum.fromApfloat(xcenter), MpirBigNum.fromApfloat(ycenter));
                }
            }

        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        Complex v = new Complex(pixel);
        if(use_center) {
            v.sub_mutable(centerd);
        }

        v = v.shear(scales);

        if(use_center) {
            v.plus_mutable(centerd);
        }

        return v;

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        BigComplex v = pixel;
        if(use_center) {
            v = v.sub(centerbc);
        }

        v = v.shear(ddscales);

        if(use_center) {
            v = v.plus(centerbc);
        }

        return v;

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        BigNumComplex v = new BigNumComplex(pixel);
        if(use_center) {
            v = v.sub(centerbn);
        }

        v = v.shear(bnscales);

        if(use_center) {
            v = v.plus(centerbn);
        }

        return v;

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        BigIntNumComplex v = new BigIntNumComplex(pixel);
        if(use_center) {
            v = v.sub(centerbin);
        }

        v = v.shear(binscales);

        if(use_center) {
            v = v.plus(centerbin);
        }

        return v;

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        MpfrBigNumComplex v = new MpfrBigNumComplex(pixel);
        if(use_center) {
            v.sub_mutable(centermpfr);
        }

        v = v.shear(mpfrbnscales);

        if(use_center) {
            v.plus_mutable(centermpfr);
        }

        return v;

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        MpirBigNumComplex v = new MpirBigNumComplex(pixel);
        if(use_center) {
            v.sub_mutable(centermpir);
        }

        v = v.shear(mpirbnscales);

        if(use_center) {
            v.plus_mutable(centermpir);
        }

        return v;

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        DDComplex v = new DDComplex(pixel);
        if(use_center) {
            v = v.sub(centerdd);
        }

        v = v.shear(ddcscales);

        if(use_center) {
            v = v.plus(centerdd);
        }

        return v;

    }
}
