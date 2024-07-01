package fractalzoomer.core.norms;

import fractalzoomer.core.Complex;

public abstract class Norm {

    public abstract double getExp();
    public abstract double computeWithRoot(Complex z);
    public abstract double computeWithoutRoot(Complex z);
}
