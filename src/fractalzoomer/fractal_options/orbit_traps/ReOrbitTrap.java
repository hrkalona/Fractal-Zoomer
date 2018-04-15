/*
 * Copyright (C) 2018 hrkalona
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
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona
 */
public class ReOrbitTrap extends OrbitTrap {

    public ReOrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth) {

        super(pointRe, pointIm, trapLength, trapWidth);

    }

    @Override
    public void check(Complex val) {
        
        double dist = Math.abs(val.getIm() - point.getIm());
        
        if(dist < trapWidth && Math.abs(val.getRe() - point.getRe()) < trapLength && dist < distance) {
            distance = dist;
        }

    }
    
}
