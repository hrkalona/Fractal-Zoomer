

package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public abstract class ConvergentBailoutCondition {
  protected double convergent_bailout;
  protected Apfloat ddconvergent_bailout;
  protected BigIntNum binddconvergent_bailout;
  protected DoubleDouble ddcconvergent_bailout;
  protected Norm normImpl;

    protected ConvergentBailoutCondition(double convergent_bailout) {
        
        this.convergent_bailout = convergent_bailout;

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddconvergent_bailout = new MyApfloat(convergent_bailout);
            ddcconvergent_bailout = new DoubleDouble(convergent_bailout);
            binddconvergent_bailout = new BigIntNum(convergent_bailout);
        }

        normImpl = new Norm2();
        
    }
    
    public abstract boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);
    public abstract boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);

    public abstract boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel);
    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);

    public abstract boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel);

    public abstract boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);


    public abstract boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);
    public abstract boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);

    public abstract boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel);
    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);

    public abstract boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel);

    public abstract boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);


    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);

    public abstract boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel);
    public abstract boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);

    public abstract boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel);
    public abstract boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);

    public abstract boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);

  public boolean Converged(GenericComplex z, GenericComplex zold, GenericComplex zold2, int iterations, GenericComplex c, GenericComplex start, GenericComplex c0, GenericComplex pixel) {
//      if(z instanceof BigNumComplex) {
//        return converged((BigNumComplex)z, (BigNumComplex)zold, (BigNumComplex)zold2, iterations, (BigNumComplex)c, (BigNumComplex)start, (BigNumComplex)c0, (BigNumComplex)pixel);
//      }
      if(z instanceof MpfrBigNumComplex) {
        return converged((MpfrBigNumComplex)z, (MpfrBigNumComplex)zold, (MpfrBigNumComplex)zold2, iterations, (MpfrBigNumComplex)c, (MpfrBigNumComplex)start, (MpfrBigNumComplex)c0, (MpfrBigNumComplex)pixel);
      }
      else if(z instanceof BigIntNumComplex) {
         return converged((BigIntNumComplex)z, (BigIntNumComplex)zold, (BigIntNumComplex)zold2, iterations, (BigIntNumComplex)c, (BigIntNumComplex)start, (BigIntNumComplex)c0, (BigIntNumComplex)pixel);
      }
      else if(z instanceof MpirBigNumComplex) {
          return converged((MpirBigNumComplex)z, (MpirBigNumComplex)zold, (MpirBigNumComplex)zold2, iterations, (MpirBigNumComplex)c, (MpirBigNumComplex)start, (MpirBigNumComplex)c0, (MpirBigNumComplex)pixel);
      }
      else if (z instanceof BigComplex) {
        return converged((BigComplex)z, (BigComplex)zold, (BigComplex)zold2, iterations, (BigComplex)c, (BigComplex)start, (BigComplex)c0, (BigComplex)pixel);
      }
      else if (z instanceof DDComplex) {
        return converged((DDComplex)z, (DDComplex)zold, (DDComplex)zold2, iterations, (DDComplex)c, (DDComplex)start, (DDComplex)c0, (DDComplex)pixel);
      }
      else {
        return converged((Complex) z, (Complex)zold, (Complex)zold2, iterations, (Complex)c, (Complex)start, (Complex)c0, (Complex)pixel);
      }
    }


  public boolean Converged(GenericComplex z, Object root, GenericComplex zold, GenericComplex zold2, int iterations, GenericComplex c, GenericComplex start, GenericComplex c0, GenericComplex pixel) {

    if(z instanceof MpfrBigNumComplex) {
      return converged((MpfrBigNumComplex)z, (MpfrBigNum)root, (MpfrBigNumComplex)zold, (MpfrBigNumComplex)zold2, iterations, (MpfrBigNumComplex)c, (MpfrBigNumComplex)start, (MpfrBigNumComplex)c0, (MpfrBigNumComplex)pixel);
    }
    else if(z instanceof MpirBigNumComplex) {
        return converged((MpirBigNumComplex)z, (MpirBigNum)root, (MpirBigNumComplex)zold, (MpirBigNumComplex)zold2, iterations, (MpirBigNumComplex)c, (MpirBigNumComplex)start, (MpirBigNumComplex)c0, (MpirBigNumComplex)pixel);
    }
    else if (z instanceof BigIntNumComplex) {
        return converged((BigIntNumComplex)z, (BigIntNum) root, (BigIntNumComplex)zold, (BigIntNumComplex)zold2, iterations, (BigIntNumComplex)c, (BigIntNumComplex)start, (BigIntNumComplex)c0, (BigIntNumComplex)pixel);
    }
    else if (z instanceof BigComplex) {
      return converged((BigComplex)z, (Apfloat)root, (BigComplex)zold, (BigComplex)zold2, iterations, (BigComplex)c, (BigComplex)start, (BigComplex)c0, (BigComplex)pixel);
    }
    else if (z instanceof DDComplex) {
        return converged((DDComplex)z, (DoubleDouble) root, (DDComplex)zold, (DDComplex)zold2, iterations, (DDComplex)c, (DDComplex)start, (DDComplex)c0, (DDComplex)pixel);
    }
    else {
      return converged((Complex) z, (Double)root, (Complex)zold, (Complex)zold2, iterations, (Complex)c, (Complex)start, (Complex)c0, (Complex)pixel);
    }
  }

    public Norm getNormImpl() {
        return normImpl;
    }
    
}
