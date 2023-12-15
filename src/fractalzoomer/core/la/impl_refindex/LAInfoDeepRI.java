package fractalzoomer.core.la.impl_refindex;

import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.LAInfoBaseDeep;
import fractalzoomer.core.la.LAstep;
import fractalzoomer.core.la.impl.LAInfo;

public class LAInfoDeepRI extends LAInfoBaseDeep {
    int RefIndex;
    public static LAInfoDeepRI create() {

        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2FullRI();
            }
            return new LAInfoDeepDetection2RI();
        }
        else {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFullRI();
            }
            return new LAInfoDeepRI();
        }
    }

    public static LAInfoDeepRI create(int RefIndex) {
        if(LAInfo.DETECTION_METHOD == 1) {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepDetection2FullRI(RefIndex);
            }
            return new LAInfoDeepDetection2RI(RefIndex);
        }
        else {
            if (TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                return new LAInfoDeepFullRI(RefIndex);
            }
            return new LAInfoDeepRI(RefIndex);
        }
    }


    protected LAInfoDeepRI() {

    }

    protected LAInfoDeepRI(LAInfoDeepRI other) {
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
    public GenericComplex getRef() {
        return new MantExpComplex(refExpRe[RefIndex], refMantsRe[RefIndex], refMantsIm[RefIndex]);
    }

    @Override
    protected boolean Step(LAInfoDeepRI out, int zRefIndex) {
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

        out.RefIndex = RefIndex;

        return outLAThreshold.compareToBothPositive(LAThreshold.multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA) {
        int zRefIndex = LA.RefIndex;
        MantExpComplex z = new MantExpComplex(refExpRe[zRefIndex], refMantsRe[zRefIndex], refMantsIm[zRefIndex]);
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

        out.RefIndex = RefIndex;

        return temp.compareToBothPositive(LAThreshold.multiply(PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected LAInfoDeepRI Composite(LAInfoDeepRI LA)  {
        LAInfoDeepRI Result = new LAInfoDeepRI();

        Composite(Result, LA);
        return Result;
    }

    @Override
    protected GenericLAInfo Step(int RefIndex) {
        LAInfoDeepRI Result = new LAInfoDeepRI();

        Step(Result, RefIndex);
        return Result;
    }

    @Override
    protected LAstep Prepare(MantExpComplex dz)  {
        //*2 is + 1

        MantExpComplex newdz = dz.times(new MantExpComplex(refExpRe[RefIndex] + 1, refMantsRe[RefIndex], refMantsIm[RefIndex]).plus_mutable(dz));
        newdz.Normalize();

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm().compareToBothPositiveReduced(new MantExp(LAThresholdExp, LAThresholdMant)) >= 0;
        temp.newDzDeep = newdz;
        return temp;
    }
    @Override
    public GenericLAInfo toDouble() {
        return new LAInfoRI(this);
    }
}
