

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class Banded extends OutColorAlgorithm {

    public Banded() {
        super();
        OutUsingIncrement = false;
    }
    
    @Override
    public double getResult(OutColorData data) {
        
        double temp = Math.log(data.z.norm_squared());
        
        temp = temp <= 0 ? 1e-33 : temp;
        
        double f = (Math.log(temp) / Math.log(2)) * 2.4;

        return data.iterations + Math.abs(f);

    }
    
}
