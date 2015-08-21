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
public class AtanReTimesImTimesAbsReTimesAbsIm extends InColorAlgorithm {

    public AtanReTimesImTimesAbsReTimesAbsIm() { 
        
        super();
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return Math.abs(Math.atan(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400 + 100820;
         
    }
    
}
