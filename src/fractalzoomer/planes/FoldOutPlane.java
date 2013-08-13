/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class FoldOutPlane extends Plane {
    
    public FoldOutPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.fold_out(1);

    }
    
}
