
package fractalzoomer.fractal_options.initial_value;

import fractalzoomer.core.Complex;
import fractalzoomer.core.numerics.*;
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
    public GenericComplex getValueGeneric(GenericComplex pixel) {

        if (pixel instanceof MpirBigNumComplex) {
            return new MpirBigNumComplex(this.pixel);
        } else if (pixel instanceof MpfrBigNumComplex) {
            return new MpfrBigNumComplex(this.pixel);
        } else if (pixel instanceof BigComplex) {
            return new BigComplex(this.pixel);
        } else if (pixel instanceof DDComplex) {
            return new DDComplex(this.pixel);
        } else if (pixel instanceof BigIntNumComplex) {
            return new BigIntNumComplex(this.pixel);
        } else if (pixel instanceof BigNumComplex) {
            return new BigNumComplex(this.pixel);
        } else if (pixel instanceof BigDecNumComplex) {
            return new BigDecNumComplex(this.pixel);
        }  else if (pixel instanceof MantExpComplex) {
            return MantExpComplex.create(this.pixel);
        } else if (pixel instanceof Complex) {
            return this.pixel;
        }
        return null;
    }
    
    @Override
    public String toString() {
        
        return pixel.toString();
        
    }

    @Override
    public boolean isStatic() {return true;}
}
