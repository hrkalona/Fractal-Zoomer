package fractalzoomer.bailout_tests;

import fractalzoomer.core.Complex;

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
    
     @Override //euclidean norm
     public boolean escaped(Complex z) {
         
        return z.norm_squared() >= bound;
         
     }
    
}
