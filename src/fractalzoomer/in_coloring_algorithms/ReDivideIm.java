package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class ReDivideIm extends InColorAlgorithm {

    public ReDivideIm() { 
        
        super();
    
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return Math.abs(((Complex)object[1]).getRe() / ((Complex)object[1]).getIm()) * 8  + 100820;

    }
    
}
