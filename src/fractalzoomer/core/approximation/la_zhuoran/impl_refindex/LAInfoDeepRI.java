package fractalzoomer.core.approximation.la_zhuoran.impl_refindex;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.GenericLAInfo;
import fractalzoomer.core.approximation.la_zhuoran.LAInfoBaseDeep;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.approximation.la_zhuoran.LAstep;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfo;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.functions.Fractal;

public class LAInfoDeepRI extends LAInfoBaseDeep {
    int RefIndex;
    public static LAInfoDeepRI create() {

        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2FullRI();
            }
            return new LAInfoDeepDetection2RI();
        }
        else {
            if (TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFullRI();
            }
            return new LAInfoDeepRI();
        }
    }

    public static LAInfoDeepRI create(int RefIndex) {
        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2FullRI(RefIndex);
            }
            return new LAInfoDeepDetection2RI(RefIndex);
        }
        else {
            if (TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFullRI(RefIndex);
            }
            return new LAInfoDeepRI(RefIndex);
        }
    }


    protected LAInfoDeepRI() {
        super(0);
    }

    public LAInfoDeepRI(LAInfoDeepRI other) {
        super(other);
        RefIndex = other.RefIndex;

        ZCoeffRe = other.ZCoeffRe;
        ZCoeffIm = other.ZCoeffIm;
        ZCoeffExp = other.ZCoeffExp;

        CCoeffRe = other.CCoeffRe;
        CCoeffIm = other.CCoeffIm;
        CCoeffExp = other.CCoeffExp;

        LAThresholdMant = other.LAThresholdMant;
        LAThresholdExp = other.LAThresholdExp;

        LAThresholdCMant = other.LAThresholdCMant;
        LAThresholdCExp = other.LAThresholdCExp;
    }

    protected LAInfoDeepRI(int RefIndex) {
        super(RefIndex);
        this.RefIndex = RefIndex;

        MantExp LAThreshold = MantExp.ONE;
        MantExp LAThresholdC = MantExp.ONE;

        ZCoeffRe = MantExpComplex.ONE.getMantissaReal();
        ZCoeffIm = MantExpComplex.ONE.getMantissaImag();
        ZCoeffExp = MantExpComplex.ONE.getExp();

        CCoeffRe = MantExpComplex.ONE.getMantissaReal();
        CCoeffIm = MantExpComplex.ONE.getMantissaImag();
        CCoeffExp = MantExpComplex.ONE.getExp();

        LAThresholdMant = LAThreshold.getMantissa();
        LAThresholdExp = LAThreshold.getExp();

        LAThresholdCMant = LAThresholdC.getMantissa();
        LAThresholdCExp = LAThresholdC.getExp();

    }

    @Override
    public GenericComplex getRef(Fractal f) {
        return f.getArrayDeepValue(Fractal.referenceDeep, RefIndex);
    }

    @Override
    protected boolean Step(LAInfoDeepRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);
        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);

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

        out.CCoeffExp = outCCoeff.getExp();
        out.CCoeffRe = outCCoeff.getMantissaReal();
        out.CCoeffIm = outCCoeff.getMantissaImag();

        out.RefIndex = RefIndex;

        out.StepLength = StepLength + 1;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0DipDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        int zRefIndex = LA.RefIndex;
        MantExpComplex z = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);
        MantExp ChebyMagz = z.chebyshevNorm();

        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        MantExpComplex CCoeff = new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
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

        out.RefIndex = RefIndex;

        out.StepLength = LA.StepLength + StepLength;
        out.NextStageLAIndex = NextStageLAIndex;

        return checkDip && temp.compareToBothPositive(LAThreshold.multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeepRI Composite(LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeepRI Result = new LAInfoDeepRI();

        Composite(Result, LA, referenceDecompressor, false);
        return Result;
    }

    @Override
    protected GenericLAInfo Step(int RefIndex, ReferenceDecompressor referenceDecompressor) {
        LAInfoDeepRI Result = new LAInfoDeepRI();

        Step(Result, RefIndex, referenceDecompressor, false);
        return Result;
    }

    @Override
    protected LAstep Prepare(Fractal f, MantExpComplex dz)  {
        MantExpComplex newdz = dz.times(f.getArrayDeepValue(Fractal.referenceDeep, RefIndex).times2_mutable().plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebyshevNorm().compareToBothPositiveReduced(LAThresholdExp, LAThresholdMant) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }
    @Override
    public GenericLAInfo toDouble() {
        return new LAInfoRI(this);
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
                RefIndex + "\n";
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeepRI)) {
            return false;
        }
        LAInfoDeepRI oth = (LAInfoDeepRI) other;
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

                RefIndex == oth.RefIndex;
    }
}
