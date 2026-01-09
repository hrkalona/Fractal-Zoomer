

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.utils.InColorData;

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
    public double getResult(InColorData data) {

        return max_iterations + data.z.norm_squared() * (max_iterations / 3.0);

    }

}
