package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;

public class LAInfo extends GenericLAInfo {
    public static int DETECTION_METHOD = ApproximationDefaultSettings.DETECTION_METHOD;
    public static double Stage0PeriodDetectionThreshold = ApproximationDefaultSettings.Stage0PeriodDetectionThreshold;
    public static double PeriodDetectionThreshold = ApproximationDefaultSettings.PeriodDetectionThreshold;
    public static double Stage0PeriodDetectionThreshold2 = ApproximationDefaultSettings.Stage0PeriodDetectionThreshold2;
    public static double PeriodDetectionThreshold2 = ApproximationDefaultSettings.PeriodDetectionThreshold2;
    public static double LAThresholdScale = ApproximationDefaultSettings.LAThresholdScale;
    public static double LAThresholdCScale = ApproximationDefaultSettings.LAThresholdCScale;

    private static final MantExp atLimit = new MantExp(0x1.0p256);
    protected double RefRe, RefIm;
    protected double ZCoeffRe, ZCoeffIm;
    protected double CCoeffRe, CCoeffIm;
    protected double LAThreshold, LAThresholdC;
    protected double MinMag;

    public LAInfo() {

    }

    public LAInfo(Complex z) {
        RefRe = z.getRe();
        RefIm = z.getIm();
        ZCoeffRe = 1.0;
        ZCoeffIm = 0.0;
        CCoeffRe = 1.0;
        CCoeffIm = 0.0;

        LAThreshold = 1.0;
        LAThresholdC = 1.0;

        if(DETECTION_METHOD == 1) {
            MinMag = 4.0;
        }

    }

    public LAInfo(LAInfoDeep deep) {
        Complex Ref = deep.getRef().toComplex();
        Complex ZCoeff = deep.getZCoeff().toComplex();
        Complex CCoeff = deep.getCCoeff().toComplex();

        RefRe = Ref.getRe();
        RefIm = Ref.getIm();

        ZCoeffRe = ZCoeff.getRe();
        ZCoeffIm = ZCoeff.getIm();

        CCoeffRe = CCoeff.getRe();
        CCoeffIm = CCoeff.getIm();

        LAThreshold = new MantExp(deep.LAThresholdExp, deep.LAThresholdMant).toDouble();
        LAThresholdC = new MantExp(deep.LAThresholdCExp, deep.LAThresholdCMant).toDouble();

        if(DETECTION_METHOD == 1) {
            MinMag = new MantExp(deep.MinMagExp, deep.MinMagMant).toDouble();
        }
    }

    /*protected boolean Stage0DetectPeriod(Complex z) {
        if(PERIOD_DETECTION_METHOD2) {
            return z.chebychevNorm() < MinMag * Stage0PeriodDetectionThreshold2;
        }
        else {
            return z.chebychevNorm() / ZCoeff.chebychevNorm() * LAThresholdScale < LAThreshold * Stage0PeriodDetectionThreshold;
        }
    }*/

    @Override
    protected boolean DetectPeriod(Complex z) {
        if(DETECTION_METHOD == 1) {
            return z.chebychevNorm() < MinMag * PeriodDetectionThreshold2;
        }
        else {
            return z.chebychevNorm() / new Complex(ZCoeffRe, ZCoeffIm).chebychevNorm() * LAThresholdScale < LAThreshold * PeriodDetectionThreshold;
        }
    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return false;
    }

    @Override
    protected GenericComplex getRef() {
        return new Complex(RefRe, RefIm);
    }

    @Override
    protected GenericComplex getZCoeff() {
        return new Complex(ZCoeffRe, ZCoeffIm);
    }

