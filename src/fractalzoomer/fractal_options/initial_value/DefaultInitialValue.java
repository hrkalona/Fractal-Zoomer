

package fractalzoomer.fractal_options.initial_value;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;

/**
 *
 * @author hrkalona2
 */
public class DefaultInitialValue extends PlanePointOption {

    public DefaultInitialValue() {
        
        super();
        
    }


    @Override
    public Complex getValue(Complex pixel) {
        
        return pixel;
        
    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return pixel;
    }
    
    @Override
    public String toString() {
        
        return "c";
        
    }
    
}
