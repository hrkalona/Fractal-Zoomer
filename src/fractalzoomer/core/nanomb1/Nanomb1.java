package fractalzoomer.core.nanomb1;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class Nanomb1 {
    public biPoly SSA;
    public MantExpComplex nucleusPos;
    public MantExp Bout;

    public Nanomb1(biPoly SSA, MantExpComplex nucleusPos, MantExp Bout) {
        this.SSA = SSA;
        this.nucleusPos = nucleusPos;
        this.Bout = Bout;
    }

}
