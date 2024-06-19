
package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class FieldLinesBailoutCondition extends BailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;


    public FieldLinesBailoutCondition(double bound) {
        
        super(bound);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (TaskRender.allocateMPFR()) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (TaskRender.allocateMPIR()) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }
        
    }
    
    @Override
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {

        if(iterations > 1) {

            if(zold.getRe() == 0 || zold.getIm() == 0) {
                return false;
            }

            return z.getRe() / zold.getRe() >= bound && z.getIm() / zold.getIm() >= bound;
        }
        return false;
     }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        Apfloat zoldRe = zold.getRe();
        Apfloat zoldIm = zold.getIm();

        if(iterations > 1 && (zoldRe.compareTo(Apfloat.ZERO) == 0 || zoldIm.compareTo(Apfloat.ZERO) == 0)) {
            return false;
        }

        return iterations > 1 && MyApfloat.fp.divide(z.getRe(), zoldRe).compareTo(ddbound) >= 0 && MyApfloat.fp.divide(z.getIm(), zoldIm).compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        return escaped(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), norm_squared.doubleValue(), pixel.toComplex());
    }

    @Override
    public boolean escaped(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNum norm_squared, BigIntNumComplex pixel) {
        BigIntNum zoldRe = zold.getRe();
        BigIntNum zoldIm = zold.getIm();

        if(iterations > 1 && (zoldRe.isZero() || zoldIm.isZero())) {
            return false;
        }

        return iterations > 1 && z.getRe().divide(zoldRe).compare(binbound) >= 0 && z.getIm().divide(zoldIm).compare(binbound) >= 0;

    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        MpfrBigNum zoldRe = zold.getRe();
        MpfrBigNum zoldIm = zold.getIm();

        if(iterations > 1 && (zoldRe.compare(0) == 0 || zoldIm.compare(0) == 0)) {
            return false;
        }

        return iterations > 1 && z.getRe().divide(zoldRe, temp1).compare(bound) >= 0 && z.getIm().divide(zoldIm, temp2).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        MpirBigNum zoldRe = zold.getRe();
        MpirBigNum zoldIm = zold.getIm();

        if(iterations > 1 && (zoldRe.compare(0) == 0 || zoldIm.compare(0) == 0)) {
            return false;
        }

        return iterations > 1 && z.getRe().divide(zoldRe, temp1p).compare(bound) >= 0 && z.getIm().divide(zoldIm, temp2p).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        DoubleDouble zoldRe = zold.getRe();
        DoubleDouble zoldIm = zold.getIm();

        if(iterations > 1 && (zoldRe.compareTo(0) == 0 || zoldIm.compareTo(0) == 0)) {
            return false;
        }

        return iterations > 1 && z.getRe().divide(zoldRe).compareTo(ddcbound) >= 0 && z.getIm().divide(zoldIm).compareTo(ddcbound) >= 0;

    }
    
}
