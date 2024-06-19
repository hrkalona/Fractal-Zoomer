

package fractalzoomer.planes.math.trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class CosPlane extends Plane {
    
    public CosPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.cos();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.cos();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.cos();

    }
    
}
