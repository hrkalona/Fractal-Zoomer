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

/**
 *
 * @author hrkalona2
 */
public class Banded extends OutColorAlgorithm {

    public Banded() {
        super();
        OutNotUsingIncrement = true;
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double temp = Math.log(((Complex)object[1]).norm_squared());
        
        temp = temp <= 0 ? 1e-33 : temp;
        
        double f = (Math.log(temp) / Math.log(2)) * 2.4;

        return (Integer)object[0] + Math.abs(f);

    }
    
}
