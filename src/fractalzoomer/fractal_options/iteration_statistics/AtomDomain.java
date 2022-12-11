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
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;

/**
 *
 * @author hrkalona2
 */
public class AtomDomain extends GenericStatistic {
    private double minNorm;
    private int minIteration;
    private boolean showAtomDomains;
    private int normtType;
    private double atomNorm;
    private double atomNormReciprocal;
    
    public AtomDomain(boolean showAtomDomains, double statistic_intensity, int normtType, double atomNorm) {
        super(statistic_intensity, false, false);
        this.showAtomDomains = showAtomDomains;
        this.normtType = normtType;
        this.atomNorm = atomNorm;
        atomNormReciprocal = 1 / atomNorm;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);

        double currentNorm = 0;

        switch (normtType) {
            case 0:
                currentNorm = z.norm();
                break;
            case 1: //Rhombus
                currentNorm = z.getAbsRe() + z.getAbsIm();
                break;
            case 2: //Square
                currentNorm = Math.max(z.getAbsRe(), z.getAbsIm());
                break;
            case 3:
                currentNorm = z.nnorm(atomNorm, atomNormReciprocal);
                break;
        }

        if(currentNorm < minNorm) {
            minNorm = currentNorm;
            minIteration = iterations;
        }
        
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        minNorm = Double.MAX_VALUE;
        minIteration = 0;
    }

    @Override
    public double getValue() {
 
        return showAtomDomains ? statistic_intensity * minIteration : statistic_intensity * minNorm;
        
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }
    
}
