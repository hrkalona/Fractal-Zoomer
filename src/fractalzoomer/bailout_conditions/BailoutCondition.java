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

package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public abstract class BailoutCondition {
  protected double bound;
  protected Apfloat ddbound;
  protected BigNum bnbound;
  protected DoubleDouble ddcbound;
  protected int id;
    
    protected BailoutCondition(double bound) {
        
        this.bound = bound;
        if(ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            ddbound = new MyApfloat(bound);
            ddcbound = new DoubleDouble(bound);

            if(ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE || ThreadDraw.HIGH_PRECISION_CALCULATION) {
                bnbound = new BigNum(ddbound);
            }
        }
        
    }
    
    public abstract boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel);
    public abstract boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel);
    public abstract boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel);

    public abstract boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel);

    public abstract boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel);

    public boolean Escaped(GenericComplex z, GenericComplex zold, GenericComplex zold2, int iterations, GenericComplex c, GenericComplex start, GenericComplex c0, Object norm_squared, GenericComplex pixel) {
        if(z instanceof BigNumComplex) {
           return escaped((BigNumComplex)z, (BigNumComplex)zold, (BigNumComplex)zold2, iterations, (BigNumComplex)c, (BigNumComplex)start, (BigNumComplex)c0, (BigNum) norm_squared, (BigNumComplex)pixel);
        }
        else if(z instanceof MpfrBigNumComplex) {
            return escaped((MpfrBigNumComplex)z, (MpfrBigNumComplex)zold, (MpfrBigNumComplex)zold2, iterations, (MpfrBigNumComplex)c, (MpfrBigNumComplex)start, (MpfrBigNumComplex)c0, (MpfrBigNum) norm_squared, (MpfrBigNumComplex)pixel);
        }
        else if (z instanceof BigComplex) {
            return escaped((BigComplex)z, (BigComplex)zold, (BigComplex)zold2, iterations, (BigComplex)c, (BigComplex)start, (BigComplex)c0, (Apfloat) norm_squared, (BigComplex)pixel);
        }
        else if (z instanceof DDComplex) {
            return escaped((DDComplex)z, (DDComplex)zold, (DDComplex)zold2, iterations, (DDComplex)c, (DDComplex)start, (DDComplex)c0, (DoubleDouble) norm_squared, (DDComplex)pixel);
        }
        else {
            return escaped((Complex) z, (Complex)zold, (Complex)zold2, iterations, (Complex)c, (Complex)start, (Complex)c0, (double) norm_squared, (Complex)pixel);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
