/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class Squares2 extends InColorAlgorithm {
    
    public Squares2() { 
        super();
    }
    
    @Override
    public double getResult(Object[] object) {
        
        double x = ((Complex)object[1]).getRe() * 16;
        double y = ((Complex)object[1]).getIm() * 16;
        
        double dx = Math.abs(x - Math.floor(x));
        double dy = Math.abs(y - Math.floor(y));
        
        return (dx < 0.5 && dy < 0.5) || (dx > 0.5 && dy > 0.5) ? (Integer)object[0] : 100920;
        
    }
    
}
