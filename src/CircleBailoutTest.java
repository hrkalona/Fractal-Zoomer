/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class CircleBailoutTest extends BailoutTest {
 
    public CircleBailoutTest(double bound) {
        
        super(bound);
        
    }
    
     @Override
     public boolean escaped(Complex z) {
         
        return z.norm_squared() >= bound;
         
     }
    
}
