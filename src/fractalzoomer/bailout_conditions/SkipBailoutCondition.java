/*
 * Copyright (C) 2020 hrkalona2
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
package fractalzoomer.bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.BigNum;
import fractalzoomer.core.BigNumComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class SkipBailoutCondition extends BailoutCondition {
    public static int SKIPPED_ITERATION_COUNT = 0;
    private BailoutCondition wrappedCondition;
 
    public SkipBailoutCondition(BailoutCondition condition) {
        
        super(0.0);
        wrappedCondition = condition;
        
    }
    
     @Override
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }
        
        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
  
     }

    @Override
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel);
    }
    
}
