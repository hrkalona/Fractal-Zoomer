/*
 * Copyright (C) 2020 hrkalona2
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
public class EscapeTimeColorDecompositionMagneticPendulum extends EscapeTimeColorDecomposition {
    private Complex[] magnets;
    
    public EscapeTimeColorDecompositionMagneticPendulum(Complex[] magnets) {
        super();
        this.magnets = magnets;
        OutUsingIncrement = false;
    }

    @Override
    public double getResult(Object[] object) {

        Complex z = ((Complex)object[1]);
        int min_i = 0;
        double min = Double.MAX_VALUE;
        
        for(int i = 0; i < magnets.length; i++) {
            double temp_dist = z.distance_squared(magnets[i]);
            if(temp_dist < min) {
                min = temp_dist;
                min_i  = i;
            }
        }
 
        return pi59 * (min_i + 1) + ((Complex)object[9]).getRe();
        
    }
    
    @Override
    public double getResult3D(Object[] object, double result) {
        
        return ((Complex)object[7]).getRe();
        
    }
    
}
