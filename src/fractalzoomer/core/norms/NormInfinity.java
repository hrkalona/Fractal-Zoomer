package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class NormInfinity extends Norm {

    public NormInfinity() {
        super();
    }

    @Override
    public double getExp() {
        return 1;
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return Math.max(z.getAbsRe(), z.getAbsIm());
    }

    @Override
    public double computeWithRoot(Complex z) {
        return Math.max(z.getAbsRe(), z.getAbsIm());
    }
}
