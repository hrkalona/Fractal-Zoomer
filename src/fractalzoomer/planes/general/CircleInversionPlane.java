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

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.planes.Plane;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class CircleInversionPlane extends Plane {

    private Complex center;
    private BigComplex ddcenter;
    private double plane_transform_radius;
    private Apfloat ddplane_transform_radius;

    public CircleInversionPlane(double[] plane_transform_center, double plane_transform_radius) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_radius = plane_transform_radius;

        if(ThreadDraw.PERTURBATION_THEORY) {
            ddplane_transform_radius = new MyApfloat(plane_transform_radius);
            ddcenter = new BigComplex(center);
        }

    }

    @Override
    public Complex transform(Complex pixel) {
        
        return pixel.circle_inversion(center, plane_transform_radius);
        
    }

    @Override
    public BigComplex transform(BigComplex pixel) {

        return pixel.circle_inversion(ddcenter, ddplane_transform_radius);

    }

}
