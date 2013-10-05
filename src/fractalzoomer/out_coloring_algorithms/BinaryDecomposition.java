package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;





/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class BinaryDecomposition extends OutColorAlgorithm {

    public BinaryDecomposition() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Complex)object[1]).getIm() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0] + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }

}
