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
public class VariablePerturbation extends DefaultPerturbation {
    private ExpressionNode expr;
    private Parser parser;
    
    public VariablePerturbation(double re, double im, String perturbation_user_formula) {
    
        super(re, im);
        
        parser = new Parser();
        expr = parser.parse(perturbation_user_formula);
        
    }


    @Override
    public Complex getPixel(Complex pixel) {
        
        if(parser.foundC()) {
            parser.setCvalue(pixel);
        }

        return this.pixel.plus(expr.getValue());
        
    }
    
}
