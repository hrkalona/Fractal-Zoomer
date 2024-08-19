

package fractalzoomer.fractal_options.perturbation;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;


/**
 *
 * @author hrkalona2
 */
public class DefaultPerturbation extends PlanePointOption {
    
    public DefaultPerturbation() {
    
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
    
}
