

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeAlgorithm2 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeAlgorithm2(OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(OutColorData data) {

        Complex z = data.z;
        Complex temp = z.sub(z.sin());
        
        return EscapeTimeAlg.getResult(data) +  Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 8;
                
    }
    
}
