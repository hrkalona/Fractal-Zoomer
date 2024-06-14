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
public class NNormAtomDomainOrbitTrap extends OrbitTrap {
    private double n_norm;
    private double n_norm_reciprocal;
    private double max_distance;
    private double old_distance;

    public NNormAtomDomainOrbitTrap(int checkType, double pointRe, double pointIm, double n_norm, boolean countTrapIterations, int lastXItems) {

        super(checkType, pointRe, pointIm, 0.0, 0.0, countTrapIterations, lastXItems);
        this.n_norm = n_norm;
        n_norm_reciprocal = 1 / n_norm;

    }
    
    @Override
    public void initialize(Complex pixel) {
        
        super.initialize(pixel);
        max_distance = 0;
        old_distance = Double.MAX_VALUE;
        
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);

        double dist = diff.nnorm(n_norm, n_norm_reciprocal);

        if (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST) {
            old_distance = distance;
            if(distance > max_distance) {
                max_distance = distance;
            }
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        } else if (checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE) {
            if (dist < distance) {
                old_distance = distance;
                if(distance > max_distance) {
                    max_distance = distance;
                }
                distance = dist;
                trapId = 0;
                setTrappedData(val, iteration);
            }
            else if (dist < old_distance && dist != distance) {
                old_distance = dist;
                countExtraIterations();
            }
        }

    }

    @Override
    public double getMaxValue() {
        if(distance <= old_distance) {
            return old_distance;
        }
        else {
            return max_distance;
        }
    }
    
}
