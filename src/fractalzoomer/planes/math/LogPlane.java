

package fractalzoomer.planes.math;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.BigComplex;
import fractalzoomer.core.numerics.DDComplex;
import fractalzoomer.core.numerics.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class LogPlane extends Plane {
    
    public LogPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return pixel.log();

    }

    @Override
    public BigComplex transform_internal(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        return new BigComplex(transform_internal(pixel.toComplex()));

    }
    
}
