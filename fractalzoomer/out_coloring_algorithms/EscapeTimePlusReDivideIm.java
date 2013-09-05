/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusReDivideIm extends OutColorAlgorithm {

    public EscapeTimePlusReDivideIm() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {
        
        return Math.abs(((Integer)object[0]) + ((Complex)object[1]).getRe() / ((Complex)object[1]).getIm() + 100800);

    }

}
