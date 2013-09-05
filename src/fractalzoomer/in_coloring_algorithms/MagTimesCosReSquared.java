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
public class MagTimesCosReSquared extends InColorAlgorithm {
  private InColorOption option;

    public MagTimesCosReSquared(boolean smoothing) { 
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
        
        return option.getFinalResult(((Complex)object[1]).norm_squared() * Math.abs(Math.cos(re * re)) * 400 + 100820); 
             
    }
    
}
