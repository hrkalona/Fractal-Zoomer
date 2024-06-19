

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class SinReSquaredMinusImSquared extends InColorAlgorithm {
    private int max_iterations;

    public SinReSquaredMinusImSquared(int max_iterations) { 
       
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;

    }
    
    @Override
    public double getResult(Object[] object) {
        
        double re = ((Complex)object[0]).getRe();
        double im = ((Complex)object[0]).getIm();
        
        return max_iterations + Math.abs(Math.sin(re * re - im * im)) * 400;
             
    }
    
}
