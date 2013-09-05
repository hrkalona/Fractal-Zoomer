/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class InitialValue extends DefaultInitialValue {

    public InitialValue(double re, double im) {
    
        super(re, im);
        
    }
    
    @Override
    public Complex getPixel(Complex pixel) {
        
        return this.pixel;
        
    }
}
