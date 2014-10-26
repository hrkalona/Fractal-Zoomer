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
public class DecompositionLike  extends InColorAlgorithm {
  private double pi2;
  private double pi59;
    

    public DecompositionLike() { 
        
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return Math.abs((((Complex)object[1]).arg() / (pi2)  + 0.75) * pi59) + 100820;

    }
    
}
