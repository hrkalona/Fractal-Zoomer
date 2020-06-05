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
public class KaleidoscopePlane extends Plane {

    private double plane_transform_angle;
    private double plane_transform_angle2;
    private double plane_transform_radius;
    private int plane_transform_sides;
    private Complex center;

    public KaleidoscopePlane(double[] plane_transform_center, double plane_transform_angle, double plane_transform_angle2, double plane_transform_radius, int plane_transform_sides) {

        super();
        center = new Complex(plane_transform_center[0], plane_transform_center[1]);
        this.plane_transform_angle = plane_transform_angle;
        this.plane_transform_radius = plane_transform_radius;
        this.plane_transform_angle2 = plane_transform_angle2;
        this.plane_transform_sides = plane_transform_sides;
    }

    @Override
    public Complex transform(Complex pixel) {
        
        return pixel.kaleidoscope(center, plane_transform_angle, plane_transform_angle2, plane_transform_radius, plane_transform_sides);
    }
}
