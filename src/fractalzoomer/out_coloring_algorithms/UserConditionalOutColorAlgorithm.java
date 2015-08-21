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
public class UserConditionalOutColorAlgorithm extends OutColorAlgorithm {

    protected ExpressionNode[] expr;
    protected Parser[] parser;
    protected ExpressionNode[] expr2;
    protected Parser[] parser2;
    protected double bailout;

    public UserConditionalOutColorAlgorithm(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double bailout) {

        super();

        parser = new Parser[user_outcoloring_conditions.length];
        expr = new ExpressionNode[user_outcoloring_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_outcoloring_conditions[i]);
        }

        parser2 = new Parser[user_outcoloring_condition_formula.length];
        expr2 = new ExpressionNode[user_outcoloring_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_outcoloring_condition_formula[i]);
        }

        this.bailout = bailout;

    }

    @Override
    public double getResult(Object[] object) {

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(((Complex)object[1]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(((Complex)object[2]));
        }

        if(parser[0].foundBail()) {
            parser[0].setBailvalue(new Complex(bailout, 0));
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[1]));
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(((Complex)object[2]));
        }

        if(parser[1].foundBail()) {
            parser[1].setBailvalue(new Complex(bailout, 0));
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[1]));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(((Complex)object[2]));
            }

            if(parser2[0].foundBail()) {
                parser2[0].setBailvalue(new Complex(bailout, 0));
            }

            return expr2[0].getValue().getAbsRe() + 100800;
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[1]));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(((Complex)object[2]));
            }

            if(parser2[1].foundBail()) {
                parser2[1].setBailvalue(new Complex(bailout, 0));
            }

            return expr2[1].getValue().getAbsRe() + 100800;
        }
        else { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[1]));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(((Complex)object[2]));
            }

            if(parser2[2].foundBail()) {
                parser2[2].setBailvalue(new Complex(bailout, 0));
            }

            return expr2[2].getValue().getAbsRe() + 100800;
        }

    }

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }

}
