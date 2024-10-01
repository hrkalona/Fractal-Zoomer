package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskRender;

public class BLADeep1Step extends BLADeep {
    //This class Assumes that B is 1 + 0i
    public static final MantExpComplex B = MantExpComplex.ONE;

    public static BLADeep create(MantExp r2, MantExpComplex A) {
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new BLADeepFull1Step(r2, A);
        }
        return new BLADeep1Step(r2, A);
    }

    private BLADeep1Step(MantExp r2, MantExpComplex A) {
        super(r2, A);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;
        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0);
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
