/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    private ExpressionNode expr;
    private Parser parser;
    private Complex cbound;
    private int bailout_test_comparison;

    public UserBailoutTest(double bound, String bailout_test_user_formula, int bailout_test_comparison) {

        super(bound);

        parser = new Parser();
        expr = parser.parse(bailout_test_user_formula);
        cbound = new Complex(bound, 0);
        this.bailout_test_comparison = bailout_test_comparison;

    }

    @Override
    public boolean escaped(Complex z) {

        parser.setZvalue(z);

        int res = expr.getValue().compare(cbound);

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
