/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmMagnet extends  UserOutColorAlgorithm {

    public UserOutColorAlgorithmMagnet(String outcoloring_formula, double bailout) {

        super(outcoloring_formula, bailout);

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
            parser.setPvalue(((Complex)object[4]));
        }
        
        if(parser.foundBail()) {
            parser.setBailvalue(new Complex(bailout, 0));
        }

        double temp = expr.getValue().getAbsRe();
        
        return (Boolean)object[2] ? temp + 100906  : temp + 100800;
        
    }

    @Override
    public double getResult3D(Object[] object) {
        
        return getResult(object);
        
    }

}
