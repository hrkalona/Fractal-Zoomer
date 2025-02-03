

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

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
    public double getResult(OutColorData data) {

        Complex z = data.z;
        return Math.abs(EscapeTimeAlg.getResult(data) + z.getRe() / z.getIm());

    }

}
