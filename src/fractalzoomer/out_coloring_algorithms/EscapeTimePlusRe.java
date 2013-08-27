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
        
        return Math.abs(((Integer)object[0]) + ((Complex)object[1]).getRe() + 100800);

    }
    
}
