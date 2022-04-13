package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public abstract class BLADeep {
    public double r2, Ax, Ay;
    public long Aexp, r2exp;

    public BLADeep(MantExp r2, MantExpComplex A) {
        this.r2 = r2.getMantissa();
        this.r2exp = r2.getExp();
        this.Ax = A.getMantissaReal();
        this.Ay = A.getMantissaImag();
        this.Aexp = A.getExp();
    }

    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0);

    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0);

    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return MantExpComplex.AtX(new MantExpComplex(Aexp, Ax, Ay), DeltaSubN);
    }

    public MantExp hypotA() {
        return MantExp.hypot(Ax, Ay, Aexp);
    }

    public abstract MantExp hypotB();

    public MantExp getR2() {
        return new MantExp(r2exp, r2);
    }

    public abstract int getL();

    public MantExpComplex getA() {
        return new MantExpComplex(Aexp, Ax, Ay);
    }

    public abstract MantExpComplex getB();

    // A = y.A * x.A
    public static MantExpComplex getNewA(BLADeep x, BLADeep y) {
        return new MantExpComplex(y.Aexp, y.Ax, y.Ay).times_mutable(new MantExpComplex(x.Aexp, x.Ax, x.Ay));
    }

    // B = y.A * x.B + y.B
    public static MantExpComplex getNewB(BLADeep x, BLADeep y) {
        return new MantExpComplex(y.Aexp, y.Ax, y.Ay).times_mutable(x.getB()).plus_mutable(y.getB());
    }
}
