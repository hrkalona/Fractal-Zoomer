

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

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
    public double getResult(Object[] object) {
        
        return max_iterations + Math.abs(((Complex)object[0]).getRe() / ((Complex)object[0]).getIm()) * 8;

    }
    
}
