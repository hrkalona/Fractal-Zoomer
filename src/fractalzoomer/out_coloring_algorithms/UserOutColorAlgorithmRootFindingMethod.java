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
public class UserOutColorAlgorithmRootFindingMethod extends OutColorAlgorithm {
    
    private ExpressionNode expr;
    private Parser parser;
    private double convergent_bailout;
    
    public UserOutColorAlgorithmRootFindingMethod(String outcoloring_formula, double convergent_bailout) {
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
        this.convergent_bailout = convergent_bailout;
        
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
            parser.setPvalue(((Complex)object[3]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[4]));
        }
        
        if(parser.foundCbail()) {
            parser.setCbailvalue(new Complex(convergent_bailout, 0));
        }

        return expr.getValue().getAbsRe() + 100800;
        
    }

    @Override
    public double getResult3D(Object[] object) {
        
        return getResult(object);
        
    }
    
}
