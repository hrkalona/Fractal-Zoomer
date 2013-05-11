package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
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

    public MagTimesCosReSquared(int out_coloring_algorithm) { 
        super();
        
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            option = new InColorOptionDouble();
        }
        else {
            option = new InColorOptionInt();
        }
    
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return option.getFinalResult(((Complex)object[1]).norm_squared() * Math.abs(Math.cos(((Complex)object[1]).getRe() * ((Complex)object[1]).getRe())) * 400 + 100820); 
             
    }
    
}
