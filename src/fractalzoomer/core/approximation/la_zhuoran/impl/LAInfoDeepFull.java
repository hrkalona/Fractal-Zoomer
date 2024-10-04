package fractalzoomer.core.approximation.la_zhuoran.impl;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.ATInfo;
import fractalzoomer.core.approximation.la_zhuoran.GenericLAInfo;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.approximation.la_zhuoran.LAstep;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.functions.Fractal;

public class LAInfoDeepFull extends LAInfoDeep {
    long RefExpIm;
    long ZCoeffExpIm;
    long CCoeffExpIm;

    protected LAInfoDeepFull() {
        super(0);
    }

    public LAInfoDeepFull(LAInfoDeepFull other) {
        super(other);
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

    protected LAInfoDeepFull(int refIndex, ReferenceDecompressor referenceDecompressor) {
        super(refIndex);
        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, refIndex);
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
    protected boolean DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().divide(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm).chebyshevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Stage0DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().divide(new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm).chebyshevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(Stage0DipDetectionThreshold)) < 0;
    }

    @Override
    public GenericComplex getRef(Fractal f) {
        return new MantExpComplexFull(RefExp, RefExpIm, RefRe, RefIm);
    }

    @Override
    public Complex getRefDouble() {return  new MantExpComplexFull(RefExp, RefExpIm, RefRe, RefIm).toComplex();}

    @Override
    public GenericComplex getZCoeff() {
        return new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
    }

    @Override
    public GenericComplex getCCoeff() {
        return new MantExpComplexFull(CCoeffExp, CCoeffExpIm, CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean Step(LAInfoDeep out1, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {

        LAInfoDeepFull out = (LAInfoDeepFull)out1;

        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);

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

        out.StepLength = StepLength + 1;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0DipDetectionThreshold)) < 0;
    }

    @Override
    protected boolean isZCoeffZero() {
        MantExpComplex ZCoeff = new MantExpComplexFull(ZCoeffExp, ZCoeffExpIm, ZCoeffRe, ZCoeffIm);
        return ZCoeff.getRe().compareTo(MantExp.ZERO) == 0 && ZCoeff.getIm().compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepFull Result = new LAInfoDeepFull();

        Step(Result, zRefIndex, referenceDecompressor, false);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out1, LAInfoDeep LA1, ReferenceDecompressor referenceDecompressor, boolean checkDip) {

        LAInfoDeepFull out = (LAInfoDeepFull)out1;
        LAInfoDeepFull LA = (LAInfoDeepFull)LA1;

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

        MantExpComplex outCCoeff = z2.times(CCoeff);
        outCCoeff.Normalize();

        ChebyMagZCoeff = outZCoeff.chebyshevNorm();
        ChebyMagCCoeff = outCCoeff.chebyshevNorm();
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

        out.StepLength = LA.StepLength + StepLength;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && temp.compareToBothPositive(LAThreshold.multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeepFull Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepFull Result = new LAInfoDeepFull();

        Composite(Result, LA, referenceDecompressor, false);
        return Result;
    }

    @Override
    public LAstep Prepare(Fractal f, MantExpComplex dz)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(new MantExpComplexFull(RefExp + 1, RefExpIm + 1, RefRe, RefIm).plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebyshevNorm().compareToBothPositiveReduced(LAThresholdExp, LAThresholdMant) >= 0;
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

    @Override
    public String toString() {
        return  StepLength + "\n" +
                NextStageLAIndex + "\n"+
                ZCoeffRe + "\n" +
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
                RefExpIm + "\n";
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeepFull)) {
            return false;
        }
        LAInfoDeepFull oth = (LAInfoDeepFull) other;
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
                RefExpIm == oth.RefExpIm;
    }
}
