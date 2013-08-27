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
public class EscapeTimeColorDecompositionConverge extends EscapeTimeColorDecomposition {
    
    public EscapeTimeColorDecompositionConverge() {
        super();    
    }

    @Override
    public double getResult(Object[] object) {

        double temp = Math.floor(1000 * ((Complex)object[1]).getRe() + 0.5) / 1000;
        double temp2 = Math.floor(1000 * ((Complex)object[1]).getIm() + 0.5) / 1000;

        return Math.abs(((Integer)object[0]) + (long)(((Math.atan2(temp2, temp) / (pi2)  + 0.75) * pi59)  + (temp * temp + temp2 * temp2) * 2.5) + 100800);

    }
    
}
