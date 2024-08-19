

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeColorDecompositionRootFindingMethod extends EscapeTimeColorDecomposition {
    
    public EscapeTimeColorDecompositionRootFindingMethod(OutColorAlgorithm EscapeTimeAlg) {
        super(EscapeTimeAlg);
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;

        return Math.abs(EscapeTimeAlg.getResult(object) + (long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5));

    }
    
    @Override
    public double getResult3D(Object[] object, double result) {
        
        return EscapeTimeAlg.getResult(object);
        
    }
    
}
