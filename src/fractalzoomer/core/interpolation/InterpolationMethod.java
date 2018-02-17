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
package fractalzoomer.core.interpolation;

/**
 *
 * @author hrkalona2
 */
public abstract class InterpolationMethod {
    
    public InterpolationMethod() {
        
    }
    
    public abstract int interpolate(int r1, int g1, int b1, int r2, int g2, int b2, double coef);
    public abstract double interpolate(double a, double b, double coef);
    public abstract int interpolate(int a, int b, double coef);
    public abstract double getCoef(double coef);
}
