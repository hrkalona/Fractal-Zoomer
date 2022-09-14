package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA256Step extends BLAGenericStep {

    public BLA256Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 256;
    }

}
