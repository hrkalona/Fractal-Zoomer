

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class Squares3 extends InColorAlgorithm {
    private int max_iterations;
    public Squares3(int max_iterations) {
        super();
        InUsingIncrement = false;
        this.max_iterations = max_iterations;
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double x = ((Complex)object[0]).getRe();
        double y = ((Complex)object[0]).getIm();
        return max_iterations + Math.abs(Math.sin(x * 50)) * Math.abs(Math.sin(y * 50)) * 100;
        
    }
    
}
