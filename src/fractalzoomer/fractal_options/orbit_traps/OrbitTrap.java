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
    protected int trapId;
    
    public OrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth) {
        
        point = new Complex(pointRe, pointIm);
        this.trapLength = trapLength;
        this.trapWidth = trapWidth;
        
    }
    
    public void initialize() {
        
        distance = Double.MAX_VALUE;
        trapId = -1;
        
    }
    
    public abstract void check(Complex val);
    
    public double getDistance() {
        
        return distance;
        
    }
    
    public abstract double getMaxValue();
    
    public double applyLineFunction(int type, double value) {
        
        if(type == 0) return 0;
        
        switch (type) {
            case 1:
                return Math.sin(value);
            case 2:
                return Math.cos(value);
            case 3:
                return Math.tan(value);
            case 4:
                return Math.sinh(value);
            case 5:
                return Math.cosh(value);
            case 6:
                return Math.tanh(value);
            case 7:
                return Math.asin(value);
            case 8:
                return Math.acos(value);
            case 9:
                return Math.atan(value);
            case 10:
                return value * value;
            case 11:
                return value * value * value;
            case 12:
                return Math.sqrt(value);
            case 13:
                return Math.cbrt(value);
            case 14:
                return Math.exp(value);
            case 15:
                return Math.log(value);
            case 16:
                return Math.abs(value);      
        }
        
        return 0;
    }
    
    public int getTrapId(){
        
        return trapId;
                
    }
    
}
