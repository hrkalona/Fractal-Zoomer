

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

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
    public double getResult(Object[] object) {

        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).sin());
        
        return EscapeTimeAlg.getResult(object) +  Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 8;
                
    }
    
}
