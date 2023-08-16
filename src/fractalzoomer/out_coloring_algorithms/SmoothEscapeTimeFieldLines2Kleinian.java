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

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

public class SmoothEscapeTimeFieldLines2Kleinian extends OutColorAlgorithm {
    private double u;
    private double log_bailout_squared;

    public SmoothEscapeTimeFieldLines2Kleinian(double u, double log_bailout_squared) {

        super();
        this.u = u * 0.5;
        this.log_bailout_squared = log_bailout_squared;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = (Integer)object[0] + 1 + Math.log(((Complex)object[1]).sub_i(u).norm());

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double lineWidth = 0.07;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = temp2 / log_bailout_squared;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) < (1.0 - lineWidth)*fz && lineWidth * fz < Math.abs(fx);

        return line ? temp : -(temp + INCREMENT);

    }
}
