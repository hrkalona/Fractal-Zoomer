package fractalzoomer.core.la;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExpComplex;

public class ATResult {
    public ATResult() {
        dzdc = new MantExpComplex();
        dzdc2 = new MantExpComplex();
    }

    public GenericComplex dz;

    public MantExpComplex dzdc;
    public MantExpComplex dzdc2;
    public int bla_iterations;
    public int bla_steps;
}
