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
public class EscapeTimeGaussianInteger2 extends OutColorAlgorithm {
    
    public EscapeTimeGaussianInteger2() {
        
        super();
        
    }
     
    @Override
    public double getResult(Object[] object) {
        
        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).gaussian_integer());
        
        return (Integer)object[0] +  Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 5 + 100800;
 
    }   
    
}
