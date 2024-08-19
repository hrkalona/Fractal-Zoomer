

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class Squares2 extends InColorAlgorithm {
    private int max_iterations;
    
    public Squares2(int max_iterations) { 
        super();
        InUsingIncrement = true;
        this.max_iterations = max_iterations;
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double x = ((Complex)object[0]).getRe() * 16;
        double y = ((Complex)object[0]).getIm() * 16;
        
        double dx = Math.abs(x - Math.floor(x));
        double dy = Math.abs(y - Math.floor(y));
        
        return (dx < 0.5 && dy < 0.5) || (dx > 0.5 && dy > 0.5) ? -(INCREMENT + max_iterations) : max_iterations;
        
    }
    
}
