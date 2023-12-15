package fractalzoomer.core.la.impl_refindex;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.la.GenericLAInfo;
import fractalzoomer.core.la.InvalidCalculationException;
import fractalzoomer.core.la.LAInfoBase;
import fractalzoomer.core.la.LAstep;
import fractalzoomer.core.la.impl.LAInfo;

public class LAInfoRI extends LAInfoBase {
    int RefIndex;

    public static LAInfoRI create() {
        if(LAInfo.DETECTION_METHOD == 1) {
            return new LAInfoDetection2RI();
        }
        return new LAInfoRI();
    }

    public static LAInfoRI create(int RefIndex) {
        if(LAInfo.DETECTION_METHOD == 1) {
            return new LAInfoDetection2RI(RefIndex);
        }
        return new LAInfoRI(RefIndex);
    }

    protected LAInfoRI() {

    }

    protected LAInfoRI(int RefIndex) {
        this.RefIndex = RefIndex;
        ZCoeffRe = 1.0;
        ZCoeffIm = 0.0;
        CCoeffRe = 1.0;
        CCoeffIm = 0.0;

        LAThreshold = 1.0;
        LAThresholdC = 1.0;
    }

    protected LAInfoRI(LAInfoRI other) {
        RefIndex = other.RefIndex;

        ZCoeffRe = other.ZCoeffRe;
        ZCoeffIm = other.ZCoeffIm;

        CCoeffRe = other.CCoeffRe;
        CCoeffIm = other.CCoeffIm;

        LAThreshold = other.LAThreshold;
        LAThresholdC = other.LAThresholdC;
    }

    protected LAInfoRI(LAInfoDeepRI deep) {
        Complex ZCoeff = deep.getZCoeff().toComplex();
        Complex CCoeff = deep.getCCoeff().toComplex();

        RefIndex = deep.RefIndex;

        ZCoeffRe = ZCoeff.getRe();
        ZCoeffIm = ZCoeff.getIm();

        CCoeffRe = CCoeff.getRe();
        CCoeffIm = CCoeff.getIm();

        LAThreshold = new MantExp(deep.LAThresholdExp, deep.LAThresholdMant).toDouble();
        LAThresholdC = new MantExp(deep.LAThresholdCExp, deep.LAThresholdCMant).toDouble();
    }

    @Override
    public GenericComplex getRef() {
        return new Complex(refRe[RefIndex], refIm[RefIndex]);
    }

    @Override
    protected boolean Step(LAInfoRI out, int zRefIndex) throws InvalidCalculationException {
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

        return out.LAThreshold < LAThreshold * Stage0PeriodDetectionThreshold;
    }

    @Override
    protected GenericLAInfo Step(int RefIndex) throws InvalidCalculationException {
        LAInfoRI Result = new LAInfoRI();

        Step(Result, RefIndex);
        return Result;
    }

    @Override
    protected boolean Composite(LAInfoRI out, LAInfoRI LA) throws InvalidCalculationException {
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
        double temp = out.LAThreshold;
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

        return temp < LAThreshold * PeriodDetectionThreshold;
    }

    @Override
    protected LAInfoRI Composite(LAInfoRI LA) throws InvalidCalculationException {
        LAInfoRI Result = new LAInfoRI();

        Composite(Result, LA);
        return Result;
    }

    @Override
    protected LAstep Prepare(Complex dz)  {
        Complex newdz = dz.times(new Complex(2 * refRe[RefIndex], 2 * refIm[RefIndex]).plus_mutable(dz));

        LAstep temp = new LAstep();
        temp.unusable = newdz.chebychevNorm() >= LAThreshold;
        temp.newDz = newdz;
        return temp;
    }

    @Override
    protected LAstep Prepare(double dre, double dim)  {
        double newdre = 2 * refRe[RefIndex] + dre;
        double newdim = 2 * refIm[RefIndex] + dim;

        double temp = newdre * dre - newdim * dim;
        newdim = newdre * dim + newdim * dre;
        newdre = temp;

        LAstep tempstep = new LAstep();
        tempstep.unusable = Math.max(Math.abs(newdre), Math.abs(newdim)) >= LAThreshold;
        tempstep.newdre = newdre;
        tempstep.newdim = newdim;
        return tempstep;
    }
}
