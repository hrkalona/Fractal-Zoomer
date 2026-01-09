

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGaussianInteger4 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeGaussianInteger4(OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }
     
    @Override
    public double getResult(OutColorData data) {

        Complex z = data.z;
        Complex temp = z.sub(z.gaussian_integer());
        
        return Math.abs(EscapeTimeAlg.getResult(data) +  temp.getRe() + temp.getIm());
 
    }   
    
}
