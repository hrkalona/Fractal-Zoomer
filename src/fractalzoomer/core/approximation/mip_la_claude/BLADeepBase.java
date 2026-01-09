package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.core.numerics.MantExpComplex;

public abstract class BLADeepBase extends BLADeep {
    public double Ax;
    public double Ay;
    public long Aexp;

    protected BLADeepBase(MantExp r2, MantExpComplex A) {
        super(r2);
        this.Ax = A.getMantissaReal();
        this.Ay = A.getMantissaImag();
        this.Aexp = A.getExp();
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return DeltaSubN.times(Aexp, Ax, Ay);
    }

    @Override
    public MantExp hypotA() {
        return new MantExpComplex(Aexp, Ax, Ay).hypot();
    }

    @Override
    public MantExpComplex getA() {
        return new MantExpComplex(Aexp, Ax, Ay);
    }
}
