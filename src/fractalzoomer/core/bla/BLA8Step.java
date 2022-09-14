package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA8Step extends BLAGenericStep {

    public BLA8Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 8;
    }

}
