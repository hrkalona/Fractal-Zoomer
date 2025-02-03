package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.numerics.GenericComplex;
import fractalzoomer.core.numerics.MantExpComplex;

public class ATResult {
    public ATResult() {
        dzdc = MantExpComplex.create();
        dzdc2 = MantExpComplex.create();
    }

    public GenericComplex dz;

    public MantExpComplex dzdc;
    public MantExpComplex dzdc2;
    public int bla_iterations;
    public int bla_steps;
}
