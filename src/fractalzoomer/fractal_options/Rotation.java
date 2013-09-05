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
public class Rotation {
  private Complex rotation;
  
    public Rotation(double cos_theta, double sin_theta) {
        
        rotation = new Complex(cos_theta, sin_theta);
        
    }
    
    public Complex getPixel(Complex pixel, boolean inv) {
        
         return inv == true ? pixel.times(rotation.conjugate()) : pixel.times(rotation);
         
    }
    
}
