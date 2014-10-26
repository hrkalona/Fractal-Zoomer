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
        
        return Math.abs(Math.atan(((Complex)object[1]).getRe() * ((Complex)object[1]).getIm() * ((Complex)object[1]).getAbsRe() * ((Complex)object[1]).getAbsIm())) * 400 + 100820;
         
    }
    
}
