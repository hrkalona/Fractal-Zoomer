/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.fractal_options;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class VariableInitialValue extends DefaultInitialValue {
    private ExpressionNode expr;
    private Parser parser;

    public VariableInitialValue(double re, double im, String initial_value_user_formula) {

        super(re, im);
        
        parser = new Parser();
        expr = parser.parse(initial_value_user_formula);

    }

    @Override
    public Complex getPixel(Complex pixel) {

        if(parser.foundC()) {
            parser.setCvalue(pixel);
        }

        return expr.getValue();

    }
}
