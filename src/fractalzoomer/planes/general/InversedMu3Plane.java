

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class InversedMu3Plane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;
    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    private MpirBigNum _14;
    public InversedMu3Plane(Fractal f) {

        super();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (NumericLibrary.allocateMPFR(f)) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
            } else if (NumericLibrary.allocateMPIR(f)) {
                tempRep = new MpirBigNum();
                tempImp = new MpirBigNum();
                _14 = new MpirBigNum(1.401155189);
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub_mutable(1.401155189);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(1.401155189);

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(new MyApfloat(1.401155189));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRe, tempIm).sub_mutable(1.401155189);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRep, tempImp).sub_mutable(_14);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().sub(1.401155189);

    }
}
