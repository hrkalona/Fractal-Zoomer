

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
public class InversedLambda2Plane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    private MpirBigNum _025;
    
    public InversedLambda2Plane(Fractal f) {
        
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
        return pixel.square().reciprocal_mutable().r_sub_mutable(0.25);
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.square().reciprocal().r_sub(new MyApfloat(0.25));

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        return pixel.square().reciprocal().r_sub(0.25);

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.square_mutable(tempRe, tempIm).reciprocal_mutable(tempRe, tempIm).r_sub_mutable(0.25);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.square_mutable(tempRep, tempImp).reciprocal_mutable(tempRep, tempImp).r_sub_mutable(_025);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.square().reciprocal().r_sub(0.25);

    }
    
}
