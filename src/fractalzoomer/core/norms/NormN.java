package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public class NormN extends Norm {
    private double n;
    private double n_reciprocal;

    private double a;
    private double b;

    public NormN(double n, double a, double b) {
        super();
        this.n = n;
        this.a = a;
        this.b = b;
        n_reciprocal = 1 / n;
    }

    @Override
    public double getExp() {
        return n;
    }

    @Override
    public double computeWithRoot(Complex z) {
        return z.nnorm(n, a, b, n_reciprocal);
    }

    @Override
    public double computeWithoutRoot(Complex z) {
        return z.nnorm_without_root(n, a, b);
    }
}
