package fractalzoomer.core.approximation.mip_la_zhuoran;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.functions.Fractal;

public class MipLADeep1Step extends MipLADeepStep {
    public static MantExpComplex B = MantExpComplex.ONE;
    private MipLADeep1Step(MantExpComplex z, Fractal f) {
        super(z, f);
    }

    public static MipLADeepStep create(MantExpComplex z, Fractal f) {
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new MipLADeepFull1Step(z, f);
        }
        return new MipLADeep1Step(z, f);
    }

    @Override
    public MipLADeepStep Composite(MipLADeepStep step) {
        MipLADeepLStep result = new MipLADeepLStep();

        MantExpComplex A = new MantExpComplex(Aexp, Ax, Ay);

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

        MantExpComplex stepA = new MantExpComplex(step.Aexp, step.Ax, step.Ay);
        MantExpComplex stepB = step.getB();

        MantExpComplex resultA = A.times(stepA);
        resultA.Normalize();
        MantExpComplex resultB = stepA.plus(stepB);
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
    public MantExpComplex getB() {
        return B;
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0) {
        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0);
    }

    @Override
    public MantExpComplex getValue(MantExpComplex DeltaSubN, MantExp DeltaSub0) {
        return DeltaSubN.times(Aexp, Ax, Ay).plus_mutable(DeltaSub0);
    }

//    @Override
//    public int getL() {
//        return 1;
//    }
}
