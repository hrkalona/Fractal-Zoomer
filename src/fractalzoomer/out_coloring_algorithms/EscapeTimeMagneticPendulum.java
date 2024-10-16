
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

        return (int)((Complex)object[9]).getRe() + getFractionalPart(object);

    }

    @Override
    public double getFractionalPart(Object[] object) {
        double val = ((Complex)object[9]).getRe();
        return val - (int)val;
    }
    
}
