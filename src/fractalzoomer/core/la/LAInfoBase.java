package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.core.la.impl.LAInfoDeep;
import fractalzoomer.core.la.impl_refindex.LAInfoDeepRI;
import fractalzoomer.core.la.impl_refindex.LAInfoRI;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;

public abstract  class LAInfoBase extends GenericLAInfo {
    public static int DETECTION_METHOD = ApproximationDefaultSettings.DETECTION_METHOD;
    public static double Stage0PeriodDetectionThreshold = ApproximationDefaultSettings.Stage0PeriodDetectionThreshold;
    public static double PeriodDetectionThreshold = ApproximationDefaultSettings.PeriodDetectionThreshold;
    public static double Stage0PeriodDetectionThreshold2 = ApproximationDefaultSettings.Stage0PeriodDetectionThreshold2;
    public static double PeriodDetectionThreshold2 = ApproximationDefaultSettings.PeriodDetectionThreshold2;
    public static double LAThresholdScale = ApproximationDefaultSettings.LAThresholdScale;
    public static double LAThresholdCScale = ApproximationDefaultSettings.LAThresholdCScale;

    public static final MantExp atLimit = new MantExp(0x1.0p256);

    protected double ZCoeffRe, ZCoeffIm;
    protected double CCoeffRe, CCoeffIm;
    protected double LAThreshold, LAThresholdC;

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return false;
    }

    @Override
    protected boolean Stage0DetectPeriod(MantExpComplex z) {
        return false;
    }

    @Override
    protected boolean Step(LAInfoRI out, int zRefIndex) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean isLAThresholdZero() {
        return LAThreshold == 0;
    }

    @Override
    protected boolean Composite(LAInfoRI out, LAInfoRI LA) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA) {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA) {
        return false;
    }

    @Override
    protected boolean Step(LAInfoDeep out, int zRefIndex) {
        return false;
    }

    @Override
    protected boolean Step(LAInfoDeepRI out, int zRefIndex) {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoRI LA) throws InvalidCalculationException {
        return null;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeep LA) {
        return null;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeepRI LA) {
        return null;
    }

    @Override
    public MantExp getLAThreshold() {
        return null;
    }

    @Override
    public MantExp getLAThresholdC() {
        return null;
    }

    @Override
    public double dgetLAThreshold() {
        return LAThreshold;
    }

    @Override
    public double dgetLAThresholdC() {
        return LAThresholdC;
    }

    @Override
    protected boolean Step(LAInfo out, int zRefIndex) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfo LA) throws InvalidCalculationException {
        return null;
    }

    @Override
    protected boolean Composite(LAInfo out, LAInfo LA) throws InvalidCalculationException {
        return false;
    }

    @Override
    public GenericComplex getZCoeff() {
        return new Complex(ZCoeffRe, ZCoeffIm);
    }

    @Override
    public GenericComplex getCCoeff() {
        return new Complex(CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean isZCoeffZero() {
        return new Complex(ZCoeffRe, ZCoeffIm).isZero();
    }

    @Override
    public Complex Evaluate(Complex newdz, Complex dc) {
        return newdz.times(new Complex(ZCoeffRe, ZCoeffIm)).plus_mutable(dc.times(new Complex(CCoeffRe, CCoeffIm)));
    }

    @Override
    public Complex Evaluate(Complex newdz) {
        return newdz.times(new Complex(ZCoeffRe, ZCoeffIm));
    }

    @Override
    public Complex Evaluate(double newdre, double newdim, double d0re, double d0im) {

        return new Complex(newdre * ZCoeffRe - newdim * ZCoeffIm + d0re * CCoeffRe - d0im * CCoeffIm,
                newdre * ZCoeffIm + newdim * ZCoeffRe + d0re * CCoeffIm + d0im * CCoeffRe);

    }

    @Override
    public Complex Evaluate(double newdre, double newdim) {

        return new Complex(newdre * ZCoeffRe - newdim * ZCoeffIm,
                newdre * ZCoeffIm + newdim * ZCoeffRe);

    }


    @Override
    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return  dzdc.times2().times_mutable(z).times_mutable(MantExpComplex.create(ZCoeffRe, ZCoeffIm) ).plus_mutable(MantExpComplex.create(CCoeffRe, CCoeffIm));
    }

    @Override
    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return  dzdc2.times(z)
                .plus_mutable(dzdc.square()).times2_mutable().times_mutable(MantExpComplex.create(ZCoeffRe, ZCoeffIm));
    }

    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {

        return CreateAT(Next, new Complex(ZCoeffRe, ZCoeffIm), new Complex(CCoeffRe, CCoeffIm), LAThreshold, LAThresholdC);

    }

    public static ATInfo CreateAT(GenericLAInfo Next, Complex ZCoeff, Complex CCoeff, double LAThreshold, double LAThresholdC) {
        ATInfo Result = new ATInfo();

        MantExpComplex zCm = MantExpComplex.create(ZCoeff);

        Result.ZCoeff = zCm;
        Result.CCoeff = zCm.times(MantExpComplex.create(CCoeff));
        Result.CCoeff.Normalize();

        Result.InvZCoeff = zCm.reciprocal();
        Result.InvZCoeff.Normalize();

        Result.CCoeffSqrInvZCoeff = Result.CCoeff.square().times_mutable(Result.InvZCoeff);
        Result.CCoeffSqrInvZCoeff.Normalize();

        Result.CCoeffInvZCoeff = Result.CCoeff.times(Result.InvZCoeff);
        Result.CCoeffInvZCoeff.Normalize();

        Result.RefC = Next.getRef().toMantExpComplex().times(zCm);
        Result.RefC.Normalize();

        Result.SqrEscapeRadius = MantExp.minBothPositive(zCm.norm_squared().multiply(LAThreshold), atLimit).toDouble();

        Result.ThresholdC = MantExp.minBothPositive(new MantExp(LAThresholdC), atLimit.divide(Result.CCoeff.chebychevNorm()));
        Result.ThresholdC.Normalize();

        return Result;
    }

    @Override
    protected boolean Stage0DetectPeriod(Complex z) {
        return z.chebychevNorm() / new Complex(ZCoeffRe, ZCoeffIm).chebychevNorm() * LAThresholdScale < LAThreshold * Stage0PeriodDetectionThreshold;
    }

    @Override
    protected boolean DetectPeriod(Complex z) {
        return z.chebychevNorm() / new Complex(ZCoeffRe, ZCoeffIm).chebychevNorm() * LAThresholdScale < LAThreshold * PeriodDetectionThreshold;
    }


}
