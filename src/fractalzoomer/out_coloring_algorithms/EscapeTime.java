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
public class EscapeTime extends OutColorAlgorithm {

    public EscapeTime() {
        super();
    }

    @Override
    public double getResult(Object[] object) {

        return (Integer)object[0] + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }

}
