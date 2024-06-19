
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeFieldLines extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;
    protected double log_bailout;
    protected double pi2;
    private Norm normImpl;

    public EscapeTimeFieldLines(double bailout, OutColorAlgorithm EscapeTimeAlg, Norm normImpl) {

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
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {

        double lineWidth = 0.008;  // freely adjustable
        double fx = ((Complex)object[1]).arg() / (pi2);  // angle within cell
        double fy = Math.log(normImpl.computeWithoutRoot((Complex)object[1])) / log_bailout;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) > lineWidth * fz;

        double result = EscapeTimeAlg.getResult(object);
                
        return line ? result : -(result + INCREMENT);

    }
}
