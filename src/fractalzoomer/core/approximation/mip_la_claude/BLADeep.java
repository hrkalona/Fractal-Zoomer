package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.core.numerics.MantExpComplex;

public class BLADeep {
    public double r2;
    public long r2exp;

    protected BLADeep() {
        r2 = MantExp.ZERO.getMantissa();
        r2exp = MantExp.ZERO.getExp();
    }

    protected BLADeep(MantExp r2) {
        this.r2 = r2.getMantissa();
        this.r2exp = r2.getExp();
    }

    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) { return  null; }

    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) { return  null; }

    public MantExpComplex getValue(MantExpComplex DeltaSubN) { return  null; }

    public MantExp hypotA() {
        return null;
    }

    public MantExp hypotB()  {
        return null;
    }

    public MantExp getR2() {
        return new MantExp(r2exp, r2);
    }

    public int getL() {
        return 0;
    }

    public MantExpComplex getA() {
        return null;
    }

    public MantExpComplex getB()  {
        return null;
    }

    // A = y.A * x.A
    public static MantExpComplex getNewA(BLADeep x, BLADeep y) {
        return y.getA().times_mutable(x.getA());
    }

    // B = y.A * x.B + y.B
    public static MantExpComplex getNewB(BLADeep x, BLADeep y) {
        return y.getA().times_mutable(x.getB()).plus_mutable(y.getB());
    }
}
