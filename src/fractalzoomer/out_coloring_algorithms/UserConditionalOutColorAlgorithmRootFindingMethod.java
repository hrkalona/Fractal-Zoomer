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
public class UserConditionalOutColorAlgorithmRootFindingMethod extends OutColorAlgorithm {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private Complex c_convergent_bailout;
    private Complex c_max_iterations;
    private boolean[] user_outcoloring_special_color;

    public UserConditionalOutColorAlgorithmRootFindingMethod(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, boolean[] user_outcoloring_special_color, double convergent_bailout, int max_iterations) {

        super();

        parser = new Parser[user_outcoloring_conditions.length];
        expr = new ExpressionNode[user_outcoloring_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_outcoloring_conditions[i]);
        }

        parser2 = new Parser[user_outcoloring_condition_formula.length];
        expr2 = new ExpressionNode[user_outcoloring_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_outcoloring_condition_formula[i]);
        }

        c_convergent_bailout = new Complex(convergent_bailout, 0);
        
        this.user_outcoloring_special_color = user_outcoloring_special_color;
        
        if(parser[0].foundCbail()) {
            parser[0].setCbailvalue(c_convergent_bailout);
        }
        
        if(parser[1].foundCbail()) {
            parser[1].setCbailvalue(c_convergent_bailout);
        }
        
        if(parser2[0].foundCbail()) {
            parser2[0].setCbailvalue(c_convergent_bailout);
        }
        
        if(parser2[1].foundCbail()) {
            parser2[1].setCbailvalue(c_convergent_bailout);
        }
        
        if(parser2[2].foundCbail()) {
            parser2[2].setCbailvalue(c_convergent_bailout);
        }
        
        c_max_iterations = new Complex(max_iterations, 0);
        
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

    }

    @Override
    public double getResult(Object[] object) {

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(((Complex)object[1]));
        }
        
        if(parser[0].foundC()) {
            parser[0].setCvalue(((Complex)object[5]));
        }
        
        if(parser[0].foundS()) {
            parser[0].setSvalue(((Complex)object[6]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(((Complex)object[3]));
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(((Complex)object[4]));
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[1]));
        }
        
        if(parser[1].foundC()) {
            parser[1].setCvalue(((Complex)object[5]));
        }
        
        if(parser[1].foundS()) {
            parser[1].setSvalue(((Complex)object[6]));
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(((Complex)object[3]));
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(((Complex)object[4]));
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[1]));
            }
            
            if(parser2[0].foundC()) {
                parser2[0].setCvalue(((Complex)object[5]));
            }
            
            if(parser2[0].foundS()) {
                parser2[0].setSvalue(((Complex)object[6]));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(((Complex)object[3]));
            }

            if(parser2[0].foundPP()) {
                parser2[0].setPPvalue(((Complex)object[4]));
            }

            double num = expr2[0].getValue().getAbsRe() + MAGIC_OFFSET_NUMBER;
            return user_outcoloring_special_color[0] ? -num: num;
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[1]));
            }
            
            if(parser2[1].foundC()) {
                parser2[1].setCvalue(((Complex)object[5]));
            }
            
            if(parser2[1].foundS()) {
                parser2[1].setSvalue(((Complex)object[6]));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(((Complex)object[3]));
            }

            if(parser2[1].foundPP()) {
                parser2[1].setPPvalue(((Complex)object[4]));
            }

            double num = expr2[1].getValue().getAbsRe() + MAGIC_OFFSET_NUMBER;
            return user_outcoloring_special_color[1] ? -num: num;
        }
        else if (result == 0) { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[1]));
            }
            
            if(parser2[2].foundC()) {
                parser2[2].setCvalue(((Complex)object[5]));
            }
            
            if(parser2[2].foundS()) {
                parser2[2].setSvalue(((Complex)object[6]));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(((Complex)object[3]));
            }

            if(parser2[2].foundPP()) {
                parser2[2].setPPvalue(((Complex)object[4]));
            }

            double num = expr2[2].getValue().getAbsRe() + MAGIC_OFFSET_NUMBER;
            return user_outcoloring_special_color[2] ? -num: num;
        }
        
        return 0;
    }

}
