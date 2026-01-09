

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class DistanceEstimator  extends OutColorAlgorithm {
    
    public DistanceEstimator() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(OutColorData data) {

         double temp2 = data.z.norm_squared();
         double temp3 = Math.log(temp2);      
         double temp = -2.0 * Math.log(temp3 * temp3 * temp2 / data.dc.norm_squared());
         
         return temp < 0 ? 0 : temp;

    }
    
}
