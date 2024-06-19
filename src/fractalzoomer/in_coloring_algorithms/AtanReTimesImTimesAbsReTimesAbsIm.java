

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class AtanReTimesImTimesAbsReTimesAbsIm extends InColorAlgorithm {
    private int max_iterations;
    
    public AtanReTimesImTimesAbsReTimesAbsIm(int max_iterations) { 
        
        super();
        
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return max_iterations + Math.abs(Math.atan(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400;
         
    }
    
}
