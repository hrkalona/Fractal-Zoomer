package fractalzoomer.core.la.impl;

import fractalzoomer.core.Complex;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.InvalidCalculationException;

public class LAInfoDetection2 extends LAInfo {
    protected double MinMag;

    protected LAInfoDetection2() {
        super();
    }

    protected LAInfoDetection2(int RefIndex) {
        super(RefIndex);
        MinMag = 4.0;
    }

    @Override
    protected boolean DetectPeriod(Complex z) {
        return z.chebychevNorm() < MinMag * PeriodDetectionThreshold2;
    }

    @Override
    protected boolean Stage0DetectPeriod(Complex z) {
        return z.chebychevNorm() < MinMag * Stage0PeriodDetectionThreshold2;
    }

    @Override
    protected boolean Step(LAInfo out1, int zRefIndex) throws InvalidCalculationException {

        LAInfoDetection2 out = (LAInfoDetection2)out1;

        Complex z = new Complex(refRe[zRefIndex], refIm[zRefIndex]);

        double ChebyMagz = z.chebychevNorm();

        Complex ZCoeff = new Complex(ZCoeffRe, ZCoeffIm);
        Complex CCoeff = new Complex(CCoeffRe, CCoeffIm);

        double ChebyMagZCoeff = ZCoeff.chebychevNorm();
        double ChebyMagCCoeff = CCoeff.chebychevNorm();

        out.LAThreshold = Math.min(LAThreshold, ChebyMagz / ChebyMagZCoeff * LAThresholdScale);
        out.LAThresholdC = Math.min(LAThresholdC, ChebyMagz / ChebyMagCCoeff * LAThresholdCScale);

        Complex z2 = z.times2();
        Complex outZCoeff = z2.times(ZCoeff);
        Complex outCCoeff = z2.times(CCoeff).plus_mutable(1);

        out.ZCoeffRe = outZCoeff.getRe();
        out.ZCoeffIm = outZCoeff.getIm();

        out.CCoeffRe = outCCoeff.getRe();
        out.CCoeffIm = outCCoeff.getIm();

        if(!Double.isFinite(out.ZCoeffRe) || !Double.isFinite(out.ZCoeffIm)
                || !Double.isFinite(out.CCoeffRe) || !Double.isFinite(out.CCoeffIm)
        || !Double.isFinite(out.LAThreshold) || !Double.isFinite(out.LAThresholdC)) {
            throw new InvalidCalculationException("Invalid calculations");
        }

        out.RefRe = RefRe;
        out.RefIm = RefIm;

        out.MinMag = Math.min(ChebyMagz, MinMag);

        return out.MinMag < MinMag * Stage0PeriodDetectionThreshold2;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex) throws InvalidCalculationException {
        LAInfoDetection2 Result = new LAInfoDetection2();

        Step(Result, zRefIndex);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfo out1, LAInfo LA1) throws InvalidCalculationException {

        LAInfoDetection2 out = (LAInfoDetection2)out1;
        LAInfoDetection2 LA = (LAInfoDetection2)LA1;

        Complex z = new Complex(LA.RefRe, LA.RefIm);
        Complex ZCoeff = new Complex(ZCoeffRe, ZCoeffIm);
        Complex CCoeff = new Complex(CCoeffRe, CCoeffIm);

        double ChebyMagz = z.chebychevNorm();
        double ChebyMagZCoeff = ZCoeff.chebychevNorm();
        double ChebyMagCCoeff = CCoeff.chebychevNorm();

        out.LAThreshold = Math.min(LAThreshold, ChebyMagz / ChebyMagZCoeff * LAThresholdScale);
        out.LAThresholdC = Math.min(LAThresholdC, ChebyMagz / ChebyMagCCoeff * LAThresholdCScale);

        Complex z2 = z.times2();
        Complex outZCoeff = z2.times(ZCoeff);

        //double RescaleFactor = out.LAThreshold / LAThreshold;
        Complex outCCoeff = z2.times(CCoeff);



        ChebyMagZCoeff = outZCoeff.chebychevNorm();
        ChebyMagCCoeff = outCCoeff.chebychevNorm();

        out.LAThreshold = Math.min(out.LAThreshold, LA.LAThreshold / ChebyMagZCoeff);
        out.LAThresholdC = Math.min(out.LAThresholdC, LA.LAThreshold / ChebyMagCCoeff);

        Complex LAZCoeff = new Complex(LA.ZCoeffRe, LA.ZCoeffIm);
        Complex LACCoeff = new Complex(LA.CCoeffRe, LA.CCoeffIm);

        outZCoeff = outZCoeff.times(LAZCoeff);
        //RescaleFactor = out.LAThreshold / temp;
        outCCoeff = outCCoeff.times(LAZCoeff).plus_mutable(LACCoeff);

        out.RefRe = RefRe;
        out.RefIm = RefIm;

        out.ZCoeffRe = outZCoeff.getRe();
        out.ZCoeffIm = outZCoeff.getIm();

        out.CCoeffRe = outCCoeff.getRe();
        out.CCoeffIm = outCCoeff.getIm();

        if(!Double.isFinite(out.ZCoeffRe) || !Double.isFinite(out.ZCoeffIm)
        || !Double.isFinite(out.CCoeffRe) || !Double.isFinite(out.CCoeffIm)
                || !Double.isFinite(out.LAThreshold) || !Double.isFinite(out.LAThresholdC)) {
            throw new InvalidCalculationException("Invalid calculations");
        }

        double temp = Math.min (ChebyMagz, MinMag);
        out.MinMag = Math.min (temp, LA.MinMag);

        return temp < MinMag * PeriodDetectionThreshold2;
    }

    @Override
    protected LAInfoDetection2 Composite(LAInfo LA) throws InvalidCalculationException {
        LAInfoDetection2 Result = new LAInfoDetection2();

        Composite(Result, LA);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfo(this);
    }
}
