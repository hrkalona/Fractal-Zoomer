

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class ZMag extends InColorAlgorithm {
    private int max_iterations;

    public ZMag(int max_iterations) {

        super();
        this.max_iterations = max_iterations;
        InUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        return max_iterations + ((Complex)object[0]).norm_squared() * (max_iterations / 3.0);

    }

}
