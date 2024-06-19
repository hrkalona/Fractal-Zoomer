

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeColorDecomposition extends OutColorAlgorithm {
  protected double pi2;
  protected double pi59;
  protected OutColorAlgorithm EscapeTimeAlg;
    
    public EscapeTimeColorDecomposition(OutColorAlgorithm EscapeTimeAlg) {
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = Math.PI * 59;
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(Object[] object) {

        return Math.abs(EscapeTimeAlg.getResult(object) + (((Complex)object[1]).arg() / pi2  + 0.75) * pi59);

    }
}
