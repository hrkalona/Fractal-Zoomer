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

package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;


/**
 *
 * @author hrkalona2
 */
public class InversedLambdaPlane extends Plane {
    private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;
    private MpfrBigNum temp1;
    private MpfrBigNum temp2;
    private MpfrBigNum temp3;
    private MpfrBigNum temp4;

    private MpirBigNum tempRep;
    private MpirBigNum tempImp;
    private MpirBigNum temp1p;
    private MpirBigNum temp2p;
    private MpirBigNum temp3p;
    private MpirBigNum temp4p;
    
    public InversedLambdaPlane() {
        
        super();

        if(TaskDraw.PERTURBATION_THEORY || TaskDraw.HIGH_PRECISION_CALCULATION) {
            if (TaskDraw.allocateMPFR()) {
                tempRe = new MpfrBigNum();
                tempIm = new MpfrBigNum();
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();
                temp3 = new MpfrBigNum();
                temp4 = new MpfrBigNum();
            } else if (TaskDraw.allocateMPIR()) {
                tempRep = new MpirBigNum();
                tempImp = new MpirBigNum();
                temp1p = new MpirBigNum();
                temp2p = new MpirBigNum();
                temp3p = new MpirBigNum();
                temp4p = new MpirBigNum();
            }
        }
        
    }

    @Override
    public Complex transform(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        Complex temp = pixel.reciprocal();
        return temp.times_mutable(temp.r_sub(1));
        
    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(MyApfloat.ONE));

    }

    @Override
    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(1));

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal_mutable(tempRe, tempIm);

        return pixel.times_mutable(pixel.r_sub(1, tempRe, tempIm), temp1, temp2, temp3, temp4);

    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal_mutable(tempRep, tempImp);

        return pixel.times_mutable(pixel.r_sub(1, tempRep, tempImp), temp1p, temp2p, temp3p, temp4p);

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        pixel = pixel.reciprocal();

        return pixel.times(pixel.r_sub(1));

    }
    
}
