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
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserStatisticColoringRootFindingMethod extends GenericUserStatistic {
    private double log_convergent_bailout;

    public UserStatisticColoringRootFindingMethod(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double convergent_bailout, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value, int reductionFunction, boolean useIterations, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, user_statistic_formula, xCenter, yCenter, max_iterations, size, point, globalVars, useAverage,  user_statistic_init_value, reductionFunction, useIterations, useSmoothing, lastXItems);

        log_convergent_bailout = Math.log(convergent_bailout);

        if (parser.foundCbail()) {
            parser.setCbailvalue(new Complex(convergent_bailout, 0));
        }

    }

    @Override
    protected double getSmoothing() {
        if(converging_smoothing_algorithm == 0) {
            return OutColorAlgorithm.fractionalPartConverging1(z_val, zold_val, zold2_val, log_convergent_bailout);
        }
        else {
            return OutColorAlgorithm.fractionalPartConverging2(z_val, zold_val, zold2_val, log_convergent_bailout);
        }
    }

    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }

}
