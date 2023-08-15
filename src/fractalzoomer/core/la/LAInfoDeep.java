package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class LAInfoDeep extends GenericLAInfo {
    public static MantExp Stage0PeriodDetectionThreshold = new MantExp(LAInfo.Stage0PeriodDetectionThreshold);
    public static MantExp PeriodDetectionThreshold = new MantExp(LAInfo.PeriodDetectionThreshold);
    public static MantExp Stage0PeriodDetectionThreshold2 = new MantExp(LAInfo.Stage0PeriodDetectionThreshold2);
    public static MantExp PeriodDetectionThreshold2 = new MantExp(LAInfo.PeriodDetectionThreshold2);
    public static MantExp LAThresholdScale = new MantExp(LAInfo.LAThresholdScale);
    public static MantExp LAThresholdCScale = new MantExp(LAInfo.LAThresholdCScale);
    private static final MantExp atLimit = new MantExp(0x1.0p256);
    double RefRe, RefIm;
    long RefExp;

    double ZCoeffRe, ZCoeffIm;
    long ZCoeffExp;

    double CCoeffRe, CCoeffIm;
    long CCoeffExp;

    double LAThresholdMant;
    long LAThresholdExp;

    double LAThresholdCMant;
    long LAThresholdCExp;

    double MinMagMant;
    long MinMagExp;


    public LAInfoDeep() {

    }

    public LAInfoDeep(MantExpComplex z) {
        z.Reduce();
        RefRe = z.getMantissaReal();
        RefIm = z.getMantissaImag();
        RefExp = z.getExp();


        MantExpComplex ZCoeff = new MantExpComplex(1.0, 0);
        MantExpComplex CCoeff = new MantExpComplex(1.0, 0);
        MantExp LAThreshold = MantExp.ONE;
        MantExp LAThresholdC = MantExp.ONE;

        ZCoeffRe = ZCoeff.getMantissaReal();
        ZCoeffIm = ZCoeff.getMantissaImag();
        ZCoeffExp = ZCoeff.getExp();

        CCoeffRe = CCoeff.getMantissaReal();
        CCoeffIm = CCoeff.getMantissaImag();
        CCoeffExp = CCoeff.getExp();

        LAThresholdMant = LAThreshold.getMantissa();
        LAThresholdExp = LAThreshold.getExp();

        LAThresholdCMant = LAThresholdC.getMantissa();
        LAThresholdCExp = LAThresholdC.getExp();

        if(LAInfo.DETECTION_METHOD == 1) {
            MantExp MinMag = MantExp.FOUR;
            MinMagMant = MinMag.getMantissa();
            MinMagExp = MinMag.getExp();
        }

    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        if(LAInfo.DETECTION_METHOD == 1) {
            return z.chebychevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(PeriodDetectionThreshold2)) < 0;
        }
        else {
            return z.chebychevNorm().divide(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm).chebychevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(PeriodDetectionThreshold)) < 0;
        }
    }

    @Override
    protected GenericComplex getRef() {
        return new MantExpComplex(RefExp, RefRe, RefIm);
    }

    @Override
    protected GenericComplex getZCoeff() {
        return new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
    }

    @Override
    protected GenericComplex getCCoeff() {
        return new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean Step(LAInfoDeep out, MantExpComplex z) {
        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);

        MantExp ChebyMagZCoeff = ZCoeff.chebychevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebychevNorm();

        if(LAInfo.DETECTION_METHOD == 1) {
            MantExp outMinMag = MantExp.minBothPositiveReduced(ChebyMagz, new MantExp(MinMagExp, MinMagMant));
            out.MinMagExp = outMinMag.getExp();
            out.MinMagMant = outMinMag.getMantissa();
        }

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAThresholdScale);
        temp1.Reduce();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAThresholdCScale);
        temp2.Reduce();

        MantExp outLAThreshold = MantExp.minBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant), temp1);
        MantExp outLAThresholdC = MantExp.minBothPositiveReduced(new MantExp(LAThresholdCExp, LAThresholdCMant), temp2);

        out.LAThresholdExp = outLAThreshold.getExp();
        out.LAThresholdMant = outLAThreshold.getMantissa();

        out.LAThresholdCExp = outLAThresholdC.getExp();
        out.LAThresholdCMant = outLAThresholdC.getMantissa();

        MantExpComplex z2 = z.times2();
        MantExpComplex outZCoeff = z2.times(ZCoeff);
        outZCoeff.Reduce();
        MantExpComplex outCCoeff = z2.times(CCoeff).plus_mutable(MantExp.ONE);
        outCCoeff.Reduce();

        out.ZCoeffExp = outZCoeff.getExp();
        out.ZCoeffRe = outZCoeff.getMantissaReal();
        out.ZCoeffIm = outZCoeff.getMantissaImag();

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();

        out.RefRe = RefRe;
        out.RefIm = RefIm;
        out.RefExp = RefExp;

        if(LAInfo.DETECTION_METHOD == 1) {
            return new MantExp(out.MinMagExp, out.MinMagMant).compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(Stage0PeriodDetectionThreshold2)) < 0;
        }
        else {
            return new MantExp(out.LAThresholdExp, out.LAThresholdMant).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(Stage0PeriodDetectionThreshold)) < 0;
        }
    }

    @Override
    protected boolean Step(LAInfo out, Complex z) {
        return false;
    }

    @Override
    protected boolean isLAThresholdZero() {
        return new MantExp(LAThresholdExp, LAThresholdMant).compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected boolean isZCoeffZero() {
        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        return ZCoeff.getRe().compareTo(MantExp.ZERO) == 0 && ZCoeff.getIm().compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected boolean DetectPeriod(Complex z) {
        return false;
    }

    @Override
    protected GenericLAInfo Step(MantExpComplex z)  {
        LAInfoDeep Result = new LAInfoDeep();

        Step(Result, z);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA) {
        MantExpComplex z = new MantExpComplex(LA.RefExp, LA.RefRe, LA.RefIm);
        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        MantExp ChebyMagZCoeff = ZCoeff.chebychevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebychevNorm();

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAThresholdScale);
        temp1.Reduce();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAThresholdCScale);
        temp2.Reduce();

        MantExp outLAThreshold = MantExp.minBothPositiveReduced(LAThreshold, temp1);
        MantExp outLAThresholdC = MantExp.minBothPositiveReduced(LAThresholdC, temp2);

        MantExpComplex z2 = z.times2();
        MantExpComplex outZCoeff = z2.times(ZCoeff);
        outZCoeff.Reduce();
        //double RescaleFactor = out.LAThreshold / LAThreshold;
        MantExpComplex outCCoeff = z2.times(CCoeff);
        outCCoeff.Reduce();

        ChebyMagZCoeff = outZCoeff.chebychevNorm();
        ChebyMagCCoeff = outCCoeff.chebychevNorm();
        MantExp temp = outLAThreshold;

        MantExp LA_LAThreshold = new MantExp(LA.LAThresholdExp, LA.LAThresholdMant);
        MantExpComplex LAZCoeff = new MantExpComplex(LA.ZCoeffExp, LA.ZCoeffRe, LA.ZCoeffIm);
        MantExpComplex LACCoeff = new MantExpComplex(LA.CCoeffExp, LA.CCoeffRe, LA.CCoeffIm);

        temp1 = LA_LAThreshold.divide(ChebyMagZCoeff);
        temp1.Reduce();

        temp2 = LA_LAThreshold.divide(ChebyMagCCoeff);
        temp2.Reduce();

        outLAThreshold = MantExp.minBothPositiveReduced(outLAThreshold, temp1);
        outLAThresholdC = MantExp.minBothPositiveReduced(outLAThresholdC, temp2);
        outZCoeff = outZCoeff.times(LAZCoeff);
        outZCoeff.Reduce();
        //RescaleFactor = out.LAThreshold / temp;
        outCCoeff = outCCoeff.times(LAZCoeff).plus_mutable(LACCoeff);
        outCCoeff.Reduce();

        out.LAThresholdExp = outLAThreshold.getExp();
        out.LAThresholdMant = outLAThreshold.getMantissa();

        out.LAThresholdCExp = outLAThresholdC.getExp();
        out.LAThresholdCMant = outLAThresholdC.getMantissa();

        out.ZCoeffExp = outZCoeff.getExp();
        out.ZCoeffRe = outZCoeff.getMantissaReal();
        out.ZCoeffIm = outZCoeff.getMantissaImag();

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();

        out.RefExp = RefExp;
        out.RefRe = RefRe;
        out.RefIm = RefIm;

        if(LAInfo.DETECTION_METHOD == 1) {
            MantExp MinMag = new MantExp(MinMagExp, MinMagMant);
            temp = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);
            MantExp outMinMag = MantExp.minBothPositiveReduced (temp, new MantExp(LA.MinMagExp, LA.MinMagMant));

            out.MinMagExp = outMinMag.getExp();
            out.MinMagMant = outMinMag.getMantissa();

            return temp.compareToBothPositive(MinMag.multiply(PeriodDetectionThreshold2)) < 0;
        }
        else {
            return temp.compareToBothPositive(LAThreshold.multiply(PeriodDetectionThreshold)) < 0;
        }
    }

    @Override
    protected GenericLAInfo Composite(LAInfo LA) {
        return null;
    }

    @Override
    protected LAInfoDeep Composite(LAInfoDeep LA)  {
        LAInfoDeep Result = new LAInfoDeep();

        Composite(Result, LA);
        return Result;
    }

    @Override
    protected GenericLAInfo Step(Complex z) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfo out, LAInfo LA) {
        return false;
    }

    protected LAstep Prepare(MantExpComplex dz, MantExpComplex dc)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(new MantExpComplex(RefExp + 1, RefRe, RefIm).plus_mutable(dz));
        newdz.Reduce();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm().compareToBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant)) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }

    public MantExpComplex Evaluate(MantExpComplex newdz, MantExpComplex dc) {
        return newdz.times(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm)).plus_mutable(dc.times(new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm)));
    }

    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return  dzdc.times2().times_mutable(z).times_mutable(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm) ).plus_mutable(new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm));
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return  dzdc2.times(z)
                .plus_mutable(dzdc.square()).times2_mutable().times_mutable(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm));
    }
    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {
        ATInfo Result = new ATInfo();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        Result.ZCoeff = ZCoeff;
        Result.CCoeff = ZCoeff.times(CCoeff);
        Result.CCoeff.Reduce();

        Result.InvZCoeff = ZCoeff.reciprocal();
        Result.InvZCoeff.Reduce();

        Result.CCoeffSqrInvZCoeff = Result.CCoeff.square().times_mutable(Result.InvZCoeff);
        Result.CCoeffSqrInvZCoeff.Reduce();

        Result.CCoeffInvZCoeff = Result.CCoeff.times(Result.InvZCoeff);
        Result.CCoeffInvZCoeff.Reduce();

        Result.RefC = Next.getRef().toMantExpComplex().times(ZCoeff);
        Result.RefC.Reduce();

        Result.CCoeffNormSqr = Result.CCoeff.norm_squared();
        Result.CCoeffNormSqr.Reduce();

        Result.RefCNormSqr = Result.RefC.norm_squared();
        Result.RefCNormSqr.Reduce();

        Result.SqrEscapeRadius = MantExp.minBothPositive(ZCoeff.norm_squared().multiply(LAThreshold), atLimit).toDouble();

        Result.ThresholdC = MantExp.minBothPositive(LAThresholdC, atLimit.divide(Result.CCoeff.chebychevNorm()));

        return Result;
    }

    @Override
    public MantExp getLAThreshold() {
        return new MantExp(LAThresholdExp, LAThresholdMant);
    }

    @Override
    public MantExp getLAThresholdC() {
        return new MantExp(LAThresholdCExp, LAThresholdCMant);
    }
}
