package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusRe extends OutColorAlgorithm {

    public EscapeTimePlusRe() {

        super();

    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[1]).getRe();
        double temp3 = ((Integer)object[0]) + temp + 100800;
        return Math.abs(temp3);

    }
    
}
