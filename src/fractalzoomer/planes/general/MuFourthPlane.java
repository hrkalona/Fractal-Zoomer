

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuFourthPlane extends Plane {
    public MuFourthPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.fourth();

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.fourth();

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.fourth();

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return pixel.fourth();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.fourth_mutable();

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.fourth_mutable();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.fourth();

    }
    
}
