package fractalzoomer.core.la.impl;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;
import fractalzoomer.core.la.ATInfo;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.LAstep;

public class LAInfoDeepFull extends LAInfoDeep {
    long RefExpIm;
    long ZCoeffExpIm;
    long CCoeffExpIm;

    protected LAInfoDeepFull() {

    }

    protected LAInfoDeepFull(LAInfoDeepFull other) {
        RefRe = other.RefRe;
        RefIm = other.RefIm;
        RefExp = other.RefExp;
        RefExpIm = other.RefExpIm;

        ZCoeffRe = other.ZCoeffRe;
        ZCoeffIm = other.ZCoeffIm;
        ZCoeffExp = other.ZCoeffExp;
        ZCoeffExpIm = other.ZCoeffExpIm;

        CCoeffRe = other.CCoeffRe;
        CCoeffIm = other.CCoeffIm;
        CCoeffExp = other.CCoeffExp;
        CCoeffExpIm = other.CCoeffExpIm;

        LAThresholdMant = other.LAThresholdMant;
        LAThresholdExp = other.LAThresholdExp;

        LAThresholdCMant = other.LAThresholdCMant;
        LAThresholdCExp = other.LAThresholdCExp;
    }

    protected LAInfoDeepFull(int refIndex) {
        MantExpComplex z = new MantExpComplexFull(refExpRe[refIndex], refExpIm[refIndex], refMantsRe[refIndex], refMantsIm[refIndex]);
        //z.Normalize();
        RefRe = z.getMantissaReal();
        RefIm = z.getMantissaImag();
        RefExp = z.getExp();
        RefExpIm = z.getExpImag();


        MantExp LAThreshold = MantExp.ONE;
        MantExp LAThresholdC = MantExp.ONE;

        ZCoeffRe = MantExpComplexFull.ONE.getMantissaReal();
        ZCoeffIm = MantExpComplexFull.ONE.getMantissaImag();
        ZCoeffExp = MantExpComplexFull.ONE.getExp();
        ZCoeffExpIm = MantExpComplexFull.ONE.getExpImag();

        CCoeffRe = MantExpComplexFull.ONE.getMantissaReal();
        CCoeffIm = MantExpComplexFull.ONE.getMantissaImag();
        CCoeffExp = MantExpComplexFull.ONE.getExp();
        CCoeffExpIm = MantExpComplexFull.ONE.getExpImag();

        LAThresholdMant = LAThreshold.getMantissa();
        LAThresholdExp = LAThreshold.getExp();

        LAThresholdCMant = LAThresholdC.getMantissa();
        LAThresholdCExp = LAThresholdC.getExp();

    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().divide(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm).chebychevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Stage0DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().divide(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm).chebychevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    public GenericComplex getRef() {
        return new MantExpComplexFull(RefExp, RefExpIm, RefRe, RefIm);
    }

    @Override
    public GenericComplex getZCoeff() {
        return new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
    }

    @Override
    public GenericComplex getCCoeff() {
        return new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean Step(LAInfoDeep out1, int zRefIndex) {

        LAInfoDeepFull out = (LAInfoDeepFull)out1;

        MantExpComplex z = new MantExpComplexFull(refExpRe[zRefIndex], refExpIm[zRefIndex], refMantsRe[zRefIndex], refMantsIm[zRefIndex]);

        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);

        MantExp ChebyMagZCoeff = ZCoeff.chebychevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebychevNorm();

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAThresholdScale);
        temp1.Normalize();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAThresholdCScale);
        temp2.Normalize();

        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp outLAThreshold = MantExp.minBothPositiveReduced(LAThreshold, temp1);
        MantExp outLAThresholdC = MantExp.minBothPositiveReduced(new MantExp(LAThresholdCExp, LAThresholdCMant), temp2);

        out.LAThresholdExp = outLAThreshold.getExp();
        out.LAThresholdMant = outLAThreshold.getMantissa();

        out.LAThresholdCExp = outLAThresholdC.getExp();
        out.LAThresholdCMant = outLAThresholdC.getMantissa();

        MantExpComplex z2 = z.times2();
        MantExpComplex outZCoeff = z2.times(ZCoeff);
        outZCoeff.Normalize();
        MantExpComplex outCCoeff = z2.times(CCoeff).plus_mutable(MantExp.ONE);
        outCCoeff.Normalize();

        out.ZCoeffExp = outZCoeff.getExp();
        out.ZCoeffRe = outZCoeff.getMantissaReal();
        out.ZCoeffIm = outZCoeff.getMantissaImag();
        out.ZCoeffExpIm = outZCoeff.getExpImag();

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();
        out.CCoeffExpIm = outCCoeff.getExpImag();

