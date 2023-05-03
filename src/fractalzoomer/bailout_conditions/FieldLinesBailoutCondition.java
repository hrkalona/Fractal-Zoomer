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

        if((ThreadDraw.PERTURBATION_THEORY && ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE) || ThreadDraw.HIGH_PRECISION_CALCULATION) {
            if (ThreadDraw.allocateMPFR()) {
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
            } else if (ThreadDraw.allocateMPIR()) {
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
            }
        }
        
    }
    
    @Override
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {

        return iterations > 1 && z.getRe() / zold.getRe() >= bound && z.getIm() / zold.getIm() >= bound;
         
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

        if(iterations > 1) {
            Complex temp = z.toComplex();
            Complex temp2 = zold.toComplex();
            return temp.getRe() / temp2.getRe() >= bound && temp.getIm() / temp2.getIm() >= bound;
        }
        return false;

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
