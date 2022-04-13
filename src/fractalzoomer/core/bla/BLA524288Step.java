package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA524288Step extends BLAGenericStep {

    public BLA524288Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 524288;
    }

}
