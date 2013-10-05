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
public class BinaryDecompositionMagnet extends BinaryDecomposition {
    
    public BinaryDecompositionMagnet() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Boolean)object[2] ? (((Complex)object[1]).getIm() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0] + 100906) : ((Complex)object[1]).getIm() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0] + 100800);

    } 
    
}
