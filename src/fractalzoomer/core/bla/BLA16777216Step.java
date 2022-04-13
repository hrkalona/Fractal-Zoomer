package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA16777216Step extends BLAGenericStep {

    public BLA16777216Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 16777216;
    }

}
