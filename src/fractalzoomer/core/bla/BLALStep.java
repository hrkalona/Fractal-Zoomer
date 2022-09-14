package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLALStep extends BLAGenericStep {
    private int l;

    public BLALStep(double r2, Complex A, Complex B, int l) {
        super(r2, A, B);
        this.l = l;
    }

    @Override
    public int getL() {
        return l;
    }

}
