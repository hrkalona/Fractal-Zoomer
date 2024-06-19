

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class BinaryDecomposition2 extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;

    public BinaryDecomposition2(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(Object[] object) {

        double val = EscapeTimeAlg.getResult(object);
        return ((Complex)object[1]).getRe() < 0 ? -(val + INCREMENT) : val;

    }
    
}
