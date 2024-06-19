

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class LambdaPlane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpfrBigNum temp3;
    private MpfrBigNum temp4;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    private MpirBigNum temp3p;
    private MpirBigNum temp4p;
    
    public LambdaPlane() {
        
        super();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (TaskRender.allocateMPFR()) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
                temp3 = new MpfrBigNum();
                temp4 = new MpfrBigNum();
            } else if (TaskRender.allocateMPIR()) {
                tempRep = new MpirBigNum();
                tempImp = new MpirBigNum();
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
                temp3p = new MpirBigNum();
                temp4p = new MpirBigNum();
            }
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {
      
        return  pixel.times(pixel.r_sub(1));
        
    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return  pixel.times(pixel.r_sub(1));

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return  pixel.times(pixel.r_sub(MyApfloat.ONE));

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return  pixel.times(pixel.r_sub(BigNum.create(1)));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.times_mutable(pixel.r_sub(1, tempRe, tempIm), temp1, temp2, temp3, temp4);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.times_mutable(pixel.r_sub(1, tempRep, tempImp), temp1p, temp2p, temp3p, temp4p);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.times(pixel.r_sub(1));

    }
    
}
