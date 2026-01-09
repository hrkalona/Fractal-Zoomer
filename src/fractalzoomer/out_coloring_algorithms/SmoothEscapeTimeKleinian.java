

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

public class SmoothEscapeTimeKleinian extends OutColorAlgorithm {
    private double u;

    public SmoothEscapeTimeKleinian(double u) {

        super();
        this.u = u * 0.5;
        OutUsingIncrement = false;
        smooth = true;

    }

    @Override
    public double getResult(OutColorData data) {

        return data.iterations + getFractionalPart(data);

    }

    @Override
    public double getFractionalPart(OutColorData data) {
        return Math.log((data.z).sub_i(u).norm());
    }
}
