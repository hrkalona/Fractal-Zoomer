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

public class SmoothEscapeTimeFieldLinesKleinian extends OutColorAlgorithm {
    private double u;
    private double log_bailout_squared;
    private double pi2;

    public SmoothEscapeTimeFieldLinesKleinian(double u, double log_bailout_squared) {

        super();
        this.u = u * 0.5;
        pi2 = Math.PI * 2;
        this.log_bailout_squared = log_bailout_squared;
        OutUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = (int)object[0] + 1 + Math.log(((Complex)object[1]).sub_i(u).norm());

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double lineWidth = 0.008;  // freely adjustable
        double fx = ((Complex)object[1]).arg() / (pi2);  // angle within cell
        double fy = temp2 / log_bailout_squared;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) > lineWidth * fz;

        return line ? temp : -(temp + INCREMENT);

    }
}
