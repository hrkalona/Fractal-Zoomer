/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserConditionalInColorAlgorithm extends InColorAlgorithm {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private int max_iterations;
    private Complex[] globalVars;

    public UserConditionalInColorAlgorithm(String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, int max_iterations, double xCenter, double yCenter, double size, double[] point, double genericBail, Complex[] globalVars) {

        super();

        this.globalVars = globalVars;
        
        parser = new Parser[user_incoloring_conditions.length];
        expr = new ExpressionNode[user_incoloring_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_incoloring_conditions[i]);
        }

        parser2 = new Parser[user_incoloring_condition_formula.length];
        expr2 = new ExpressionNode[user_incoloring_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_incoloring_condition_formula[i]);
        }

        Complex c_max_iterations = new Complex(max_iterations, 0);

        if(parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(c_max_iterations);
        }

        if(parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(c_max_iterations);
        }

        if(parser2[0].foundMaxn()) {
            parser2[0].setMaxnvalue(c_max_iterations);
        }

        if(parser2[1].foundMaxn()) {
            parser2[1].setMaxnvalue(c_max_iterations);
        }

        if(parser2[2].foundMaxn()) {
            parser2[2].setMaxnvalue(c_max_iterations);
        }
        
        if(parser[0].foundN()) {
            parser[0].setNvalue(c_max_iterations);
        }
        
        if(parser[1].foundN()) {
            parser[1].setNvalue(c_max_iterations);
        }

        if(parser2[0].foundN()) {
            parser2[0].setNvalue(c_max_iterations);
        }
        
        if(parser2[1].foundN()) {
            parser2[1].setNvalue(c_max_iterations);
        }
        
        if(parser2[2].foundN()) {
            parser2[2].setNvalue(c_max_iterations);
        }
        
        Complex c_center = new Complex(xCenter, yCenter);

        if(parser[0].foundCenter()) {
            parser[0].setCentervalue(c_center);
        }

        if(parser[1].foundCenter()) {
            parser[1].setCentervalue(c_center);
        }

        if(parser2[0].foundCenter()) {
            parser2[0].setCentervalue(c_center);
        }

        if(parser2[1].foundCenter()) {
            parser2[1].setCentervalue(c_center);
        }

        if(parser2[2].foundCenter()) {
            parser2[2].setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if(parser[0].foundSize()) {
            parser[0].setSizevalue(c_size);
        }

        if(parser[1].foundSize()) {
            parser[1].setSizevalue(c_size);
        }

        if(parser2[0].foundSize()) {
            parser2[0].setSizevalue(c_size);
        }

        if(parser2[1].foundSize()) {
            parser2[1].setSizevalue(c_size);
        }

        if(parser2[2].foundSize()) {
            parser2[2].setSizevalue(c_size);
        }
        
        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);
        if (parser[0].foundISize()) {
            parser[0].setISizevalue(c_isize);
        }

        if (parser[1].foundISize()) {
            parser[1].setISizevalue(c_isize);
        }

        if (parser2[0].foundISize()) {
            parser2[0].setISizevalue(c_isize);
        }

        if (parser2[1].foundISize()) {
            parser2[1].setISizevalue(c_isize);
        }

        if (parser2[2].foundISize()) {
            parser2[2].setISizevalue(c_isize);
        }
        
        Complex c_point = new Complex(point[0], point[1]);
        if(parser[0].foundPoint()) {
            parser[0].setPointvalue(c_point);
        }
        
        if(parser[1].foundPoint()) {
            parser[1].setPointvalue(c_point);
        }
        
        if(parser2[0].foundPoint()) {
            parser2[0].setPointvalue(c_point);
        }
        
        if(parser2[1].foundPoint()) {
            parser2[1].setPointvalue(c_point);
        }
        
        if(parser2[2].foundPoint()) {
            parser2[2].setPointvalue(c_point);
        }
        
        Complex c_bail = new Complex(genericBail, 0);
        if(parser[0].foundBail()) {
            parser[0].setBailvalue(c_bail);
        }
        
        if(parser[1].foundBail()) {
            parser[1].setBailvalue(c_bail);
        }
        
        if(parser2[0].foundBail()) {
            parser2[0].setBailvalue(c_bail);
        }
        
        if(parser2[1].foundBail()) {
            parser2[1].setBailvalue(c_bail);
        }
        
        if(parser2[2].foundBail()) {
            parser2[2].setBailvalue(c_bail);
        }
        
        
        if(parser[0].foundCbail()) {
            parser[0].setCbailvalue(c_bail);
        }
        
        if(parser[1].foundCbail()) {
            parser[1].setCbailvalue(c_bail);
        }
        
        if(parser2[0].foundCbail()) {
            parser2[0].setCbailvalue(c_bail);
        }
        
        if(parser2[1].foundCbail()) {
            parser2[1].setCbailvalue(c_bail);
        }
        
        if(parser2[2].foundCbail()) {
            parser2[2].setCbailvalue(c_bail);
        }

        this.max_iterations = max_iterations;
        InNotUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        /* LEFT */
        if(parser[0].foundZ()) {
            parser[0].setZvalue(((Complex)object[0]));
        }

        if(parser[0].foundC()) {
            parser[0].setCvalue(((Complex)object[3]));
        }

        if(parser[0].foundS()) {
            parser[0].setSvalue(((Complex)object[4]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(((Complex)object[1]));
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(((Complex)object[2]));
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, globalVars[i]);
            }
        }


        /* RIGHT */
        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[0]));
        }

        if(parser[1].foundC()) {
            parser[1].setCvalue(((Complex)object[3]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(((Complex)object[4]));
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(((Complex)object[1]));
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(((Complex)object[2]));
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, globalVars[i]);
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[0]));
            }

            if(parser2[0].foundC()) {
                parser2[0].setCvalue(((Complex)object[3]));
            }

            if(parser2[0].foundS()) {
                parser2[0].setSvalue(((Complex)object[4]));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(((Complex)object[1]));
            }

            if(parser2[0].foundPP()) {
                parser2[0].setPPvalue(((Complex)object[2]));
            }

            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[0].foundVar(i)) {
                    parser2[0].setVarsvalue(i, globalVars[i]);
                }
            }

            double result2 = expr2[0].getValue().getRe();
 
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
        
            return result2 < 0 ? result2 - max_iterations : result2 + max_iterations;        
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[0]));
            }

            if(parser2[1].foundC()) {
                parser2[1].setCvalue(((Complex)object[3]));
            }

            if(parser2[1].foundS()) {
                parser2[1].setSvalue(((Complex)object[4]));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(((Complex)object[1]));
            }

            if(parser2[1].foundPP()) {
                parser2[1].setPPvalue(((Complex)object[2]));
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[1].foundVar(i)) {
                    parser2[1].setVarsvalue(i, globalVars[i]);
                }
            }

           double result2 = expr2[1].getValue().getRe();
 
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
        
            return result2 < 0 ? result2 - max_iterations : result2 + max_iterations;   
        }
        else if(result == 0) { //left == right
            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[0]));
            }

            if(parser2[2].foundC()) {
                parser2[2].setCvalue(((Complex)object[3]));
            }

            if(parser2[2].foundS()) {
                parser2[2].setSvalue(((Complex)object[4]));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(((Complex)object[1]));
            }

            if(parser2[2].foundPP()) {
                parser2[2].setPPvalue(((Complex)object[2]));
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[2].foundVar(i)) {
                    parser2[2].setVarsvalue(i, globalVars[i]);
                }
            }

            double result2 = expr2[2].getValue().getRe();
 
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
        
            return result2 < 0 ? result2 - max_iterations : result2 + max_iterations;   
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;

    }

}
