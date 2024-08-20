package fractalzoomer.core.mipla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;
import fractalzoomer.core.TaskRender;
import fractalzoomer.functions.Fractal;

public class MipLADeepFull1Step extends MipLADeepFullStep {
    public static MantExpComplex B = MantExpComplexFull.ONE;
    protected MipLADeepFull1Step(MantExpComplex z, Fractal f) {
        super(z, f);
    }

    public static MipLADeepStep create(MantExpComplex z, Fractal f) {
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new MipLADeepFull1Step(z, f);
        }
        return new MipLADeepFull1Step(z, f);
    }

    @Override
    public MipLADeepStep Composite(MipLADeepStep step1) {

        MipLADeepFullStep step = (MipLADeepFullStep) step1;

        MipLADeepFullLStep result = new MipLADeepFullLStep();

        MantExpComplexFull A = new MantExpComplexFull(Aexp, AexpIm, Ax, Ay);

        MantExp validRadius = new MantExp(ValidRadiusExp, ValidRadius);
        MantExp stepValidRadius = new MantExp(step.ValidRadiusExp, step.ValidRadius);
        MantExp finalValidRadius = MantExp.min(validRadius, stepValidRadius.divide(A.chebyshevNorm()));

        result.ValidRadius = finalValidRadius.getMantissa();
        result.ValidRadiusExp = finalValidRadius.getExp();


        MantExp validRadiusC = new MantExp(ValidRadiusCExp, ValidRadiusC);
        MantExp stepValidRadiusC = new MantExp(step.ValidRadiusCExp, step.ValidRadiusC);
        MantExp finalValidRadiusC = MantExp.min(validRadiusC, MantExp.min(stepValidRadiusC, stepValidRadius));

        result.ValidRadiusC = finalValidRadiusC.getMantissa();
        result.ValidRadiusCExp = finalValidRadiusC.getExp();

        MantExpComplexFull stepA = new MantExpComplexFull(step.Aexp, step.AexpIm, step.Ax, step.Ay);
        MantExpComplexFull stepB = (MantExpComplexFull)step.getB();

        MantExpComplexFull resultA = A.times(stepA);
        resultA.Normalize();
        MantExpComplexFull resultB = stepA.plus(stepB);
        resultB.Normalize();

        result.Ax = resultA.getMantissaReal();
        result.Ay = resultA.getMantissaImag();
        result.Aexp = resultA.getExp();
        result.AexpIm = resultA.getExpImag();
        result.Bx = resultB.getMantissaReal();
        result.By = resultB.getMantissaImag();
        result.Bexp = resultB.getExp();
        result.BexpIm = resultB.getExpImag();
        //result.l = getL() + step.getL();

        return result;
    }

    @Override
    public MantExpComplex getB() {
        return B;
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {
        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(DeltaSub0);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {
        return DeltaSubN.times(new MantExpComplexFull(Aexp, AexpIm, Ax, Ay)).plus_mutable(DeltaSub0);
    }

//    @Override
//    public int getL() {
//        return 1;
//    }
}
