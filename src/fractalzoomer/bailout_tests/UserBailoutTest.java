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

package fractalzoomer.bailout_tests;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserBailoutTest extends BailoutTest {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private int bailout_test_comparison;
    private Complex cbound;

    public UserBailoutTest(double bound, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison) {

        super(bound);

        parser = new Parser[2];
        expr = new ExpressionNode[2];
        
        parser[0] = new Parser();
        parser[1] = new Parser();
        
        expr[0] = parser[0].parse(bailout_test_user_formula);
        expr[1] = parser[1].parse(bailout_test_user_formula2);
        
        this.bailout_test_comparison = bailout_test_comparison;
        
        cbound = new Complex(bound, 0);

    }

    @Override
    public boolean escaped(Complex z, Complex zold) {
        
        /*LEFT*/
        if(parser[0].foundZ()) {
            parser[0].setZvalue(z);
        }
        
        if(parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }
        
        if(parser[0].foundBail()) {
            parser[0].setBailvalue(cbound);
        }
        
        /*RIGHT*/
        if(parser[1].foundZ()) {
            parser[1].setZvalue(z);
        }
        
        if(parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }
        
        if(parser[1].foundBail()) {
            parser[1].setBailvalue(cbound);
        }

        int res = expr[0].getValue().compare(expr[1].getValue());

        switch (bailout_test_comparison) {
            case 0: //Escape if a > b    
                return res == -1 ? true : false;
            case 1: //Escape if a >= b
                return (res == -1 || res == 0) ? true : false;
            case 2: //Escape if a < b
                return res == 1 ? true : false;
            case 3: //Escape if a <= b
                return (res == 1 || res == 0) ? true : false;
            default:
                return false;
        }

    }
}
