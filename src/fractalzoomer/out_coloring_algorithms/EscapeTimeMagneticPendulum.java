
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeMagneticPendulum extends OutColorAlgorithm {

    public EscapeTimeMagneticPendulum() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        return ((Complex)object[9]).getRe();

    }
    
}
