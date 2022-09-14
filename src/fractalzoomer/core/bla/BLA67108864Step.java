package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA67108864Step extends BLAGenericStep {

    public BLA67108864Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 67108864;
    }

}
