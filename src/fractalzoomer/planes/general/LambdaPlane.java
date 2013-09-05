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
public class LambdaPlane extends Plane {
    
    public LambdaPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
      
        return  pixel.times(pixel.r_sub(1));
        
    }
    
}
