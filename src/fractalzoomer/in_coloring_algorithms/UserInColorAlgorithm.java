/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserInColorAlgorithm extends InColorAlgorithm {
    private ExpressionNode expr;
    private Parser parser;
    private int max_iterations;
    
    public UserInColorAlgorithm(String incoloring_formula, int max_iterations, double xCenter, double yCenter, double size, double[] point) {
        
        super();
        
        parser = new Parser();
        expr = parser.parse(incoloring_formula); 
                
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
        }
        
        if(parser.foundSize()) {
            parser.setSizevalue(new Complex(size, 0));
        }
        
        if(parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }
        
        this.max_iterations = max_iterations;
        
        InNotUsingIncrement = true;
        
    }

    @Override
    public double getResult(Object[] object) {

        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[0]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[3]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[4]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[1]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[2]));
        }
        
        Complex[] vars = (Complex[])object[5];
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser.foundVar(i)) {
                parser.setVarsvalue(i, vars[i]);
            }
        }
        
        double result = expr.getValue().getRe();
 
        if(Math.abs(result) == max_iterations) {
            return result;
        }
        
        if(result < 0) {
            return result - MAGIC_OFFSET_NUMBER;
        }
        else {
            return result + MAGIC_OFFSET_NUMBER;
        }
        
    }
    
}
