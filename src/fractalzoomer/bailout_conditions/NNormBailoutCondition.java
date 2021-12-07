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

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class NNormBailoutCondition extends BailoutCondition {
  protected double n_norm;
  protected Apfloat ddn_norm;
 
    public NNormBailoutCondition(double bound, double n_norm) {
        
        super(bound);
        this.n_norm = n_norm;
        ddn_norm = new MyApfloat(n_norm);
        
    }
    
     @Override //N norm
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        return z.nnorm(n_norm) >= bound;
         
     }

    @Override //N norm
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        return z.nnorm(ddn_norm).compareTo(ddbound) >= 0;

    }
}
    