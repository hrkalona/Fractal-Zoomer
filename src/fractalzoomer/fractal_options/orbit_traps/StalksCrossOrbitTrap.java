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

/**
 *
 * @author hrkalona
 */
public class StalksCrossOrbitTrap extends OrbitTrap {
    private double stalksradiushigh;
    private double stalksradiuslow;
    private double cnorm;
    private int lineType;
    
    public StalksCrossOrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth, int lineType) {
        
        super(pointRe, pointIm, trapLength, trapWidth);
        this.lineType = lineType;
        
    }

    @Override
    public void check(Complex val, int iteration) {

        double dist = val.distance(point);

        if (dist <= stalksradiushigh && dist >= stalksradiuslow && iteration > 0) {
            distance = dist;
            trapId = 0;
            setTrappedData(val, iteration);
        }
        
        dist = Math.abs(val.getRe() - applyLineFunction(lineType, val.getIm()) - point.getRe());     
        if(dist < trapWidth && Math.abs(val.getIm() - point.getIm()) < trapLength && dist < distance) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

        dist = Math.abs(val.getIm() - applyLineFunction(lineType, val.getRe()) - point.getIm());
        if(dist < trapWidth && Math.abs(val.getRe() - point.getRe()) < trapLength && dist < distance) {
            distance = dist;
            trapId = 2;
            setTrappedData(val, iteration);
        } 

    }

    @Override
    public double getDistance() {

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
