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


/**
 *
 * @author hrkalona2
 */
public class Rotation {
  private Complex rotation;
  private Complex inv_rotation;
  private Complex center;

  private BigComplex ddrotation;
  private BigComplex ddcenter;

  private DDComplex ddcrotation;
  private DDComplex ddccenter;

  private BigNumComplex bnrotation;
  private BigNumComplex bncenter;

  private MpfrBigNumComplex mpfrbnrotation;
  private MpfrBigNumComplex mpfrbncenter;

    private boolean hasRotation = false;
    private boolean hasRotationCenter = false;

    private MpfrBigNum tempRotationRe;
    private MpfrBigNum tempRotationIm;


  
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
        this.ddrotation = ddrotation;
        this.ddcenter = ddcenter;
    }

    public Rotation(DDComplex ddrotation, DDComplex ddcenter) {
        this.ddcrotation = ddrotation;
        this.ddccenter = ddcenter;
    }

    public Rotation(BigNumComplex bnrotation, BigNumComplex bncenter) {
        this.bnrotation = bnrotation;
        this.bncenter = bncenter;
    }

    public Rotation(MpfrBigNumComplex mpfrbnrotation, MpfrBigNumComplex mpfrbncenter) {
        this.mpfrbnrotation = mpfrbnrotation;
        this.mpfrbncenter = mpfrbncenter;
        hasRotation = !mpfrbnrotation.isOne();
        hasRotationCenter = !mpfrbncenter.isZero();

        if(hasRotation) {
            tempRotationRe = new MpfrBigNum();
            tempRotationIm = new MpfrBigNum();
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
        BigComplex temp = pixel.sub(ddcenter);
        return temp.times(ddrotation).plus(ddcenter);
    }

    public DDComplex rotate(DDComplex pixel) {
        DDComplex temp = pixel.sub(ddccenter);
        return temp.times(ddcrotation).plus(ddccenter);
    }

    public BigNumComplex rotate(BigNumComplex pixel) {
        BigNumComplex temp = pixel.sub(bncenter);
        return temp.times(bnrotation).plus(bncenter);
    }

    public MpfrBigNumComplex rotate(MpfrBigNumComplex pixel) {
        if(hasRotationCenter) {
            pixel.sub_mutable(mpfrbncenter);
        }

        if(hasRotation) {
            pixel.times_mutable(mpfrbnrotation, tempRotationRe, tempRotationIm);
        }

        if(hasRotationCenter) {
            pixel.plus_mutable(mpfrbncenter);
        }
        return pixel;
    }
    
}
