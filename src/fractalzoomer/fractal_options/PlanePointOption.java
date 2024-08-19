
package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;

/**
 *
 * @author hrkalona2
 */
public abstract class PlanePointOption {
    
    protected PlanePointOption() {
        
    }
    
    public abstract Complex getValue(Complex pixel);
    public abstract MantExpComplex getValueDeep(MantExpComplex pixel);

    public boolean isStatic() {return false;}
    
}
