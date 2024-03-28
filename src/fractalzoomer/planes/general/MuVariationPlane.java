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
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class MuVariationPlane extends Plane {
    private MpirBigNum _025;
    
    public MuVariationPlane() {

        super();

        if (TaskRender.allocateMPIR()) {
            _025 = new MpirBigNum(0.25);
        }

    }

    @Override
    public Complex transform(Complex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(0.25));

    }

    @Override
    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(0.25));

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(new MyApfloat(0.25)));

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(0.25));

    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        return pixel.square().divide_mutable(pixel.fourth().sub_mutable(_025));

    }

    @Override
    public DDComplex transform(DDComplex pixel) {

        return pixel.square().divide(pixel.fourth().sub(new DoubleDouble(0.25)));

    }

}
