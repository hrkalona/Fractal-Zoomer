/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGaussianInteger5 extends OutColorAlgorithm {
    
    public EscapeTimeGaussianInteger5() {
        
        super();
        
    }
     
    @Override
    public double getResult(Object[] object) {
        
        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).gaussian_integer());
        
        double re = temp.getRe();
        double im = temp.getIm();
 
        return Math.abs((Integer)object[0] +  re + im + re / im) + 100800;
        
    } 
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
