

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuPlane extends Plane {

    public MuPlane() {

        super();

    }

    @Override
    public Complex transform_internal(Complex pixel) {

        return pixel;
        
    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        return pixel;

    }

    @Override
    public BigNumComplex transform_internal(BigNumComplex pixel) {

        return pixel;

    }

    @Override
    public BigIntNumComplex transform_internal(BigIntNumComplex pixel) {

        return pixel;

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel;

    }

    @Override
    public MpirBigNumComplex transform_internal(MpirBigNumComplex pixel) {

        return pixel;

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel;

    }
}
