package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class NormP extends Norm {
    private double p;
    private double p_reciprocal;

    public NormP(double p) {
        super();
        this.p = p;
        p_reciprocal = 1/ p;
    }

    @Override
    public double getExp() {
        return p;
    }

    @Override
    public double computeWithRoot(Complex z) {
        return z.nnorm(p, p_reciprocal);
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return z.nnorm_without_root(p);
    }
}
