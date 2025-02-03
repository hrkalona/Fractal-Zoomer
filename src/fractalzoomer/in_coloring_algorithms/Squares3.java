

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.InColorData;

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
    public double getResult(InColorData data) {

        Complex z = data.z;
        double x = z.getRe();
        double y = z.getIm();
        return max_iterations + Math.abs(Math.sin(x * 50)) * Math.abs(Math.sin(y * 50)) * 100;
        
    }
    
}
