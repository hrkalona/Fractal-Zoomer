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
public class CosMag extends InColorAlgorithm {

    

    public CosMag() { 
        
        super();
 
    }
    
    @Override
    public double getResult(Object[] object) {
 
        return ((int)(((Complex)object[0]).norm_squared() * 10)) % 2 == 1 ? Math.abs(Math.cos(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400 + 100920 : Math.abs(Math.sin(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400 + 100820;
       
    }
    
}
