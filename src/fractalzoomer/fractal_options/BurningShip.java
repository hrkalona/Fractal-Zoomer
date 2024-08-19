

package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class BurningShip extends MandelVariation {
    
    public BurningShip() {
        
        super();

    }

    @Override
    public Complex getValue(Complex pixel) {
        
        return pixel.abs_mutable();
        
    }
    
}
