

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

public class SmoothEscapeTimeKleinian extends OutColorAlgorithm {
    private double u;

    public SmoothEscapeTimeKleinian(double u) {

        super();
        this.u = u * 0.5;
        OutUsingIncrement = false;
        smooth = true;

    }

    @Override
    public double getResult(Object[] object) {

        return (int)object[0] + 1 + Math.log(((Complex)object[1]).sub_i(u).norm());

    }
}
