

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class CosMag extends InColorAlgorithm {
    private int max_iterations;
    
    public CosMag(int max_iterations) { 
        
        super();
        InUsingIncrement = true;
        this.max_iterations = max_iterations;
        
    }
    
    @Override
    public double getResult(Object[] object) {
 
        return ((int)(((Complex)object[0]).norm_squared() * 10)) % 2 == 1 ? -(Math.abs(Math.cos(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400 + INCREMENT + max_iterations) : max_iterations + Math.abs(Math.sin(((Complex)object[0]).getRe() * ((Complex)object[0]).getIm() * ((Complex)object[0]).getAbsRe() * ((Complex)object[0]).getAbsIm())) * 400;
       
    }
    
}
