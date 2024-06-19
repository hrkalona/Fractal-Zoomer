

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeEscapeRadius extends OutColorAlgorithm {
  protected double log_bailout;
  protected double pi2;
  protected OutColorAlgorithm EscapeTimeAlg;
  private Norm normImpl;
    
    public EscapeTimeEscapeRadius(double bailout, OutColorAlgorithm EscapeTimeAlg, Norm normImpl) {

        super();

        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }

        pi2 = Math.PI * 2;
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {
        
        double zabs = Math.log(normImpl.computeWithoutRoot((Complex)object[1])) / log_bailout - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        
        return EscapeTimeAlg.getResult(object) + zabs + zarg;

    }
    
}
