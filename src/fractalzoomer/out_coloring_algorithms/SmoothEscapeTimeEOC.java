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
 * @author hrkalona
 */
public class SmoothEscapeTimeEOC extends SmoothEscapeTime {

    private double log_convergent_bailout;
    private double convergent_bailout;
    private int algorithm2;
    private Norm cNormImpl;

    public SmoothEscapeTimeEOC(double bailout, double convergent_bailout, int algorithm, int algorithm2, Norm normImpl) {

        super(bailout, algorithm, normImpl);
        this.convergent_bailout = convergent_bailout;
        this.algorithm2 = algorithm2;
        OutUsingIncrement = false;
        smooth = true;

    }

    @Override
    public void setNormImpl(Norm normImpl) {
        cNormImpl = normImpl;

        double exp = cNormImpl.getExp();
        if(exp != 2) {
            log_convergent_bailout = exp != 1 ? Math.log(Math.pow(convergent_bailout, exp)) : Math.log(convergent_bailout);
        }
        else {
            log_convergent_bailout = Math.log(convergent_bailout * convergent_bailout);
        }
    }

    @Override
    public double getResult(Object[] object) {

        if ((boolean) object[8]) {

            if (algorithm == 0) {
                return (int) object[0] + getSmoothing1((Complex) object[1], (Complex) object[2], log_bailout, normImpl) + MAGNET_INCREMENT;
            } else if (algorithm == 2) {
                return (int) object[0] + getSmoothing3((Complex) object[1], (Complex) object[2], bailout, normImpl) + MAGNET_INCREMENT;
            } else {
                //double temp2 = ((Complex)object[1]).norm_squared();
                //return (int)object[0] + 1 - Math.log((Math.log(temp2)) / log_bailout_squared) / log_power + MAGNET_INCREMENT;
                return (int) object[0] + getSmoothing2((Complex) object[1], (Complex) object[2], log_bailout, usePower, log_power, normImpl) + MAGNET_INCREMENT;
            }
        } else {
            if (algorithm2 == 0) {
                return (int) object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing1((Complex) object[1], (Complex) object[2], (Complex) object[3], log_convergent_bailout, cNormImpl);
            } else {
                return (int) object[0] + SmoothEscapeTimeRootFindingMethod.getSmoothing2((Complex) object[1], (Complex) object[2], (Complex) object[3], log_convergent_bailout, cNormImpl);
            }
        }

    }
}
