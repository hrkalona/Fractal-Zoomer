package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA64Step extends BLAGenericStep {

    public BLA64Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 64;
    }

}
