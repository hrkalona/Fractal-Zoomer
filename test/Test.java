/*
 * This software and all files contained in it are distrubted under the MIT license.
 * 
 * Copyright (c) 2013 Cogito Learning Ltd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import fractalzoomer.core.Complex;
import fractalzoomer.parser.EvaluationException;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.parser.SetVariable;

/**
 * Test the Parser
 */
public class Test {

    /**
     * The main method to test the functionality of the parser
     */
    public static void main(String[] args) {

        Parser parser = new Parser();
        try {
            ExpressionNode expr = parser.parse("2i*(1+sin(pi/2))^2 -3.0i");
            ExpressionNode expr2 = parser.parse("--(+12/+3++4^+4/-2*+3/-2+-24/+3*+8/-2*+cos(0)+-(+10/-2-+3*-5))"); // 218 like wolfram
            ExpressionNode expr3 = parser.parse("12/3+4^4/(-2)*3/(-2)-24/3*8/(-2)*cos(0)-(10/(-2)-3*(-5))");
            ExpressionNode expr4 = parser.parse("--------2++++++-+++++i*----1");
            ExpressionNode expr5 = parser.parse("(0.6-3.33i) % (0.4+1.3i)");
            ExpressionNode expr6 = parser.parse("3%-4");
            ExpressionNode expr7 = parser.parse("inflect(2i*(1+sin(pi/2))^2 -3.0i, +12/+3++4^+4/-2*+3/-2+-24/+3*+8/-2*+cos(0)+-(+10/-2-+3*-5))");
            ExpressionNode expr8 = parser.parse("inflect(5i, 218)");
            ExpressionNode expr9 = parser.parse("f'(1/3*z^3, z)");
            ExpressionNode expr10 = parser.parse("f''(1/12*z^4, z)");
            ExpressionNode expr11 = parser.parse("z^2");
            ExpressionNode expr12 = parser.parse("z^3-1");
            ExpressionNode expr13 = parser.parse("z * 3");
            ExpressionNode expr14 = parser.parse("logn(z, c)");
            ExpressionNode expr15 = parser.parse("f'''(1/60*z^5, z)");
            
            expr9.accept(new SetVariable("z", new Complex(-4.3242342, 5.534543)));
            expr10.accept(new SetVariable("z", new Complex(-4.3242342, 5.534543)));
            expr11.accept(new SetVariable("z", new Complex(-4.3242342, 5.534543)));
            expr15.accept(new SetVariable("z", new Complex(-4.3242342, 5.534543)));

            expr12.accept(new SetVariable("z", new Complex(1, 0)));
            expr13.accept(new SetVariable("z", new Complex(1, 0)));
            expr14.accept(new SetVariable("z", new Complex(3, 0)));
            expr14.accept(new SetVariable("c", new Complex(6, 0)));

            
            Complex val1 = expr.getValue();
            Complex val2 = expr2.getValue();
            Complex val3 = expr3.getValue();
            Complex val4 = expr4.getValue();
            Complex val5 = expr5.getValue();
            Complex val6 = expr6.getValue();
            Complex val7 = expr7.getValue();
            Complex val8 = expr8.getValue();
            Complex val9 = expr9.getValue();
            Complex val10 = expr10.getValue();
            Complex val11 = expr11.getValue();
            Complex val12 = expr12.getValue();
            Complex val13 = expr13.getValue();
            Complex val14 = expr14.getValue();
            Complex val15 = expr15.getValue();
            
            System.out.println("The value of the expression is " + val1);
            System.out.println("The value of the expression is " + val2);
            System.out.println("The value of the expression is " + val3);
            System.out.println("The value of the expression is " + val4);
            System.out.println("The value of the expression is " + val5);
            System.out.println("The value of the expression is " + val6);
            System.out.println("The value of the expression is " + val7);
            System.out.println("The value of the expression is " + val8);
            System.out.println("The value of the expression is " + val9);
            System.out.println("The value of the expression is " + val10);
            System.out.println("The value of the expression is " + val11);
            System.out.println("The value of the expression is " + val12);
            System.out.println("The value of the expression is " + val13);
            System.out.println("The value of the expression is " + val14);
            System.out.println("The value of the expression is " + val15);

            if(val1.compare(new Complex(0.0, 5.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val2.compare(new Complex(218.0, 0.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val3.compare(new Complex(218.0, 0.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val4.compare(new Complex(2.0, -1.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val5.compare(new Complex(0.09999999999999998, -0.33000000000000007)) != 0) {
                throw new AssertionError();
            }
            
            if(val6.compare(new Complex(-1, 0.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val7.compare(new Complex(47717.0, -2180.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val8.compare(new Complex(47717.0, -2180.0)) != 0) {
                throw new AssertionError();
            }
            
            if(val9.distance(val10) > 1e-3) {
                throw new AssertionError();
            }
            
            if(val10.distance(val11) > 1e-3) {
                throw new AssertionError();
            }

            if(val11.distance(val15) > 1e-3) {
                throw new AssertionError();
            }
            
            if(val12.compare(new Complex()) != 0) {
                throw new AssertionError();
            }
            
            if(val13.compare(new Complex(3, 0)) != 0) {
                throw new AssertionError();
            }
            
            if(val14.compare(new Complex(0.6131471927654585, 0)) != 0) {
                throw new AssertionError();
            }

            System.out.println("Test Completed.");

        }
        catch(ParserException e) {
            System.out.println(e.getMessage());
        }
        catch(EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
