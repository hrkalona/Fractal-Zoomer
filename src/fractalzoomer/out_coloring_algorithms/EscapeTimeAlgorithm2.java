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
public class EscapeTimeAlgorithm2 extends OutColorAlgorithm {
    
    public EscapeTimeAlgorithm2() {
        
        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        Complex temp = ((Complex)object[1]).sub(((Complex)object[1]).sin());
        
        return (Integer)object[0] +  Math.abs(Math.atan(temp.getIm() / temp.getRe())) * 8 + 100800;      
                
    }
    
    @Override
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
}
