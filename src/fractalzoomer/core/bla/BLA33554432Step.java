package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA33554432Step extends BLAGenericStep {

    public BLA33554432Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 33554432;
    }

}
