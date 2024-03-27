package fractalzoomer.core.la;

import fractalzoomer.core.*;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.core.la.impl.LAInfoDeep;
import fractalzoomer.core.la.impl_refindex.LAInfoDeepRI;
import fractalzoomer.core.la.impl_refindex.LAInfoRI;
import fractalzoomer.functions.Fractal;

public abstract class GenericLAInfo {

    private static final int ITERATIONS_MEMORY_THRESHOLD = 50_000_000;

    public static GenericLAInfo create(int length, boolean deepZoom, int refIndex, ReferenceDecompressor referenceDecompressor) {
        if(TaskDraw.USE_RI_ON_BLA2 || (!TaskDraw.DISABLE_RI_ON_BLA2 && !TaskDraw.COMPRESS_REFERENCE_IF_POSSIBLE && length > ITERATIONS_MEMORY_THRESHOLD)) {
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
        if(TaskDraw.USE_RI_ON_BLA2 || (!TaskDraw.DISABLE_RI_ON_BLA2 && !TaskDraw.COMPRESS_REFERENCE_IF_POSSIBLE && length > ITERATIONS_MEMORY_THRESHOLD)) {
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

    protected abstract GenericLAInfo Composite(LAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract GenericLAInfo Composite(LAInfoRI LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;

    protected abstract GenericLAInfo Composite(LAInfoDeep LA, ReferenceDecompressor referenceDecompressor);
    protected abstract GenericLAInfo Composite(LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor);


    protected abstract GenericLAInfo Step(int RefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfo out, LAInfo LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoRI out, LAInfoRI LA, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoDeep out, LAInfoDeep LA, ReferenceDecompressor referenceDecompressor);

    protected abstract boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA, ReferenceDecompressor referenceDecompressor);
    protected abstract boolean Step(LAInfoDeep out, int zRefIndex, ReferenceDecompressor referenceDecompressor);
    protected abstract boolean Step(LAInfoDeepRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor);
    protected abstract boolean Step(LAInfo out, int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;
    protected abstract boolean Step(LAInfoRI out, int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException;

    protected boolean Step(GenericLAInfo out, int zRefIndex, ReferenceDecompressor referenceDecompressor) throws InvalidCalculationException {
        if(out instanceof LAInfo) {
            return Step((LAInfo) out, zRefIndex, referenceDecompressor);
        }
        else if(out instanceof LAInfoRI) {
            return Step((LAInfoRI) out, zRefIndex, referenceDecompressor);
        }
        else if(out instanceof LAInfoDeep) {
            return Step((LAInfoDeep) out, zRefIndex, referenceDecompressor);
        }
        else {
            return Step((LAInfoDeepRI) out, zRefIndex, referenceDecompressor);
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
            return Composite((LAInfo) out, (LAInfo) LA, referenceDecompressor);
        }
        else if(LA instanceof  LAInfoRI) {
            return Composite((LAInfoRI) out, (LAInfoRI) LA, referenceDecompressor);
        }
        else if(LA instanceof  LAInfoDeep) {
            return Composite((LAInfoDeep) out, (LAInfoDeep) LA, referenceDecompressor);
        }
        else  {
            return Composite((LAInfoDeepRI) out, (LAInfoDeepRI) LA, referenceDecompressor);
        }
    }

    protected abstract boolean isLAThresholdZero();
    protected abstract boolean isZCoeffZero();

    protected abstract boolean DetectPeriod(Complex z);
    protected abstract boolean Stage0DetectPeriod(Complex z);
    protected abstract boolean DetectPeriod(MantExpComplex z);
    protected abstract boolean Stage0DetectPeriod(MantExpComplex z);

    protected boolean DetectPeriod(GenericComplex z) {
        if(z instanceof  Complex) {
            return DetectPeriod((Complex) z);
        }
        else {
            return DetectPeriod((MantExpComplex) z);
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
