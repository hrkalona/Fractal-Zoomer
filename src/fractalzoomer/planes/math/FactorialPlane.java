/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.math;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FactorialPlane extends Plane {
    
    public FactorialPlane() {
        super();
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.factorial();
        
    }   
    
}
