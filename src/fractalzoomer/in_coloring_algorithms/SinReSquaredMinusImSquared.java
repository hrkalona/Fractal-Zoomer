

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.InColorData;


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
    public double getResult(InColorData data) {

        Complex z = data.z;
        double re = z.getRe();
        double im = z.getIm();
        
        return max_iterations + Math.abs(Math.sin(re * re - im * im)) * 400;
             
    }
    
}
