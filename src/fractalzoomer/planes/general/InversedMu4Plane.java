

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class InversedMu4Plane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;

    public InversedMu4Plane() {

        super();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (TaskRender.allocateMPFR()) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
            } else if (TaskRender.allocateMPIR()) {
                tempRep = new MpirBigNum();
                tempImp = new MpirBigNum();
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub_mutable(2);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(2);

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(MyApfloat.TWO);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRe, tempIm).sub_mutable(2);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRep, tempImp).sub_mutable(2);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(2);

    }
}
