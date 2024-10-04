package fractalzoomer.core.approximation.la_zhuoran.impl;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.core.approximation.la_zhuoran.GenericLAInfo;
import fractalzoomer.core.approximation.la_zhuoran.LAInfoBaseDeep;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.functions.Fractal;

public class LAInfoDeepDetection2 extends LAInfoDeep {
    double MinMagMant;
    long MinMagExp;

    protected LAInfoDeepDetection2() {
        super(0);
    }

    protected LAInfoDeepDetection2(int refIndex, ReferenceDecompressor referenceDecompressor) {
        super(refIndex, referenceDecompressor);
        MantExp MinMag = MantExp.FOUR;
        MinMagMant = MinMag.getMantissa();
        MinMagExp = MinMag.getExp();
    }

    public LAInfoDeepDetection2(LAInfoDeepDetection2 other) {
        super(other);
        MinMagMant = other.MinMagMant;
        MinMagExp = other.MinMagExp;
    }

    @Override
    protected boolean DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(LAInfoBaseDeep.DipDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Stage0DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().compareToBothPositive(new MantExp(MinMagExp, MinMagMant).multiply(LAInfoBaseDeep.Stage0DipDetectionThreshold2)) < 0;
    }

    @Override
    protected boolean Step(LAInfoDeep out1, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        LAInfoDeepDetection2 out = (LAInfoDeepDetection2)out1;

        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);

        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);

        MantExp ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebyshevNorm();

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAInfoBaseDeep.LAThresholdScale);
        temp1.Normalize();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAInfoBaseDeep.LAThresholdCScale);
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

        MantExp MinMag = new MantExp(MinMagExp, MinMagMant);
        MantExp outMinMag = MantExp.minBothPositiveReduced(ChebyMagz, MinMag);

        out.MinMagExp = outMinMag.getExp();
        out.MinMagMant = outMinMag.getMantissa();

        out.StepLength = StepLength + 1;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && outMinMag.compareToBothPositive(MinMag.multiply(LAInfoBaseDeep.Stage0DipDetectionThreshold2)) < 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepDetection2 Result = new LAInfoDeepDetection2();

        Step(Result, zRefIndex, referenceDecompressor, false);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out1, LAInfoDeep LA1, ReferenceDecompressor referenceDecompressor, boolean checkDip) {

        LAInfoDeepDetection2 out = (LAInfoDeepDetection2)out1;
        LAInfoDeepDetection2 LA = (LAInfoDeepDetection2)LA1;

        MantExpComplex z = new MantExpComplex(LA.RefExp, LA.RefRe, LA.RefIm);
        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        MantExp ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        MantExp ChebyMagCCoeff = CCoeff.chebyshevNorm();

        MantExp temp1 = ChebyMagz.divide(ChebyMagZCoeff).multiply_mutable(LAInfoBaseDeep.LAThresholdScale);
        temp1.Normalize();

        MantExp temp2 = ChebyMagz.divide(ChebyMagCCoeff).multiply_mutable(LAInfoBaseDeep.LAThresholdCScale);
        temp2.Normalize();

        MantExp outLAThreshold = MantExp.minBothPositiveReduced(LAThreshold, temp1);
        MantExp outLAThresholdC = MantExp.minBothPositiveReduced(LAThresholdC, temp2);

        MantExpComplex z2 = z.times2();
        MantExpComplex outZCoeff = z2.times(ZCoeff);
        outZCoeff.Normalize();

        MantExpComplex outCCoeff = z2.times(CCoeff);
        outCCoeff.Normalize();

        ChebyMagZCoeff = outZCoeff.chebyshevNorm();
        ChebyMagCCoeff = outCCoeff.chebyshevNorm();

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

        out.StepLength = LA.StepLength + StepLength;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && temp.compareToBothPositive(MinMag.multiply(LAInfoBaseDeep.DipDetectionThreshold2)) < 0;
    }
    @Override
    protected LAInfoDeepDetection2 Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepDetection2 Result = new LAInfoDeepDetection2();

        Composite(Result, LA, referenceDecompressor, false);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfoDeep(this);
    }

    @Override
    public String toString() {
        return  StepLength + "\n" +
                NextStageLAIndex + "\n"+
                ZCoeffRe + "\n" +
                ZCoeffIm + "\n" +
                ZCoeffExp + "\n" +
                CCoeffRe + "\n" +
                CCoeffIm + "\n" +
                CCoeffExp + "\n" +
                LAThresholdMant + "\n" +
                LAThresholdExp + "\n" +
                LAThresholdCMant + "\n" +
                LAThresholdExp + "\n" +
                RefRe + "\n" +
                RefIm + "\n" +
                RefExp + "\n" +
                MinMagMant + "\n" +
                MinMagExp + "\n";
    }
    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeepDetection2)) {
            return false;
        }
        LAInfoDeepDetection2 oth = (LAInfoDeepDetection2) other;
        if(this == other) {
            return true;
        }

        return ZCoeffRe == oth.ZCoeffRe &&
                ZCoeffIm == oth.ZCoeffIm &&
                ZCoeffExp == oth.ZCoeffExp &&

                CCoeffRe == oth.CCoeffRe &&
                CCoeffIm == oth.CCoeffIm &&
                CCoeffExp == oth.CCoeffExp &&

                LAThresholdMant == oth.LAThresholdMant &&
                LAThresholdExp == oth.LAThresholdExp &&

                LAThresholdCMant == oth.LAThresholdCMant &&
                LAThresholdCExp == oth.LAThresholdCExp &&

                RefRe == oth.RefRe &&
                RefIm == oth.RefIm &&
                RefExp == oth.RefExp &&

                MinMagMant == oth.MinMagMant &&
                MinMagExp == oth.MinMagExp;
    }
}
