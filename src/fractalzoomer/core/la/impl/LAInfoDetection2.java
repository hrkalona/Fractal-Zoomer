package fractalzoomer.core.la.impl;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ReferenceDecompressor;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.InvalidCalculationException;
import fractalzoomer.functions.Fractal;

import static fractalzoomer.core.la.LAReference.f;

public class LAInfoDetection2 extends LAInfo {
    protected double MinMag;

    protected LAInfoDetection2() {
        super();
    }

    protected LAInfoDetection2(int RefIndex, ReferenceDecompressor referenceDecompressor) {
        super(RefIndex, referenceDecompressor);
        MinMag = 4.0;
    }

    @Override
    protected boolean DetectDip(Complex z) {
        return z.chebyshevNorm() < MinMag * DipDetectionThreshold2;
    }

    @Override
    protected boolean Stage0DetectDip(Complex z) {
        return z.chebyshevNorm() < MinMag * Stage0DipDetectionThreshold2;
    }

    @Override
    protected boolean Step(LAInfo out1, int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {

        LAInfoDetection2 out = (LAInfoDetection2)out1;

        Complex z = f.getArrayValue(referenceDecompressor, Fractal.reference, zRefIndex);

        double ChebyMagz = z.chebyshevNorm();

        Complex ZCoeff = new Complex(ZCoeffRe, ZCoeffIm);
        Complex CCoeff = new Complex(CCoeffRe, CCoeffIm);

        double ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        double ChebyMagCCoeff = CCoeff.chebyshevNorm();

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

        return out.MinMag < MinMag * Stage0DipDetectionThreshold2;
    }

    @Override
    protected GenericLAInfo Step(int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        LAInfoDetection2 Result = new LAInfoDetection2();

        Step(Result, zRefIndex, referenceDecompressor);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfo out1, LAInfo LA1, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {

        LAInfoDetection2 out = (LAInfoDetection2)out1;
        LAInfoDetection2 LA = (LAInfoDetection2)LA1;

        Complex z = new Complex(LA.RefRe, LA.RefIm);
        Complex ZCoeff = new Complex(ZCoeffRe, ZCoeffIm);
        Complex CCoeff = new Complex(CCoeffRe, CCoeffIm);

        double ChebyMagz = z.chebyshevNorm();
        double ChebyMagZCoeff = ZCoeff.chebyshevNorm();
        double ChebyMagCCoeff = CCoeff.chebyshevNorm();

        out.LAThreshold = Math.min(LAThreshold, ChebyMagz / ChebyMagZCoeff * LAThresholdScale);
        out.LAThresholdC = Math.min(LAThresholdC, ChebyMagz / ChebyMagCCoeff * LAThresholdCScale);

        Complex z2 = z.times2();
        Complex outZCoeff = z2.times(ZCoeff);

        //double RescaleFactor = out.LAThreshold / LAThreshold;
        Complex outCCoeff = z2.times(CCoeff);



        ChebyMagZCoeff = outZCoeff.chebyshevNorm();
        ChebyMagCCoeff = outCCoeff.chebyshevNorm();

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

        return temp < MinMag * DipDetectionThreshold2;
    }

    @Override
    protected LAInfoDetection2 Composite(LAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        LAInfoDetection2 Result = new LAInfoDetection2();

        Composite(Result, LA, referenceDecompressor);
        return Result;
    }

    @Override
    public GenericLAInfo minimize() {
        return new LAInfo(this);
    }

    @Override
    public String toString() {
        return  ZCoeffRe + "\n" +
                ZCoeffIm + "\n" +
                CCoeffRe + "\n" +
                CCoeffIm + "\n" +
                LAThreshold + "\n" +
                LAThresholdC + "\n" +
                RefRe + "\n" +
                RefIm + "\n" +
                MinMag + "\n";
    }

    @Override
    public boolean isEqual(GenericLAInfo other) {
        if(other == null) {
            return false;
        }
        if(!(other instanceof LAInfoDetection2)) {
            return false;
        }
        LAInfoDetection2 oth = (LAInfoDetection2) other;
        if(this == other) {
            return true;
        }

        return ZCoeffRe == oth.ZCoeffRe &&
                ZCoeffIm == oth.ZCoeffIm &&
                CCoeffRe == oth.CCoeffRe &&
                CCoeffIm == oth.CCoeffIm &&
                LAThreshold == oth.LAThreshold &&
                LAThresholdC == oth.LAThresholdC &&
                RefRe == oth.RefRe &&
                RefIm == oth.RefIm &&
                MinMag == oth.MinMag;
    }
}
