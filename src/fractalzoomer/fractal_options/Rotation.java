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
  private Complex center;
  
    public Rotation(double cos_theta, double sin_theta, double x, double y) {
        
        rotation = new Complex(cos_theta, sin_theta);
        center = new Complex(x, y);
        
    }
    
    public Complex getPixel(Complex pixel, boolean inv) {
        
         Complex temp = pixel.sub(center);
         return inv == true ? temp.times(rotation.conjugate()).plus_mutable(center) : temp.times(rotation).plus_mutable(center);
         
    }
    
}
