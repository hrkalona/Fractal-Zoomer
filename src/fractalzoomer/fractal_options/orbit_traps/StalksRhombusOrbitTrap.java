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

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class StalksRhombusOrbitTrap extends OrbitTrap {
    private double stalksradiushigh;
    private double stalksradiuslow;
    private double cnorm;
    
    public StalksRhombusOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, boolean countTrapIterations, int lastXItems) {
        
        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        double dist = val.distance(point);

        if (dist <= stalksradiushigh && dist >= stalksradiuslow && iteration > 0 && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }
        
        Complex temp = val.sub(point);
        dist = Math.abs(temp.getAbsRe() + temp.getAbsIm() - trapLength);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

    }

    @Override
    public double getDistance() {

        if(keepLastXItems && !processedLastItems) {
            processLastItems();
        }

        return trapId == 0 ? (distance != Double.MAX_VALUE ? Math.abs(trapWidth - 2 * (distance - cnorm + trapWidth * 0.5)) : distance) : distance;

    }
    
    @Override
    public void initialize(Complex pixel) {
        
        super.initialize(pixel);
        
        cnorm = pixel.norm();
        stalksradiushigh = cnorm + trapWidth * 0.5;
        stalksradiuslow = cnorm - trapWidth * 0.5;
        
    }

    @Override
    public double getMaxValue() {
        return trapWidth;
    }
    
}
