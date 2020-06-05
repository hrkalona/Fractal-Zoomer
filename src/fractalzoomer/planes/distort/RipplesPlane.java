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
public class RipplesPlane extends Plane {
    private double[] plane_transform_scales;
    private double[] plane_transform_wavelength;
    private int waveType;

    public RipplesPlane(double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType) {

        super();
        this.plane_transform_scales = plane_transform_scales;
        this.plane_transform_wavelength = plane_transform_wavelength;
        this.waveType = waveType;

    }

    @Override
    public Complex transform(Complex pixel) {

        return pixel.ripples(new Complex(plane_transform_wavelength[0], plane_transform_wavelength[1]), new Complex(plane_transform_scales[0], plane_transform_scales[1]), waveType);

    }
}
