
package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.norms.Norm;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class SkipBailoutCondition extends BailoutCondition {
    public static int SKIPPED_ITERATION_COUNT = 0;
    private BailoutCondition wrappedCondition;
 
    public SkipBailoutCondition(BailoutCondition condition) {
        
        super(0.0);
        wrappedCondition = condition;
        
    }
    
     @Override
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }
        
        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
  
     }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);

    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
    }

    @Override
    public boolean escaped(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNum norm_squared, BigIntNumComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
    }

    @Override
    public Norm getNormImpl() {
        return wrappedCondition.getNormImpl();
    }
    
}
