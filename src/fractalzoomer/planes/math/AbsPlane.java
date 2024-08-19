

package fractalzoomer.planes.math;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class AbsPlane extends Plane {
    
    public AbsPlane() {
        super();
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.abs();
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel.abs();

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel.abs();

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return pixel.abs();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.abs();

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel.abs();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.abs();

    }

}
