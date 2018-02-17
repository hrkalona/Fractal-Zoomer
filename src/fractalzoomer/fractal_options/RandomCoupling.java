/*
 * Copyright (C) 2018 hrkalona
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

import java.util.Random;

/**
 *
 * @author hrkalona
 */
public class RandomCoupling extends Coupling {
    private double coupling_amplitude;
    private int coupling_seed;
    private Random generator;
    
    public RandomCoupling(double coupling, double coupling_amplitude, int coupling_seed) {
        
        super(coupling);
        
        this.coupling_amplitude = coupling_amplitude;
        this.coupling_seed = coupling_seed;
        generator = new Random(coupling_seed);
        
    }

    @Override
    public double calculateFinalCoupling(int iterations) {
        
          if(iterations == 0) {
                generator = new Random(coupling_seed);
          }
          return coupling * (1 + coupling_amplitude * (generator.nextDouble() - 0.5) * 2);
          
    }
    
}
