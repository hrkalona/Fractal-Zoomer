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
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class SquareOrbitTrap extends OrbitTrap {

    public SquareOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);

    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);
        
        double dist = Math.abs(Math.max(diff.getAbsRe(), diff.getAbsIm()) - trapLength);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapWidth;
    }
    
}
