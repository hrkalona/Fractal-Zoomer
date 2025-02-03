

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusRe extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;

    public EscapeTimePlusRe(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;

    }

    @Override
    public double getResult(OutColorData data) {
        
        return Math.abs(EscapeTimeAlg.getResult(data) + data.z.getRe());

    }
    
}
