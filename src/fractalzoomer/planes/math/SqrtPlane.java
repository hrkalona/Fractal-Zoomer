package fractalzoomer.planes.math;

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
public class SqrtPlane extends Plane {
    
    public SqrtPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.sqrt();

    }
    
}
