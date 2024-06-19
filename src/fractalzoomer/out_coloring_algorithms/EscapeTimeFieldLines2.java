
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeFieldLines2 extends OutColorAlgorithm {

    protected double log_bailout;
    protected OutColorAlgorithm EscapeTimeAlg;
    private Norm normImpl;

    public EscapeTimeFieldLines2(double bailout, OutColorAlgorithm EscapeTimeAlg, Norm normImpl) {

        super();
        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {

        double lineWidth = 0.07;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = Math.log(normImpl.computeWithoutRoot((Complex)object[1])) / log_bailout;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) < (1.0 - lineWidth)*fz && lineWidth * fz < Math.abs(fx);
        double result = EscapeTimeAlg.getResult(object);
                
        return line ? result : -(result + INCREMENT);

    }
    
}
