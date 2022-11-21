package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public abstract class BLADeepGenericStep extends BLADeep {

    public double Bx;
    public double By;
    public long Bexp;

    protected BLADeepGenericStep(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A);
        this.Bx = B.getMantissaReal();
        this.By = B.getMantissaImag();
        this.Bexp = B.getExp();
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return MantExpComplex.AtXpBtY(new MantExpComplex(Aexp, Ax, Ay), DeltaSubN, new MantExpComplex(Bexp, Bx, By), DeltaSub0);

    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return MantExpComplex.AtXpBtY(new MantExpComplex(Aexp, Ax, Ay), DeltaSubN, new MantExpComplex(Bexp, Bx, By), DeltaSub0);

    }

    @Override
    public MantExp hypotB() {
        return MantExpComplex.hypot(Bx, By, Bexp);
    }

    @Override
    public MantExpComplex getB() {
        return new MantExpComplex(Bexp, Bx, By);
    }

    public static BLADeepGenericStep getGenericStep(MantExp r2, MantExpComplex A, MantExpComplex B, int l) {

        if(!BLAS.MEMORY_PACKING) {
            return new BLADeepLStep(r2, A, B, l);
        }

        switch (l) {
            case 2:
                return new BLADeep2Step(r2, A, B);
            case 4:
                return new BLADeep4Step(r2, A, B);
            case 8:
                return new BLADeep8Step(r2, A, B);
            case 16:
                return new BLADeep16Step(r2, A, B);
            case 32:
                return new BLADeep32Step(r2, A, B);
            case 64:
                return new BLADeep64Step(r2, A, B);
            case 128:
                return new BLADeep128Step(r2, A, B);
            case 256:
                return new BLADeep256Step(r2, A, B);
            case 512:
                return new BLADeep512Step(r2, A, B);
            case 1024:
                return new BLADeep1024Step(r2, A, B);
            case 2048:
                return new BLADeep2048Step(r2, A, B);
            case 4096:
                return new BLADeep4096Step(r2, A, B);
            case 8192:
                return new BLADeep8192Step(r2, A, B);
            case 16384:
                return new BLADeep16384Step(r2, A, B);
            case 32768:
                return new BLADeep32768Step(r2, A, B);
            case 65536:
                return new BLADeep65536Step(r2, A, B);
            case 131072:
                return new BLADeep131072Step(r2, A, B);
            case 262144:
                return new BLADeep262144Step(r2, A, B);
            case 524288:
                return new BLADeep524288Step(r2, A, B);
            case 1048576:
                return new BLADeep1048576Step(r2, A, B);
            case 2097152:
                return new BLADeep2097152Step(r2, A, B);
            case 4194304:
                return new BLADeep4194304Step(r2, A, B);
            case 8388608:
                return new BLADeep8388608Step(r2, A, B);
            case 16777216:
                return new BLADeep16777216Step(r2, A, B);
            case 33554432:
                return new BLADeep33554432Step(r2, A, B);
            case 67108864:
                return new BLADeep67108864Step(r2, A, B);
            case 134217728:
                return new BLADeep134217728Step(r2, A, B);
            case 268435456:
                return new BLADeep268435456Step(r2, A, B);
            case 536870912:
                return new BLADeep536870912Step(r2, A, B);
            case 1073741824:
                return new BLADeep1073741824Step(r2, A, B);
            default:
                return new BLADeepLStep(r2, A, B, l);
        }
    }
}
