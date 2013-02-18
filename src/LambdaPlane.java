

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
      
        return  pixel.times(pixel.sub(1, pixel));
        
    }
    
}
