

package fractalzoomer.planes.math;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FactorialPlane extends Plane {
    
    public FactorialPlane() {
        super();
    }

    @Override
    public Complex transform_internal(Complex pixel) {
        
        return pixel.factorial();
        
    }   
    
}
