/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
public abstract class OrbitTrap {
    protected double distance;
    protected Complex point;
    protected double trapLength;
    protected double trapWidth;
    
    public OrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth) {
        
        point = new Complex(pointRe, pointIm);
        this.trapLength = trapLength;
        this.trapWidth = trapWidth;
        
    }
    
    public void initialize() {
        
        distance = Double.MAX_VALUE;
        
    }
    
    public abstract void check(Complex val);
    
    public double getDistance() {
        
        return distance;
        
    }
}
