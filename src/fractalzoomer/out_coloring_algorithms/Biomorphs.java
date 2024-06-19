

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona
 */
public class Biomorphs extends OutColorAlgorithm {
  protected double bailout;
  protected OutColorAlgorithm EscapeTimeAlg;

    public Biomorphs(double bailout, OutColorAlgorithm EscapeTimeAlg) {

        super();
        this.bailout = bailout;
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;

    }

    @Override
    public double getResult(Object[] object) {

        double result = EscapeTimeAlg.getResult(object);
        double temp = ((Complex)object[1]).getRe();
        double temp2 = ((Complex)object[1]).getIm();
        return temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ?  result : -(result + INCREMENT);

    }

}
