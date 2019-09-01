/*
 * Copyright (C) 2019 hrkalona2
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
 * @author hrkalona2
 */
public class GoldenRatioSpiralCrossOrbitTrap extends OrbitTrap {
    private int lineType;
    private double phi;

    public GoldenRatioSpiralCrossOrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth, int lineType) {

        super(pointRe, pointIm, trapLength, trapWidth);
        this.lineType = lineType;
        phi = 0.5 * (1 + Math.sqrt(5));

    }

    @Override
    public void check(Complex val, int iteration) {

        if(!trapped) {
            Complex temp = val.sub(point);
            double dist = Math.log(temp.norm()) / (4 * Math.log(phi)) - (temp.arg()) / (2 * Math.PI);
            dist = 18 * Math.abs(dist - Math.round(dist));

            if (dist < trapWidth && dist < distance) {
                distance = dist;
                trapId = 0;
                setTrappedData(val, iteration);
            }
        }
        
        double dist = Math.abs(val.getRe() - applyLineFunction(lineType, val.getIm()) - point.getRe());
        if(dist < trapWidth && Math.abs(val.getIm() - point.getIm()) < trapLength && dist < distance) {
            distance = dist;
            trapId = 1;
            setTrappedData(val, iteration);
        }

        dist = Math.abs(val.getIm() - applyLineFunction(lineType, val.getRe()) - point.getIm());
        if(dist < trapWidth && Math.abs(val.getRe() - point.getRe()) < trapLength && dist < distance) {
            distance = dist;
            trapId = 2;
            iterations = iteration;
        }

    }
    
    @Override
    public double getMaxValue() {
        return trapWidth;
    }
    
}
