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
public class DefaultInitialValue {
  protected Complex pixel;

    public DefaultInitialValue(double re, double im) {
    
        pixel = new Complex(re, im);
        
    }


    public Complex getPixel(Complex pixel) {
        
        return pixel;
        
    }
    
}
