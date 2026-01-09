

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;


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
    public double getResult(OutColorData data) {

        double val = EscapeTimeAlg.getResult(data);
        return data.z.getRe() < 0 ? -(val + INCREMENT) : val;

    }
    
}
