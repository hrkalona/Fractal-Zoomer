package fractalzoomer.core.approximation.mip_la_zhuoran;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.functions.Fractal;

public abstract class MipLADeepStep {
    protected double Ax;
    protected double Ay;

    protected long Aexp;
    protected double ValidRadius, ValidRadiusC;
    protected long ValidRadiusExp, ValidRadiusCExp;

    public MipLADeepStep() {

    }

    public MipLADeepStep(MantExpComplex z, Fractal f) {
        MantExpComplex A = f.getBlaA(z);
        A.Normalize();
        Ax = A.getMantissaReal();
        Ay = A.getMantissaImag();
        Aexp = A.getExp();
        MantExp vr = z.chebyshevNorm().multiply(MipLAStep.ValidRadiusScale);
        vr.Normalize();
        ValidRadius = vr.getMantissa();
        ValidRadiusExp = vr.getExp();
        ValidRadiusC = 1;
        ValidRadiusCExp = Long.MAX_VALUE;
    }

    public abstract MipLADeepStep Composite(MipLADeepStep step);

    public abstract MantExpComplex getValue(MantExpComplex dz, MantExpComplex dc);

    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0);

    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return DeltaSubN.times(Aexp, Ax, Ay);
    }

    public abstract MantExpComplex getB();

    public MantExp getValidRadius() {
        return new MantExp(ValidRadiusExp, ValidRadius);
    }

    public MantExp getValidRadiusC() {
        return new MantExp(ValidRadiusCExp, ValidRadiusC);
    }
    //public abstract int getL();
}
