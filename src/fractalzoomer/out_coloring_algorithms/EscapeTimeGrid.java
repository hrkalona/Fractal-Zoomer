

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGrid extends OutColorAlgorithm {
  protected double log_bailout;
  protected double pi2;
  protected OutColorAlgorithm EscapeTimeAlg;
  private boolean abs;
  private Norm normImpl;
    
    public EscapeTimeGrid(double bailout, OutColorAlgorithm EscapeTimeAlg, boolean abs, Norm normImpl) {

        super();

        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }

        pi2 = Math.PI * 2;
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        this.abs = abs;
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {

        double zabs = Math.log(normImpl.computeWithoutRoot((Complex)object[1])) / log_bailout - 1.0f;

        if(abs) {
            zabs = Math.abs(zabs);
        }

        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        boolean grid;
        if(EscapeTimeAlg.smooth) {
            double k = Math.pow(0.5, 0.5 - zabs);
            double grid_weight = 0.05;
            grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);
        }
        else {
            grid = 0.05 < zabs && zabs < 0.95 && 0.05 < zarg && zarg < 0.95;
        }

        double result = EscapeTimeAlg.getResult(object);
        return grid ? result : -(result + INCREMENT);

    }
    
}
