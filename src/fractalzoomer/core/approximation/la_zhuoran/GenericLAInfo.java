package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.impl.*;
import fractalzoomer.core.approximation.la_zhuoran.impl_refindex.*;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.functions.Fractal;

public abstract class GenericLAInfo {
    protected int StepLength;
    protected int NextStageLAIndex;

    private static final int ITERATIONS_MEMORY_THRESHOLD = 50_000_000;

    public static GenericLAInfo create(int length, boolean deepZoom, int refIndex, ReferenceDecompressor referenceDecompressor) {
        if(TaskRender.USE_RI_ON_BLA2 || (!TaskRender.DISABLE_RI_ON_BLA2 && !TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE && length > ITERATIONS_MEMORY_THRESHOLD)) {
            if (deepZoom) {
                return LAInfoDeepRI.create(refIndex);
            } else {
                return LAInfoRI.create(refIndex);
            }
        }
        else {
            if (deepZoom) {
                return LAInfoDeep.create(refIndex, referenceDecompressor);
            } else {
                return LAInfo.create(refIndex, referenceDecompressor);
            }
        }
    }

    public static GenericLAInfo create(int length, boolean deepZoom) {
        if(TaskRender.USE_RI_ON_BLA2 || (!TaskRender.DISABLE_RI_ON_BLA2 && !TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE && length > ITERATIONS_MEMORY_THRESHOLD)) {
            if (deepZoom) {
                return LAInfoDeepRI.create();
            } else {
                return LAInfoRI.create();
            }
        }
        else {
            if (deepZoom) {
                return LAInfoDeep.create();
            } else {
                return LAInfo.create();
            }
        }
    }

    public static GenericLAInfo copy(GenericLAInfo other) {
        if(other instanceof LAInfoDetection2) {
            return new LAInfoDetection2((LAInfoDetection2) other);
        }

        if(other instanceof LAInfoDeepDetection2Full) {
            return new LAInfoDeepDetection2Full((LAInfoDeepDetection2Full) other);
        }

        if(other instanceof LAInfoDeepDetection2) {
            return new LAInfoDeepDetection2((LAInfoDeepDetection2) other);
        }

        if(other instanceof LAInfoDeepFull) {
            return new LAInfoDeepFull((LAInfoDeepFull) other);
        }

        if(other instanceof LAInfoDeep) {
            return new LAInfoDeep((LAInfoDeep) other);
        }

        if(other instanceof LAInfo) {
            return new LAInfo((LAInfo) other);
        }

        //RI
        if(other instanceof LAInfoDetection2RI) {
            return new LAInfoDetection2RI((LAInfoDetection2RI) other);
        }

        if(other instanceof LAInfoDeepDetection2FullRI) {
            return new LAInfoDeepDetection2FullRI((LAInfoDeepDetection2FullRI) other);
        }

        if(other instanceof LAInfoDeepDetection2RI) {
            return new LAInfoDeepDetection2RI((LAInfoDeepDetection2RI) other);
        }

        if(other instanceof LAInfoDeepFullRI) {
            return new LAInfoDeepFullRI((LAInfoDeepFullRI) other);
        }

        if(other instanceof LAInfoDeepRI) {
            return new LAInfoDeepRI((LAInfoDeepRI) other);
        }

        if(other instanceof LAInfoRI) {
            return new LAInfoRI((LAInfoRI) other);
        }

        return null;
    }

    protected GenericLAInfo(int RefIndex) {
        StepLength = 1;
        NextStageLAIndex = RefIndex;
    }

    protected GenericLAInfo(GenericLAInfo other) {
        StepLength = other.StepLength;
        NextStageLAIndex = other.NextStageLAIndex;
    }

    public void invalidateInfo() {
        StepLength = -1;
        NextStageLAIndex = -1;
    }

    public void clearInfo() {
        StepLength = 0;
        NextStageLAIndex = 0;
    }
    public void setNextStageLAIndex(int NextStageLAIndex) {
        this.NextStageLAIndex = NextStageLAIndex;
    }

