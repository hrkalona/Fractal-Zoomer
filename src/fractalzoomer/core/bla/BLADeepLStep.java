package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeepLStep extends BLADeep {
    public double Bx, By;
    public long Bexp;
    public int l;

    public BLADeepLStep(MantExp r2, MantExpComplex A, MantExpComplex B, int l) {
        super(r2, A);
        this.Bx = B.getMantissaReal();
        this.By = B.getMantissaImag();
        this.Bexp = B.getExp();
        this.l = l;
    }

    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return MantExpComplex.AtXpBtY(new MantExpComplex(Aexp, Ax, Ay), DeltaSubN, new MantExpComplex(Bexp, Bx, By), DeltaSub0);

    }

    @Override
    public MantExp hypotB() {
        return MantExp.hypot(Bx, By, Bexp);
    }

    @Override
    public int getL() {
        return l;
    }

    @Override
    public MantExpComplex getB() {
        return new MantExpComplex(Bexp, Bx, By);
    }
}
