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
public class EscapeTimeFieldLines2Magnet extends EscapeTimeFieldLines2 {
    
    public EscapeTimeFieldLines2Magnet(double log_bailout_squared) {

        super(log_bailout_squared);
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {
 
        double lineWidth = 0.008;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = Math.log(((Complex)object[1]).norm_squared()) / log_bailout_squared;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) > lineWidth * fz;
        
        double temp = line ? (Integer)object[0] : -((Integer)object[0] + INCREMENT);
        
        if(temp < 0) {
            return (Boolean)object[2] ? temp - MAGNET_INCREMENT : temp;
        }
        else {
            return (Boolean)object[2] ? temp + MAGNET_INCREMENT : temp;
        }

    }
    
}
