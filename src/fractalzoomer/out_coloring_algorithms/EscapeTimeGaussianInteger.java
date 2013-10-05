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
public class EscapeTimeGaussianInteger extends OutColorAlgorithm {
    
    public EscapeTimeGaussianInteger() {
        
        super();
        
    }
     
    @Override
    public double getResult(Object[] object) {
        
        return (Integer)object[0] + ((Complex)(object[1])).distance_squared(((Complex)(object[1])).gaussian_integer()) * 90 + 100800;
 
    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
