

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;


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
    public double getResult(OutColorData data) {

        double val = EscapeTimeAlg.getResult(data);
        return data.z.getIm() < 0 ? -(val + INCREMENT) : val;

    }

}
