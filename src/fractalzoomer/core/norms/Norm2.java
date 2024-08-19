package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class Norm2 extends Norm {

    public Norm2() {
        super();
    }

    @Override
    public double getExp() {
        return 2;
    }

    @Override
    public double computeWithRoot(Complex z) {
        return z.norm();
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return z.norm_squared();
    }
}
