/*
 * Copyright (C) 2017 hrkalona
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

/**
 *
 * @author hrkalona
 */
public class CosineCoupling extends Coupling {
    private double coupling_amplitude;
    private double coupling_frequency;
    
    public CosineCoupling(double coupling, double coupling_amplitude, double coupling_frequency) {
        
        super(coupling);
        
        this.coupling_amplitude = coupling_amplitude;
        this.coupling_frequency = coupling_frequency;
        
    }

    @Override
    public double calculateFinalCoupling(int iterations) {
        
        return coupling * (1 + coupling_amplitude * Math.cos(iterations * coupling_frequency));
        
    }
    
}
