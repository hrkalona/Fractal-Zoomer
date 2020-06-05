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

package fractalzoomer.planes.distort;


import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class PinchPlane extends Plane {

    private double plane_transform_angle;
    private double plane_transform_radius;
    private double plane_transform_amount;
    private Complex center;

    public PinchPlane(double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double plane_transform_amount) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_angle = plane_transform_angle;
        this.plane_transform_radius = plane_transform_radius;
        this.plane_transform_amount = plane_transform_amount;
        
    }

    @Override
    public Complex transform(Complex pixel) {
        
        return pixel.pinch(center, plane_transform_radius, plane_transform_amount, plane_transform_angle);
        
    }
}
