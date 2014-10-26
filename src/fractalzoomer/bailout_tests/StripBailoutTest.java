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
public class StripBailoutTest extends BailoutTest {
 
    public StripBailoutTest(double bound) {
        
        super(bound);
        
    }
    
     @Override
     public boolean escaped(Complex z) {

        return z.getAbsRe() >= bound;
         
     }
    
}
