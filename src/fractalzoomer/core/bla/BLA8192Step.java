package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA8192Step extends BLAGenericStep {

    public BLA8192Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 8192;
    }

}
