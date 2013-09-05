package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.in_coloring_algorithms.options.InColorOption;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionDouble;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionInt;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class CosMag extends InColorAlgorithm {
  private InColorOption option;

    

    public CosMag(boolean smoothing) { 
        super();
        
        if(!smoothing) {
            option = new InColorOptionInt();
        }
        else {
            option = new InColorOptionDouble();
        }
    
    }
    
    @Override
    public double getResult(Object[] object) {
 
        return ((int)(((Complex)object[1]).norm_squared() * 10)) % 2 == 1 ? option.getFinalResult(Math.abs(Math.cos(((Complex)object[1]).getRe() * ((Complex)object[1]).getIm() * ((Complex)object[1]).absRe() * ((Complex)object[1]).absIm())) * 400 + 100920) : option.getFinalResult(Math.abs(Math.sin(((Complex)object[1]).getRe() * ((Complex)object[1]).getIm() * ((Complex)object[1]).absRe() * ((Complex)object[1]).absIm())) * 400 + 100820);
       
    }
    
}
