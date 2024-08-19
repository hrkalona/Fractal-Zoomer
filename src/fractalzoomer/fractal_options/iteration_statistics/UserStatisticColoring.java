
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserStatisticColoring extends GenericUserStatistic {
    private double log_bailout_squared;
    private double bailout;

    public UserStatisticColoring(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double bailout, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value, int reductionFunction, boolean useIterations, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, user_statistic_formula, xCenter, yCenter, max_iterations, size, point, globalVars, useAverage,  user_statistic_init_value, reductionFunction, useIterations, useSmoothing, lastXItems);
        log_bailout_squared = Math.log(bailout * bailout);

        if (parser.foundBail()) {
            parser.setBailvalue(new Complex(bailout, 0));
        }

        this.bailout = bailout;

    }

    @Override
    protected double getSmoothing() {
        if(escaping_smoothing_algorithm == 0 && !usePower) {
            return OutColorAlgorithm.fractionalPartEscaping1(z_val, zold_val, log_bailout_squared, normSmoothingImpl);
        }
        else if(escaping_smoothing_algorithm == 2 && !usePower) {
            return OutColorAlgorithm.fractionalPartEscaping3(z_val, zold_val, bailout, normSmoothingImpl);
        }
        else {
            return usePower ? OutColorAlgorithm.fractionalPartEscapingWithPower(z_val, log_bailout_squared, log_power, normSmoothingImpl) : OutColorAlgorithm.fractionalPartEscaping2(z_val, zold_val, log_bailout_squared, normSmoothingImpl);
        }
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }

}
