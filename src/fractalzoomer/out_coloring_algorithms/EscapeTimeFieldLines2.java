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
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeFieldLines2 extends OutColorAlgorithm {

    protected double log_bailout;
    protected OutColorAlgorithm EscapeTimeAlg;
    private Norm normImpl;

    public EscapeTimeFieldLines2(double bailout, OutColorAlgorithm EscapeTimeAlg, Norm normImpl) {

        super();
        double exp = normImpl.getExp();
        if(exp != 2) {
            log_bailout = exp != 1 ? Math.log(Math.pow(bailout, exp)) : Math.log(bailout);
        }
        else {
            log_bailout = Math.log(bailout * bailout);
        }
        OutUsingIncrement = true;
        this.EscapeTimeAlg = EscapeTimeAlg;
        this.normImpl = normImpl;

    }

    @Override
    public double getResult(Object[] object) {

        double lineWidth = 0.07;  // freely adjustable
        double fx = (((Complex)object[1]).arg() / 2) * Math.PI;
        double fy = Math.log(normImpl.computeWithoutRoot((Complex)object[1])) / log_bailout;  // radius within cell
        double fz = Math.pow(0.5, -fy);  // make wider on the outside

        boolean line = Math.abs(fx) < (1.0 - lineWidth)*fz && lineWidth * fz < Math.abs(fx);
        double result = EscapeTimeAlg.getResult(object);
                
        return line ? result : -(result + INCREMENT);

    }
    
}
