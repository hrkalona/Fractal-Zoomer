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
public class MuSquaredPlane extends Plane {
    
    public MuSquaredPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.square();

    }
    
}
