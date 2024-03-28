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
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmRootFindingMethod extends OutColorAlgorithm {
    
    private ExpressionNode expr;
    private Parser parser;
    protected int max_iterations;
    private Complex[] globalVars;
    
    public UserOutColorAlgorithmRootFindingMethod(String outcoloring_formula, double convergent_bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars) {
        
        super();
        
        this.globalVars = globalVars;
        
        this.max_iterations = max_iterations;
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
                   
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
            parser.setISizevalue(new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0));
        }

        if (parser.foundWidth()) {
            parser.setWidthvalue(new Complex(TaskRender.WIDTH, 0));
        }

        if (parser.foundHeight()) {
            parser.setHeightvalue(new Complex(TaskRender.HEIGHT, 0));
        }

        if(parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }
        
        OutUsingIncrement = false;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        if(parser.foundN()) {
            parser.setNvalue(new Complex((int)object[0], 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[1]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[5]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[6]));
        }

        if(parser.foundC0()) {
            parser.setC0value(((Complex)object[7]));
        }

        if (parser.foundPixel()) {
            parser.setPixelvalue(((Complex) object[8]));
        }

        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[3]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[4]));
        }
        
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }
        
        double result = expr.getValue().getRe();
        
        if (TaskRender.USE_DIRECT_COLOR) {
            return result;
        }
        
        if(Math.abs(result) == max_iterations) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
        }

        if(Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS_DE : ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
        }
        
        return result; 
        
    }
    
}
