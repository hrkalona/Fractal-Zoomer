package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;

public class LAstep {
    public int step;
    public int nextStageLAindex;
    protected LAInfo LAj;
    protected LAInfoDeep LAjdeep;
    protected Complex Refp1;
    protected MantExpComplex Refp1Deep;

    protected Complex newDz;
    protected MantExpComplex newDzDeep;
    public boolean unusable;

    public Complex Evaluate(Complex DeltaSub0) {
        return LAj.Evaluate(newDz, DeltaSub0);
    }

    public Complex getZ(Complex DeltaSubN) { return Refp1.plus(DeltaSubN);}

    public MantExpComplex Evaluate(MantExpComplex DeltaSub0) {
        return LAjdeep.Evaluate(newDzDeep, DeltaSub0);
    }

    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex EvaluateDzdcDeep(MantExpComplex z, MantExpComplex dzdc) {
        return LAjdeep.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2Deep(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAjdeep.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex getZ(MantExpComplex DeltaSubN) { return Refp1Deep.plus(DeltaSubN);}

}
