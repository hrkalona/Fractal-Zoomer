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
public abstract class MandelVariation {
    
    public MandelVariation() {

    }

    public abstract Complex getPixel(Complex pixel);
    
}
