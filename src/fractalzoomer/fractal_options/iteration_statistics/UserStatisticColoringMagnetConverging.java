
package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserStatisticColoringMagnetConverging extends GenericUserStatistic {
    private double log_convergent_bailout;
    private Complex root;
    
    public UserStatisticColoringMagnetConverging(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double convergent_bailout, double bailout, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value, int reductionFunction, boolean useIterations, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, user_statistic_formula, xCenter, yCenter, max_iterations, size, point, globalVars, useAverage,  user_statistic_init_value, reductionFunction, useIterations, useSmoothing, lastXItems);
        
        root = new Complex(1, 0);
        
        log_convergent_bailout = Math.log(convergent_bailout);
        

        if(parser.foundBail()) {
            parser.setBailvalue(new Complex(bailout, 0));
        }

    }

    @Override
    protected double getSmoothing() {
        if(converging_smoothing_algorithm == 0) {
            return OutColorAlgorithm.fractionalPartMagnetConverging1(z_val, zold_val, root, log_convergent_bailout, normSmoothingImpl);
        }
        else {
            return OutColorAlgorithm.fractionalPartMagnetConverging2(z_val, zold_val, root, log_convergent_bailout, normSmoothingImpl);
        }
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
