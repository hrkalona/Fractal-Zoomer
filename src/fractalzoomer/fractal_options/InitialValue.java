package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class InitialValue {
  protected Complex pixel;

    
    public InitialValue(double re, double im) {
    
        pixel = new Complex(re, im);
        
    }


    public Complex getPixel(Complex pixel) {
        
        return pixel;
        
    }
    
}
