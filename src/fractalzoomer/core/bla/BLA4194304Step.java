package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA4194304Step extends BLAGenericStep {

    public BLA4194304Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 4194304;
    }

}
