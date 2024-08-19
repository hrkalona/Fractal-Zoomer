

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

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
    public double getResult(Object[] object) {
        
        double temp = Math.log(((Complex)object[1]).norm_squared());
        
        temp = temp <= 0 ? 1e-33 : temp;
        
        double f = (Math.log(temp) / Math.log(2)) * 2.4;

        return (int)object[0] + Math.abs(f);

    }
    
}
