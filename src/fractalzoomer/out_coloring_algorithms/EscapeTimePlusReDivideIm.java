

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusReDivideIm extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;

    public EscapeTimePlusReDivideIm(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        return Math.abs(EscapeTimeAlg.getResult(object) + ((Complex)object[1]).getRe() / ((Complex)object[1]).getIm());

    }

}
