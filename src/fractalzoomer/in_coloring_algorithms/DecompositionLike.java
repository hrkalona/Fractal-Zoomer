

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.utils.InColorData;

/**
 *
 * @author hrkalona2
 */
public class DecompositionLike  extends InColorAlgorithm {
  private double pi2;
  private double pi59;
  private int max_iterations;
    

    public DecompositionLike(int max_iterations) { 
        
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
        
    }
    
    @Override
    public double getResult(InColorData data) {
        
        return max_iterations + Math.abs((data.z.arg() / (pi2)  + 0.75) * pi59);

    }
    
}
