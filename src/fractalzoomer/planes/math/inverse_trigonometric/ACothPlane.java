/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.math.inverse_trigonometric;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class ACothPlane extends Plane {
    
    public ACothPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.acoth();

    }
    
}
