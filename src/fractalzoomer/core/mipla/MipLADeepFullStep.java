package fractalzoomer.core.mipla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;
import fractalzoomer.functions.Fractal;

import static fractalzoomer.core.mipla.MipLAStep.ValidRadiusScale;

public abstract class MipLADeepFullStep extends MipLADeepStep{
    public long AexpIm;

    public MipLADeepFullStep() {

    }

    public MipLADeepFullStep(MantExpComplex z, Fractal f) {
        super();
        MantExpComplex A = f.getBlaA(z);
        A.Normalize();
        Ax = A.getMantissaReal();
        Ay = A.getMantissaImag();
        Aexp = A.getExp();
        AexpIm = A.getExpImag();
        MantExp vr = z.chebyshevNorm().multiply(ValidRadiusScale);
        vr.Normalize();
        ValidRadius = vr.getMantissa();
        ValidRadiusExp = vr.getExp();
        ValidRadiusC = 1;
        ValidRadiusCExp = Long.MAX_VALUE;
    }

    @Override
    public abstract MipLADeepStep Composite(MipLADeepStep step);

    @Override
    public abstract MantExpComplex getValue(MantExpComplex dz, MantExpComplex dc);

    @Override
    public abstract MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0);

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN) {
        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay));
    }

    @Override
    public abstract MantExpComplex getB();
}
