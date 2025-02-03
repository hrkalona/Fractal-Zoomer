

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.InColorData;

/**
 *
 * @author hrkalona2
 */
public class ReDivideIm extends InColorAlgorithm {
    private int max_iterations;

    public ReDivideIm(int max_iterations) { 
        
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;        
    
    }
    
    @Override
    public double getResult(InColorData data) {

        Complex z = data.z;
        return max_iterations + Math.abs(z.getRe() / z.getIm()) * 8;

    }
    
}
