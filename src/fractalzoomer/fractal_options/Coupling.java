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

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona
 */
public abstract class Coupling {
    protected double coupling;
    
    public Coupling(double coupling) {
        
        this.coupling = coupling;
        
    }
    
    public abstract double calculateFinalCoupling(int iterations);
            
    public Complex[] couple(Complex a1, Complex a2, int iterations) {
        
        double final_coupling = calculateFinalCoupling(iterations);
        
        return new Complex[] {(a2.sub(a1)).times_mutable(final_coupling).plus_mutable(a1), (a1.sub(a2)).times_mutable(final_coupling).plus_mutable(a2)};
        
    }
}
