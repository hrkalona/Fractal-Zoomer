package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;

public class BLADeepFull1Step extends BLADeepFull {
    //This class Assumes that B is 1 + 0i
    public static final MantExpComplex B = MantExpComplexFull.ONE;

    protected BLADeepFull1Step(MantExp r2, MantExpComplex A) {
        super(r2, A);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(DeltaSub0);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(DeltaSub0);
    }

    @Override
    public MantExp hypotB() {
        return new MantExp(MantExp.ONE);
    }

    @Override
    public int getL() {
        return 1;
    }

    @Override
    public MantExpComplex getB() {
        return MantExpComplex.copy(B);
    }
}
