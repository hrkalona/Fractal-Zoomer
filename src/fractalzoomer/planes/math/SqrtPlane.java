

package fractalzoomer.planes.math;

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class SqrtPlane extends Plane {
    
    public SqrtPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.sqrt();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.sqrt();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.sqrt();

    }
    
}
