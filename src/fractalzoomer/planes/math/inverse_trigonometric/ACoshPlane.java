

package fractalzoomer.planes.math.inverse_trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.core.DDComplex;
import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ACoshPlane extends Plane {
    
    public ACoshPlane() {
        
        super();
        
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.acosh();

    }

    @Override
    public MpfrBigNumComplex transform_internal(MpfrBigNumComplex pixel) {

        return pixel.acosh();

    }

    @Override
    public DDComplex transform_internal(DDComplex pixel) {

        return pixel.acosh();

    }
    
}
