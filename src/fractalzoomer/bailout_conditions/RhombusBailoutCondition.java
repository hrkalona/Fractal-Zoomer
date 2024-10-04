

package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.norms.Norm1;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class RhombusBailoutCondition extends BailoutCondition {
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;

    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    
    public RhombusBailoutCondition(double bound, Fractal f) {
        
        super(bound);

        normImpl = new Norm1();

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if (NumericLibrary.allocateMPFR(f)) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (NumericLibrary.allocateMPIR(f)) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }
        
    } 
    
    @Override //one norm  
    public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        return z.getAbsRe() + z.getAbsIm() >= bound;
         
    }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        return MyApfloat.fp.add(z.getAbsRe(), z.getAbsIm()).compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        return z.getAbsRe().add(z.getAbsIm()).compareBothPositive(bnbound) >= 0;
    }

    @Override
    public boolean escaped(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNum norm_squared, BigIntNumComplex pixel) {
        return z.getAbsRe().add(z.getAbsIm()).compare(binbound) >= 0;
    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        return z.getAbsRe(temp1).add(z.getAbsIm(temp2), temp1).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        return z.getAbsRe(temp1p).add(z.getAbsIm(temp2p), temp1p).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        return z.getAbsRe().add(z.getAbsIm()).compareTo(ddcbound) >= 0;

    }
    
}
