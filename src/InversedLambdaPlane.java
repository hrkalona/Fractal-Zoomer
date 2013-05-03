

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class InversedLambdaPlane extends Plane {
    
    public InversedLambdaPlane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        Complex temp = pixel.divide(1, 0);
        return temp.times(temp.sub(1, 0));
        
    }
    
}
