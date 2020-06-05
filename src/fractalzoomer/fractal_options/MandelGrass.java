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

package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class MandelGrass extends MandelVariation {
  private Complex factor;
    
    public MandelGrass(double real, double imaginary) {
        
        super();
        
        factor = new Complex(real, imaginary);

    }

    @Override
    public Complex getValue(Complex pixel) {
        
        return pixel.plus_mutable((pixel.divide(pixel.norm())).times_mutable(factor));
        
    }
    
}
