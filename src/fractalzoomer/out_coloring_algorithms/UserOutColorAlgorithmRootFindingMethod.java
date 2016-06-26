/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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
public class UserOutColorAlgorithmRootFindingMethod extends OutColorAlgorithm {
    
    private ExpressionNode expr;
    private Parser parser;
    private Complex c_convergent_bailout;
    
    public UserOutColorAlgorithmRootFindingMethod(String outcoloring_formula, double convergent_bailout) {
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
        c_convergent_bailout = new Complex(convergent_bailout, 0);
        
    }

    @Override
    public double getResult(Object[] object) {
        
        if(parser.foundN()) {
            parser.setNvalue(new Complex((Integer)object[0], 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[1]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[3]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[4]));
        }
        
        if(parser.foundCbail()) {
            parser.setCbailvalue(c_convergent_bailout);
        }

        return expr.getValue().getAbsRe() + 100800;
        
    }

    @Override
    public double getResult3D(Object[] object) {
        
        return getResult(object);
        
    }
    
}