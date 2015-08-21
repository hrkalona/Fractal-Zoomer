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
public class SinReSquaredMinusImSquared extends InColorAlgorithm {

    public SinReSquaredMinusImSquared() { 
       
        super();

    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[0]).getRe();
        double im = ((Complex)object[0]).getIm();
        
        return Math.abs(Math.sin(re * re - im * im)) * 400 + 100820;
             
    }
    
}
