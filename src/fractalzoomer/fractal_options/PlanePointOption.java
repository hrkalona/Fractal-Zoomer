
package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExpComplex;

/**
 *
 * @author hrkalona2
 */
public abstract class PlanePointOption {
    
    protected PlanePointOption() {
        
    }
    
    public abstract Complex getValue(Complex pixel);
    public abstract MantExpComplex getValueDeep(MantExpComplex pixel);

    public GenericComplex getValueGeneric(GenericComplex pixel) {
        return null;
    }

    public boolean isStatic() {return false;}
    
}
