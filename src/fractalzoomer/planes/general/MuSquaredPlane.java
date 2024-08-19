

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuSquaredPlane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    
    public MuSquaredPlane() {
        
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
        
        return pixel.square();

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.square();

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.square();

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return pixel.square();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.square_mutable(tempRe, tempIm);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.square_mutable(tempRep, tempImp);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.square();

    }
    
}
