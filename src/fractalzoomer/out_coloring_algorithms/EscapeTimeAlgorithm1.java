

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeAlgorithm1 extends OutColorAlgorithm {
  private int zold;
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeAlgorithm1(int zold, OutColorAlgorithm EscapeTimeAlg) {
        
        super();
        
        this.zold = zold;
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        Complex temp = (((Complex)object[1]).sub(((Complex)object[zold])));
        
        return EscapeTimeAlg.getResult(object) + Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 4;
           
    }
    
}
