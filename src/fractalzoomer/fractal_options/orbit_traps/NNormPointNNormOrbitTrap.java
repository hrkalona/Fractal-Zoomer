/*
 * Copyright (C) 2019 hrkalona
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

public class NNormPointNNormOrbitTrap extends OrbitTrap {
    private double n_norm;

    public NNormPointNNormOrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth, double n_norm) {

        super(pointRe, pointIm, trapLength, trapWidth);
        this.n_norm = n_norm;
        
    }

    @Override
    public void check(Complex val) {

        Complex diff = val.sub(point);
        double norm = diff.nnorm(n_norm);

        double dist = Math.abs(norm - trapLength);

        if(dist < trapWidth && dist < distance) {
            distance = dist;
            trapId = 0;
        }
        
        dist = norm;

        if(dist < trapLength && dist < distance) {
            distance = dist;
            trapId = 1;
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapId == 1 ? trapLength : trapWidth;
    }
    
}
