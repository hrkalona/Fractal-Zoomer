

package fractalzoomer.planes.math.inverse_trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.DDComplex;
import fractalzoomer.core.numerics.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ATanPlane extends Plane {
    
    public ATanPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.atan();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.atan();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.atan();

    }
    
}
