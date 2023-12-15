package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.core.la.impl.LAInfoDeep;
import fractalzoomer.core.la.impl_refindex.LAInfoDeepRI;
import fractalzoomer.core.la.impl_refindex.LAInfoRI;

public abstract class GenericLAInfo {
    public static double[] refRe;
    public static double[] refIm;
    public static double[] refMantsRe;
    public static double[] refMantsIm;
    public static long[] refExpRe;
    public static long[] refExpIm;

    private static final int ITERATIONS_MEMORY_THRESHOLD = 50_000_000;

    public static GenericLAInfo create(int length, boolean deepZoom, int refIndex) {
        if(length > ITERATIONS_MEMORY_THRESHOLD) {
            if (deepZoom) {
                return LAInfoDeepRI.create(refIndex);
            } else {
                return LAInfoRI.create(refIndex);
            }
        }
        else {
            if (deepZoom) {
                return LAInfoDeep.create(refIndex);
            } else {
                return LAInfo.create(refIndex);
            }
        }
    }

    public static GenericLAInfo create(int length, boolean deepZoom) {
        if(length > ITERATIONS_MEMORY_THRESHOLD) {
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

    protected abstract GenericLAInfo Composite(LAInfo LA) throws InvalidCalculationException;
    protected abstract GenericLAInfo Composite(LAInfoRI LA) throws InvalidCalculationException;

    protected abstract GenericLAInfo Composite(LAInfoDeep LA);
    protected abstract GenericLAInfo Composite(LAInfoDeepRI LA);


    protected abstract GenericLAInfo Step(int RefIndex) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfo out, LAInfo LA) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoRI out, LAInfoRI LA) throws InvalidCalculationException;
    protected abstract boolean Composite(LAInfoDeep out, LAInfoDeep LA);

    protected abstract boolean Composite(LAInfoDeepRI out, LAInfoDeepRI LA);
    protected abstract boolean Step(LAInfoDeep out, int zRefIndex);
    protected abstract boolean Step(LAInfoDeepRI out, int zRefIndex);
    protected abstract boolean Step(LAInfo out, int zRefIndex) throws InvalidCalculationException;
    protected abstract boolean Step(LAInfoRI out, int zRefIndex) throws InvalidCalculationException;

    protected boolean Step(GenericLAInfo out, int zRefIndex) throws InvalidCalculationException {
        if(out instanceof LAInfo) {
            return Step((LAInfo) out, zRefIndex);
        }
        else if(out instanceof LAInfoRI) {
            return Step((LAInfoRI) out, zRefIndex);
        }
        else if(out instanceof LAInfoDeep) {
            return Step((LAInfoDeep) out, zRefIndex);
        }
        else {
            return Step((LAInfoDeepRI) out, zRefIndex);
        }
    }

    protected GenericLAInfo Composite(GenericLAInfo LA) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) LA);
        }
        else if(LA instanceof  LAInfoRI) {
            return Composite((LAInfoRI) LA);
        }
        else if(LA instanceof  LAInfoDeep) {
            return Composite((LAInfoDeep) LA);
        }
        else {
            return Composite((LAInfoDeepRI) LA);
        }
    }

    protected boolean Composite(GenericLAInfo out, GenericLAInfo LA) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) out, (LAInfo) LA);
        }
        else if(LA instanceof  LAInfoRI) {
            return Composite((LAInfoRI) out, (LAInfoRI) LA);
        }
        else if(LA instanceof  LAInfoDeep) {
            return Composite((LAInfoDeep) out, (LAInfoDeep) LA);
        }
        else  {
            return Composite((LAInfoDeepRI) out, (LAInfoDeepRI) LA);
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

    public abstract GenericComplex getRef();

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

    protected LAstep Prepare(MantExpComplex dz)  {
        return null;
    }

    protected LAstep Prepare(Complex dz)  {
        return null;
    }

    protected LAstep Prepare(double dre, double dim)  {
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

}