    protected abstract GenericLAInfo Composite(LAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract GenericLAInfo Composite(LAInfoRI LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract GenericLAInfo Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor);
    protected abstract GenericLAInfo Composite(LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor);
    protected abstract GenericLAInfo Step(int RefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfo out, LAInfo LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoRI out, LAInfoRI LA, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoDeep out, LAInfoDeep LA, ReferenceDecompressor referenceDecompressor, boolean checkDip);
    protected abstract boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor, boolean checkDip);
    protected abstract boolean Step(LAInfoDeep out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip);
    protected abstract boolean Step(LAInfoDeepRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip);
    protected abstract boolean Step(LAInfo out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException;
    protected abstract boolean Step(LAInfoRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor, boolean checkDip) throws InvalidCalculationException;

    protected boolean Step(GenericLAInfo out, int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        if(out instanceof LAInfo) {
            return Step((LAInfo) out, zRefIndex, referenceDecompressor, true);
        }
        else if(out instanceof LAInfoRI) {
            return Step((LAInfoRI) out, zRefIndex, referenceDecompressor, true);
        }
        else if(out instanceof LAInfoDeep) {
            return Step((LAInfoDeep) out, zRefIndex, referenceDecompressor, true);
        }
        else {
            return Step((LAInfoDeepRI) out, zRefIndex, referenceDecompressor, true);
        }
    }

    protected GenericLAInfo Composite(GenericLAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) LA, referenceDecompressor);
        }
        else if(LA instanceof  LAInfoRI) {
            return Composite((LAInfoRI) LA, referenceDecompressor);
        }
        else if(LA instanceof  LAInfoDeep) {
            return Composite((LAInfoDeep) LA, referenceDecompressor);
        }
        else {
            return Composite((LAInfoDeepRI) LA, referenceDecompressor);
        }
    }

    protected boolean Composite(GenericLAInfo out, GenericLAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) out, (LAInfo) LA, referenceDecompressor, true);
        }
        else if(LA instanceof  LAInfoRI) {
            return Composite((LAInfoRI) out, (LAInfoRI) LA, referenceDecompressor, true);
        }
        else if(LA instanceof  LAInfoDeep) {
            return Composite((LAInfoDeep) out, (LAInfoDeep) LA, referenceDecompressor, true);
        }
        else  {
            return Composite((LAInfoDeepRI) out, (LAInfoDeepRI) LA, referenceDecompressor, true);
        }
    }

    protected abstract boolean isLAThresholdZero();
    protected abstract boolean isZCoeffZero();
    protected abstract boolean DetectDip(Complex z);
    protected abstract boolean Stage0DetectDip(Complex z);
    protected abstract boolean DetectDip(MantExpComplex z);
    protected abstract boolean Stage0DetectDip(MantExpComplex z);

    protected boolean DetectDip(GenericComplex z) {
        if(z instanceof  Complex) {
            return DetectDip((Complex) z);
        }
        else {
            return DetectDip((MantExpComplex) z);
        }
    }

    public abstract GenericComplex getRef(Fractal f);
    public Complex getRefDouble() {return  null;}

    public abstract GenericComplex getZCoeff();

    public abstract GenericComplex getCCoeff();
    protected abstract ATInfo CreateAT(GenericLAInfo Next);

    public abstract MantExp getLAThreshold();
    public abstract MantExp getLAThresholdC();

    public abstract double dgetLAThreshold();
    public abstract double dgetLAThresholdC();

    public GenericLAInfo minimize() {
        return this;
    }

    public GenericLAInfo toDouble() {
        return this;
    }

    protected LAstep Prepare(Fractal f, MantExpComplex dz)  {
        return null;
    }

    protected LAstep Prepare(Fractal f, Complex dz)  {
        return null;
    }

    protected LAstep Prepare(Fractal f, double dre, double dim)  {
        return null;
    }

    public Complex Evaluate(Complex newdz, Complex dc) {
        return null;
    }

    public Complex Evaluate(Complex newdz) {
        return null;
    }

    public Complex Evaluate(double newdre, double newdim, double d0re, double d0im) {

       return null;

    }

    public Complex Evaluate(double newdre, double newdim) {

        return null;

    }

    public MantExpComplex Evaluate(MantExpComplex newdz, MantExpComplex dc) {
        return null;
    }

    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc)  {
        return null;
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc)  {
        return null;
    }

    public abstract boolean isEqual(GenericLAInfo other);

    @Override
    public String toString() {
        return "";
    }


}
