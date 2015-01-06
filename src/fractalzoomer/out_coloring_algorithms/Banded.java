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
public class Banded extends OutColorAlgorithm {

    public Banded() {
        super();
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double temp = Math.log(((Complex)object[1]).norm_squared());
        
        temp = temp <= 0 ? 1e-33 : temp;
        
        double f = (Math.log(temp) / Math.log(2)) * 2.4;

        return (Integer)object[0] + Math.abs(f) + 100800;

    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
