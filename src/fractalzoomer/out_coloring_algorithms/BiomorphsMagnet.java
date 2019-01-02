/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
public class BiomorphsMagnet extends Biomorphs {
    
    public BiomorphsMagnet(double bailout) {

        super(bailout);
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[1]).getRe();
        double temp2 = ((Complex)object[1]).getIm();
        
        double temp3 = temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ?  (Integer)object[0] : -((Integer)object[0] + INCREMENT);
        
        
        if(temp3 < 0) {
            return (Boolean)object[2] ? temp3 - MAGNET_INCREMENT : temp3;
        }
        else {
            return (Boolean)object[2] ? temp3 + MAGNET_INCREMENT : temp3;
        }

    }
    
}
