package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.OutColorData;

public class EscapeTimeSquares extends OutColorAlgorithm {
    private OutColorAlgorithm EscapeTimeAlg;

    private double factor;

    public EscapeTimeSquares(OutColorAlgorithm EscapeTimeAlg) {

        super();
        OutUsingIncrement = false;
        this.EscapeTimeAlg = EscapeTimeAlg;
        factor = 50;

    }

    @Override
    public double getResult(OutColorData data) {

        Complex c = data.c0;
        double x = c.getRe();
        double y = c.getIm();
        return EscapeTimeAlg.getResult(data) + Math.abs(Math.cos(x * factor)) * Math.abs(Math.sin(y * factor)) * 20;

    }
}