        out.RefRe = RefRe;
        out.RefIm = RefIm;
        out.RefExp = RefExp;
        out.RefExpIm = RefExpIm;

        return outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected boolean isZCoeffZero() {
        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        return ZCoeff.getRe().compareTo(MantExp.ZERO) == 0 && ZCoeff.getIm().compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex)  {
        LAInfoDeepFull Result = new LAInfoDeepFull();

        Step(Result, zRefIndex);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out1, LAInfoDeep LA1) {

        LAInfoDeepFull out = (LAInfoDeepFull)out1;
        LAInfoDeepFull LA = (LAInfoDeepFull)LA1;

        MantExpComplex z = new MantExpComplexFull(LA.RefExp, LA.RefExpIm, LA.RefRe, LA.RefIm);
        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);
        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        MantExp ChebyMagZCoeff = ZCoeff.chebychevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebychevNorm();

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAThresholdScale);
        temp1.Normalize();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAThresholdCScale);
        temp2.Normalize();

        MantExp outLAThreshold = MantExp.minBothPositiveReduced(LAThreshold, temp1);
        MantExp outLAThresholdC = MantExp.minBothPositiveReduced(LAThresholdC, temp2);

        MantExpComplex z2 = z.times2();
        MantExpComplex outZCoeff = z2.times(ZCoeff);
        outZCoeff.Normalize();
        //double RescaleFactor = out.LAThreshold / LAThreshold;
        MantExpComplex outCCoeff = z2.times(CCoeff);
        outCCoeff.Normalize();

        ChebyMagZCoeff = outZCoeff.chebychevNorm();
        ChebyMagCCoeff = outCCoeff.chebychevNorm();
        MantExp temp = outLAThreshold;

        MantExp LA_LAThreshold = new MantExp(LA.LAThresholdExp, LA.LAThresholdMant);
        MantExpComplex LAZCoeff = new MantExpComplexFull(LA.ZCoeffExp, LA.ZCoeffExpIm, LA.ZCoeffRe, LA.ZCoeffIm);
        MantExpComplex LACCoeff = new MantExpComplexFull(LA.CCoeffExp, LA.CCoeffExpIm, LA.CCoeffRe, LA.CCoeffIm);

        temp1 = LA_LAThreshold.divide(ChebyMagZCoeff);
        temp1.Normalize();

        temp2 = LA_LAThreshold.divide(ChebyMagCCoeff);
        temp2.Normalize();

        outLAThreshold = MantExp.minBothPositiveReduced(outLAThreshold, temp1);
        outLAThresholdC = MantExp.minBothPositiveReduced(outLAThresholdC, temp2);
        outZCoeff = outZCoeff.times(LAZCoeff);
        outZCoeff.Normalize();
        //RescaleFactor = out.LAThreshold / temp;
        outCCoeff = outCCoeff.times(LAZCoeff).plus_mutable(LACCoeff);
        outCCoeff.Normalize();

        out.LAThresholdExp = outLAThreshold.getExp();
        out.LAThresholdMant = outLAThreshold.getMantissa();

        out.LAThresholdCExp = outLAThresholdC.getExp();
        out.LAThresholdCMant = outLAThresholdC.getMantissa();

        out.ZCoeffExp = outZCoeff.getExp();
        out.ZCoeffRe = outZCoeff.getMantissaReal();
        out.ZCoeffIm = outZCoeff.getMantissaImag();
        out.ZCoeffExpIm = outZCoeff.getExpImag();

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();
        out.CCoeffExpIm = outCCoeff.getExpImag();

        out.RefExp = RefExp;
        out.RefRe = RefRe;
        out.RefIm = RefIm;
        out.RefExpIm = RefExpIm;

        return temp.compareToBothPositive(LAThreshold.multiply(PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeepFull Composite(LAInfoDeep LA)  {
        LAInfoDeepFull Result = new LAInfoDeepFull();

        Composite(Result, LA);
        return Result;
    }

    @Override
    public LAstep Prepare(MantExpComplex dz)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(new MantExpComplexFull(RefExp + 1, RefExpIm + 1, RefRe, RefIm).plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm().compareToBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant)) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }

    @Override
    public MantExpComplex Evaluate(MantExpComplex newdz, MantExpComplex dc) {
        return newdz.times(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm)).plus_mutable(dc.times(new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm)));
    }

    @Override
    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return  dzdc.times2().times_mutable(z).times_mutable(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm) ).plus_mutable(new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm));
    }

    @Override
    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return  dzdc2.times(z)
                .plus_mutable(dzdc.square()).times2_mutable().times_mutable(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm));
    }

    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {
        return CreateAT(Next, new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm), new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm), LAThresholdExp, LAThresholdMant, LAThresholdCExp, LAThresholdCMant);
    }
}