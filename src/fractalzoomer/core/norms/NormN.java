package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class NormN extends Norm {
    private double n;
    private double n_reciprocal;

    public NormN(double n) {
        super();
        this.n = n;
        n_reciprocal = 1 / n;
    }

    @Override
    public double getExp() {
        return n;
    }

    @Override
    public double getRootExp() {
        return n_reciprocal;
    }

    @Override
    public double computeWithRoot(Complex z) {
        return z.nnorm(n, n_reciprocal);
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return z.nnorm_without_root(n);
    }
}
