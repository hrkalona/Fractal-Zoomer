package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA1048576Step extends BLAGenericStep {

    public BLA1048576Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 1048576;
    }

}
