package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.core.la.impl.LAInfoDeep;
import fractalzoomer.core.la.impl_refindex.LAInfoDeepRI;
import fractalzoomer.core.la.impl_refindex.LAInfoRI;

import static fractalzoomer.core.la.LAInfoBase.atLimit;

public abstract class LAInfoBaseDeep extends GenericLAInfo {

    public static MantExp Stage0PeriodDetectionThreshold = new MantExp(LAInfo.Stage0PeriodDetectionThreshold);
    public static MantExp PeriodDetectionThreshold = new MantExp(LAInfo.PeriodDetectionThreshold);
    public static MantExp Stage0PeriodDetectionThreshold2 = new MantExp(LAInfo.Stage0PeriodDetectionThreshold2);
    public static MantExp PeriodDetectionThreshold2 = new MantExp(LAInfo.PeriodDetectionThreshold2);
    public static MantExp LAThresholdScale = new MantExp(LAInfo.LAThresholdScale);
    public static MantExp LAThresholdCScale = new MantExp(LAInfo.LAThresholdCScale);

    protected double ZCoeffRe, ZCoeffIm;
    protected long ZCoeffExp;

    protected double CCoeffRe, CCoeffIm;
    protected long CCoeffExp;

    public double LAThresholdMant;
    public long LAThresholdExp;

    public double LAThresholdCMant;
    public long LAThresholdCExp;

    @Override
    public MantExp getLAThreshold() {
        return new MantExp(LAThresholdExp, LAThresholdMant);
    }

    @Override
    public MantExp getLAThresholdC() {
        return new MantExp(LAThresholdCExp, LAThresholdCMant);
    }

    @Override
    public double dgetLAThreshold() {
        return 0;
    }

    @Override
    public double dgetLAThresholdC() {
        return 0;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeepRI LA) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfo out, LAInfo LA) {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoRI out, LAInfoRI LA) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean Step(LAInfoDeepRI out, int zRefIndex) {
        return false;
    }

    @Override
    protected boolean Step(LAInfo out, int zRefIndex) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean Step(LAInfoRI out, int zRefIndex) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean isLAThresholdZero() {
        return new MantExp(LAThresholdExp, LAThresholdMant).compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected boolean DetectPeriod(Complex z) {
        return false;
    }

    @Override
    protected boolean Stage0DetectPeriod(Complex z) {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA) {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfo LA) {
        return null;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoRI LA) throws InvalidCalculationException {
        return null;
    }

    @Override
    protected boolean Step(LAInfoDeep out, int zRefIndex) {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeep LA) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA) {
        return false;
    }

    @Override
    protected boolean DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().divide(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm).chebychevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(PeriodDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Stage0DetectPeriod(MantExpComplex z) {
        return z.chebychevNorm().divide(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm).chebychevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(Stage0PeriodDetectionThreshold)) < 0;
    }

    @Override
    public GenericComplex getZCoeff() {
        return new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
    }

    @Override
    public GenericComplex getCCoeff() {
        return new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm);
    }

    @Override
    protected boolean isZCoeffZero() {
        MantExpComplex ZCoeff = new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm);
        return ZCoeff.getRe().compareTo(MantExp.ZERO) == 0 && ZCoeff.getIm().compareTo(MantExp.ZERO) == 0;
    }

    @Override
    public MantExpComplex Evaluate(MantExpComplex newdz, MantExpComplex dc) {
        return newdz.times(ZCoeffExp, ZCoeffRe, ZCoeffIm).plus_mutable(dc.times(CCoeffExp, CCoeffRe, CCoeffIm));
    }

    @Override
    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return  dzdc.times2().times_mutable(z).times_mutable(ZCoeffExp, ZCoeffRe, ZCoeffIm).plus_mutable(CCoeffExp, CCoeffRe, CCoeffIm);
    }

    @Override
    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return  dzdc2.times(z)
                .plus_mutable(dzdc.square()).times2_mutable().times_mutable(ZCoeffExp, ZCoeffRe, ZCoeffIm);
    }

    public static ATInfo CreateAT(GenericLAInfo Next, MantExpComplex ZCoeff, MantExpComplex CCoeff, long LAThresholdExp, double LAThresholdMant,
                                  long LAThresholdCExp, double LAThresholdCMant) {

        ATInfo Result = new ATInfo();

        MantExp LAThreshold = new MantExp(LAThresholdExp, LAThresholdMant);
        MantExp LAThresholdC = new MantExp(LAThresholdCExp, LAThresholdCMant);

        Result.ZCoeff = ZCoeff;
        Result.CCoeff = ZCoeff.times(CCoeff);
        Result.CCoeff.Normalize();

        Result.InvZCoeff = ZCoeff.reciprocal();
        Result.InvZCoeff.Normalize();

        Result.CCoeffSqrInvZCoeff = Result.CCoeff.square().times_mutable(Result.InvZCoeff);
        Result.CCoeffSqrInvZCoeff.Normalize();

        Result.CCoeffInvZCoeff = Result.CCoeff.times(Result.InvZCoeff);
        Result.CCoeffInvZCoeff.Normalize();

        Result.RefC = Next.getRef().toMantExpComplex().times(ZCoeff);
        Result.RefC.Normalize();

        Result.SqrEscapeRadius = MantExp.minBothPositive(ZCoeff.norm_squared().multiply(LAThreshold), atLimit).toDouble();

        Result.ThresholdC = MantExp.minBothPositive(LAThresholdC, atLimit.divide(Result.CCoeff.chebychevNorm()));

        return Result;
    }
    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {
        return CreateAT(Next, new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm), new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm), LAThresholdExp, LAThresholdMant, LAThresholdCExp, LAThresholdCMant);
    }
}
