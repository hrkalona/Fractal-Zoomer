package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;

public abstract class BLADeepFull extends BLADeep {
    public long AexpIm;
    protected BLADeepFull(MantExp r2, MantExpComplex A) {
        super(r2, A);
        this.AexpIm = A.getExpImag();
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay));
    }

    @Override
    public MantExp hypotA() {
        return new MantExpComplexFull(Aexp, AexpIm, Ax, Ay).hypot();
    }

    @Override
    public MantExpComplex getA() {
        return new MantExpComplexFull(Aexp, AexpIm, Ax, Ay);
    }
}
