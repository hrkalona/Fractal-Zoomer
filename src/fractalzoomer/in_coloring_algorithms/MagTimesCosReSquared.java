

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class MagTimesCosReSquared extends InColorAlgorithm {
    private int max_iterations;
    
    public MagTimesCosReSquared(int max_iterations) { 
       
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
    
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[0]).getRe();
        
        return max_iterations + ((Complex)object[0]).norm_squared() * Math.abs(Math.cos(re * re)) * 400; 
             
    }
    
}
