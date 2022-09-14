package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA4Step extends BLAGenericStep {

    public BLA4Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 4;
    }

}
