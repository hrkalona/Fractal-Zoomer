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
import org.apfloat.ApfloatMath;

/**
 *
 * @author hrkalona2
 */
public class SquareBailoutCondition extends BailoutCondition {
 
    public SquareBailoutCondition(double bound) {
        
        super(bound);
        
    }
    
     @Override //infinity norm   
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        return Math.max(z.getAbsRe(), z.getAbsIm()) >= bound;
         
     }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        Apfloat absRe = z.getAbsRe();
        Apfloat absIm = z.getAbsIm();

        Apfloat max = ApfloatMath.max(absRe, absIm);

        return max.compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        BigNum absRe = z.getAbsRe();
        BigNum absIm = z.getAbsIm();

        BigNum max = absRe.compare(absIm) == 1 ? absRe : absIm;

        return max.compare(bnbound) >= 0;

    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        MpfrBigNum absRe = z.getAbsRe();
        MpfrBigNum absIm = z.getAbsIm();

        MpfrBigNum max = absRe.compare(absIm) == 1 ? absRe : absIm;

        return max.compare(bound) >= 0;
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        DoubleDouble absRe = z.getAbsRe();
        DoubleDouble absIm = z.getAbsIm();

        DoubleDouble max = absRe.compareTo(absIm) == 1 ? absRe : absIm;

        return max.compareTo(ddcbound) >= 0;

    }
    
}
