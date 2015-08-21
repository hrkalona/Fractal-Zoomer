/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithm extends OutColorAlgorithm {
    protected ExpressionNode expr;
    protected Parser parser;
    protected double bailout;

    public UserOutColorAlgorithm(String outcoloring_formula, double bailout) {

        super();
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
        this.bailout = bailout;

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
            parser.setPvalue(((Complex)object[2]));
        }
        
        if(parser.foundBail()) {
            parser.setBailvalue(new Complex(bailout, 0));
        }

        return expr.getValue().getAbsRe() + 100800;
        
    }

    @Override
    public double getResult3D(Object[] object) {
        
        return getResult(object);
        
    }

}
