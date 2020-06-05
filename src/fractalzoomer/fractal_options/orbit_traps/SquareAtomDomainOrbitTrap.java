/*
 * Copyright (C) 2020 hrkalona
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
public class SquareAtomDomainOrbitTrap extends OrbitTrap {
    private double old_distance;

    public SquareAtomDomainOrbitTrap(double pointRe, double pointIm, boolean countTrapIterations) {

        super(pointRe, pointIm, 0.0, 0.0, countTrapIterations);

    }
    
    @Override
    public void initialize(Complex pixel) {
        
        super.initialize(pixel);
        old_distance = Double.MAX_VALUE;
        
    }

    @Override
    public void check(Complex val, int iteration) {

        Complex diff = val.sub(point);
        
        double dist = Math.max(diff.getAbsRe(), diff.getAbsIm());

        if (dist < distance) {
            old_distance = distance;
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);           
        } else if (dist < old_distance) {
            old_distance = dist;
            countExtraIterations();
        }

    }

    @Override
    public double getMaxValue() {
        return old_distance;
    }
    
}