    @Override
    protected GenericComplex getCCoeff() {
        return new Complex(CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean Step(LAInfo out, Complex z) throws InvalidCalculationException {
        double ChebyMagz = z.chebychevNorm();

        Complex ZCoeff = new Complex(ZCoeffRe, ZCoeffIm);
        Complex CCoeff = new Complex(CCoeffRe, CCoeffIm);

        double ChebyMagZCoeff = ZCoeff.chebychevNorm();
        double ChebyMagCCoeff = CCoeff.chebychevNorm();

        if(DETECTION_METHOD == 1) {
            out.MinMag = Math.min(ChebyMagz, MinMag);
        }

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

        if(DETECTION_METHOD == 1) {
            return out.MinMag < MinMag * Stage0PeriodDetectionThreshold2;
        }
        else {
            return out.LAThreshold < LAThreshold * Stage0PeriodDetectionThreshold;
        }
    }

    @Override
    protected boolean isLAThresholdZero() {
        return LAThreshold == 0;
    }

    @Override
    protected boolean isZCoeffZero() {
        return new Complex(ZCoeffRe, ZCoeffIm).isZero();
    }

    @Override
    protected GenericLAInfo Step(Complex z) throws InvalidCalculationException {
        LAInfo Result = new LAInfo();

        Step(Result, z);
        return Result;
    }

    @Override
    protected GenericLAInfo Step(MantExpComplex z) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfo out, LAInfo LA) throws InvalidCalculationException {
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
        double temp = out.LAThreshold;
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

        if(DETECTION_METHOD == 1) {
            temp = Math.min (ChebyMagz, MinMag);
            out.MinMag = Math.min (temp, LA.MinMag);

            return temp < MinMag * PeriodDetectionThreshold2;
        }
        else {
            return temp < LAThreshold * PeriodDetectionThreshold;
        }
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA) {
        return false;
    }

    @Override
    protected boolean Step(LAInfoDeep out, MantExpComplex z) {
        return false;
    }

    @Override
    protected LAInfo Composite(LAInfo LA) throws InvalidCalculationException {
        LAInfo Result = new LAInfo();

        Composite(Result, LA);
        return Result;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeep LA) {
        return null;
    }

    protected LAstep Prepare(Complex dz, Complex dc)  {
        Complex newdz = dz.times(new Complex(RefRe, RefIm).times2().plus_mutable(dz));

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm() >= LAThreshold;
        temp.newDz = newdz;
        return temp;
    }

    public Complex Evaluate(Complex newdz, Complex dc) {
        return newdz.times(new Complex(ZCoeffRe, ZCoeffIm)).plus_mutable(dc.times(new Complex(CCoeffRe, CCoeffIm)));
    }


    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return  dzdc.times2().times_mutable(z).times_mutable(new MantExpComplex(ZCoeffRe, ZCoeffIm) ).plus_mutable(new MantExpComplex(CCoeffRe, CCoeffIm));
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return  dzdc2.times(z)
                .plus_mutable(dzdc.square()).times2_mutable().times_mutable(new MantExpComplex(ZCoeffRe, ZCoeffIm));
    }

    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {
        ATInfo Result = new ATInfo();

        MantExpComplex zCm = new MantExpComplex(new Complex(ZCoeffRe, ZCoeffIm));

        Result.ZCoeff = zCm;
        Result.CCoeff = zCm.times(new MantExpComplex(new Complex(CCoeffRe, CCoeffIm)));
        Result.CCoeff.Reduce();

        Result.InvZCoeff = zCm.reciprocal();
        Result.InvZCoeff.Reduce();

        Result.CCoeffSqrInvZCoeff = Result.CCoeff.square().times_mutable(Result.InvZCoeff);
        Result.CCoeffSqrInvZCoeff.Reduce();

        Result.CCoeffInvZCoeff = Result.CCoeff.times(Result.InvZCoeff);
        Result.CCoeffInvZCoeff.Reduce();

        Result.RefC = Next.getRef().toMantExpComplex().times(zCm);
        Result.RefC.Reduce();

        Result.CCoeffNormSqr = Result.CCoeff.norm_squared();
        Result.CCoeffNormSqr.Reduce();

        Result.RefCNormSqr = Result.RefC.norm_squared();
        Result.RefCNormSqr.Reduce();

        Result.SqrEscapeRadius = MantExp.minBothPositive(zCm.norm_squared().multiply(LAThreshold), atLimit).toDouble();

        Result.ThresholdC = MantExp.minBothPositive(new MantExp(LAThresholdC), atLimit.divide(Result.CCoeff.chebychevNorm()));
        Result.ThresholdC.Reduce();

        return Result;
    }

    @Override
    public MantExp getLAThreshold() {
        return null;
    }

    @Override
    public MantExp getLAThresholdC() {
        return null;
    }
}
