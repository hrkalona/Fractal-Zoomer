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
package fractalzoomer.core.iteration_algorithm;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.functions.Fractal;

/**
 *
 * @author hrkalona2
 */
public class FractalIterationAlgorithm extends IterationAlgorithm {
    public FractalIterationAlgorithm(Fractal fractal) {
        
        super(fractal);
        
    }
    
    @Override
    public double calculate(GenericComplex number) {
        
        return fractal.calculateFractal(number);
        
    }

    @Override
    public Complex calculateDomain(GenericComplex number) {
        
        return fractal.calculateFractalDomain((Complex)number);
        
    }

    @Override
    public double[] calculateVectorized(GenericComplex[] numbers) {

        return fractal.calculateFractalVectorized(numbers);

    }

    @Override
    public void calculateOrbit() {
        
        fractal.calculateFractalOrbit();
        
    }

    @Override
    public double[] calculate3D(GenericComplex number) {
        
        double value = fractal.calculateFractal(number);
        return new double[] {fractal.getFractal3DHeight(value), value};
        
    }

    @Override
    public boolean isJulia() {
        return false;
    }
    
}
