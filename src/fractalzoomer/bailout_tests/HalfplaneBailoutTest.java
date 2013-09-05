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
public class HalfplaneBailoutTest extends BailoutTest {
 
    public HalfplaneBailoutTest(double bound) {
        
        super(bound);
        
    }
    
     @Override
     public boolean escaped(Complex z) {
         
        return z.getRe() >= bound;
         
     }
    
}
