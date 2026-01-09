

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;


/**
 *
 * @author hrkalona2
 */
public class ColorDecomposition extends OutColorAlgorithm {
  protected double pi2;
  protected double pi59;
    
    public ColorDecomposition() {
        super();
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        OutUsingIncrement = false;
        
    }

    @Override
    public double getResult(OutColorData data) {

        return Math.abs((data.z.arg() / (pi2)  + 0.75) * pi59);
        
    }
    
}
