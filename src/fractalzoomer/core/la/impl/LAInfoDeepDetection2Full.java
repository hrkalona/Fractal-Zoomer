package fractalzoomer.core.la.impl;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.MantExpComplexFull;
import fractalzoomer.core.ReferenceDecompressor;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.functions.Fractal;

import static fractalzoomer.core.la.LAReference.f;

public class LAInfoDeepDetection2Full extends LAInfoDeepFull {
    double MinMagMant;
    long MinMagExp;

    protected LAInfoDeepDetection2Full() {
        super();
    }

    protected LAInfoDeepDetection2Full(int refIndex, ReferenceDecompressor referenceDecompressor) {
        super(refIndex, referenceDecompressor);
        MantExp MinMag = MantExp.FOUR;
        MinMagMant = MinMag.getMantissa();
        MinMagExp = MinMag.getExp();
    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return z.chebyshevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Stage0DetectPeriod(MantExpComplex z) {
        return z.chebyshevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(Stage0PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Step(LAInfoDeep out1, int zRefIndex, ReferenceDecompressor referenceDecompressor) {

        LAInfoDeepDetection2Full out = (LAInfoDeepDetection2Full)out1;

        MantExpComplex z = f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);

        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);

        MantExp ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebyshevNorm();

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

        MantExp MinMag = new MantExp(MinMagExp, MinMagMant);
        MantExp outMinMag = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);
        out.MinMagExp = outMinMag.getExp();
        out.MinMagMant = outMinMag.getMantissa();

        return outMinMag.compareToBothPositive(MinMag.multiply(Stage0PeriodDetectionThreshold2)) < 0;
    }
    @Override
    protected GenericLAInfo Step(int zRefIndex, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepDetection2Full Result = new LAInfoDeepDetection2Full();

        Step(Result, zRefIndex, referenceDecompressor);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out1, LAInfoDeep LA1, ReferenceDecompressor referenceDecompressor) {

        LAInfoDeepDetection2Full out = (LAInfoDeepDetection2Full)out1;
        LAInfoDeepDetection2Full LA = (LAInfoDeepDetection2Full)LA1;

        MantExpComplex z = new MantExpComplexFull(LA.RefExp, LA.RefExpIm, LA.RefRe, LA.RefIm);
        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);
        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        MantExp ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebyshevNorm();

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

        ChebyMagZCoeff = outZCoeff.chebyshevNorm();
        ChebyMagCCoeff = outCCoeff.chebyshevNorm();

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

        MantExp MinMag = new MantExp(MinMagExp, MinMagMant);
        MantExp temp = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);
        MantExp outMinMag = MantExp.minBothPositiveReduced (temp, new MantExp(LA.MinMagExp, LA.MinMagMant));

        out.MinMagExp = outMinMag.getExp();
        out.MinMagMant = outMinMag.getMantissa();

        return temp.compareToBothPositive(MinMag.multiply(PeriodDetectionThreshold2)) < 0;
    }

    @Override
    protected LAInfoDeepDetection2Full Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepDetection2Full Result = new LAInfoDeepDetection2Full();

        Composite(Result, LA, referenceDecompressor);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfoDeepFull(this);
    }

    @Override
    public String toString() {
        return  ZCoeffRe + "\n" +
                ZCoeffIm + "\n" +
                ZCoeffExp + "\n" +
                ZCoeffExpIm + "\n" +
                CCoeffRe + "\n" +
                CCoeffIm + "\n" +
                CCoeffExp + "\n" +
                CCoeffExpIm + "\n" +
                LAThresholdMant + "\n" +
                LAThresholdExp + "\n" +
                LAThresholdCMant + "\n" +
                LAThresholdExp + "\n" +
                RefRe + "\n" +
                RefIm + "\n" +
                RefExp + "\n" +
                RefExpIm + "\n" +
                MinMagMant + "\n" +
                MinMagExp + "\n";
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeepDetection2Full)) {
            return false;
        }
        LAInfoDeepDetection2Full oth = (LAInfoDeepDetection2Full) other;
        if(this == other) {
            return true;
        }

        return ZCoeffRe == oth.ZCoeffRe &&
                ZCoeffIm == oth.ZCoeffIm &&
                ZCoeffExp == oth.ZCoeffExp &&
                ZCoeffExpIm == oth.ZCoeffExpIm &&

                CCoeffRe == oth.CCoeffRe &&
                CCoeffIm == oth.CCoeffIm &&
                CCoeffExp == oth.CCoeffExp &&
                CCoeffExpIm == oth.CCoeffExpIm &&

                LAThresholdMant == oth.LAThresholdMant &&
                LAThresholdExp == oth.LAThresholdExp &&

                LAThresholdCMant == oth.LAThresholdCMant &&
                LAThresholdCExp == oth.LAThresholdCExp &&

                RefRe == oth.RefRe &&
                RefIm == oth.RefIm &&
                RefExp == oth.RefExp &&
                RefExpIm == oth.RefExpIm &&

                MinMagMant == oth.MinMagMant &&
                MinMagExp == oth.MinMagExp;
    }
}
