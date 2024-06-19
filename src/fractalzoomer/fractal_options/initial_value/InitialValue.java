
package fractalzoomer.fractal_options.initial_value;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;

/**
 *
 * @author hrkalona2
 */
public class InitialValue extends PlanePointOption {

    private Complex pixel; 

    public InitialValue(double re, double im) {
        
        super();
        pixel = new Complex(re, im);
        
    }

    public InitialValue(Complex z) {

        super();
        pixel = new Complex(z);

    }

    @Override
    public Complex getValue(Complex pixel) {

        return this.pixel;

    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return MantExpComplex.create(this.pixel);
    }
    
    @Override
    public String toString() {
        
        return pixel.toString();
        
    }

    @Override
    public boolean isStatic() {return true;}
}
