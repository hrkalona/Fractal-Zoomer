

package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public abstract class MandelVariation {
    
    protected MandelVariation() {

    }

    public abstract Complex getValue(Complex pixel);
    
}
