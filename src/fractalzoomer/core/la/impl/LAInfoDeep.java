package fractalzoomer.core.la.impl;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.LAInfoBaseDeep;
import fractalzoomer.core.la.LAstep;

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

    public static LAInfoDeep create(int refIndex) {
        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2Full(refIndex);
            }
            return new LAInfoDeepDetection2(refIndex);
        }
        else {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFull(refIndex);
            }
            return new LAInfoDeep(refIndex);
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

    protected LAInfoDeep(int RefIndex) {
        MantExpComplex z = new MantExpComplex(refExpRe[RefIndex], refMantsRe[RefIndex], refMantsIm[RefIndex]);
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
    public GenericComplex getRef() {
        return new MantExpComplex(RefExp, RefRe, RefIm);
    }

    @Override
    protected boolean Step(LAInfoDeep out, int zRefIndex) {

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

        return outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex)  {
        LAInfoDeep Result = new LAInfoDeep();

        Step(Result, zRefIndex);
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
    protected LAInfoDeep Composite(LAInfoDeep LA)  {
        LAInfoDeep Result = new LAInfoDeep();

        Composite(Result, LA);
        return Result;
    }

    @Override
    public LAstep Prepare(MantExpComplex dz)  {
        //*2 is + 1
        MantExpComplex newdz = dz.times(new MantExpComplex(RefExp + 1, RefRe, RefIm).plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm().compareToBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant)) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }

    @Override
    public GenericLAInfo toDouble() {
        return new LAInfo(this);
    }

}
