package fractalzoomer.planes;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class CosPlane extends Plane {
    
    public CosPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.cos();

    }
    
}
