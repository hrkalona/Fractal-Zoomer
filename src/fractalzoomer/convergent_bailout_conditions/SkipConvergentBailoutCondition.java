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
package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.BigComplex;
import fractalzoomer.core.Complex;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class SkipConvergentBailoutCondition extends ConvergentBailoutCondition {
    public static int SKIPPED_ITERATION_COUNT = 0;
    private ConvergentBailoutCondition wrappedCondition;
 
    public SkipConvergentBailoutCondition(ConvergentBailoutCondition condition) {
        
        super(0.0);
        wrappedCondition = condition;
        
    }


    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, root, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, root, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, root, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        if(iterations < SKIPPED_ITERATION_COUNT) {
            return false;
        }

        return wrappedCondition.converged(z, root, zold, zold2, iterations, c, start, c0, pixel);
    }
}
