

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGaussianInteger extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeGaussianInteger(OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }
     
    @Override
    public double getResult(OutColorData data) {

        Complex z = data.z;
        return EscapeTimeAlg.getResult(data) + z.distance_squared(z.gaussian_integer()) * 90;
 
    }
    
}
