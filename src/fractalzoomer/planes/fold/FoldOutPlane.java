

package fractalzoomer.planes.fold;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FoldOutPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;

    private BigIntNumComplex bincenter;

    private MpfrBigNumComplex mpfrbncenter;

    private MpirBigNumComplex mpirbncenter;

    private DDComplex ddccenter;

    public FoldOutPlane(double plane_transform_radius) {

        super();
        center = new Complex(plane_transform_radius, 0);
        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddcenter = new BigComplex(center);
            ddccenter = new DDComplex(center);
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
        
        return pixel.fold_out(center);

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.fold_out(ddcenter);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.fold_out(mpfrbncenter);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.fold_out(bincenter);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.fold_out(mpirbncenter);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.fold_out(ddccenter);

    }
}
