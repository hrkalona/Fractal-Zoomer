

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;


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
    public double getResult(OutColorData data) {

        double result = EscapeTimeAlg.getResult(data);
        Complex z = data.z;
        double temp = z.re;
        double temp2 = z.im;
        return temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ?  result : -(result + INCREMENT);

    }

}
