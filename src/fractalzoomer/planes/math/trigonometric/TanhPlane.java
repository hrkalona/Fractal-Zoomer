

package fractalzoomer.planes.math.trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.DDComplex;
import fractalzoomer.core.numerics.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class TanhPlane extends Plane {
    
    public TanhPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.tanh();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.tanh();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.tanh();

    }
    
}
