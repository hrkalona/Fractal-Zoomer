package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfo;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfoDeep;
import fractalzoomer.core.approximation.la_zhuoran.impl_refindex.LAInfoDeepRI;
import fractalzoomer.core.approximation.la_zhuoran.impl_refindex.LAInfoRI;
import fractalzoomer.core.reference.ReferenceDecompressor;

public abstract class LAInfoBaseDeep extends GenericLAInfo {

    public static MantExp Stage0DipDetectionThreshold = new MantExp(LAInfo.Stage0DipDetectionThreshold);
    public static MantExp DipDetectionThreshold = new MantExp(LAInfo.DipDetectionThreshold);
    public static MantExp Stage0DipDetectionThreshold2 = new MantExp(LAInfo.Stage0DipDetectionThreshold2);
    public static MantExp DipDetectionThreshold2 = new MantExp(LAInfo.DipDetectionThreshold2);
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

    protected LAInfoBaseDeep(int RefIndex) {
        super(RefIndex);
    }

    protected LAInfoBaseDeep(LAInfoBaseDeep other) {
        super(other);
    }

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
    protected GenericLAInfo Composite(LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfo out, LAInfo LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoRI out, LAInfoRI LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean Step(LAInfoDeepRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        return false;
    }

    @Override
    protected boolean Step(LAInfo out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean Step(LAInfoRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException {
        return false;
    }

    @Override
    protected boolean isLAThresholdZero() {
        return new MantExp(LAThresholdExp, LAThresholdMant).compareTo(MantExp.ZERO) == 0;
    }

    @Override
    protected boolean DetectDip(Complex z) {
        return false;
    }

    @Override
    protected boolean Stage0DetectDip(Complex z) {
        return false;
    }

    @Override
    protected boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfo LA, ReferenceDecompressor referenceDecompressor) {
        return null;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoRI LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        return null;
    }

    @Override
    protected boolean Step(LAInfoDeep out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        return false;
    }

    @Override
    protected GenericLAInfo Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor) {
        return null;
    }

    @Override
    protected boolean Composite(LAInfoDeep out, LAInfoDeep LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) {
        return false;
    }

    @Override
    protected boolean DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().divide(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm).chebyshevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    protected boolean Stage0DetectDip(MantExpComplex z) {
        return z.chebyshevNorm().divide(new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm).chebyshevNorm()).multiply_mutable(LAThresholdScale).compareToBothPositive(new MantExp(LAThresholdExp, LAThresholdMant).multiply(Stage0DipDetectionThreshold)) < 0;
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

        Result.RefC = Next.getRef(LAReference.f).toMantExpComplex().times(ZCoeff);
        Result.RefC.Normalize();

        Result.SqrEscapeRadius = MantExp.minBothPositive(ZCoeff.norm_squared().multiply(LAThreshold), LAInfoBase.atLimit).toDouble();

        Result.ThresholdC = MantExp.minBothPositive(LAThresholdC, LAInfoBase.atLimit.divide(Result.CCoeff.chebyshevNorm()));

        Result.CCoeffNormSquaredTFactor = Result.CCoeff.norm_squared().multiply_mutable(ATInfo.factor);
        Result.CCoeffNormSquaredTFactor.Normalize();

        Result.RefCNormSquared = Result.RefC.norm_squared();
        Result.RefCNormSquared.Normalize();

        return Result;
    }
    @Override
    protected ATInfo CreateAT(GenericLAInfo Next) {
        return CreateAT(Next, new MantExpComplex(ZCoeffExp, ZCoeffRe, ZCoeffIm), new MantExpComplex(CCoeffExp, CCoeffRe, CCoeffIm), LAThresholdExp, LAThresholdMant, LAThresholdCExp, LAThresholdCMant);
    }
}
