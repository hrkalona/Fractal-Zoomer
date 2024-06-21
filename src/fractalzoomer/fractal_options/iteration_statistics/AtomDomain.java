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
import fractalzoomer.core.norms.*;
import fractalzoomer.main.MainWindow;

/**
 *
 * @author hrkalona2
 */
public class AtomDomain extends GenericStatistic {
    private double minNorm;
    private int minIteration;
    private boolean showAtomDomains;
    private Norm normImpl;

    
    public AtomDomain(boolean showAtomDomains, double statistic_intensity, int normtType, double atomNorm, int lastXItems) {
        super(statistic_intensity, false, false, lastXItems);
        this.showAtomDomains = showAtomDomains;

        switch (normtType) {
            case 0:
                normImpl = new Norm2();
                break;
            case 1: //Rhombus
                normImpl = new Norm1();
                break;
            case 2: //Square
                normImpl = new NormInfinity();
                break;
            case 3:
                normImpl = new NormN(atomNorm);
                break;
        }
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        addSample(z);

    }

    private void sampleLastX() {
        minNorm = Double.MAX_VALUE;
        minIteration = 0;
        samples = 0;

        int start = sampleItem >= sampleItems.length ? sampleItem % sampleItems.length : 0;
        for(int i = start, count = 0; count < sampleItems.length; i++, count++) {
            StatisticSample sam = sampleItems[i % sampleItems.length];

            if(sam != null) {
                addSample(sam.z_val);
            }
        }
    }

    private void addSample(Complex z) {
        double currentNorm = normImpl.computeWithRoot(z);

        if(currentNorm < minNorm) {
            minNorm = currentNorm;
            minIteration = iterations;
        }

        samples++;
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        minNorm = Double.MAX_VALUE;
        minIteration = 0;
    }

    @Override
    protected double getValue() {

        if(keepLastXItems) {
            sampleLastX();
        }

        return showAtomDomains ? statistic_intensity * minIteration : statistic_intensity * minNorm;
        
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING_AND_CONVERGING;
    }
    
}
