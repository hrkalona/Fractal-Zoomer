

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class MaximumIterations extends InColorAlgorithm {
    
    public MaximumIterations() { 
        super();
        InUsingIncrement = false;
    }
    
    @Override
    public double getResult(Object[] object) {
  
        return ColorAlgorithm.MAXIMUM_ITERATIONS;
 
    }
    
}
