package fractalzoomer.core.approximation.la_zhuoran.impl_refindex;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.ATInfo;
import fractalzoomer.core.approximation.la_zhuoran.GenericLAInfo;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.approximation.la_zhuoran.LAstep;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.functions.Fractal;

public class LAInfoDeepFullRI extends LAInfoDeepRI {
    long ZCoeffExpIm;
    long CCoeffExpIm;

    protected LAInfoDeepFullRI() {
        super();
    }

    public LAInfoDeepFullRI(LAInfoDeepFullRI other) {
        super(other);

        RefIndex = other.RefIndex;

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

    protected LAInfoDeepFullRI(int RefIndex) {
        super(RefIndex);
        this.RefIndex = RefIndex;

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
        return f.getArrayDeepValue(Fractal.referenceDeep, RefIndex);
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
    protected boolean Step(LAInfoDeepRI out1, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {

        LAInfoDeepFullRI out = (LAInfoDeepFullRI)out1;

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

        out.RefIndex = RefIndex;

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
    protected boolean Composite(LAInfoDeepRI out1, LAInfoDeepRI LA1, ReferenceDecompressor referenceDecompressor, boolean checkDip) {

        LAInfoDeepFullRI out = (LAInfoDeepFullRI)out1;
        LAInfoDeepFullRI LA = (LAInfoDeepFullRI)LA1;

        int zRefIndex = LA.RefIndex;

        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);
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

        out.RefIndex = RefIndex;

        out.StepLength = LA.StepLength + StepLength;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && temp.compareToBothPositive(LAThreshold.multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeepFullRI Composite(LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepFullRI Result = new LAInfoDeepFullRI();

        Composite(Result, LA, referenceDecompressor, false);
        return Result;
    }

    @Override
    protected LAstep Prepare(Fractal f, MantExpComplex dz)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(f.getArrayDeepValue(Fractal.referenceDeep, RefIndex).times2_mutable().plus_mutable(dz));
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
    protected GenericLAInfo Step(int RefIndex, ReferenceDecompressor referenceDecompressor) {
        LAInfoDeepFullRI Result = new LAInfoDeepFullRI();

        Step(Result, RefIndex, referenceDecompressor, false);
        return Result;
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
                RefIndex;
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeepFullRI)) {
            return false;
        }
        LAInfoDeepFullRI oth = (LAInfoDeepFullRI) other;
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

                RefIndex == oth.RefIndex;
    }
}
