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
public class MandelGrass extends MandelVariation {
  private Complex factor;
    
    public MandelGrass(double real, double imaginary) {
        
        super();
        
        factor = new Complex(real, imaginary);

    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.plus_mutable((pixel.divide(pixel.norm())).times_mutable(factor));
        
    }
    
}
