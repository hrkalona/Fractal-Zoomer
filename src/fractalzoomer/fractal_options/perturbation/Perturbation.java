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

package fractalzoomer.fractal_options.perturbation;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.fractal_options.PlanePointOption;


/**
 *
 * @author hrkalona2
 */
public class Perturbation extends PlanePointOption {
    private Complex pixel;
    
    public Perturbation(double re, double im) {
    
        super();
        pixel = new Complex(re, im);
        
    }


    @Override
    public Complex getValue(Complex pixel) {
        
        return this.pixel.plus(pixel);
        
    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return MantExpComplex.create(this.pixel).plus(pixel);
    }
    
}
