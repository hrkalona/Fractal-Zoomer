package fractalzoomer.core.la.impl_refindex;

import fractalzoomer.core.Complex;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.InvalidCalculationException;

public class LAInfoDetection2RI extends LAInfoRI {
    protected double MinMag;

    protected LAInfoDetection2RI() {
        super();
    }

    protected LAInfoDetection2RI(int RefIndex) {
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
    protected GenericLAInfo Step(int RefIndex) throws InvalidCalculationException {
        LAInfoDetection2RI Result = new LAInfoDetection2RI();

        Step(Result, RefIndex);
        return Result;
    }

    @Override
    protected boolean Step(LAInfoRI out1, int zRefIndex) throws InvalidCalculationException {

        LAInfoDetection2RI out = (LAInfoDetection2RI)out1;

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

        out.RefIndex = RefIndex;

        out.MinMag = Math.min(ChebyMagz, MinMag);

        return out.MinMag < MinMag * Stage0PeriodDetectionThreshold2;
    }

    @Override
    protected boolean Composite(LAInfoRI out1, LAInfoRI LA1) throws InvalidCalculationException {

        LAInfoDetection2RI out = (LAInfoDetection2RI)out1;
        LAInfoDetection2RI LA = (LAInfoDetection2RI)LA1;

        int zRefIndex = LA.RefIndex;
        Complex z = new Complex(refRe[zRefIndex], refIm[zRefIndex]);
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

        out.RefIndex = RefIndex;

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
    protected LAInfoDetection2RI Composite(LAInfoRI LA) throws InvalidCalculationException {
        LAInfoDetection2RI Result = new LAInfoDetection2RI();

        Composite(Result, LA);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfoRI(this);
    }
}
