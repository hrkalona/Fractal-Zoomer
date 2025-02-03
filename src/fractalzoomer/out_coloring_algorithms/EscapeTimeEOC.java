

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.utils.OutColorData;

public class EscapeTimeEOC extends EscapeTime {

    public EscapeTimeEOC() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(OutColorData data) {

        return data.escaped ? data.iterations + MAGNET_INCREMENT  : data.iterations;

    }
    
}
