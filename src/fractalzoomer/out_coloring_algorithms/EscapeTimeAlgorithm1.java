

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeAlgorithm1 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeAlgorithm1(OutColorAlgorithm EscapeTimeAlg) {
        
        super();

        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(OutColorData data) {
        
        Complex temp = data.z.sub(data.zold);
        
        return EscapeTimeAlg.getResult(data) + Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 4;
           
    }
    
}
