package fractalzoomer.core.la.impl;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.la.GenericLAInfo;

public class LAInfoDeepDetection2 extends LAInfoDeep {
    double MinMagMant;
    long MinMagExp;

    protected LAInfoDeepDetection2() {
        super();
    }

    protected LAInfoDeepDetection2(int refIndex) {
        super(refIndex);
        MantExp MinMag = MantExp.FOUR;
        MinMagMant = MinMag.getMantissa();
        MinMagExp = MinMag.getExp();
    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Stage0DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(Stage0PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Step(LAInfoDeep out1, int zRefIndex) {
        LAInfoDeepDetection2 out = (LAInfoDeepDetection2)out1;

        MantExpComplex z = new MantExpComplex(refExpRe[zRefIndex], refMantsRe[zRefIndex], refMantsIm[zRefIndex]);

        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);

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

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();

        out.RefRe = RefRe;
        out.RefIm = RefIm;
        out.RefExp = RefExp;

        MantExp MinMag = new MantExp(MinMagExp, MinMagMant);;
        MantExp outMinMag = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);

        out.MinMagExp = outMinMag.getExp();
        out.MinMagMant = outMinMag.getMantissa();

        return outMinMag.compareToBothPositive(MinMag.multiply(Stage0PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex)  {
        LAInfoDeepDetection2 Result = new LAInfoDeepDetection2();

        Step(Result, zRefIndex);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out1, LAInfoDeep LA1) {

        LAInfoDeepDetection2 out = (LAInfoDeepDetection2)out1;
        LAInfoDeepDetection2 LA = (LAInfoDeepDetection2)LA1;

        MantExpComplex z = new MantExpComplex(LA.RefExp, LA.RefRe, LA.RefIm);
        MantExp ChebyMagz = z.chebychevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
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

        MantExp LA_LAThreshold = new MantExp(LA.LAThresholdExp, LA.LAThresholdMant);
        MantExpComplex LAZCoeff = new MantExpComplex(LA.ZCoeffExp, LA.ZCoeffRe, LA.ZCoeffIm);
        MantExpComplex LACCoeff = new MantExpComplex(LA.CCoeffExp, LA.CCoeffRe, LA.CCoeffIm);

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

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();

        out.RefExp = RefExp;
        out.RefRe = RefRe;
        out.RefIm = RefIm;

        MantExp MinMag = new MantExp(MinMagExp, MinMagMant);
        MantExp temp = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);
        MantExp outMinMag = MantExp.minBothPositiveReduced (temp, new MantExp(LA.MinMagExp, LA.MinMagMant));

        out.MinMagExp = outMinMag.getExp();
        out.MinMagMant = outMinMag.getMantissa();

        return temp.compareToBothPositive(MinMag.multiply(PeriodDetectionThreshold2)) < 0;
    }
    @Override
    protected LAInfoDeepDetection2 Composite(LAInfoDeep LA)  {
        LAInfoDeepDetection2 Result = new LAInfoDeepDetection2();

        Composite(Result, LA);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfoDeep(this);
    }
}
