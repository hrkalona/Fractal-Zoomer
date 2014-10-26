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
public class NNormBailoutTest extends BailoutTest {
  protected double n_norm;
 
    public NNormBailoutTest(double bound, double n_norm) {
        
        super(bound);
        this.n_norm = n_norm;
        
    }
    
     @Override //N norm
     public boolean escaped(Complex z) {
         
        return Math.pow(Math.pow(z.getAbsRe(), n_norm) + Math.pow(z.getAbsIm(), n_norm), 1 / n_norm) >= bound;
         
     }  
}
    