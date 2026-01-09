

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;


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
    public double getResult(OutColorData data) {

        Complex z = data.z;
        double temp = Math.floor(1000 * z.getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * z.getIm() + 0.5) / 1000;
        
        return Math.abs((long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5));

    }
    
    @Override
    public double getResult3D(OutColorData data, double result) {
  
        return EscapeTimeAlg.getResult(data);

    }
    
}
