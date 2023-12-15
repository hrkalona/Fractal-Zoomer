package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;

public class LAstep {
    public int step;
    public int nextStageLAindex;
    protected GenericLAInfo LAj;
    protected Complex Refp1;
    protected MantExpComplex Refp1Deep;

    public Complex newDz;
    public MantExpComplex newDzDeep;
    public boolean unusable;

    public double newdre;
    public double newdim;

    public Complex Evaluate(Complex DeltaSub0) {
        return LAj.Evaluate(newDz, DeltaSub0);
    }

    public Complex EvaluateWithZeroD0(Complex DeltaSub0) {
        return LAj.Evaluate(newDz);
    }

    public Complex EvaluateWithZeroD0(double d0re, double d0im) {
        return LAj.Evaluate(newdre, newdim);
    }

    public Complex Evaluate(double d0re, double d0im) {
        return LAj.Evaluate(newdre, newdim, d0re, d0im);
    }

    public Complex getZ(Complex DeltaSubN) { return Refp1.plus(DeltaSubN);}

    public Complex getRefp1() {
        return Refp1;
    }

    public MantExpComplex Evaluate(MantExpComplex DeltaSub0) {
        return LAj.Evaluate(newDzDeep, DeltaSub0);
    }

    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex EvaluateDzdcDeep(MantExpComplex z, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2Deep(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex getZ(MantExpComplex DeltaSubN) { return Refp1Deep.plus(DeltaSubN);}

}
