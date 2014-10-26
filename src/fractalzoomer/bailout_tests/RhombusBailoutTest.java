/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.bailout_tests;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class RhombusBailoutTest extends BailoutTest {
    
    public RhombusBailoutTest(double bound) {
        
        super(bound);
        
    } 
    
    @Override //one norm  
    public boolean escaped(Complex z) {
         
        return z.getAbsRe() + z.getAbsIm() >= bound;
         
    }
    
}
