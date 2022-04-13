package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA32768Step extends BLAGenericStep {

    public BLA32768Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 32768;
    }

}
