package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public abstract class BLADeep {
    public double r2;
    public double Ax;
    public double Ay;
    public long Aexp;
    public long r2exp;

    protected BLADeep(MantExp r2, MantExpComplex A) {
        this.r2 = r2.getMantissa();
        this.r2exp = r2.getExp();
        this.Ax = A.getMantissaReal();
        this.Ay = A.getMantissaImag();
        this.Aexp = A.getExp();
    }

    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0);

    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0);

    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return DeltaSubN.times(Aexp, Ax, Ay);
    }

    public MantExp hypotA() {
        return new MantExpComplex(Aexp, Ax, Ay).hypot();
    }

    public abstract MantExp hypotB();

    public MantExp getR2() {
        return new MantExp(r2exp, r2);
    }

    public int getL() {
        return 0;
    }

    public MantExpComplex getA() {
        return new MantExpComplex(Aexp, Ax, Ay);
    }

    public abstract MantExpComplex getB();

    // A = y.A * x.A
    public static MantExpComplex getNewA(BLADeep x, BLADeep y) {
        return y.getA().times_mutable(x.getA());
    }

    // B = y.A * x.B + y.B
    public static MantExpComplex getNewB(BLADeep x, BLADeep y) {
        return y.getA().times_mutable(x.getB()).plus_mutable(y.getB());
    }
}
