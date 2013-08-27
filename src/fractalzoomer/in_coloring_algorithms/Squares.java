package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.in_coloring_algorithms.options.InColorOption;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionDouble;
import fractalzoomer.in_coloring_algorithms.options.InColorOptionInt;
import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Squares extends InColorAlgorithm {
  private InColorOption option;
  private double pi2;
  private double pi59;
    

    public Squares(int out_coloring_algorithm) { 
        super();
        
        if(out_coloring_algorithm == MainWindow.NORMAL || out_coloring_algorithm == MainWindow.BINARY_DECOMPOSITION || out_coloring_algorithm == MainWindow.BINARY_DECOMPOSITION2 || out_coloring_algorithm == MainWindow.BIOMORPH) {
            option = new InColorOptionInt();
        }
        else {
            option = new InColorOptionDouble();
        }
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[1]).getRe();
        double im = ((Complex)object[1]).getIm();
        
        return ((Math.abs((int)(re * 40)) % 2) ^ (Math.abs((int)(im * 40)) % 2)) == 1 ? option.getFinalResult(Math.abs((Math.atan2(im, re) / (pi2)  + 0.75) * pi59 + 100820)) :  option.getFinalResult(Math.abs((Math.atan2(re, im) / (pi2)  + 0.75) * pi59 + 100820));

    }
    
}
