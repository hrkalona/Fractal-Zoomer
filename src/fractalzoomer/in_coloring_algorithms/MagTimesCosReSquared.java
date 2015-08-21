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
public class MagTimesCosReSquared extends InColorAlgorithm {

    public MagTimesCosReSquared() { 
       
        super();
    
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[0]).getRe();
        
        return ((Complex)object[0]).norm_squared() * Math.abs(Math.cos(re * re)) * 400 + 100820; 
             
    }
    
}
