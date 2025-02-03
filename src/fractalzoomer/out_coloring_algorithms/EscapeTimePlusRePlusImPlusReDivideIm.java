

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;


/**
 *
 * @author hrkalona
 */
public class EscapeTimePlusRePlusImPlusReDivideIm extends OutColorAlgorithm {
    protected OutColorAlgorithm EscapeTimeAlg;

    public EscapeTimePlusRePlusImPlusReDivideIm(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        
    }

    @Override
    public double getResult(OutColorData data) {

        Complex z = data.z;
        double temp = z.getRe();
        double temp2 = z.getIm();
        
        return Math.abs(EscapeTimeAlg.getResult(data) + temp + temp2 + temp / temp2);

    }

}
