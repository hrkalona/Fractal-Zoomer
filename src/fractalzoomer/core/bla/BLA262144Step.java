package fractalzoomer.core.bla;

import fractalzoomer.core.Complex;

public class BLA262144Step extends BLAGenericStep {

    public BLA262144Step(double r2, Complex A, Complex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 262144;
    }

}