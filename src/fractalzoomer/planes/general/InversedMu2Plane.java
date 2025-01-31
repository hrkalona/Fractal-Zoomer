

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
public class InversedMu2Plane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    private MpirBigNum _025;

    public InversedMu2Plane(Fractal f) {

        super();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (NumericLibrary.allocateMPFR(f)) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
            } else if (NumericLibrary.allocateMPIR(f)) {
                tempRep = new MpirBigNum();
                tempImp = new MpirBigNum();
                _025 = new MpirBigNum(0.25);
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().plus_mutable(0.25);

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().plus(0.25);

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().plus(new MyApfloat(0.25));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRe, tempIm).plus_mutable(0.25);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal_mutable(tempRep, tempImp).plus_mutable(_025);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.reciprocal().plus(0.25);

    }
}
