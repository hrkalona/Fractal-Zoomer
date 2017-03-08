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

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithm extends OutColorAlgorithm {
    protected ExpressionNode expr;
    protected Parser parser;
    protected Complex c_bailout;
    protected Complex c_max_iterations;

    public UserOutColorAlgorithm(String outcoloring_formula, double bailout, int max_iterations) {

        super();
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
        c_bailout = new Complex(bailout, 0);    
                
        if(parser.foundBail()) {
            parser.setBailvalue(c_bailout);
        }
        
        c_max_iterations = new Complex(max_iterations, 0);
                    
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(c_max_iterations);
        }

    }

    @Override
    public double getResult(Object[] object) {

        if(parser.foundN()) {
            parser.setNvalue(new Complex((Integer)object[0], 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[1]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[4]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[5]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[2]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[3]));
        }

        return expr.getValue().getAbsRe() + MAGIC_OFFSET_NUMBER;
        
    }

}
