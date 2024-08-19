

package fractalzoomer.fractal_options.perturbation;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;


/**
 *
 * @author hrkalona2
 */
public class Perturbation extends PlanePointOption {
    private Complex pixel;
    
    public Perturbation(double re, double im) {
    
        super();
        pixel = new Complex(re, im);
        
    }


    @Override
    public Complex getValue(Complex pixel) {
        
        return this.pixel.plus(pixel);
        
    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return MantExpComplex.create(this.pixel).plus(pixel);
    }
    
}
