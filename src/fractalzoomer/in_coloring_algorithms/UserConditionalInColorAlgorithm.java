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
public class UserConditionalInColorAlgorithm extends InColorAlgorithm {
    
    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private int max_iterations;
    
    public UserConditionalInColorAlgorithm(String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, int max_iterations) {
        
        super();

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

        this.max_iterations = max_iterations;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        /* LEFT */
        if(parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(((Complex)object[0]));
        }


        /* RIGHT */
        if(parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[0]));
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundMaxn()) {
                parser2[0].setMaxnvalue(new Complex(max_iterations, 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[0]));
            }
            
            double temp = expr2[0].getValue().getAbsRe();

            return temp == max_iterations ? max_iterations : temp + 100820;
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundMaxn()) {
                parser2[1].setMaxnvalue(new Complex(max_iterations, 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[0]));
            }
            
            double temp = expr2[1].getValue().getAbsRe();

            return temp == max_iterations ? max_iterations : temp + 100820;
        }
        else { //left == right
            if(parser2[2].foundMaxn()) {
                parser2[2].setMaxnvalue(new Complex(max_iterations, 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[0]));
            }
            
            double temp = expr2[2].getValue().getAbsRe();

            return temp == max_iterations ? max_iterations : temp + 100820;
        }
        
    }
    
}
