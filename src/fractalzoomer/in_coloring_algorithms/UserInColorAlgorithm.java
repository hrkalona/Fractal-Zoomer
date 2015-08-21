/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public UserInColorAlgorithm(String incoloring_formula, int max_iterations) {
        
        super();
        
        parser = new Parser();
        expr = parser.parse(incoloring_formula);
        this.max_iterations = max_iterations;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[0]));
        }
        
        double temp = expr.getValue().getAbsRe();
        
        return temp == max_iterations ? max_iterations : temp + 100820;
        
    }
    
}
