package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

public class EscapeTimeSquares extends OutColorAlgorithm {
    private OutColorAlgorithm EscapeTimeAlg;
    private int c_index;

    private double factor;

    public EscapeTimeSquares(int c_index, OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = false;
        this.c_index = c_index;
        this.EscapeTimeAlg = EscapeTimeAlg;
        factor = 50;

    }

    @Override
    public double getResult(Object[] object) {

        Complex c = ((Complex)object[c_index]);
        double x = c.getRe();
        double y = c.getIm();
        return EscapeTimeAlg.getResult(object) + Math.abs(Math.cos(x * factor)) * Math.abs(Math.sin(y * factor)) * 20;

    }
}
