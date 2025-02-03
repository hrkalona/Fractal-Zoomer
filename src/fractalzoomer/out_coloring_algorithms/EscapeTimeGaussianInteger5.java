

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGaussianInteger5 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeGaussianInteger5(OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }
     
    @Override
    public double getResult(OutColorData data) {

        Complex z = data.z;
        Complex temp = z.sub(z.gaussian_integer());
        
        double re = temp.getRe();
        double im = temp.getIm();
 
        return Math.abs(EscapeTimeAlg.getResult(data) +  re + im + re / im);
        
    } 
    
}
