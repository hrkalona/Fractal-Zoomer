/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public abstract class BailoutTest {
  protected double bound;
    
    public BailoutTest(double bound) {
        
        this.bound = bound;
        
    }
    
    public abstract boolean escaped(Complex z);
    
}
