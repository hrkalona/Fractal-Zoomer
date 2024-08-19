

package fractalzoomer.planes.fold;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FoldLeftPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;
    private BigNumComplex bncenter;

    private BigIntNumComplex bincenter;

    private MpfrBigNumComplex mpfrbncenter;

    private MpirBigNumComplex mpirbncenter;

    private DDComplex ddccenter;

    public FoldLeftPlane(double[] plane_transform_center) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddcenter = new BigComplex(center);
            ddccenter = new DDComplex(center);
            bncenter = new BigNumComplex(center);
            bincenter = new BigIntNumComplex(center);

            if (TaskRender.allocateMPFR()) {
                mpfrbncenter = new MpfrBigNumComplex(center);
            } else if (TaskRender.allocateMPIR()) {
                mpirbncenter = new MpirBigNumComplex(center);
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        return pixel.fold_left(center);

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.fold_left(ddcenter);

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return pixel.fold_left(bncenter);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.fold_left(bincenter);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.fold_left(mpfrbncenter);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.fold_left(mpirbncenter);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.fold_left(ddccenter);

    }
}
