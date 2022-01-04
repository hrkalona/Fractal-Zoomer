package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep1Step extends BLADeep {
    //This class Assumes that B is 1 + 0i
    public static MantExpComplex B = new MantExpComplex(MantExp.ONE, MantExp.ZERO);

    public BLADeep1Step(MantExp r2, MantExpComplex A) {
        super(r2, A);
    }

    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return MantExpComplex.AtXpY(new MantExpComplex(Aexp, Ax, Ay), DeltaSubN, DeltaSub0);
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
        return new MantExpComplex(B);
    }
}
