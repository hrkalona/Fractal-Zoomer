package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA2048Step extends BLAGenericStep {

    public BLA2048Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 2048;
    }

}
