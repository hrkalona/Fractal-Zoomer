package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA65536Step extends BLAGenericStep {

    public BLA65536Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 65536;
    }

}
