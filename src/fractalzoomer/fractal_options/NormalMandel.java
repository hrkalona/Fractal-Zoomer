

package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class NormalMandel extends MandelVariation {
    
    public NormalMandel() {
        
        super();

    }

    @Override
    public Complex getValue(Complex pixel) {
        
        return pixel;
        
    }
    
}
