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

package fractalzoomer.planes.fold;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.BigNumComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class FoldDownPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;
    private BigNumComplex bncenter;

    public FoldDownPlane(double[] plane_transform_center) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        ddcenter = new BigComplex(center);
        bncenter = new BigNumComplex(center);

    }

    @Override
    public Complex transform(Complex pixel) {
        
        return pixel.fold_down(center);

    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        return pixel.fold_down(ddcenter);

    }

    @Override
    public BigNumComplex transform(BigNumComplex pixel) {

        return pixel.fold_down(bncenter);

    }
}
