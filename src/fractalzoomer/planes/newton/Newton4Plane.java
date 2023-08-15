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

package fractalzoomer.planes.newton;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class Newton4Plane extends Plane {
    
    public Newton4Plane() {
        
        super();
        
    }

    @Override
    public Complex transform(Complex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.fourth().sub_mutable(1);
            Complex dfz = temp.cube().times_mutable(4);
            
            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;
 
    }

    @Override
    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        BigIntNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            BigIntNumComplex fz = temp.fourth().sub(1);
            BigIntNumComplex dfz = temp.cube().times(4);

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        BigComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            BigComplex fz = temp.fourth().sub(MyApfloat.ONE);
            BigComplex dfz = temp.cube().times(MyApfloat.FOUR);

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        DDComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            DDComplex fz = temp.fourth().sub(1);
            DDComplex dfz = temp.cube().times(4);

            temp = temp.sub(fz.divide(dfz));
        }

        return temp;

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        MpfrBigNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            MpfrBigNumComplex fz = temp.fourth().sub_mutable(1);
            MpfrBigNumComplex dfz = temp.cube().times4_mutable();

            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;

    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        if(pixel.isZero()) {
            return pixel;
        }

        MpirBigNumComplex temp = pixel;

        for(int iterations = 0; iterations < 5; iterations++) {
            MpirBigNumComplex fz = temp.fourth().sub_mutable(1);
            MpirBigNumComplex dfz = temp.cube().times4_mutable();

            temp = temp.sub(fz.divide_mutable(dfz));
        }

        return temp;

    }
}
