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
import fractalzoomer.core.norms.Norm;
import fractalzoomer.core.norms.Norm2;
import fractalzoomer.core.norms.NormN;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class StalksNNormOrbitTrap extends OrbitTrap {
    private double stalksradiushigh;
    private double stalksradiuslow;
    private double cnorm;
    private Norm normImpl;
    private Norm normImpl2;
    
    public StalksNNormOrbitTrap(int checkType, double pointRe, double pointIm, double trapLength, double trapWidth, double n_norm, boolean countTrapIterations, int lastXItems) {
        
        super(checkType, pointRe, pointIm, trapLength, trapWidth, countTrapIterations, lastXItems);
        normImpl = new NormN(n_norm);
        normImpl2 = new Norm2();
        
    }

    @Override
    protected void checkInternal(Complex val, int iteration) {

        if(checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST && trapped) {
            return;
        }

        Complex diff = val.sub(point);

        double dist = normImpl2.computeWithRoot(diff);

        if (dist <= stalksradiushigh && dist >= stalksradiuslow && iteration > 0 && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }

        dist = Math.abs(normImpl.computeWithRoot(diff) - trapLength);

        if(dist < trapWidth && (checkType == TRAP_CHECK_TYPE_TRAPPED_FIRST || checkType == TRAP_CHECK_TYPE_TRAPPED_LAST ||  checkType == TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE && dist < distance)) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

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
        if(trapId == 0) {
            return stalksradiushigh;
        }
        else {
            return trapWidth;
        }
    }

    @Override
    public double getMinValue() {
        if(trapId == 0) {
            return stalksradiuslow;
        }
        else {
            return 0;
        }
    }
    
}
