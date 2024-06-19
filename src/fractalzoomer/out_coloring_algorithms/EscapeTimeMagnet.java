

package fractalzoomer.out_coloring_algorithms;

public class EscapeTimeMagnet extends EscapeTime {
    
    public EscapeTimeMagnet() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        return  (boolean)object[2] ? (int)object[0] + MAGNET_INCREMENT  : (int)object[0];

    }
    
}
