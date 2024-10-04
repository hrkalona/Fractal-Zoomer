package fractalzoomer.core.approximation.mip_la_zhuoran;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class MipLADeepLStep extends MipLADeepStep {
    protected double Bx;
    protected double By;

    protected long Bexp;
    //protected int l;

    protected MipLADeepLStep() {

    }

    @Override
    public MipLADeepStep Composite(MipLADeepStep step) {
        MipLADeepLStep result = new MipLADeepLStep();

        MantExpComplex A = new MantExpComplex(Aexp, Ax, Ay);
        MantExpComplex B = new MantExpComplex(Bexp, Bx, By);

        MantExp validRadius = new MantExp(ValidRadiusExp, ValidRadius);
        MantExp stepValidRadius = new MantExp(step.ValidRadiusExp, step.ValidRadius);
        MantExp finalValidRadius = MantExp.min(validRadius, stepValidRadius.divide(A.chebyshevNorm()));

        result.ValidRadius = finalValidRadius.getMantissa();
        result.ValidRadiusExp = finalValidRadius.getExp();


        MantExp validRadiusC = new MantExp(ValidRadiusCExp, ValidRadiusC);
        MantExp stepValidRadiusC = new MantExp(step.ValidRadiusCExp, step.ValidRadiusC);

        MantExp finalValidRadiusC = MantExp.min(validRadiusC, MantExp.min(stepValidRadiusC, stepValidRadius.divide(B.chebyshevNorm())));

        result.ValidRadiusC = finalValidRadiusC.getMantissa();
        result.ValidRadiusCExp = finalValidRadiusC.getExp();

        MantExpComplex stepA = new MantExpComplex(step.Aexp, step.Ax, step.Ay);
        MantExpComplex stepB = step.getB();

        MantExpComplex resultA = A.times(stepA);
        resultA.Normalize();
        MantExpComplex resultB = B.times(stepA).plus_mutable(stepB);
        resultB.Normalize();

        result.Ax = resultA.getMantissaReal();
        result.Ay = resultA.getMantissaImag();
        result.Aexp = resultA.getExp();
        result.Bx = resultB.getMantissaReal();
        result.By = resultB.getMantissaImag();
        result.Bexp = resultB.getExp();
        //result.l = getL() + step.getL();

        return result;
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {
        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0.times(Bexp, Bx, By));
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {
        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(new MantExpComplex(Bexp, Bx, By).times(DeltaSub0));
    }

    @Override
    public MantExpComplex getB() {
        return new MantExpComplex(Bexp, Bx, By);
    }

//    @Override
//    public int getL() {
//        return l;
//    }
}
