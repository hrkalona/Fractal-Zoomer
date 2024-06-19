

package fractalzoomer.out_coloring_algorithms;

public class EscapeTimeEOC extends EscapeTime {

    public EscapeTimeEOC() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        return  (boolean)object[8] ? (int)object[0] + MAGNET_INCREMENT  : (int)object[0];

    }
    
}
