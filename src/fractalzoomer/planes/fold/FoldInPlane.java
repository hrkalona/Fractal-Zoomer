

package fractalzoomer.planes.fold;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FoldInPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;

    private MpfrBigNumComplex mpfrbncenter;

    private BigIntNumComplex bincenter;

    private MpirBigNumComplex mpirbncenter;

    private DDComplex ddccenter;

    public FoldInPlane(double plane_transform_radius, Fractal f) {

        super();
        center = new Complex(plane_transform_radius, 0);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddcenter = new BigComplex(center);
            ddccenter = new DDComplex(center);
            bincenter = new BigIntNumComplex(center);
            if (NumericLibrary.allocateMPFR(f)) {
                mpfrbncenter = new MpfrBigNumComplex(center);
            } else if (NumericLibrary.allocateMPIR(f)) {
                mpirbncenter = new MpirBigNumComplex(center);
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(center);
  
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(ddcenter);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(bincenter);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(mpfrbncenter);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(mpirbncenter);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.fold_in(ddccenter);

    }
}
