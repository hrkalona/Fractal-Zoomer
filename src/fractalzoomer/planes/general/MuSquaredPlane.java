package fractalzoomer.planes.general;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

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
