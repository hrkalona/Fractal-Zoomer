/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserStatisticColoringRootFindingMethod extends GenericStatistic {
    private Complex sum;
    private Complex sum2;
    private ExpressionNode expr;
    private Parser parser;
    private Complex[] globalVars;
    private double log_convergent_bailout;
    private boolean useAverage;
    private PlanePointOption init_val;
    
    public UserStatisticColoringRootFindingMethod(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double convergent_bailout, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value) {
        super(statistic_intensity);
        sum = new Complex();
        sum2 = new Complex();
        
        this.useAverage = useAverage;
        
        log_convergent_bailout = Math.log(convergent_bailout);
        
        this.globalVars = globalVars;
        
        parser = new Parser();
        expr = parser.parse(user_statistic_formula);  
                
        if(parser.foundCbail()) {
            parser.setCbailvalue(new Complex(convergent_bailout, 0));
        }

        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
        }
        
        if(parser.foundSize()) {
            parser.setSizevalue(new Complex(size, 0));
        }
        
        if (parser.foundISize()) {
            parser.setISizevalue(new Complex(ThreadDraw.IMAGE_SIZE, 0));
        }

        if(parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }
        
        init_val = new VariableInitialValue(user_statistic_init_value, xCenter, yCenter, size, max_iterations, point, globalVars);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start) {
        
        if(parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(z);
        }
        
        if(parser.foundC()) {
            parser.setCvalue(c);
        }
        
        if(parser.foundS()) {
            parser.setSvalue(start);
        }
        
        if(parser.foundP()) {
            parser.setPvalue(zold);
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }
       
        samples++;
        sum2.assign(sum);
        sum.plus_mutable(expr.getValue());
        
        z_val.assign(z);
        zold_val.assign(zold);
        zold2_val.assign(zold2);
    }

    @Override
    public void initialize(Complex pixel) {
        sum = new Complex(init_val.getValue(pixel));
        sum2 = new Complex(sum);
        samples = 0;
    }

    @Override
    public double getValue() {
        if(samples < 1) {
            return 0;
        }
        
        double smoothing = OutColorAlgorithm.fractionalPartConverging(z_val, zold_val, zold2_val, log_convergent_bailout);
        double sumRe = sum.getRe();
        double sum2Re = sum2.getRe();
        
        if(useAverage) {
            sumRe = sumRe / samples;
            sum2Re = samples < 2 ? 0 : sum2Re / (samples - 1);
        }
        
	return (sumRe + (sum2Re - sumRe) * smoothing) * statistic_intensity;
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
