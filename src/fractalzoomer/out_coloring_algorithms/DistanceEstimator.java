

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

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
    public double getResult(Object[] object) {

         double temp2 = (((Complex)object[1]).norm_squared());
         double temp3 = Math.log(temp2);      
         double temp = -2.0 * Math.log(temp3 * temp3 * temp2 / ((Complex)object[2]).norm_squared());
         
         return temp < 0 ? 0 : temp;

    }
    
}
