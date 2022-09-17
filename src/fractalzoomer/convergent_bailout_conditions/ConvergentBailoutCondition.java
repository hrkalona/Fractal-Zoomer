/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public abstract class ConvergentBailoutCondition {
  protected double convergent_bailout;
  protected Apfloat ddconvergent_bailout;
  protected DoubleDouble ddcconvergent_bailout;
  protected double distance;

    protected ConvergentBailoutCondition(double convergent_bailout) {
        
        this.convergent_bailout = convergent_bailout;

        if(ThreadDraw.PERTURBATION_THEORY) {
          ddconvergent_bailout = new MyApfloat(convergent_bailout);
            ddcconvergent_bailout = new DoubleDouble(convergent_bailout);
        }
        
    }
    
    public abstract boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);
    public abstract boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);
    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);

    public abstract boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);


    public abstract boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);
    public abstract boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);
    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);

    public abstract boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);


    public abstract boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel);
    public abstract boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel);
    public abstract boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel);

    public abstract boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel);

  public boolean Converged(GenericComplex z, GenericComplex zold, GenericComplex zold2, int iterations, GenericComplex c, GenericComplex start, GenericComplex c0, GenericComplex pixel) {
//      if(z instanceof BigNumComplex) {
//        return converged((BigNumComplex)z, (BigNumComplex)zold, (BigNumComplex)zold2, iterations, (BigNumComplex)c, (BigNumComplex)start, (BigNumComplex)c0, (BigNumComplex)pixel);
//      }
      if(z instanceof MpfrBigNumComplex) {
        return converged((MpfrBigNumComplex)z, (MpfrBigNumComplex)zold, (MpfrBigNumComplex)zold2, iterations, (MpfrBigNumComplex)c, (MpfrBigNumComplex)start, (MpfrBigNumComplex)c0, (MpfrBigNumComplex)pixel);
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
    public double getDistance() {
        return distance;
    }
    
}
