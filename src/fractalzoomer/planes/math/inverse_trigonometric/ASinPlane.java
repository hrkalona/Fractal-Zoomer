

package fractalzoomer.planes.math.inverse_trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ASinPlane extends Plane {
    
    public ASinPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.asin();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.asin();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.asin();

    }
    
}
