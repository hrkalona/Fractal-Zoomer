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

package fractalzoomer.fractal_options;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import org.apfloat.Apfloat;


/**
 *
 * @author hrkalona2
 */
public class Rotation {
  private Complex rotation;
  private Complex inv_rotation;
  private Complex center;

  private Apfloat ddrotationA;
  private Apfloat ddrotationApB;
  private Apfloat ddrotationAsB;
  private BigComplex ddcenter;

  private DoubleDouble ddcrotationA;
  private DoubleDouble ddcrotationApB;
  private DoubleDouble ddcrotationAsB;
  private DDComplex ddccenter;

  private BigNum bnRotationA;
  private BigNum bnrotationApB;
  private BigNum bnrotationAsB;
  private BigNumComplex bncenter;

  private MpfrBigNum mpfrbnrotationA;
  private MpfrBigNum mpfrbnrotationApB;
  private MpfrBigNum mpfrbnrotationAsB;

  private MpfrBigNum F;
  private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;
  private MpfrBigNumComplex mpfrbncenter;

    private boolean hasRotation = false;
    private boolean hasRotationCenter = false;

  
    public Rotation(double cos_theta, double sin_theta, double x, double y) {
        
        rotation = new Complex(cos_theta, sin_theta);
        inv_rotation = rotation.conjugate();

        center = new Complex(x, y);
        
    }

    public Rotation(Complex rotation, Complex center) {
        this.rotation = rotation;
        this.center = center;
    }

    public Rotation(BigComplex ddrotation, BigComplex ddcenter) {
        this.ddcenter = ddcenter;
        hasRotation = !ddrotation.isOne();
        hasRotationCenter = !ddcenter.isZero();

        if(hasRotation) {
            ddrotationA = ddrotation.getRe();
            ddrotationApB = ddrotationA.add(ddrotation.getIm());
            ddrotationAsB = ddrotationA.subtract(ddrotation.getIm());
        }
    }

    public Rotation(DDComplex ddrotation, DDComplex ddcenter) {
        this.ddccenter = ddcenter;
        hasRotation = !ddrotation.isOne();
        hasRotationCenter = !ddcenter.isZero();

        if(hasRotation) {
            ddcrotationA = ddrotation.getRe();
            ddcrotationApB = ddcrotationA.add(ddrotation.getIm());
            ddcrotationAsB = ddcrotationA.subtract(ddrotation.getIm());
        }
    }

    public Rotation(BigNumComplex bnrotation, BigNumComplex bncenter) {
        this.bncenter = bncenter;
        hasRotation = !bnrotation.isOne();
        hasRotationCenter = !bncenter.isZero();

        if(hasRotation) {
            bnRotationA = bnrotation.getRe();
            bnrotationApB = bnRotationA.add(bnrotation.getIm());
            bnrotationAsB = bnRotationA.sub(bnrotation.getIm());
        }
    }

    public Rotation(MpfrBigNumComplex mpfrbnrotation, MpfrBigNumComplex mpfrbncenter) {

        this.mpfrbncenter = mpfrbncenter;
        hasRotation = !mpfrbnrotation.isOne();
        hasRotationCenter = !mpfrbncenter.isZero();

        if(hasRotation) {
            mpfrbnrotationA = mpfrbnrotation.getRe();
            mpfrbnrotationApB = mpfrbnrotationA.add(mpfrbnrotation.getIm());
            mpfrbnrotationAsB = mpfrbnrotationA.sub(mpfrbnrotation.getIm());
            F = new MpfrBigNum();
            tempRe = new MpfrBigNum();
            tempIm = new MpfrBigNum();
        }
    }
    
    public Complex rotate(Complex pixel) {
        
         Complex temp = pixel.sub(center);
         return temp.times_mutable(rotation).plus_mutable(center);
         
    }
    
    public Complex rotateInverse(Complex pixel) {
        
         Complex temp = pixel.sub(center);
         return temp.times_mutable(inv_rotation).plus_mutable(center);
         
    }

    public BigComplex rotate(BigComplex pixel) {
        if(hasRotationCenter) {
            pixel = pixel.sub(ddcenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            Apfloat X = pixel.getRe();
            Apfloat Y = pixel.getIm();
            Apfloat F = MyApfloat.fp.multiply(ddrotationA, MyApfloat.fp.subtract(X, Y));
            pixel = new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(ddrotationAsB, Y), F), MyApfloat.fp.subtract(MyApfloat.fp.multiply(ddrotationApB, X), F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(ddcenter);
        }
        return pixel;
    }

    public DDComplex rotate(DDComplex pixel) {
        if(hasRotationCenter) {
            pixel = pixel.sub(ddccenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            DoubleDouble X = pixel.getRe();
            DoubleDouble Y = pixel.getIm();
            DoubleDouble F = ddcrotationA.multiply(X.subtract(Y));
            pixel = new DDComplex(ddcrotationAsB.multiply(Y).add(F), ddcrotationApB.multiply(X).subtract(F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(ddccenter);
        }

        return pixel;
    }

    public BigNumComplex rotate(BigNumComplex pixel) {

        if(hasRotationCenter) {
            pixel = pixel.sub(bncenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            BigNum X = pixel.getRe();
            BigNum Y = pixel.getIm();
            BigNum F = bnRotationA.mult(X.sub(Y));
            pixel = new BigNumComplex(bnrotationAsB.mult(Y).add(F), bnrotationApB.mult(X).sub(F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(bncenter);
        }

        return pixel;
    }

    public MpfrBigNumComplex rotate(MpfrBigNumComplex pixel) {
        if(hasRotationCenter) {
            pixel.sub_mutable(mpfrbncenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            MpfrBigNum X = pixel.getRe();
            MpfrBigNum Y = pixel.getIm();

//            X.sub(Y, F);
//            mpfrbnrotationA.mult(F, F);
//
//            mpfrbnrotationAsB.mult(Y, tempRe);
//            mpfrbnrotationApB.mult(X, tempIm);
//
//            tempRe.add(F, X);
//            tempIm.sub(F, Y);
            MpfrBigNum.rotation(X, Y, tempRe, tempIm, F, mpfrbnrotationA, mpfrbnrotationAsB, mpfrbnrotationApB);
        }

        if(hasRotationCenter) {
            pixel.plus_mutable(mpfrbncenter);
        }
        return pixel;
    }
    
}
