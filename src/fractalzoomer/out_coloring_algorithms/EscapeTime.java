

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

public class EscapeTime extends OutColorAlgorithm {

    public EscapeTime() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(OutColorData data) {

        return data.iterations;

    }

}
