package fractalzoomer.in_coloring_algorithms;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class MaximumIterations extends InColorAlgorithm {
    
    public MaximumIterations() { 
        super();
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return (Integer)object[0];
        
    }
    
}
