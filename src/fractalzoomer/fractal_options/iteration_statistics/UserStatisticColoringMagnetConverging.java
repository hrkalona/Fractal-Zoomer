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
public class UserStatisticColoringMagnetConverging extends GenericStatistic {
    private Complex val;
    private Complex val2;
    private int statisticIteration;
    private ExpressionNode expr;
    private Parser parser;
    private Complex[] globalVars;
    private double log_convergent_bailout;
    private Complex root;
    private PlanePointOption init_val;
    private int reductionFunction;
    private boolean useIterations;
    
    public UserStatisticColoringMagnetConverging(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double convergent_bailout, double bailout, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value, int reductionFunction, boolean useIterations, boolean useSmoothing) {
        super(statistic_intensity, useSmoothing, useAverage);
        val = new Complex();
        val2 = new Complex();
        
        root = new Complex(1, 0);
        
        this.useAverage = useAverage;
        this.reductionFunction = reductionFunction;
        this.useIterations = useIterations;
        
        log_convergent_bailout = Math.log(convergent_bailout);
        
        this.globalVars = globalVars;
        
        parser = new Parser();
        expr = parser.parse(user_statistic_formula);  
                
        if(parser.foundBail()) {
            parser.setBailvalue(new Complex(bailout, 0));
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
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);
        
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

        if (parser.foundC0()) {
            parser.setC0value(c0);
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
       
        switch (reductionFunction) {
            case MainWindow.REDUCTION_SUM:
                samples++;
                val2.assign(val);
                val.plus_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MIN:
                Complex newVal = expr.getValue();
                int res = newVal.compare(val);

                if (res == 1) { //newVal < val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_MAX:
                newVal = expr.getValue();
                res = newVal.compare(val);

                if (res == -1) { //newVal > val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_ASSIGN:
                val.assign(expr.getValue());
                break;
            case MainWindow.REDUCTION_SUB:
                samples++;
                val2.assign(val);
                val.sub_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MULT:
                val.times_mutable(expr.getValue());
                break;
        }
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        val = new Complex(init_val.getValue(pixel));
        val2 = new Complex(val);
        samples = 0;
        statisticIteration = 0;
    }

    @Override
    public double getValue() {
        
        if (reductionFunction == MainWindow.REDUCTION_MAX || reductionFunction == MainWindow.REDUCTION_MIN) {
            return useIterations ? statisticIteration * statistic_intensity : val.getRe() * statistic_intensity;
        }
        else if(reductionFunction != MainWindow.REDUCTION_SUM && reductionFunction != MainWindow.REDUCTION_SUB) {
            return val.getRe() * statistic_intensity;
        }
        
        if(samples < 1) {
            return 0;
        }
        
        double sumRe = val.getRe();
        double sum2Re = val2.getRe();
        
        if(useAverage) {
            sumRe = sumRe / samples;
            sum2Re = samples < 2 ? 0 : sum2Re / (samples - 1);
        }
        
        if(!useSmoothing) {
            return sumRe * statistic_intensity;
        }
        
        double smoothing = OutColorAlgorithm.fractionalPartMagnetConverging(z_val, zold_val, root, log_convergent_bailout);
        
	    return (sumRe + (sum2Re - sumRe) * smoothing) * statistic_intensity;
    }
    
    @Override
    public int getType() {
        return MainWindow.CONVERGING;
    }
    
}
