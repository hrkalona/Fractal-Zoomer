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
public class DefaultPerturbation {
  protected Complex pixel;

    
    public DefaultPerturbation(double re, double im) {
    
        pixel = new Complex(re, im);
        
    }


    public Complex getPixel(Complex pixel) {
        
        return pixel;
        
    }
    
}
