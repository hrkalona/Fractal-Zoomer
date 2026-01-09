

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.InColorData;

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
    public double getResult(InColorData data) {
  
        return ColorAlgorithm.MAXIMUM_ITERATIONS;
 
    }
    
}
