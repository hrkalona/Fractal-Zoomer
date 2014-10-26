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
public class Squares extends InColorAlgorithm {
  private double pi2;
  private double pi59;
    

    public Squares() { 
       
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[1]).getRe();
        double im = ((Complex)object[1]).getIm();
        
        return ((Math.abs((int)(re * 40)) % 2) ^ (Math.abs((int)(im * 40)) % 2)) == 1 ? Math.abs((Math.atan2(im, re) / (pi2)  + 0.75) * pi59) + 100820 :  Math.abs((Math.atan2(re, im) / (pi2)  + 0.75) * pi59) + 100820;

    }
    
}
