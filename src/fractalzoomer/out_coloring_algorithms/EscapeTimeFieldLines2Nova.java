/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import static fractalzoomer.utils.ColorAlgorithm.INCREMENT;
import static fractalzoomer.utils.ColorAlgorithm.MAGIC_OFFSET_NUMBER;
import static fractalzoomer.utils.ColorAlgorithm.OutNotUsingIncrement;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeFieldLines2Nova extends EscapeTimeFieldLines2 {
    
    public EscapeTimeFieldLines2Nova() {

        super(Math.log(16.0));
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {
        
        double lineWidth = 0.008;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) > lineWidth * fz;
        
        return line ? (Integer)object[0] + MAGIC_OFFSET_NUMBER : -((Integer)object[0] + MAGIC_OFFSET_NUMBER + INCREMENT);

    }
    
}
