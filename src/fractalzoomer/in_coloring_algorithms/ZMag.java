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
public class ZMag extends InColorAlgorithm {
  
    public ZMag() { 
        
      super();
  
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return ((Complex)object[1]).norm_squared() * ((Integer)object[0] / 3.0) + 100820;

    }
    
}
