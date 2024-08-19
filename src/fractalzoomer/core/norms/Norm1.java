package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class Norm1 extends Norm {

    public Norm1() {
        super();
    }

    @Override
    public double getExp() {
        return 1;
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return z.getAbsRe() + z.getAbsIm();
    }

    @Override
    public double computeWithRoot(Complex z) {
        return z.getAbsRe() + z.getAbsIm();
    }

}
