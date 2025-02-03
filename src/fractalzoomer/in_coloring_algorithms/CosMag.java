

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.InColorData;

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
    public double getResult(InColorData data) {

        Complex z = data.z;
        return ((int)(z.norm_squared() * 10)) % 2 == 1 ? -(Math.abs(Math.cos(z.getRe() * z.getIm() * z.getAbsRe() * z.getAbsIm())) * 400 + INCREMENT + max_iterations) : max_iterations + Math.abs(Math.sin(z.getRe() * z.getIm() * z.getAbsRe() * z.getAbsIm())) * 400;
       
    }
    
}
