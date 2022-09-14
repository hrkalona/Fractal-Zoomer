package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA131072Step extends BLAGenericStep {

    public BLA131072Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 131072;
    }

}
