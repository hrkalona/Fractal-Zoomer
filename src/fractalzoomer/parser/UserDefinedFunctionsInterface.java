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
package fractalzoomer.parser;

import fractalzoomer.core.Complex;

/**
 *
 * @author kaloch
 */
public interface UserDefinedFunctionsInterface {
    
    public Complex f(Complex z);
    public Complex g(Complex z, Complex w);  
    public Complex m(Complex z1, Complex z2, Complex z3, Complex z4, Complex z5, Complex z6, Complex z7, Complex z8, Complex z9, Complex z10);
    public Complex k(Complex z1, Complex z2, Complex z3, Complex z4, Complex z5, Complex z6, Complex z7, Complex z8, Complex z9, Complex z10, Complex z11, Complex z12, Complex z13, Complex z14, Complex z15, Complex z16, Complex z17, Complex z18, Complex z19, Complex z20);
    
}
