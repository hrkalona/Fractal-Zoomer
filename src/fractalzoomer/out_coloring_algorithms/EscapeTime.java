

package fractalzoomer.out_coloring_algorithms;

public class EscapeTime extends OutColorAlgorithm {

    public EscapeTime() {
        super();
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        return (int)object[0];

    }

}
