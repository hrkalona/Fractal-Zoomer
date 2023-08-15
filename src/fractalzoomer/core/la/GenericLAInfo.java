package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public abstract class GenericLAInfo {

    protected abstract GenericLAInfo Composite(LAInfo LA) throws InvalidCalculationException;
    protected abstract GenericLAInfo Composite(LAInfoDeep LA);

    protected abstract GenericLAInfo Step(Complex z) throws InvalidCalculationException;
    protected abstract GenericLAInfo Step(MantExpComplex z);

    protected abstract boolean Composite(LAInfo out, LAInfo LA) throws InvalidCalculationException;

    protected abstract boolean Composite(LAInfoDeep out, LAInfoDeep LA);

    protected abstract boolean Step(LAInfoDeep out, MantExpComplex z);

    protected abstract boolean Step(LAInfo out, Complex z) throws InvalidCalculationException;

    protected GenericLAInfo Step(GenericComplex z) throws InvalidCalculationException {
        if(z instanceof  Complex) {
            return Step((Complex) z);
        }
        else {
            return Step((MantExpComplex) z);
        }
    }

    protected boolean Step(GenericLAInfo out, GenericComplex z) throws InvalidCalculationException {
        if(z instanceof  Complex) {
            return Step((LAInfo)out, (Complex) z);
        }
        else {
            return Step((LAInfoDeep)out, (MantExpComplex) z);
        }
    }

    protected GenericLAInfo Composite(GenericLAInfo LA) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) LA);
        }
        else {
            return Composite((LAInfoDeep) LA);
        }
    }

    protected boolean Composite(GenericLAInfo out, GenericLAInfo LA) throws InvalidCalculationException {
        if(LA instanceof  LAInfo) {
            return Composite((LAInfo) out, (LAInfo) LA);
        }
        else {
            return Composite((LAInfoDeep) out, (LAInfoDeep) LA);
        }
    }

    protected abstract boolean isLAThresholdZero();
    protected abstract boolean isZCoeffZero();

    protected abstract boolean DetectPeriod(Complex z);
    protected abstract boolean DetectPeriod(MantExpComplex z);

    protected boolean DetectPeriod(GenericComplex z) {
        if(z instanceof  Complex) {
            return DetectPeriod((Complex) z);
        }
        else {
            return DetectPeriod((MantExpComplex) z);
        }
    }

    protected abstract GenericComplex getRef();

    protected abstract GenericComplex getZCoeff();

    protected abstract GenericComplex getCCoeff();
    protected abstract ATInfo CreateAT(GenericLAInfo Next);

    public abstract MantExp getLAThreshold();
    public abstract MantExp getLAThresholdC();

}
