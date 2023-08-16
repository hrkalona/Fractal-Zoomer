package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;

public class BLADeepFullLStep extends BLADeepFull {
    public double Bx;
    public double By;
    public long Bexp;
    public long BexpIm;

    protected BLADeepFullLStep(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A);
        this.Bx = B.getMantissaReal();
        this.By = B.getMantissaImag();
        this.Bexp = B.getExp();
        this.BexpIm = B.getExpImag();
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(DeltaSub0.times(new MantExpComplexFull(Bexp, BexpIm, Bx, By)));

    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {

        //double zxn = Ax * zx - Ay * zy + Bx * cx - By * cy;
        //double zyn = Ax * zy + Ay * zx + Bx * cy + By * cx;

        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(new MantExpComplexFull(Bexp, BexpIm, Bx, By).times(DeltaSub0));

    }

    @Override
    public MantExp hypotB() {
        return new MantExpComplexFull(Bexp, BexpIm, Bx, By).hypot();
    }

    @Override
    public MantExpComplex getB() {
        return new MantExpComplexFull(Bexp, BexpIm, Bx, By);
    }

}
