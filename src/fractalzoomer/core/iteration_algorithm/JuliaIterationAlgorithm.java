/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.core.iteration_algorithm;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.Fractal;

/**
 *
 * @author hrkalona2
 */
public class JuliaIterationAlgorithm extends IterationAlgorithm {
    
    public JuliaIterationAlgorithm(Fractal fractal) {
        
        super(fractal);
        
    }
    
    @Override
    public double calculate(Complex number) {
        
        return fractal.calculateJulia(number);
        
    }
    
    @Override
    public Complex calculateDomain(Complex number) {
        
        return fractal.calculateJuliaDomain(number);
        
    }

    @Override
    public void calculateOrbit() {
        
        fractal.calculateJuliaOrbit();
        
    }

    @Override
    public double[] calculate3D(Complex number) {
        
        return fractal.calculateJulia3D(number);
        
    }
}
