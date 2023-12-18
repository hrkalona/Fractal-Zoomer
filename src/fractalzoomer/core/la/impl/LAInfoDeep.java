package fractalzoomer.core.la.impl;

import fractalzoomer.core.*;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.LAInfoBaseDeep;
import fractalzoomer.core.la.LAstep;
import fractalzoomer.functions.Fractal;

import static fractalzoomer.core.la.LAReference.f;

public class LAInfoDeep extends LAInfoBaseDeep {
    double RefRe, RefIm;
    long RefExp;

    public static LAInfoDeep create() {

        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2Full();
            }
            return new LAInfoDeepDetection2();
        }
        else {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFull();
            }
            return new LAInfoDeep();
        }
    }

    public static LAInfoDeep create(int refIndex, ReferenceDecompressor referenceDecompressor) {
        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2Full(refIndex, referenceDecompressor);
            }
            return new LAInfoDeepDetection2(refIndex, referenceDecompressor);
        }
        else {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFull(refIndex, referenceDecompressor);
            }
            return new LAInfoDeep(refIndex, referenceDecompressor);
        }
    }


    protected LAInfoDeep() {

    }

    protected LAInfoDeep(LAInfoDeep other) {
        RefRe = other.RefRe;
        RefIm = other.RefIm;
        RefExp = other.RefExp;

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

    protected LAInfoDeep(int RefIndex, ReferenceDecompressor referenceDecompressor) {
        MantExpComplex z = f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, RefIndex);
        //z.Normalize();
        RefRe = z.getMantissaReal();
        RefIm = z.getMantissaImag();
        RefExp = z.getExp();


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
        return new MantExpComplex(RefExp, RefRe, RefIm);
    }

    @Override
    public Complex getRefDouble() {return new MantExpComplex(RefExp, RefRe, RefIm).toComplex();}

    @Override
    protected boolean Step(LAInfoDeep out, int zRefIndex, ReferenceDecompressor referenceDecompressor) {

        MantExpComplex z = f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, zRefIndex);

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

        out.RefRe = RefRe;
        out.RefIm = RefIm;
        out.RefExp = RefExp;

        return outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeep Result = new LAInfoDeep();

        Step(Result, zRefIndex, referenceDecompressor);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA, ReferenceDecompressor referenceDecompressor) {
        MantExpComplex z = new MantExpComplex(LA.RefExp, LA.RefRe, LA.RefIm);
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
        //double RescaleFactor = out.LAThreshold / LAThreshold;
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

        return temp.compareToBothPositive(LAThreshold.multiply(PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeep Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor)  {
        LAInfoDeep Result = new LAInfoDeep();

        Composite(Result, LA, referenceDecompressor);
        return Result;
    }

    @Override
    public LAstep Prepare(Fractal f, MantExpComplex dz)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(new MantExpComplex(RefExp + 1, RefRe, RefIm).plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebyshevNorm().compareToBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant)) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }

    @Override
    public GenericLAInfo toDouble() {
        return new LAInfo(this);
    }

    @Override
    public String toString() {
        return  ZCoeffRe + "\n" +
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
                RefExp + "\n";
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDeep)) {
            return false;
        }
        LAInfoDeep oth = (LAInfoDeep) other;
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
                RefExp == oth.RefExp;
    }

}
