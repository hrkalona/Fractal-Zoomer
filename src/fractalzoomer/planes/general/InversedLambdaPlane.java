

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
public class InversedLambdaPlane extends Plane {
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
    
    public InversedLambdaPlane(Fractal f) {
        
        super();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (NumericLibrary.allocateMPFR(f)) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
                temp3 = new MpfrBigNum();
                temp4 = new MpfrBigNum();
            } else if (NumericLibrary.allocateMPIR(f)) {
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

        if(pixel.isZero()) {
            return pixel;
        }
        Complex temp = pixel.reciprocal();
        return temp.times_mutable(temp.r_sub(1));
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(MyApfloat.ONE));

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(1));

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal_mutable(tempRe, tempIm);

        return pixel.times_mutable(pixel.r_sub(1, tempRe, tempIm), temp1, temp2, temp3, temp4);

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal_mutable(tempRep, tempImp);

        return pixel.times_mutable(pixel.r_sub(1, tempRep, tempImp), temp1p, temp2p, temp3p, temp4p);

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(1));

    }
    
}
