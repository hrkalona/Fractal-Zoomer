

package fractalzoomer.planes.math;

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;


/**
 *
 * @author hrkalona2
 */
public class ExpPlane extends Plane {
    
    public ExpPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.exp();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.exp();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.exp();

    }

    
}
