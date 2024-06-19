

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGaussianInteger3 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeGaussianInteger3(OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }
     
    @Override
    public double getResult(Object[] object) {
        
        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).gaussian_integer());
        
        return Math.abs(EscapeTimeAlg.getResult(object) + temp.getRe());
 
    } 
    
}
