/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class AbsPlane extends Plane {
    
    public AbsPlane() {
        super();
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        return pixel.abs();
        
    }   
    
}
