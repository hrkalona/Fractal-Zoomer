package fractalzoomer.core.approximation.mip_la_claude;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskRender;

public class BLADeepLStep extends BLADeep {
    public double Bx;
    public double By;
    public long Bexp;
    public int l;

    public static BLADeep create(MantExp r2, MantExpComplex A, MantExpComplex B, int l) {
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new BLADeepFullLStep(r2, A, B, l);
        }
        return new BLADeepLStep(r2, A, B, l);
    }

    private BLADeepLStep(MantExp r2, MantExpComplex A, MantExpComplex B, int l) {
        super(r2, A);
        this.Bx = B.getMantissaReal();
        this.By = B.getMantissaImag();
        this.Bexp = B.getExp();
        this.l = l;
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0.times(Bexp, Bx, By));

    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(new MantExpComplex(Bexp, Bx, By).times(DeltaSub0));
    }

    @Override
    public MantExp hypotB() {
        return new MantExpComplex(Bexp, Bx, By).hypot();
    }

    @Override
    public MantExpComplex getB() {
        return new MantExpComplex(Bexp, Bx, By);
    }

    @Override
    public int getL() {
        return l;
    }

}
