
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

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
    public double getResult(OutColorData data) {

        return (int)(data.pendulumLen.getRe()) + getFractionalPart(data);

    }

    @Override
    public double getFractionalPart(OutColorData data) {
        double val = data.pendulumLen.getRe();
        return val - (long)val;
    }
    
}
