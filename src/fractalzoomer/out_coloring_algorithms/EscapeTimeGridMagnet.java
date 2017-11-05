/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeGridMagnet extends EscapeTimeGrid {
    
    public EscapeTimeGridMagnet(double log_bailout_squared) {

        super(log_bailout_squared);
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {
        
        double zabs = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;
        boolean grid = 0.05 < zabs && zabs < 0.95 && 0.05 < zarg && zarg < 0.95;
        
        double temp = grid ? (Integer)object[0] : (Integer)object[0] + INCREMENT;
        
        return (Boolean)object[2] ? temp + MAGIC_OFFSET_NUMBER + 106 : temp + MAGIC_OFFSET_NUMBER;

    }
    
}
