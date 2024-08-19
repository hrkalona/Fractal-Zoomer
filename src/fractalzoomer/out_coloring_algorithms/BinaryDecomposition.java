

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona
 */
public class BinaryDecomposition extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;

    public BinaryDecomposition(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(Object[] object) {

        double val = EscapeTimeAlg.getResult(object);
        return ((Complex)object[1]).getIm() < 0 ? -(val + INCREMENT) : val;

    }

}
