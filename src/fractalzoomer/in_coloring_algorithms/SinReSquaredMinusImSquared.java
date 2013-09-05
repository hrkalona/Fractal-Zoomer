package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.in_coloring_algorithms.options.InColorOption;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionDouble;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionInt;
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
  private InColorOption option;

    public SinReSquaredMinusImSquared(boolean smoothing) { 
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
        
        double re = ((Complex)object[1]).getRe();
        double im = ((Complex)object[1]).getIm();
        
        return option.getFinalResult(Math.abs(Math.sin(re * re - im * im)) * 400 + 100820);
             
    }
    
}
