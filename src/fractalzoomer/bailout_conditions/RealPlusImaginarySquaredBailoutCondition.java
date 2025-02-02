
package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class RealPlusImaginarySquaredBailoutCondition extends BailoutCondition {
    private MpfrBigNum temp1;
    private MpirBigNum temp1p;
 
    public RealPlusImaginarySquaredBailoutCondition(double bound, Fractal f) {
        
        super(bound);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            if(NumericLibrary.allocateMPFR(f)) {
                temp1 = new MpfrBigNum();
            }
            else if(NumericLibrary.allocateMPIR(f)) {
                temp1p = new MpirBigNum();
            }
        }
        
    }
    
     @Override
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        double temp = z.getRe() + z.getIm();
        return  temp * temp >= bound;
  
     }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        Apfloat temp = MyApfloat.fp.add(z.getRe(), z.getIm());
        return MyApfloat.fp.multiply(temp, temp).compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        BigNum temp = z.getRe().add(z.getIm());
        return temp.squareFull().compareBothPositive(bnbound) >= 0;
    }

    @Override
    public boolean escaped(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNum norm_squared, BigIntNumComplex pixel) {
        BigIntNum temp = z.getRe().add(z.getIm());
        return temp.square().compare(binbound) >= 0;
    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        z.getRe().add(z.getIm(), temp1);
        temp1.square(temp1);
        return temp1.compare(bound) >= 0;
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        z.getRe().add(z.getIm(), temp1p);
        temp1p.square(temp1p);
        return temp1p.compare(bound) >= 0;
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        DoubleDouble temp = z.getRe().add(z.getIm());
        return temp.sqr().compareTo(ddcbound) >= 0;

    }
    
}
