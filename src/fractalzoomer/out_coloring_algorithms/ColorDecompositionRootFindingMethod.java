

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;


/**
 *
 * @author hrkalona2
 */
public class ColorDecompositionRootFindingMethod extends ColorDecomposition {
    protected OutColorAlgorithm EscapeTimeAlg;
    
    public ColorDecompositionRootFindingMethod(OutColorAlgorithm EscapeTimeAlg) {
        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
    }

    @Override
    public double getResult(Object[] object) {

        double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;
        
        return Math.abs((long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5));

    }
    
    @Override
    public double getResult3D(Object[] object, double result) {
  
        return EscapeTimeAlg.getResult(object);

    }
    
}
